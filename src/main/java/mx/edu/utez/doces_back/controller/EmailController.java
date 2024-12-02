package mx.edu.utez.doces_back.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.service.email.EmailService;
import mx.edu.utez.doces_back.utils.Utilities;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static mx.edu.utez.doces_back.service.email.EmailService.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@MultipartConfig
public class EmailController {

    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse> senderEmail(@RequestParam("toEmail") String email,
                                                   @RequestParam("subject") String subject,
                                                   @RequestParam("title") String title,
                                                   @RequestParam("messageContent") String messageContent,
                                                   @RequestParam("file") MultipartFile file
                                                   ) throws MessagingException {
        return emailService
                .sendEmail(email, subject, title, messageContent, file);
    }

    @PostMapping("/documentRequest/sendEmail")
    public ResponseEntity<ResponseEntity<Object>> sendSimpleEmail(
            @RequestParam("toEmail") String toEmail,
            @RequestParam("title") String title,
            @RequestParam("subject") String subject,
            @RequestParam("messageContent") String messageContent) {
        try {
            emailService.sendSimpleEmail(toEmail, title, subject, messageContent);
            return new ResponseEntity<>(Utilities.generateResponse(HttpStatus.OK, "Correo enviado correctamente"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}