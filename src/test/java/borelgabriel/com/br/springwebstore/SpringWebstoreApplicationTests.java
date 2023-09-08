package borelgabriel.com.br.springwebstore;

import borelgabriel.com.br.springwebstore.model.Access;
import borelgabriel.com.br.springwebstore.repository.AccessRepository;
import borelgabriel.com.br.springwebstore.service.AccessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringWebstoreApplication.class)
class SpringWebstoreApplicationTests {
    @Autowired
    private AccessService accessService;

    @Autowired
    private AccessRepository accessRepository;

    @Test
    void testCreateAccess() {
        Access access = new Access();
        access.setDescription("ROLE_ADMIN");
        accessService.save(access);
    }
}
