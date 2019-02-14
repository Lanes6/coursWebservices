package controller;

import modele.Joueur;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class ClientController {
    public static void main(String args[]) {


        String res=getDicos();
        System.out.println(res);


        //Joueur player= new Joueur();
        //player.createJoueur("ps");
    }


    public static String getDicos(){
        final String uri = "http://localhost:8080/dicos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }
}
