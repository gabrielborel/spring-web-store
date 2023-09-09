package borelgabriel.com.br.springwebstore.controller;

import borelgabriel.com.br.springwebstore.model.Access;
import borelgabriel.com.br.springwebstore.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class AccessController {
    @Autowired
    private AccessService accessService;

    @PostMapping("/access")
    @ResponseBody
    public ResponseEntity<Access> createAccess(@RequestBody Access access) {
        Access createdAccess = this.accessService.save(access);
        return new ResponseEntity<>(createdAccess, HttpStatus.CREATED);
    }

    @DeleteMapping("/access/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteAccess(@PathVariable Long id) {
        this.accessService.delete(id);
        return new ResponseEntity<>("Access deleted", HttpStatus.OK);
    }

    @GetMapping("/access/{id}")
    @ResponseBody
    public ResponseEntity<Access> getAccess(@PathVariable Long id) {
        Access access = this.accessService.findById(id);
        return new ResponseEntity<>(access, HttpStatus.OK);
    }

    @GetMapping("/access/description/{description}")
    @ResponseBody
    public ResponseEntity<List<Access>> getAccessByDescription(@PathVariable String description) {
        List<Access> accesses = this.accessService.findByDescription(description);
        return new ResponseEntity<>(accesses, HttpStatus.OK);
    }
}
