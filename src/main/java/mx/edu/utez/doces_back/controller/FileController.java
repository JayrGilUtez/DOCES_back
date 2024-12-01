package mx.edu.utez.doces_back.controller;

import mx.edu.utez.doces_back.config.ApiResponse;
import mx.edu.utez.doces_back.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files, @RequestParam("documentRequestId") Integer documentRequestId) {
        try {
            return fileService.saveMultipleFiles(files, documentRequestId);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            true,
                            "Error al subir los archivos"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return fileService.save(file);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            true,
                            "Error al subir el archivo"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> downloadFile(@PathVariable Integer id) {
        return fileService.findById(id);

    }

    @GetMapping("/documentRequest/{documentRequestId}")
    public ResponseEntity<ApiResponse> getFilesByDocumentRequestId(@PathVariable Integer documentRequestId) {
        return fileService.findAllByDocumentRequestId(documentRequestId);
    }



}
