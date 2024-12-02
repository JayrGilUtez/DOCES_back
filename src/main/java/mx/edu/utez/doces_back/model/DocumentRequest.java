package mx.edu.utez.doces_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "document_requests")
public class DocumentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String documentName;
    private Integer admin_id;
    private String status;
    private String priority;
    private Integer id_formato_de_certificacion_de_estudios;
    private Integer id_recibo_de_pago;
    private Integer id_constancia_de_no_adeudo;
    private Integer id_carta_libereacion_de_estadias;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    @OneToMany(mappedBy = "documentRequest")
    private Set<File> files = new HashSet<>();

}
