package fr.miage.orleans.applisondages.controller;


import modele.Sondage;
import modele.Vote;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/sondages")
public class SondageController {

    private static Map<Long, Sondage> bd = new HashMap<>();

    static {
        Sondage sondage = new Sondage("Qui qui ?", asList("A", "B", "C"));
        bd.put(sondage.getId(), sondage);
        sondage = new Sondage("Quoi ?", asList("Z", "X"));
        bd.put(sondage.getId(), sondage);

    }


    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Collection<Sondage> getAllSondages() {

        return bd.values();

    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sondage getSondageById(@PathVariable("id") Long id) {
        return bd.get(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> creerSondage(@RequestParam String question,
                                               @RequestParam List<String> propositions) {


        Sondage nouveauSondage = new Sondage(question, propositions);
        bd.put(nouveauSondage.getId(), nouveauSondage);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(nouveauSondage.getId()).toUri();

        return ResponseEntity.created(location).build();

    }


    @RequestMapping(value = "{idSondage}/votes", method = RequestMethod.POST)
    public ResponseEntity<Vote> voter(
            @PathVariable("idSondage") Long id,
            @RequestParam("votant") String votant,
            @RequestParam("choix") String choix) {
        Sondage leSondage = bd.get(id);
        if (leSondage.getReponses().contains(choix)) {
            Vote monVote = new Vote(votant, choix);
            leSondage.voter(monVote);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(monVote.getId()).toUri();
            return ResponseEntity.created(location).body(monVote);
        }
        return new ResponseEntity<Vote>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "{id}/votes", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Vote> voter(@PathVariable("id") Long id, @RequestBody Vote vote) {
        Sondage sondage = bd.get(id);
        if (sondage.getReponses().contains(vote.getChoix())) {
            Vote vote1 = new Vote(vote.getVotant(), vote.getChoix());
            sondage.voter(vote1);
            return new ResponseEntity<Vote>(vote1, HttpStatus.CREATED);
        }
        return new ResponseEntity<Vote>(HttpStatus.BAD_REQUEST);
    }

}
