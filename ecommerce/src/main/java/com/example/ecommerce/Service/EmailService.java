package com.example.ecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    static private final String fromemail="ecommerceweb4457@gmail.com";

    public void notifyUser(String email, String message)
    {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(email);
            mail.setFrom(fromemail);
            mail.setSubject("Welcoming Mail");
            mail.setText(message);
            javaMailSender.send(mail);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
