package modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.client.RestTemplate;
public class Joueur {

    private String pseudo;

    public Joueur(){

    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getPseudo() {
        return this.pseudo;
    }
}
