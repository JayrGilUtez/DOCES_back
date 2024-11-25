package mx.edu.utez.doces_back.service;

import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.model.File;
import mx.edu.utez.doces_back.repository.IFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "File upload failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse> findById (Integer id) {
        return new ResponseEntity<>(new ApiResponse(repository.findById(id), HttpStatus.OK), HttpStatus.OK);
    }



}
