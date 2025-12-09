package com.jdc.clinica.services.Implemets;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Base64;

@Service
public class MailServiceImplement {

    private final static String MY_EMAIL = "no-reply@pruebatecnica.co.za";

    @Autowired
    private AmazonSimpleEmailService client;

    public void sendPdfEmail(byte[] pdfBytes, String toEmail) throws Exception {

        String boundary = "NextPart";
        String from = MY_EMAIL;
        String to = toEmail;
        String subject = "Reporte PDF de Inventario";

        // Construir mensaje raw MIME
        StringBuilder rawMail = new StringBuilder();
        rawMail.append("From: ").append(from).append("\n");
        rawMail.append("To: ").append(to).append("\n");
        rawMail.append("Subject: ").append(subject).append("\n");
        rawMail.append("MIME-Version: 1.0").append("\n");
        rawMail.append("Content-Type: multipart/mixed; boundary=\"").append(boundary).append("\"\n\n");

        // Parte del cuerpo (texto simple)
        rawMail.append("--").append(boundary).append("\n");
        rawMail.append("Content-Type: text/plain; charset=UTF-8\n\n");
        rawMail.append("Adjunto encontrarás el reporte de inventario.\n\n");

        // Parte del adjunto (PDF)
        rawMail.append("--").append(boundary).append("\n");
        rawMail.append("Content-Type: application/pdf; name=\"inventario.pdf\"\n");
        rawMail.append("Content-Disposition: attachment; filename=\"inventario.pdf\"\n");
        rawMail.append("Content-Transfer-Encoding: base64\n\n");

        // Adjuntar el pdf codificado a base64
        rawMail.append(Base64.getEncoder().encodeToString(pdfBytes)).append("\n\n");

        rawMail.append("--").append(boundary).append("--");

        RawMessage rawMessage = new RawMessage()
                .withData(ByteBuffer.wrap(rawMail.toString().getBytes("UTF-8")));

        SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest()
                .withSource(from)
                .withDestinations(to)
                .withRawMessage(rawMessage);

        SendRawEmailResult result = client.sendRawEmail(rawEmailRequest);
        System.out.println("📨 Email enviado con ID: " + result.getMessageId());
    }
}
