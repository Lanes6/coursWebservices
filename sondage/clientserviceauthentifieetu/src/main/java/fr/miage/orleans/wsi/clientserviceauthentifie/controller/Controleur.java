package fr.miage.orleans.wsi.clientserviceauthentifie.controller;

import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.ProxyService;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.BadTokenException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.NoAuthenticationServerIsKnownException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.NoServerFoundException;
import fr.miage.orleans.wsi.clientserviceauthentifie.proxyservice.exceptions.UnknownUserException;
import fr.miage.orleans.wsi.clientserviceauthentifie.views.*;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;

public class Controleur {

    private String login;
    private String password;
    Menu menu;
    ListeSondageView listeSondageView;
    DemandeToken demandeToken;
    Quitter quitter;
    CreerSondage creerSondage;
    String token;


    private String authenticationUri;


    public Controleur() {
        menu = new Menu(this);
        listeSondageView = new ListeSondageView();
        demandeToken = new DemandeToken(this);
        creerSondage = new CreerSondage();
        quitter = new Quitter();
        goToMenu();
    }

    public void goToRequeteJeton() {
        demandeToken.afficher();

    }

    public void goRecupererListe() {
        try {
            listeSondageView.setListeSondages(ProxyService.getListeSondage(token));
            listeSondageView.afficher();
            goToMenu();
        } catch (BadTokenException e) {
            authenticationUri = e.getUri();
            listeSondageView.recuperationServeur(authenticationUri);
            goToMenu();
        } catch (NoServerFoundException e) {
            listeSondageView.serveurInaccessible();
            goToMenu();

        } catch (ResourceAccessException e) {
            listeSondageView.serveurInaccessible();
            goToMenu();
        }


    }


    public void goToMenu() {
        menu.afficher();
    }

    public void goToCreerSondage() {
        creerSondage.afficher();
        goToMenu();

    }

    public void goToQuitter() {
        quitter.afficher();
    }

    public void demandeToken(String login, String password) {
        try {
            token = ProxyService.getToken(authenticationUri, login, password);
            goToMenu();
        } catch (NoAuthenticationServerIsKnownException e) {
            demandeToken.help();
            goToMenu();
        } catch (NoServerFoundException e) {
            demandeToken.erreurServeur();
            goToMenu();
        } catch (UnknownUserException e) {
            demandeToken.erreur();
            goToMenu();
        } catch (ResourceAccessException e) {
            demandeToken.erreurServeur();
            goToMenu();
        }
    }
}
