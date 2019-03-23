package com.etu.servtokenn.controllers;

import com.etu.servtokenn.SecurityMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Security;
import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping
public class Controller {
    private static SecurityMap secureMap = new SecurityMap();

    @RequestMapping(value = "/token/{pseudo}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createToken(@PathVariable("pseudo") String pseudo) {
        System.out.println("post");
        try {
            String jwt = secureMap.encode(pseudo);
            return ResponseEntity.status(HttpStatus.OK).body(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/token/{jwt}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> verifyToken(@PathVariable("jwt") String jwt) {
        try {
            String res= secureMap.decode(jwt);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }
}
