/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.view;

import com.fges.ckonsoru.dao.DisponibilitesDAO;
import com.fges.ckonsoru.dao.ListeAttenteDAO;
import com.fges.ckonsoru.model.Disponibilite;
import com.fges.ckonsoru.model.ListeAttente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author julie.jacques
 */
public class AfficheCreneauxDateAction
    extends ActionConsole {
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    
    protected DisponibilitesDAO dispoDAO;
    protected ListeAttenteDAO listeAttenteDAO;
    
    public AfficheCreneauxDateAction(int numero, String description, 
                                     DisponibilitesDAO dispoDAO, ListeAttenteDAO listeAttenteDAO) {
        super(numero, description);
        this.dispoDAO = dispoDAO;
        this.listeAttenteDAO = listeAttenteDAO;
    }

    @Override
    public void executer(Scanner scanner) {
        System.out.println("Entrer une date au format JJ/MM/AAAA (ex: 18/03/2021)");
        String sDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(sDate, dateFormatter);
        List<Disponibilite> dispos = dispoDAO.getDisponibilitesPourDate(date);
        System.out.println("Disponibilités pour le " + date.format(dateFormatter));
        if (dispos.size() > 0) {
            for (Disponibilite dispo : dispos) {
                System.out.println(dispo.getVeterinaire() + " : " + dateTimeFormatter.format(dispo.getDebut()));
            }
        } else {
            System.out.println("Aucune disponibilité pour le " + date + "\n"
                    + "Appuyez sur 1 pour vous inscrire en liste d'attente, 0 pour retourner au menu principal");
            String choix = scanner.nextLine();
            if (choix.compareTo("1") == 0 ) {
                System.out.println("Indiquer votre nom (ex: P. Smith)");
                String nom = scanner.nextLine();
                System.out.println("Indiquez un numéro auquel on pourra vous rappeler (ex: +33612345678)");
                String numero = scanner.nextLine();
                ListeAttente listeAttente = new ListeAttente();
                listeAttente.setNomClient(nom);
                listeAttente.setNumTel(numero);
                listeAttente.setDateAuPlusTard(date);
                listeAttenteDAO.creerListeAttente(listeAttente);
            }
        }
        
    }
    
}
