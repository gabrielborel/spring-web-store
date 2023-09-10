package borelgabriel.com.br.springwebstore.service;

import borelgabriel.com.br.springwebstore.exceptions.ResourceAlreadyExistsException;
import borelgabriel.com.br.springwebstore.exceptions.ResourceNotFoundException;
import borelgabriel.com.br.springwebstore.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borelgabriel.com.br.springwebstore.model.Access;

import java.util.List;

@Service
public class AccessService {
    @Autowired
    private AccessRepository accessRepository;

    public Access save(Access access) throws ResourceAlreadyExistsException {
        List<Access> accesses = this.accessRepository.findAccessByDescription(access.getDescription().toUpperCase());
        System.out.println(accesses.isEmpty());
        if (!accesses.isEmpty()) throw new ResourceAlreadyExistsException("Access with description: '"+ access.getDescription() + "' already exists");
        return this.accessRepository.save(access);
    }

    public void delete(Long id) {
        this.accessRepository.deleteById(id);
    }

    public Access findById(Long id) throws ResourceNotFoundException {
        var access = this.accessRepository.findById(id).orElse(null);
        if (access == null) throw new ResourceNotFoundException("Access with id "+ id + " not found");
        return access;
    }

    public List<Access> findByDescription(String description) {
        return this.accessRepository.findAccessByDescription(description);
    }
}
