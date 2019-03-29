package modele;

public interface Facade {
    String uriJoueur = "http://localhost:8080/motus/joueur";
    String uriDicos = "http://localhost:8080/motus/dicos";
    String uriDeco = "http://localhost:8080/motus/joueur/{pseudo}";
    String uriPartie = "http://localhost:8080/motus/partie";
    String uriGetPartie = "http://localhost:8080/motus/partie/{pseudo}";

    public String connexion(String pseudo);

    public String deconnexion();

    public String getDicos();

    public String test();

    public String creaPartie(String dico);

    public String getPartie();

    public String jouer(String mot);


}
