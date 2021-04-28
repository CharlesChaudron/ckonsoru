package com.fges.ckonsoru;

public class Manager {

    DatabaseInteraction db;
    Menu menu;

    public Manager(DatabaseInteraction db) {
        this.db = db;
        this.menu = new Menu();
    }

    public int executerAction(int choix) {
        switch (choix) {
        case 1://liste des rdvs dispos à une date donnée
            this.rdvsDispos();
            return 1;

        case 2://liste les rdvs d'un client
            System.out.println("liste les rdv d'un client");
            return 1;

        case 3://enregistre un rdv pour un client chez un vétérianire
            System.out.println("prendre un rdv");
            return 1;

        case 4://supprime un rdv
            System.out.println("supprimer un rdv");
            return 1;

        case 9://quitte le programme
            this.quitter();
            return 0;

        default://action inconnue
            this.actionInconnue();
            return 1;
        }
    }

    public void rdvsDispos() {
        RdvManager rdvManager = new RdvManager(db);
        rdvManager.afficherDisponibilitesJour(this.menu.attendreDate());
    }

    public void actionInconnue() {
        System.out.println("Cette action n'existe pas.");
    }

    public void quitter() {
        System.out.println("A bientôt !");
    }
}
