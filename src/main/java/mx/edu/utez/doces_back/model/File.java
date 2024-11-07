package mx.edu.utez.doces_back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @Lob
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "fk_document_request")
    private DocumentRequest documentRequest;


}