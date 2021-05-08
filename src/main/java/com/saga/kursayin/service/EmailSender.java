package com.saga.kursayin.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Service
public class EmailSender {

    public void sendEmail(String to, String html,String text){

        String from = "poghosyan.gurgen.99@gmail.com";

        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "cnjkkoeglwfacvsj");

            }

        });
        session.setDebug(true);

        try {
            InternetHeaders headers = new InternetHeaders();
            headers.addHeader("Content-type", "text/html; charset=UTF-8");
            MimeBodyPart body = new MimeBodyPart(headers, html.getBytes("UTF-8"));

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setContent(multipart);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(text);

            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException | UnsupportedEncodingException mex) {
            mex.printStackTrace();
        }
    }

}
