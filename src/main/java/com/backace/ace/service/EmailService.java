package com.backace.ace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String asunto, String mensajeHtml) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensajeHtml, true); // El 'true' indica que el contenido es HTML

            mailSender.send(mimeMessage);
            System.out.println("Correo HTML enviado a: " + destinatario);
        } catch (MessagingException e) {
            System.err.println("Error enviando el correo HTML: " + e.getMessage());
        }
    }
}
