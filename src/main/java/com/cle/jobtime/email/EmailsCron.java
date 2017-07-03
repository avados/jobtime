package com.cle.jobtime.email;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cle.jobtime.model.JobDone;
import com.cle.jobtime.service.MainService;

@EnableScheduling
@Component
public class EmailsCron {

	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

	private static final Logger logger = LoggerFactory.getLogger(EmailsCron.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@Autowired
	private TaskExecutor taskExecutor;
	
	@Autowired
	MainService mainService;

	// to test every minute
//	 @Scheduled(cron = "0 0/10 * * * ?")

	// every weekday at 20h,
	// second minute hour day month weekday

	//@Scheduled(fixedRate = 60 * 1000, initialDelay = 1000)
	@Scheduled(cron = "0 0 20 * * MON-FRI")
	public void reportCurrentTime()
	{

		logger.info("Starting Cron Email Job at: " + timeFormat.format(new Date()));
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		try
		{

			MimeMessage email = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(email, true);
			final String subject = "Suivi quotidien";

			helper.setSubject(subject);

			String emailText = getEmailText();

			helper.setFrom(env.getProperty("support.email"));

			try
			{
				helper.setTo(env.getProperty("email.perso"));

				helper.setText(emailText, true);

				taskExecutor.execute(new MailSenderTask(email, mailSender));
			}
			catch (Exception e)
			{
				logger.info("Invalid email: ", e);
				logger.error("Invalid email: ", e);
			}

		}
		catch (Exception e)
		{
			logger.info("Error when sending follow up email", e);
			logger.error("Error when sending follow up email", e);
		}
		logger.info("Stoping Cron Email Job at: " + timeFormat.format(new Date()));
	}

	private String getEmailText()
	{
		StringBuilder page = new StringBuilder();
		page.append("<html><head><meta charset=\"UTF-8\"></head><body>");
		
		Calendar cal = Calendar.getInstance();
		
		for (Object[] daySum : mainService.getSumByDaySince(getFirstDayOfWeekDate()))
		{
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = formatter.format((Date) daySum[0]);
			
			page.append("Le "+formattedDate+" : "+(double)daySum[1]);
			page.append("<p>" );
			
		}
		page.append("</body></html>");

		return page.toString();
	}
	
	private static Date getFirstDayOfWeekDate() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_WEEK,
	            cal.getActualMinimum(Calendar.DAY_OF_WEEK));
	    Date now = new Date();
	    cal.setTime(now);
	    int week = cal.get(Calendar.DAY_OF_WEEK);
	    return new Date(now.getTime() - 24 * 60 * 60 * 1000 * (week - 1));
	}

}
