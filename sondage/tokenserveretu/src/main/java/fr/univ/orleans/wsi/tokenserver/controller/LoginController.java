package fr.univ.orleans.wsi.tokenserver.controller;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static services.Constantes.AUTHORIZATION;

@RestController
public class LoginController {

    private static Map<String, String> loginPassword = new HashMap<>();
    private static Map<String, List<String>> loginRoles = new HashMap<>();

    static {
        loginPassword.put("fred", "abcd");
        loginRoles.put("fred", Arrays.asList("user", "admin", "supersuperadmin"));
        loginPassword.put("yo", "yo");
        loginRoles.put("yo", Arrays.asList("user", "scrummaster"));
        loginPassword.put("math", "math");
        loginRoles.put("math", Arrays.asList("admin", "ceinturenoire"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String login,
                                        @RequestParam String password) {
        //TODO
        return null;
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken(@RequestHeader(AUTHORIZATION) String token) {
        //TODO
        return null;
    }
}
