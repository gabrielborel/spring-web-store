package borelgabriel.com.br.springwebstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import borelgabriel.com.br.springwebstore.model.Access;

@Repository
@Transactional
public interface AccessRepository extends JpaRepository<Access, Long> {

}
