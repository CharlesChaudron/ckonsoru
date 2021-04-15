package com.fges.ckonsoru;

public class Manager {
    public int executerAction(int choix) {
        switch (choix) {
        case 1:
            System.out.println("Affiche les crénaux disponibles");
            return 1;

        case 2:
            System.out.println("liste les rdv d'un client");
            return 1;

        case 3:
            System.out.println("prendre un rdv");
            return 1;

        case 4:
            System.out.println("supprimer un rdv");
            return 1;

        case 9:
            System.out.println("Quitter");
            return 0;

        default:
            System.out.println("Cette action n'existe pas.");
            return 1;
        }
    }
}
