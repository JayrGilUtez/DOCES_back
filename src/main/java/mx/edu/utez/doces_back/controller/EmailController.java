package mx.edu.utez.doces_back.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.annotation.MultipartConfig;
import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.dto.IncorrectInfoDTO;
import mx.edu.utez.doces_back.service.EmailService;
import mx.edu.utez.doces_back.utils.Utilities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@MultipartConfig
public class EmailController {

    private final EmailService emailService;
    private static final String INTERNAL_SERVER_ERROR = "An internal server error occurred.";

    EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse> senderEmail(@RequestParam("toEmail") String email,
            @RequestParam("subject") String subject,
            @RequestParam("title") String title,
            @RequestParam("messageContent") String messageContent,
            @RequestParam("type") int type,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) throws MessagingException {
        return emailService
                .sendEmail(email, subject, title, messageContent, type, file, name);
    }

    @PostMapping("/incorrect-info")
    public ResponseEntity<Object> incorrectInfo(@RequestBody IncorrectInfoDTO request) {
        String errorMessage = request.getErrorMessage();
        String userEmail = request.getUserEmail();
        try {
            emailService.sendErrorNotification(userEmail, errorMessage);
            return Utilities.generateResponse(HttpStatus.OK, "Se envió el correo correctamente");
        } catch (Exception e) {
            return Utilities.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
        }
    }
}