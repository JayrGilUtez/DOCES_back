package mx.edu.utez.doces_back.service;

import jakarta.transaction.Transactional;
import mx.edu.utez.doces_back.model.DocumentRequest;
import mx.edu.utez.doces_back.repository.IDocumentRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentRequestService  {
    private final IDocumentRequestRepository repository;

    public DocumentRequestService(IDocumentRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<DocumentRequest> findById(Integer id) {
        return repository.findById(id);
    }
}
