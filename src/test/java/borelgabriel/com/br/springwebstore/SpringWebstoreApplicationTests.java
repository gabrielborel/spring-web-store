package borelgabriel.com.br.springwebstore;

import borelgabriel.com.br.springwebstore.controller.AccessController;
import borelgabriel.com.br.springwebstore.model.Access;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebstoreApplication.class)
public class SpringWebstoreApplicationTests extends TestCase {
    @Autowired
    private AccessController accessController;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testAPIRestSaveAccess() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Access access = new Access();
        access.setDescription("ROLE_ADMIN");

        ObjectMapper mapper = new ObjectMapper();

        ResultActions result = mockMvc
                .perform(MockMvcRequestBuilders.post("/access")
                        .content(mapper.writeValueAsString(access))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        String content = result.andReturn().getResponse().getContentAsString();
        Access createdAccess = mapper.readValue(content, Access.class);

        assertEquals(access.getDescription(), createdAccess.getDescription());
    }

    @Test
    public void testCreateAccess() {
        Access access = new Access();
        access.setDescription("ROLE_ADMIN");
        Access createdAccess = this.accessController.createAccess(access).getBody();
        assert createdAccess != null;
        assertTrue(createdAccess.getId() > 0);
        assertEquals("ROLE_ADMIN", createdAccess.getDescription());
    }
}
