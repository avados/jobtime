package com.cle.jobtime.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration 
public class MailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //TODO read from ENV settings
        final String username = env.getProperty("support.email");//"noreply@mapytrip.com";     // email address by which registered on zoho (another domain based)
		final String password = env.getProperty("support.pwd");//"%jBpRfMQV3*&UT6R";
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
		Properties props = new Properties();
		
		props.setProperty("mail.smtp.host", "smtp.zoho.com");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.debug.auth", "true");
		props.setProperty( "mail.pop3.socketFactory.fallback", "false");

		return props;
    }
}
