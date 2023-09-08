package borelgabriel.com.br.springwebstore.service;

import borelgabriel.com.br.springwebstore.repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borelgabriel.com.br.springwebstore.model.Access;

@Service
public class AccessService {
    @Autowired
    private AccessRepository accessRepository;

    public Access save(Access access) {
        return this.accessRepository.save(access);
    }
}
