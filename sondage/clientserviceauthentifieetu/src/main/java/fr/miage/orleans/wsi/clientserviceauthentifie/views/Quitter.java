package fr.miage.orleans.wsi.clientserviceauthentifie.views;

import fr.miage.orleans.wsi.clientserviceauthentifie.controller.Controleur;

import java.util.Scanner;

public class Quitter implements View {


    public Quitter() {

    }


    @Override
    public void afficher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println(" Bye!");
    }
}
