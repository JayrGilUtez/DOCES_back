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

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRequestService {
    private final IDocumentRequestRepository documentRequestRepository;
    private final IUserRepository userRepository;

    public DocumentRequestService(IDocumentRequestRepository documentRequestRepository, IUserRepository userRepository) {
        this.documentRequestRepository = documentRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> createDocumentRequest(Integer userId, String documentName) {
        try {
            DocumentRequest documentRequest = new DocumentRequest();
            documentRequest.setUser_id(userId);
            documentRequest.setDocumentName(documentName);
            documentRequest.setStatus("Pendiente");
            documentRequestRepository.save(documentRequest);
            ApiResponse response = new ApiResponse(
                    documentRequest,
                    HttpStatus.CREATED,
                    "Solicitud creada"
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Error al crear solicitud"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> updateStatus(Integer documentRequestId, String status) {
        try {
            Optional<DocumentRequest> optional = documentRequestRepository.findById(documentRequestId);
            DocumentRequest documentRequest = optional.orElse(null);
            if (documentRequest != null) {
                documentRequest.setStatus(status);
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        false,
                        "Se actualizo el status de la solicutd");
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "Solicitud no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Fallo al actualizar el estatus de la solicitud");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> updatePriority(Integer documentRequestId, String priority) {
        try {
            Optional<DocumentRequest> optional = documentRequestRepository.findById(documentRequestId);
            DocumentRequest documentRequest = optional.orElse(null);
            if (documentRequest != null) {
                documentRequest.setPriority(priority);
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        false,
                        "Se actualizo la prioridad de la solicutd");
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "Solicitud no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Fallo al actualizar la prioridad de la solicitud");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<ApiResponse> deleteDocumentRequest(Integer documentRequestId) {
        try {
            Optional<DocumentRequest> optional = documentRequestRepository.findById(documentRequestId);
            if (optional.isPresent()) {
                documentRequestRepository.deleteById(documentRequestId);
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        false,
                        "Solicitud eliminada exitosamente"
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "Solicitud no encontrada"
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Fallo al eliminar la solicitud"
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public List<DocumentRequest> findAll() {
        return documentRequestRepository.findAll();
    }

    public ResponseEntity<DocumentRequest> findById(Integer id) {
        Optional<DocumentRequest> documentRequest = documentRequestRepository.findById(id);
        if (documentRequest.isPresent()) {
            return new ResponseEntity<>(documentRequest.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    public ResponseEntity<ApiResponse> updateAdminId(Integer documentRequestId, Integer adminId) {
        try {
            Optional<DocumentRequest> optional = documentRequestRepository.findById(documentRequestId);
            DocumentRequest documentRequest = optional.orElse(null);
            if (documentRequest != null) {
                documentRequest.setAdmin_id(adminId);
                documentRequestRepository.save(documentRequest);
                ApiResponse response = new ApiResponse(
                        HttpStatus.OK,
                        false,
                        "Admin ID updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse response = new ApiResponse(
                        HttpStatus.NOT_FOUND,
                        true,
                        "DocumentRequest not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Failed to update Admin ID");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}