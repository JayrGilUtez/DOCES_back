package mx.edu.utez.doces_back.repository;

import mx.edu.utez.doces_back.model.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDocumentRequestRepository extends JpaRepository<DocumentRequest, Integer> {
    List<DocumentRequest> findAllByUser_id(Integer userId);
}
