package mx.edu.utez.doces_back.service;

import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.model.File;
import mx.edu.utez.doces_back.repository.IFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final IFileRepository repository;

    public FileService(IFileRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<ApiResponse> save(MultipartFile file) {
        try {
            File newFile = new File();
            newFile.setName(file.getOriginalFilename());
            newFile.setType(file.getContentType());
            newFile.setData(file.getBytes());
            return new ResponseEntity<>(new ApiResponse(repository.save(newFile), HttpStatus.OK), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Fallo al subir archivo"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> saveMultipleFiles(List<MultipartFile> files, Integer documentRequestId) {
        if (files == null || files.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(
                    HttpStatus.BAD_REQUEST,
                    true,
                    "No files provided"
            ),HttpStatus.BAD_REQUEST);
        }
        try {
            List<File> savedFiles = files.stream().map(file -> {
                if (file.isEmpty()) {
                    throw new RuntimeException("One of the files is empty");
                }
                File newFile = new File();
                try {
                    newFile.setName(file.getOriginalFilename());
                    newFile.setType(file.getContentType());
                    newFile.setData(file.getBytes());
                    DocumentRequest documentRequest = new DocumentRequest();
                    documentRequest.setId(documentRequestId);
                    newFile.setDocumentRequest(documentRequest);
                } catch (IOException e) {
                    throw new RuntimeException("Fallo al cargar el archivo", e);
                }
                return repository.save(newFile);
            }).collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponse(savedFiles, HttpStatus.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    true,
                    "Fallo al cargar multiples archivos"
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> findById (Integer id) {
        return new ResponseEntity<>(new ApiResponse(repository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> findAllByDocumentRequestId(Integer documentRequestId) {
        List<File> files = repository.findAllByDocumentRequestId(documentRequestId);
        return new ResponseEntity<>(new ApiResponse(files, HttpStatus.OK), HttpStatus.OK);
    }

}
