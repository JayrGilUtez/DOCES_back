package mx.edu.utez.doces_back.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import mx.edu.utez.doces_back.config.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailService implements Email_Service_Interface {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public ResponseEntity<ApiResponse> sendEmail(String toEmail, String subject, String title, String messageContent, int type, MultipartFile file, String name) throws MessagingException {
       try {
           // Crear contexto de Thymeleaf
           Context context = new Context();
           context.setVariable("title", title);
           context.setVariable("message", messageContent);
           context.setVariable("name", name);


           String[] plantillaAlerta = new String[] {"alerta", "descarga", "verificacion"};

           // Procesar la plantilla y generar el contenido HTML
           String htmlContent = templateEngine.process(plantillaAlerta[type], context);

           // Crear el mensaje MIME
           MimeMessage message = javaMailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true);
           helper.setTo(toEmail);
           helper.setSubject(subject);
           helper.setText(htmlContent, true); // Indicar que el texto es HTML
           helper.setFrom("utezdoces@gmail.com");  // Asegúrate de colocar aquí el correo del remitente

           helper.addAttachment(file.getOriginalFilename(), file);
           context.setVariable("fileCid", "attachment-" + file.getOriginalFilename());


           // Enviar el correo
           javaMailSender.send(message);
           System.out.println("Correo HTML enviado con éxito a " + toEmail);

           return new ResponseEntity<>(new ApiResponse(HttpStatus.OK,false,"El email se envio correctamente"),HttpStatus.OK) ;

       }catch (Exception e){

           System.out.println(e);
           return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST,true,"No se envio el email"),HttpStatus.OK) ;

       }

    }


}
