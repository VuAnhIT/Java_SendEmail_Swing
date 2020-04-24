package com.theanh.business;


import com.theanh.entity.MailMessage;
import com.theanh.entity.SMTPServer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * Created by Vu The Anh  - vutheanhit@gmail.com
 * On 07/05/2019
 * This is the class that handle the logic to create email session and send email
 */

public class MyMail {

    public Session getMailSession(SMTPServer smtpServer, final String from, final String password) {
        Properties props = new Properties();

        // sets properties
        props.put("mail.smtp.host", smtpServer.getServer());
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", smtpServer.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");

        //Get the Session object
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    // Method to send a MailMessage
    public boolean sendMail(MailMessage mailMessage, Session session, File[] attachFiles) throws Exception {
        // Create a default MineMessage object
        Message message = new MimeMessage(session);

        // Set From: The From input by user
        message.setFrom(new InternetAddress(mailMessage.getFrom()));

        // Set To: The To input by user
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(mailMessage.getTo()));

        // Set Subject
        message.setSubject(mailMessage.getSubject());

        // Create message part and add msg text
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mailMessage.getMessage(), "text/html");

        // Create multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Add attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (File aFile : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
                attachPart.attachFile(aFile);
                multipart.addBodyPart(attachPart);
            }
        }

        // Sets the multi-part as e-mail's content
        message.setContent(multipart);

        // Start to send the message
        Transport.send(message);
        return true;
    }


}
