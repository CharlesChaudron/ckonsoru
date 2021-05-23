/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.view;

import com.fges.ckonsoru.dao.ListeAttenteDAO;
import com.fges.ckonsoru.dao.RendezVousDAO;
import com.fges.ckonsoru.model.RendezVous;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author julie.jacques
 */
public class ListeAttenteAction 
    extends ActionConsole {
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    protected  ListeAttenteDAO ListeAttente;
    
    public ListeAttenteAction(int numero, String description, ListeAttenteDAO ListeAttente) {
        super(numero, description);
        this.ListeAttente = ListeAttente;
    }

    @Override
    public void executer(Scanner scanner) {
        System.out.println("Affichage de la liste d'attente d'un client");
        System.out.println("Indiquer le nom du client");
        String client = scanner.nextLine();
        List<RendezVous> rdvs = ListeAttente.listeAttentePourClient(client);
        System.out.println(rdvs.size() + " rendez-vous proposé(s) pour " + client);
        for (RendezVous rdv : rdvs){
            System.out.println(dateTimeFormatter.format(rdv.getDate())+" avec "+rdv.getVeterinaire());
        }
        
    }
   
}
