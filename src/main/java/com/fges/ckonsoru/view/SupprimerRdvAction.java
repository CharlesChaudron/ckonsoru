/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.view;

import com.fges.ckonsoru.dao.AnnulationDAO;
import com.fges.ckonsoru.dao.RendezVousDAO;
import com.fges.ckonsoru.model.Annulation;
import com.fges.ckonsoru.model.RendezVous;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author julie.jacques
 */
public class SupprimerRdvAction 
    extends ActionConsole {

    protected RendezVousDAO rdvDao;
    protected AnnulationDAO annDao;
    protected DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public SupprimerRdvAction(int numero, String description, RendezVousDAO rdvDao, AnnulationDAO annDao) {
        super(numero, description);
        this.rdvDao = rdvDao;
        this.annDao = annDao;
    }

    @Override
    public void executer(Scanner scanner) {
        System.out.println("Suppression de rendez-vous");
        System.out.println("Indiquer une date et heure de début au format JJ/MM/AAAA HH:MM (ex: 18/03/2021 15:00)");
        String sDebut = scanner.nextLine();
        LocalDateTime debut = LocalDateTime.parse(sDebut, timeFormatter);
        System.out.println("Indiquer le nom du client");
        String client = scanner.nextLine();
        RendezVous delRdv = new RendezVous(client,debut,"");
        this.rdvDao.supprimerRendezVous(delRdv);
        //devrait être fait dans un controller et pas dans la vue pour respecter le mvc
        Long delaiSecondes = Duration.between(LocalDateTime.now(), debut).getSeconds();
        if (delaiSecondes < 86400) {
            LocalTime delai = LocalTime.ofSecondOfDay(delaiSecondes);
            Annulation annulation = new Annulation(client, debut, "", delai);
            this.annDao.tracerAnnulation(annulation);
        }
        System.out.println("Un rendez-vous pour "+client+" le  "+ timeFormatter.format(debut) + " a été supprimé");
    }
    
}
