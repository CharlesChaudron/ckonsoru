package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    java.util.Scanner entree;

    public Menu() {
        this.entree = new java.util.Scanner(System.in);
    }

    public void afficher() {
        System.out.println("Actions disponibles : \n"
            + "1: Afficher les crénaux disponibles pour une date donnée\n"
            + "2: Lister les rendez-vous passés, présents et à venir d'un client\n"
            + "3: Prendre un rendez-vous\n"
            + "4: Supprimer un rendez-vous\n"
            + "9: Quitter\n"
            + "Entrer un numéro d'action:\n"
        );
    }
    
    public int attendreChoix() {
        int choix = 9;
        String saisie;
        try {
            saisie = entree.nextLine();
            choix = Integer.parseInt(saisie);
        } catch (NumberFormatException e) {
            System.out.println("Merci de saisir un choix valide.");
            this.attendreChoix();
        }
        return choix;
    }

    public String attendreDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String saisie = null;
        System.out.println("Entrer une date au format JJ/MM/AAAA (ex: 18/03/2021)");
        try {
            saisie = entree.nextLine();
            LocalDate.parse(saisie, formatter);
        } catch (Exception e) {
            System.out.println("Merci de siasir une date au bon format.");
            saisie = this.attendreDate();
        }
        return saisie;
    }

    public String attendreNom(String personne) {
        Pattern pattern = Pattern.compile("^[A-Z]\\. [A-Z]{1}[a-z]+?");
        String saisie = null;
        System.out.println("Indiquer le nom du " + personne + ". (ex: M. Byrnison)");
        try {
            saisie = entree.nextLine();
            Matcher m = pattern.matcher(saisie);
            if (m.matches() != true) {
                System.out.println("Merci de siasir un nom au bon format.");
                saisie = this.attendreNom(personne);
            }
        } catch (Exception e) {
            System.out.println("Merci de siasir un nom au bon format.");
            saisie = this.attendreDate();
        }
        return saisie;
    }
}
