package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public String sendEmail() {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("hansal@stud.ntnu.no");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        //javaMailSender.send(msg);

        return "ok";
    }
}
