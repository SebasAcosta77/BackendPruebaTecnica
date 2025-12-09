package com.jdc.clinica.rest;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.jdc.clinica.services.Implemets.MailServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
public class MailController {
    @Autowired
    private MailServiceImplement mailServiceImplement;

    @GetMapping("/hello")
    void sendHello() {
        return;
    }
}
