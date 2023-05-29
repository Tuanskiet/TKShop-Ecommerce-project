package com.poly.TKShop.utils;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class SendEmail {

    @Autowired
    public  JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String FORM_EMAIL;
    final String SUBJECT = "hi hi";
    final String BODY_HTML = "$tokenValue";
    final String BODY_TEXT = "";

    public void sendMailHtml(String toEmail, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom( new InternetAddress(FORM_EMAIL));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, false));
        mimeMessage.setSubject(SUBJECT);
        mimeMessage.setContent(body, "text/html; charset=utf-8");

        javaMailSender.send(mimeMessage);
    }

    public void sendMail(String toEmail, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(SUBJECT);
        mimeMessageHelper.setText(body);

        javaMailSender.send(mimeMessage);
    }

    public void sendVerifyEmail( String toEmail, String token) throws MessagingException {
        String htmlBodyWithToken = BODY_HTML.replace("$tokenValue", token);
        this.sendMailHtml(toEmail, htmlBodyWithToken);
    }

    public void resetPasswordWithToken( String toEmail, String token) throws MessagingException {
        String htmlBodyWithToken = BODY_HTML.replace("$tokenValue", token);
        this.sendMailHtml(toEmail, htmlBodyWithToken);
    }
}
