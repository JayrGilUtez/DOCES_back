package mx.edu.utez.doces_back.service;

import jakarta.transaction.Transactional;
import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.repository.IDocumentRequestRepository;
import mx.edu.utez.doces_back.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentRequestService  {
    private final IDocumentRequestRepository documentRequestRepository;
    private final IUserRepository userRepository;

    public DocumentRequestService(IDocumentRequestRepository documentRequestRepository, IUserRepository userRepository) {
        this.documentRequestRepository = documentRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> createDocumentRequest(Integer userId) {
        try {
            UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            DocumentRequest documentRequest = new DocumentRequest();
            documentRequest.setUser_id(userId);
            documentRequestRepository.save(documentRequest);
            ApiResponse response = new ApiResponse(documentRequest, HttpStatus.CREATED, "Document request created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Failed to create document request");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Optional<DocumentRequest> findById(Integer id) {
        return documentRequestRepository.findById(id);
    }
}
