package com.fges.ckonsoru;

public class Manager {

    DAOInterface db;
    Menu menu;
    RdvManager rdvManager;

    public Manager(DAOInterface db) {
        this.db = db;
        this.menu = new Menu();
        this.rdvManager = new RdvManager(db);
    }

    public int executerAction(int choix) {
        switch (choix) {
        case 1://liste des rdvs dispos à une date donnée
        System.out.println("Afficher la liste des disponibilités pour une date");
            this.rdvsDispos();
            return 1;

        case 2://liste les rdvs d'un client
            System.out.println("Affichage des rendez-vous d un client");
            this.rdvsClient();
            return 1;

        case 3://enregistre un rdv pour un client chez un vétérianire
            System.out.println("Prise de rendez-vous");
            this.addRdvClient();
            return 1;

        case 4://supprime un rdv
            System.out.println("Suppression de rendez-vous");
            this.deleteRdvClient();
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
        this.rdvManager.afficherDisponibilitesJour(this.menu.attendreDate());
    }

    public void rdvsClient() {
        this.rdvManager.afficherRdvsClient(this.menu.attendreNom("client"));
    }

    public void addRdvClient() {
        this.rdvManager.addRdv(this.menu.attendreDateTime(), this.menu.attendreNom("veterinaire"),
                this.menu.attendreNom("client"));
    }
    
    public void deleteRdvClient() {
        this.rdvManager.deleteRdv(this.menu.attendreDateTime(),
                this.menu.attendreNom("client"));
    }

    public void actionInconnue() {
        System.out.println("Cette action n'existe pas.");
    }

    public void quitter() {
        System.out.println("A bientôt !");
    }
}
