package services;

public interface Constantes {


    String URL_SONDAGE = "http://localhost:8080/sondages";
    /**
     * Nom du champ où est placé le token généré par le serveur d'authentification
     * (Header)
     */
    String AUTHORIZATION = "Authorization";

    /**
     * Nom du champ qui part du client et qui va vers le serveur de sondage contenant
     * le token généré par le serveur d'authentification (Header)
     */
    String TOKEN = "token";

    /**
     * Nom du champ pour le login qu'attend le serveur d'authentification
     */
    String LOGIN = "login";
    /**
     * Nom du champ pour le password qu'attend le serveur d'authentification
     */

    String PASSWORD = "password";

    /**
     * Nom du champ qui contient l'adresse du serveur d'authentification quand
     * on attaque le serveur de service sans être passé par le serveur d'authentification.
     * (Header)
     */
    String LOCATION = "location";
}
