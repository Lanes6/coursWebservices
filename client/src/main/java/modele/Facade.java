package modele;

public interface Facade {
     String uriJoueur = "http://localhost:8080/motus/joueur";
     String uriDicos = "http://localhost:8080/motus/dicos";
     String uriDeco = "http://localhost:8080/motus/joueur/{pseudo}";

    public String connexion(String pseudo);

    public String deconnexion();

    public String getDicos();



}
