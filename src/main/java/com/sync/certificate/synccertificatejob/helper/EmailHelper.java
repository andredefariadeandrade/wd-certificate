package com.sync.certificate.synccertificatejob.helper;

import com.sync.certificate.synccertificatejob.config.ExternalConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailHelper {

    private final ExternalConfiguration externalConfiguration;
    private final String recipientEmail;
    private final String subject;
    private final String message;

    public EmailHelper(ExternalConfiguration externalConfiguration, String recipientEmail,
                       String subject, String message) {
        this.externalConfiguration = externalConfiguration;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.message = message;
    }

    public void sendEmail() throws MessagingException {
        final String email = this.externalConfiguration.getEmailAddress();
        final String password = this.externalConfiguration.getEmailPassword();

        Properties props = this.createProperties();
        Session session = this.createSession(props, email, password);

        Message message = this.configureMessage(session, email, this.recipientEmail, this.subject, this.message);
        Transport.send(message);
    }

    private Properties createProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Session createSession(final Properties props, final String userName, final String password){
        return Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });
    }

    private Message configureMessage(final Session session, final String proximoImovelEmail,
                                     final String recipientEmail, final String subject,
                                     final String emailMessage) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(proximoImovelEmail));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail));
        message.setSubject(subject);
        message.setText(emailMessage);
        return message;
    }



}
