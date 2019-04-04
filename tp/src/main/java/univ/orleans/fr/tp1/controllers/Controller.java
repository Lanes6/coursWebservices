package univ.orleans.fr.tp1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import modele.Partie;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import univ.orleans.fr.tp1.Joueur;
import univ.orleans.fr.tp1.Mottt;
import univ.orleans.fr.tp1.Partieee;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping
public class Controller {
    private static FacadeMotus facadeMotus = new FacadeMotusStatic();

    String uriToken = "http://localhost:8090/token";

    @RequestMapping(value = "/motus/joueur", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> connexion(@RequestBody Joueur joueur) {
        try {
            facadeMotus.connexion(joueur.getPseudo());
            String jwt=createToken(joueur.getPseudo());
            System.out.println(jwt);
            if(jwt!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body(jwt);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
            }
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pseudo deja pris");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/motus/joueur/{pseudo}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deconnexion(@PathVariable("pseudo") String pseudo) {
        try {
            facadeMotus.deconnexion(pseudo);
            return ResponseEntity.status(HttpStatus.OK).body("Bye");
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/motus/dicos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getDicos() {
        try {
            Collection<String> temp = facadeMotus.getListeDicos();
            String res = "Liste des dicos:";
            Iterator it = temp.iterator();
            while (it.hasNext()) {
                res += it.next() + ",";
            }
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/motus/partie", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> nouvellePartie(@RequestBody Partieee partieee) {
        try {
            facadeMotus.nouvellePartie(partieee.getPseudo(), partieee.getDico());
            return ResponseEntity.created(new URI("/motus/partie" + partieee.getPseudo())).build();
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mauvaise URI");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/motus/partie/{pseudo}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getPartie(@PathVariable("pseudo") String pseudo) {
        try {
            Partie partie = facadeMotus.getPartie(pseudo);
            if (partie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune partie en cours");
            }
            String json = "";
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                json = ow.writeValueAsString(partie);
            } catch (JsonProcessingException e) {
                json = "Erreur traduction json";
            }
            json = json.replaceAll("\n", " ");
            json = json.replaceAll("\"dico.*},", "");
            json = json.replaceAll("\"dico.*}", "");
            json = json.replaceAll("\"motRecherche\" : \"[A-Z]*\",", "");
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    @RequestMapping(value = "/motus/partie", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> jouer(@RequestBody Mottt mottt) {
        try {
            Partie partie = facadeMotus.getPartie(mottt.getPseudo());
            System.out.println(partie.getMotRecherche());
            if (partie == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune partie en cours");
            }
            String res = facadeMotus.jouer(mottt.getPseudo(), mottt.getMot());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (MaxNbCoupsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MaxNbCoup");
        } catch (MotInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot inexistant");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur serveur");
        }
    }

    public String createToken(String pseudo) {
        String json = "{\"pseudo\" : \"" + pseudo+ "\"}";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(uriToken, httpEntity, String.class);
            return response.getBody().toString();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }









        /*String json = "{\"pseudo\" : \"" + pseudo+ "\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {


            HttpHeaders headers2 = new HttpHeaders();
            String test=restTemplate.getForObject("http://localhost:8090/test", String.class, headers);
            System.out.println(test);

            //ResponseEntity<String> jwt = restTemplate.postForEntity(uriToken, httpEntity,String.class);
            //restTemplate.postForEntity("http://localhost:8090/token",new HttpEntity<>(),String.class);
            //return jwt.toString();
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());
            return null;
        }*/

        /*RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            String test=restTemplate.getForObject("http://localhost:8090/test", String.class, headers2);
            return "ok";
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    */
    }

}