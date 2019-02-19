package controller;

import modele.Facade;
import modele.MotusLocal;

import java.util.Scanner;

public class ClientController {
    public static void main(String args[]) {
        int choix = 10;
        String temp = "";
        Scanner sc = new Scanner(System.in);
        Facade motus = new MotusLocal();
        while (choix != 0) {
            System.out.println("-----------------------");
            System.out.println("Actions=> 1-Connexion  2-Deconnexion  3-Dicos  4-Nouvelle partie  5-Jouer  6-Info Partie 0-Quitter");
            try {
                choix = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                choix = 10;
            }
            switch (choix) {
                case 1:
                    System.out.println("Nom de joueur?");
                    temp = sc.nextLine();
                    System.out.println(motus.connexion(temp));
                    //System.out.println(motus.connexion("Joueur1000"));
                    break;
                case 2:
                    System.out.println(motus.deconnexion());
                    break;
                case 3:
                    System.out.println(motus.getDicos());
                    break;
                case 4:
                    System.out.println("Dico utilisé?");
                    temp = sc.nextLine();
                    System.out.println(motus.creaPartie(temp));
                    //System.out.println(motus.creaPartie("dico7lettres"));
                    break;
                case 5: //temp=motus.jouer("CREMEUX");
                    System.out.println("Mot?");
                    temp = sc.nextLine();
                    temp = motus.jouer(temp);
                    System.out.println(motus.getPartie());
                    System.out.println(temp);
                    break;
                case 6:
                    System.out.println(motus.getPartie());
                    break;
            }

        }
    }
}