package borelgabriel.com.br.springwebstore.service;

import borelgabriel.com.br.springwebstore.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borelgabriel.com.br.springwebstore.model.Access;

import java.util.List;
import java.util.Optional;

@Service
public class AccessService {
    @Autowired
    private AccessRepository accessRepository;

    public Access save(Access access) {
        return this.accessRepository.save(access);
    }

    public void delete(Long id) {
        this.accessRepository.deleteById(id);
    }

    public Access findById(Long id) {
        return this.accessRepository.findById(id).isPresent()
                ? this.accessRepository.findById(id).get()
                : null;
    }

    public List<Access> findByDescription(String description) {
        return this.accessRepository.findAccessByDescription(description);
    }
}
