package borelgabriel.com.br.springwebstore;

import borelgabriel.com.br.springwebstore.controller.AccessController;
import borelgabriel.com.br.springwebstore.model.Access;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringWebstoreApplication.class)
public class SpringWebstoreApplicationTests extends TestCase {
    @Autowired
    private AccessController accessController;

    public void testCreateAccess() {
        Access access = new Access();
        access.setDescription("ROLE_ADMIN");
        Access createdAccess = this.accessController.createAccess(access).getBody();
        assert createdAccess != null;
        assertTrue(createdAccess.getId() > 0);
    }
}
