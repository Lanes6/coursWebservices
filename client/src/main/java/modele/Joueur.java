package modele;

import org.springframework.web.client.RestTemplate;

public class Joueur {

    private String pseudo;

    public Joueur(){

    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}