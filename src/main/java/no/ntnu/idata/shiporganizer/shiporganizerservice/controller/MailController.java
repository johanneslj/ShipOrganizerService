package no.ntnu.idata.shiporganizer.shiporganizerservice.controller;

import no.ntnu.idata.shiporganizer.shiporganizerservice.service.MailService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/mail")
public class MailController {
    private final MailService mailService;

    //TODO move these parts into userController, no mailController is necessary
    MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> sendCreateUserMail(HttpEntity<String> entity) {
        System.out.println(entity.getBody());
        return ResponseEntity.ok(mailService.sendEmail());
    }
}
