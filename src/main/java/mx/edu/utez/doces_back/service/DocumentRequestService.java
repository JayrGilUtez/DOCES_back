package mx.edu.utez.doces_back.service;

import jakarta.transaction.Transactional;
import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.model.File;
import mx.edu.utez.doces_back.model.UserModel;
import mx.edu.utez.doces_back.repository.IDocumentRequestRepository;
import mx.edu.utez.doces_back.repository.IFileRepository;
import mx.edu.utez.doces_back.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DocumentRequestService {
    private final IDocumentRequestRepository documentRequestRepository;
    private final IUserRepository userRepository;
    private final IFileRepository fileRepository;

    public DocumentRequestService(IDocumentRequestRepository documentRequestRepository, IUserRepository userRepository, IFileRepository fileRepository) {
        this.documentRequestRepository = documentRequestRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public ResponseEntity<ApiResponse> createDocumentRequest(Integer userId, String documentName, List<MultipartFile> files) {
        try {
            DocumentRequest documentRequest = new DocumentRequest();
            documentRequest.setUser_id(userId);
            documentRequest.setDocumentName(documentName);
            DocumentRequest savedDocumentRequest = documentRequestRepository.save(documentRequest);

            List<File> savedFiles = files.stream().map(file -> {
                File newFile = new File();
                try {
                    newFile.setName(file.getOriginalFilename());
                    newFile.setType(file.getContentType());
                    newFile.setData(file.getBytes());
                    newFile.setDocumentRequest(savedDocumentRequest);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file", e);
                }
                return fileRepository.save(newFile);
            }).collect(Collectors.toList());

            savedDocumentRequest.setFiles(new HashSet<>(savedFiles));
            documentRequestRepository.save(savedDocumentRequest);

            return new ResponseEntity<>(new ApiResponse(savedDocumentRequest, HttpStatus.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Failed to create document request"), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public Optional<DocumentRequest> findById(Integer id) {
        return documentRequestRepository.findById(id);
    }
}
