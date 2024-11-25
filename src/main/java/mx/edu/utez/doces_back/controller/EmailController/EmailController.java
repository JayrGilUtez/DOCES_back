package mx.edu.utez.doces_back.controller.EmailController;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.service.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/email")
@CrossOrigin(origins = {"*"})
@AllArgsConstructor

public class EmailController  {

    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse> senderEmail(@RequestParam("toEmail")String email,@RequestParam("subject")String subject, @RequestParam("title")String title,@RequestParam("messageContent")String messageContent) throws MessagingException {

            return emailService.sendEmail(email,subject,title,messageContent);

    }


    }
