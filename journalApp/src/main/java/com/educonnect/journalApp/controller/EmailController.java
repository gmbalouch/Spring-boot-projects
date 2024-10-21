package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.educonnect.journalApp.schedular.UserScheduler;
import com.educonnect.journalApp.service.EmailService;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/mail")
@Slf4j
public class EmailController {
    // @Autowired
    // private UserScheduler userScheduler;

    @Autowired
    private EmailService emailService;

    @GetMapping("/health")
    public String health() {

        return "OK";
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> emailDetails) {
        String to = emailDetails.get("to");
        String subject = emailDetails.get("subject");
        String body = emailDetails.get("body");

        log.info("To " + to);
        if (to == null || subject == null || body == null) {
            return new ResponseEntity<>("Email details are incomplete", HttpStatus.BAD_REQUEST);
        }

        try {
            Boolean send = emailService.sendEmail(to, subject, body);
            return new ResponseEntity<>("Email sent successfully " + send, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending email: ", e);
            return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping
    // public ResponseEntity<?> sentSentiment() {

    // userScheduler.fectchUsersAndSendMail();

    // return new ResponseEntity<>("Successfully SendSentiment Mail",
    // HttpStatus.OK);
    // }

}
