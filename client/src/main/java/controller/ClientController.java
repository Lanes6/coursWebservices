package controller;

import modele.Joueur;
import modele.MotusLocal;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static modele.MotusLocal.connexion;
import static modele.MotusLocal.getDicos;


public class ClientController {
    public static void main(String args[]) {


        //String res=getDicos();
        Joueur res= connexion("j");
        System.out.println(res);


        //Joueur player= new Joueur();
        //player.createJoueur("ps");
    }


   /* public static Joueur connexion(String pseudo, String mdp){
        final String uri = "http://localhost:8080/joueur";
        RestTemplate restTemplate = new RestTemplate();



    }*/
        /*RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.put("login", Arrays.asList(login));
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map , httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login



        try {
            ResponseEntity<String> resultat = restTemplate.postForEntity(urlAuthentication, httpEntity, String.class);
            String contenu = resultat.getBody();

        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                throw new NoServerFoundException();
            }
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                throw new UnknownUserException();
            }
        }
            */
    }
