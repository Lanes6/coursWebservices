package fr.miage.orleans.wsi.clientserviceauthentifie.views;

import modele.Sondage;

import java.util.List;

public class ListeSondageView implements View {

    List<Sondage> listeSondages;

    public void setListeSondages(List<Sondage> listeSondages) {
        this.listeSondages = listeSondages;
    }

    public ListeSondageView() {
    }

    public ListeSondageView(List<Sondage> listeSondages) {
        this.listeSondages = listeSondages;
    }

    @Override
    public void afficher() {

        System.out.println("---------------------------------------------");
        for (Sondage s : listeSondages) {
            System.out.println(s.getQuestion());
            System.out.println("Reponses possibles :");
            for (String rep : s.getReponses()) {
                System.out.println("    " + rep);
            }
        }
        System.out.println("---------------------------------------------");

    }

    public void recuperationServeur(String authenticationUri) {
        System.out.println("---------------------------------------------");
        System.out.println("Serveur d'authentification connu :" + authenticationUri);
        System.out.println("---------------------------------------------");

    }

    public void serveurInaccessible() {
        System.out.println("---------------------------------------------");
        System.out.println("le serveur de sondage n'est pas fonctionnel");
        System.out.println("---------------------------------------------");

    }
}
