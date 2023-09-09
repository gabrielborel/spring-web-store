package borelgabriel.com.br.springwebstore;

import borelgabriel.com.br.springwebstore.controller.AccessController;
import borelgabriel.com.br.springwebstore.model.Access;
import borelgabriel.com.br.springwebstore.repository.AccessRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebstoreApplication.class)
public class SpringWebstoreApplicationTests extends TestCase {
    @Autowired
    private AccessController accessController;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void testAPIRestSaveAccess() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Access access = new Access();
        access.setDescription("ROLE_ADMIN_SAVE");

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
    public void testAPIRestDeleteAccess() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Access access = new Access();
        access.setDescription("ROLE_ADMIN_DELETE");
        Access createdAccess = this.accessRepository.save(access);

        ObjectMapper mapper = new ObjectMapper();

        ResultActions result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/access/" + createdAccess.getId())
                        .content(mapper.writeValueAsString(access))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        String responseContent = result.andReturn().getResponse().getContentAsString();
        int responseStatus = result.andReturn().getResponse().getStatus();
        assertEquals("Access deleted", responseContent);
        assertEquals(200, responseStatus);
    }

    @Test
    public void testAPIRestFindAccessById() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Access access = new Access();
        access.setDescription("ROLE_ADMIN_FIND");
        Access createdAccess = this.accessRepository.save(access);

        ObjectMapper mapper = new ObjectMapper();

        ResultActions result = mockMvc
                .perform(MockMvcRequestBuilders.get("/access/" + createdAccess.getId())
                        .content(mapper.writeValueAsString(access))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        String responseContent = result.andReturn().getResponse().getContentAsString();
        Access fetchedAccess = mapper.readValue(responseContent, Access.class);
        assertEquals(createdAccess.getId(), fetchedAccess.getId());
        assertEquals(createdAccess.getDescription(), fetchedAccess.getDescription());
    }

    @Test
    public void testAPIRestFindAccessesByDescription() throws Exception {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        MockMvc mockMvc = builder.build();

        Access access = new Access();
        access.setDescription("ROLE_ADMIN_FIND_BY_DESCRIPTION");
        Access createdAccess = this.accessRepository.save(access);

        ObjectMapper mapper = new ObjectMapper();

        ResultActions result = mockMvc
                .perform(MockMvcRequestBuilders.get("/access/description/DESCRIPTION")
                        .content(mapper.writeValueAsString(access))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        String responseContent = result.andReturn().getResponse().getContentAsString();
        List<Access> fetchedAccesses = mapper.readValue(responseContent, new TypeReference<List<Access>>() {});

        assertEquals(1, fetchedAccesses.size());
        assertEquals(createdAccess.getId(), fetchedAccesses.get(0).getId());
        assertEquals(createdAccess.getDescription(), fetchedAccesses.get(0).getDescription());

        this.accessRepository.deleteById(createdAccess.getId());
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
