package com.contacts.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ExtraConfig {

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(25);

        mailSender.setUsername("dharmikthanki70@gmail.com");
        mailSender.setPassword("jofvevonqrufxrxg");

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage eMailTemplate(){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("nadeem@gmail.com");
        message.setFrom("dharmikthanki70@gmail.com");
        message.setText("Hey This Mail is just for Practice ");

        return message;
    }
}
