package com.jdc.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ClinicaApplication {
    private static final Logger log = LoggerFactory.getLogger(ClinicaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
        log.info(" Aplicación iniciada correctamente.");
    }

}
