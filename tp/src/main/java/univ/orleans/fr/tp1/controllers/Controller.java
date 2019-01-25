package univ.orleans.fr.tp1.controllers;
import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import modele.Partie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping
public class Controller {
    private static FacadeMotus facadeMotus = new FacadeMotusStatic();

    @RequestMapping(value = "/joueur", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> connexion(@RequestParam String pseudo) {
        try {
            facadeMotus.connexion(pseudo);
            return ResponseEntity.created(new URI("/motus/partie" + pseudo)).build();
        } catch (PseudoDejaPrisException | URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo deja pris");
        }
    }

    @RequestMapping(value = "/deco", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deconnexion(@RequestParam String pseudo) {
        try {
            facadeMotus.deconnexion(pseudo);
            return ResponseEntity.status(HttpStatus.OK).body("Bye");
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        }
    }

    @RequestMapping(value = "/mot", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> jouer(@RequestParam String pseudo, @RequestParam String mot) {
        try {
            String res=facadeMotus.jouer(pseudo, mot);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (MaxNbCoupsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MaxNbCoup");
        } catch (MotInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot inexistant");
        }
    }

    @RequestMapping(value = "/nouvellePartie", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> nouvellePartie(@RequestParam String pseudo,@RequestParam String dico) {
        try {
            facadeMotus.nouvellePartie(pseudo,dico);
            return ResponseEntity.created(new URI("/motus/partie" + pseudo)).build();
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mauvaise URI");
        }
    }

    @RequestMapping(value = "/dicos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getDicos(@RequestParam String pseudo,@RequestParam String dico) {
        Collection<String> temp = facadeMotus.getListeDicos();
        String res = temp.toString();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @RequestMapping(value = "/partie", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getPartie(@RequestParam String pseudo) {
        try {
            Partie temp = facadeMotus.getPartie(pseudo);
            String res = temp.toString();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (PseudoNonConnecteException e) {
            e.printStackTrace();
        }
    }


}
