package mx.edu.utez.doces_back.repository;

import mx.edu.utez.doces_back.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileRepository extends JpaRepository<File, Integer> {

    List<File> findAllByDocumentRequestId(Integer documentRequestId);
}
