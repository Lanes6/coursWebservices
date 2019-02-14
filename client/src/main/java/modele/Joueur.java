package modele;

import org.springframework.web.client.RestTemplate;

public class Joueur {

    private String pseudo;

    public Joueur(){

    }

    public void createJoueur(String ps){
        final String uri="http://localhost:8080/joueur";

        Joueur player = new Joueur();
        player.setPseudo(ps);
        RestTemplate restTemplate = new RestTemplate();
        Joueur result = restTemplate.postForObject(uri,player,Joueur.class);
        System.out.println(result);
    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
