package borelgabriel.com.br.springwebstore.controller;

import borelgabriel.com.br.springwebstore.model.Access;
import borelgabriel.com.br.springwebstore.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>("Access deleted",HttpStatus.OK);
    }
}
