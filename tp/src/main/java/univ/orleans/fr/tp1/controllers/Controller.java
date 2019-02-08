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
import org.springframework.web.bind.annotation.*;
import univ.orleans.fr.tp1.Joueur;
import univ.orleans.fr.tp1.Mottt;
import univ.orleans.fr.tp1.Partieee;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping
public class Controller {
    private static FacadeMotus facadeMotus = new FacadeMotusStatic();

    @RequestMapping (value = "/joueur", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> connexion(@RequestBody Joueur joueur) {
        try {
            facadeMotus.connexion(joueur.getPseudo());
            return ResponseEntity.created(new URI("/motus/partie" + joueur.getPseudo())).build();
        } catch (PseudoDejaPrisException | URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo deja pris");
        }
    }

    @RequestMapping (value = "/joueur", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deconnexion(@RequestBody Joueur joueur) {
        try {
            facadeMotus.deconnexion(joueur.getPseudo());
            return ResponseEntity.status(HttpStatus.OK).body("Bye");
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        }
    }

    @RequestMapping (value = "/dicos", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getDicos() {
        Collection<String> temp = facadeMotus.getListeDicos();
        String res = "Liste des dicos:";
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            res += it.next() + ",";
        }
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @RequestMapping (value = "/partie", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> nouvellePartie(@RequestBody Partieee partieee) {
        try {
            facadeMotus.nouvellePartie(partieee.getPseudo(), partieee.getDico());
            return ResponseEntity.created(new URI("/motus/partie" + partieee.getPseudo())).build();
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mauvaise URI");
        }
    }

    @RequestMapping (value = "/partie", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getPartie(@RequestBody Partieee partieee) {
        try {
            Partie partie = facadeMotus.getPartie(partieee.getPseudo());
            String res = "Partieee de " + partieee.getPseudo();
            res += " Nb Essais:" + partie.getNbEssais();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        }
    }

    @RequestMapping (value = "/partie", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> jouer(@RequestBody Mottt mottt) {
        try {
            String res = facadeMotus.jouer(mottt.getPseudo(), mottt.getMot());
            res += "  Nombre d'essais: " + facadeMotus.getPartie(mottt.getPseudo()).getNbEssais();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo non connecté");
        } catch (MaxNbCoupsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("MaxNbCoup");
        } catch (MotInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot inexistant");
        }
    }
}