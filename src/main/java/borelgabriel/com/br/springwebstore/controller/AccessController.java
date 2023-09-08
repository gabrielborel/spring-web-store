package borelgabriel.com.br.springwebstore.controller;

import borelgabriel.com.br.springwebstore.model.Access;
import borelgabriel.com.br.springwebstore.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {
    @Autowired
    private AccessService accessService;

    @PostMapping("/access")
    public Access createAccess(Access access) {
        return this.accessService.save(access);
    }
}
