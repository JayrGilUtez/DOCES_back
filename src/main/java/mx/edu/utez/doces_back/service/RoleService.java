package mx.edu.utez.doces_back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mx.edu.utez.doces_back.model.RoleModel;
import mx.edu.utez.doces_back.repository.IRoleRepository;

@Service
@Primary
@Transactional
public class RoleService {

    private final IRoleRepository repository;

    RoleService(IRoleRepository repository) {
        this.repository = repository;
    }

    public List<RoleModel> getAll() {
        return this.repository.findAll(Sort.by("id").descending());
    }

    public RoleModel findById(Integer id) {
        Optional<RoleModel> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public void save(RoleModel role) {
        this.repository.save(role);
    }

    public void delete(Integer id) {
        this.repository.deleteById(id);
    }

}
