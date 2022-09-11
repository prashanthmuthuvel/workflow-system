package com.workflow.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.workflow.constant.WorkflowConstant;
import com.workflow.model.User;

public class Email {
	
	public static void send(User user) {

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(WorkflowConstant.EMAIL_USERNAME, WorkflowConstant.PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(WorkflowConstant.EMAIL_USERNAME));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmailId())
            );
            message.setSubject("Your Action is Needed");
            message.setText("Dear " + user.getFirstName() + ","
            		+ "\n\n A ticket has been added to your queue.");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

		
	}

}
