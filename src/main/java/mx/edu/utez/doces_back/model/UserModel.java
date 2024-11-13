package mx.edu.utez.doces_back.model;

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
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private String matricula;
    private String email;
    private String password;
    private Integer cuatrimestre;
    private Character grupo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleModel role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_requests", joinColumns = @JoinColumn(name = "fk_user"), inverseJoinColumns = @JoinColumn(name = "fk_document_request"))
    private Set<DocumentRequest> documentRequests = new HashSet<>();

}
