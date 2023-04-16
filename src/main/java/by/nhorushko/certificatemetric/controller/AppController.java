package by.nhorushko.certificatemetric.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("RUN!");
    }
}
