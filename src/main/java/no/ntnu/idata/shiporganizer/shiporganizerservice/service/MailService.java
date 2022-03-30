package no.ntnu.idata.shiporganizer.shiporganizerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

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

    /// Sends the created PDF for all the products to order as an Email to a specific email
    public void sendPdfEmail(String userEmail, String[] recipients, String fileToAdd) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            FileSystemResource file = new FileSystemResource(new File(fileToAdd));
            MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(userEmail);
            helper.setCc(recipients);
            helper.setSubject("Ship Organizer: PDF for orders");
            helper.setText("There has been a order list sent from the Ship Organizer app.\n" +
                    "Please see attachment");
            helper.addAttachment("Order.pdf", file);

            javaMailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }



}
