/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Souhail
 */
public class EmailSender {

    public static void sendEmailWithAttachments(String toAddress, String subject, String message)
            throws AddressException, MessagingException {

        String host = "smtp.gmail.com";
        String port = "587";
        final String userName = "hassan.makhloufi@esprit.tn";
        final String password = "0ruP3nkI";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html ");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        msg.setContent(multipart);
        msg.saveChanges();

        Transport.send(msg);

    }

//    public static void main(String[] args) {
//        // SMTP info
//        // message info
//        String mailTo = "crinnxx@gmail.com";
//        String subject = "test";
//        String message = "<h1> test</h1>";
//        try {
//            sendEmailWithAttachments(mailTo,
//                    subject, message);
//            System.out.println("Email sent.");
//        } catch (Exception ex) {
//            System.out.println("Could not send email.");
//            ex.printStackTrace();
//        }
//    }
}
