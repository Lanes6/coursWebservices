package fr.miage.orleans.wsi.clientserviceauthentifie.views;

import fr.miage.orleans.wsi.clientserviceauthentifie.controller.Controleur;

import java.util.Scanner;

public class Menu implements View {


    private Controleur controleur;


    public Menu(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void afficher() {

        System.out.println("---------------------------------------------");
        System.out.println(" (1) Demander un jeton");
        System.out.println(" (2) Recuperer la liste des sondages");
        System.out.println(" (3) Creer un sondage");
        System.out.println(" (4) Quitter");
        System.out.println(" Saisir 1, 2, 3 ou 4");
        System.out.println("---------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int choix = -1;
        do {
            choix = scanner.nextInt();

        }
        while (choix < 1 || choix > 4);

        switch (choix) {
            case 1: {
                controleur.goToRequeteJeton();
                break;
            }

            case 2: {
                controleur.goRecupererListe();
                break;
            }

            case 3: {
                controleur.goToCreerSondage();
                break;
            }

            case 4: {
                controleur.goToQuitter();
                break;
            }

        }


    }
}
