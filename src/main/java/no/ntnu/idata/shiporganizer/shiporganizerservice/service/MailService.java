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

/**
 * Service used to send email to users
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Send mail to given email with the given verification code
     * @param email The users email
     * @param code the verification code
     */
    public void sendNewPasswordVerificationCode(String email, String code) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("post@maoyi.no");

        msg.setTo(email);
        msg.setSubject("Ship Organizer: Verification Code");
        msg.setText("Here is your verification code: " + code);

        javaMailSender.send(msg);
    }

    /**
     * Sends a mail to the given user to tell the user that the user is registered
     * @param email the new users email
     */
    public void sendRegisteredEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("post@maoyi.no");

        msg.setTo(email);
        msg.setSubject("Ship Organizer: You have been registered!");
        msg.setText("Your email has now been registered for the Ship Organizer app.\n" +
            "You can now set a new password to complete the registration.");

        javaMailSender.send(msg);
    }

    /**
     *  Sends the created PDF for all the products to order as an Email to a specific email
     * @param userEmail Email to the user who creates the report
     * @param recipients List of recipients to receive the email
     * @param fileToAdd Order list to add as an attachment
     */
    public void sendPdfEmail(String userEmail, String[] recipients, String fileToAdd){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            msg.setFrom("post@maoyi.no");
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
        } catch (MessagingException | MailException ex) {
            System.err.println(ex.getMessage());
        }
    }


}
