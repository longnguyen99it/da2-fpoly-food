package fpoly.websitefpoly.service.impl;

import fpoly.websitefpoly.service.SendEmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender emailSender;

    public SendEmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    @Override
    public void sendEmail(String email, String title, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(body);
        this.emailSender.send(message);
    }
}