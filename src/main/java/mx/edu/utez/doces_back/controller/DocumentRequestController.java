package mx.edu.utez.doces_back.controller;


import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.model.File;
import mx.edu.utez.doces_back.service.DocumentRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/documentRequest")
public class DocumentRequestController {
    private final DocumentRequestService documentRequestService;

    public DocumentRequestController(DocumentRequestService documentRequestService) {
        this.documentRequestService = documentRequestService;
    }

    @PostMapping("/{userId}/{documentName}")
    public ResponseEntity<ApiResponse> createDocumentRequest(
            @PathVariable Integer userId,
            @PathVariable String documentName,
            @RequestParam(value = "files", required = false) List<MultipartFile> files
    )
    {
        return documentRequestService.createDocumentRequest(userId, documentName, files);
    }

    @PutMapping("status/{documentRequestId}/{status}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Integer documentRequestId, @PathVariable String status) {
        return documentRequestService.updateStatus(documentRequestId, status);

    }

    @PutMapping("priority/{documentRequestId}/{priority}")
    public ResponseEntity<ApiResponse> updatePriority(@PathVariable Integer documentRequestId, @PathVariable String priority) {
        return documentRequestService.updatePriority(documentRequestId, priority);

    }

    @DeleteMapping("/{documentRequestId}")
    public ResponseEntity<ApiResponse> deleteDocumentRequest(@PathVariable Integer documentRequestId) {
        return documentRequestService.deleteDocumentRequest(documentRequestId);
    }

    @GetMapping("/")
    public ResponseEntity<List<DocumentRequest>> getAllDocumentRequests() {
        List<DocumentRequest> documentRequests = documentRequestService.findAll();
        return new ResponseEntity<>(documentRequests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentRequest> getDocumentRequestById(@PathVariable Integer id) {
        return documentRequestService.findById(id);
    }

    @PutMapping("admin/{documentRequestId}/{adminId}")
    public ResponseEntity<ApiResponse> updateAdminId(@PathVariable Integer documentRequestId, @PathVariable Integer adminId) {
        return documentRequestService.updateAdminId(documentRequestId, adminId);
    }


}
