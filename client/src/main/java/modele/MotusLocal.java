package modele;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MotusLocal implements Facade {

    private Joueur player;




    public String connexion(String pseudo) {
        if(player==null){
            this.player = new Joueur();
            String json = "";
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            this.player.setPseudo(pseudo);
            RestTemplate restTemplate = new RestTemplate();
            try {
                json = ow.writeValueAsString(this.player);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authozisation",player.getAccesToken());
            HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(uriJoueur, httpEntity, String.class);
                if (response.getStatusCode().value() == HttpStatus.CREATED.value()) {
                    return "Le joueur " + this.player.getPseudo() + " a été créé";
                } else {
                    return "Erreur inconnue! ";
                }
            } catch (HttpClientErrorException |HttpServerErrorException e) {
                if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                    return "Requete mal formée";
                }
                if (e.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
                    this.player.setPseudo(pseudo);
                    return "Reprise de la session du joueur " + this.player.getPseudo();
                }
                if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                    return authentification(pseudo,e.getResponseBodyAsString());
                }
                return "Erreur inconnue";
            }
        }else{
            return "Vous êtes déja connecté en temps que " + player.getPseudo() + ". Deconnectez vous d'abord!";
        }
    }

    public String deconnexion() {
        if(player==null){
            return "Vous n'êtes pas connecté";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("pseudo",player.getPseudo());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authozisation",player.getAccesToken());
        try{
            restTemplate.delete ( uriDeco,  params,headers );
            player=null;
            return "Deconnexion réussie";
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }


    public String getDicos() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authozisation",player.getAccesToken());
        try{
            return restTemplate.getForObject(uriDicos, String.class,headers);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    public String creaPartie(String dico) {
        if(player==null){
            return "Connectez vous avant de créer une partie";
        }
        String json="{\"pseudo\" : \""+player.getPseudo()+"\",\"dico\" : \""+dico+"\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authozisation",player.getAccesToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {
            restTemplate.postForEntity(uriPartie, httpEntity, String.class);
            return "Partie créée";
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    public String getPartie() {
        if(player==null){
            return "Vous n'êtes pas connecté";
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("pseudo",player.getPseudo());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authozisation",player.getAccesToken());
        try {
            String result = restTemplate.getForObject(uriGetPartie, String.class, params, headers);
            result = result.replace("}", "");
            result = result.replace("{", "");
            result = result.replace("\"", "");
            return result.trim();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    public String jouer(String mot) {
        if(player==null){
            return "Connectez vous avant de jouer";
        }
        String json="{\"pseudo\" : \""+player.getPseudo()+"\",\"mot\" : \""+mot+"\"}";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authozisation",player.getAccesToken());
        HttpEntity<String> httpEntity = new HttpEntity<String>(json, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(uriPartie, HttpMethod.PUT, httpEntity, String.class, headers);
            String res=response.getBody();
            res=res.replace("m","*");
            char[] resultat=res.toCharArray();
            char[] proposition=mot.toCharArray();
            for (int x=0; x<res.length();x++){
                if (resultat[x]=='X'){
                    resultat[x]=proposition[x];
                }
            }
            res=String.valueOf(resultat).toUpperCase();
            if(!res.contains("*")){
                res=res+"\nGAGNER";
            }
             return res;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    public String authentification(String pseudo, String uri){
        Map<String, String> params = new HashMap<String, String>();
        params.put("pseudo",pseudo);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String result = restTemplate.getForObject(uri, String.class, params);
            player.setAccesToken(result);
            return connexion(pseudo);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return e.getResponseBodyAsString();
        }
    }



}
