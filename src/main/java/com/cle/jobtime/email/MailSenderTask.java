package com.cle.jobtime.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;

public class MailSenderTask implements Runnable {

	private MimeMessage message;

    private JavaMailSender mailSender;
    
	public MailSenderTask() 
	{
	}
	public MailSenderTask(MimeMessage message, JavaMailSender mailSender) 
	{
		this.message=message;
		this.mailSender = mailSender;
	}

    public void run() {
    	mailSender.send(message );
    }
	
}
