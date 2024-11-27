package mx.edu.utez.doces_back.controller;



import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.service.DocumentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/documentRequest")
public class DocumentRequestController {
    private final DocumentRequestService documentRequestService;

    public DocumentRequestController(DocumentRequestService documentRequestService) {
        this.documentRequestService = documentRequestService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse> createDocumentRequest(@PathVariable Integer userId) {
        return documentRequestService.createDocumentRequest(userId);
    }


}
