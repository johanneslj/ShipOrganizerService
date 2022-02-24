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

    public void sendNewPasswordVerificationCode(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject("Ship Organizer: Verification Code");
        msg.setText("Here is your verification code: " + code);

        javaMailSender.send(msg);
    }

    public void sendRegisteredEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setSubject("Ship Organizer: You have been registered!");
        msg.setText("Your email has now been registered for the Ship Organizer app.\n" +
            "You can now set a new password to complete the registration.");

        javaMailSender.send(msg);
    }
}
