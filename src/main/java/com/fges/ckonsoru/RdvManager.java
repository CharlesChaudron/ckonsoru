package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RdvManager {

    DatabaseInteraction dbInteraction;

    public RdvManager(DatabaseInteraction dbInteraction) {
        this.dbInteraction = dbInteraction;
    }

    public Map<Integer, Object[]> getRdvsDisponibles(String dateText) {
        Map<Integer, Object[]> rdvsDispos = new HashMap<Integer, Object[]>();
        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dateText + " 00:00:00", formatter);
        String jour = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        LocalDate dateJour = date.toLocalDate();
        
        Map<Integer, Map<String, String>> disponibilites;
        String[] tagsDispos = { "jour", "debut", "fin", "veterinaire" };
        disponibilites = dbInteraction.selectElementsFromWhere(tagsDispos, "disponibilite", "jour", jour);
        
        Map<Integer, Map<String, String>> rdvPris;
        String[] tagsRdvs = { "debut", "veterinaire" };
        rdvPris = dbInteraction.selectElementsFromWhere(tagsRdvs, "rendezvous", "debut", dateJour.toString());
        
        for (int i = 0; i < disponibilites.size(); i++) {
            String creneau = disponibilites.get(i).get("debut");
            String fin = disponibilites.get(i).get("fin");
            String veterinaire = disponibilites.get(i).get("veterinaire");
        
            LocalDateTime creneauLocalDateTime = LocalDateTime.parse(dateJour + "T" + creneau + ":00", formatter);
            LocalDateTime finLocalDateTime = LocalDateTime.parse(dateJour + "T" + fin, formatter);
            while (creneauLocalDateTime.isBefore(finLocalDateTime)) {
                boolean dispo = true;
                for (int j = 0; j < rdvPris.size(); j++) {
                    LocalDateTime creneauRdv = LocalDateTime.parse(rdvPris.get(j).get("debut"), formatter);
                    if (rdvPris.get(j).get("veterinaire") == veterinaire || creneauRdv == creneauLocalDateTime) {
                        dispo = false;
                    }
                }
                if (dispo) {
                    rdvsDispos.add(veterinaire + " : " + creneauLocalDateTime);
                }
                creneauLocalDateTime.plus(20, ChronoUnit.MINUTES);
            }
        }
        */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateJour = LocalDate.parse(dateText, formatter);
        String jourSemaine = dateJour.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));

        //récupération de toutes les disponibilités au jour donné
        Map<Integer, Map<String, String>> disponibilites;
        String[] tagsDispos = { "jour", "debut", "fin", "veterinaire" };
        disponibilites = dbInteraction.selectElementsFromWhere(tagsDispos, "disponibilite", "jour", jourSemaine);

        //récupération des rdvs déjà pris à la date donnée
        Map<Integer, Map<String, String>> rdvPris;
        String[] tagsRdvs = { "debut", "veterinaire" };
        rdvPris = dbInteraction.selectElementsFromWhere(tagsRdvs, "rendezvous", "debut", dateJour.toString());

        for (int i = 0; i < disponibilites.size(); i++) {
            Integer heurePremier = Integer.parseInt(disponibilites.get(i).get("debut").split(":")[0]);
            Integer minutesPremier = Integer.parseInt(disponibilites.get(i).get("debut").split(":")[1]);
            Integer heureFin = Integer.parseInt(disponibilites.get(i).get("fin").split(":")[0]);
            Integer minutesFin = Integer.parseInt(disponibilites.get(i).get("fin").split(":")[1]);
            LocalDateTime creneau = dateJour.atTime(heurePremier, minutesPremier);
            LocalDateTime creneauFin = dateJour.atTime(heureFin, minutesFin);
            String veterinaire = disponibilites.get(i).get("veterinaire");

            while (creneau.isBefore(creneauFin)) {
                boolean reserve = false;
                for (int j = 0; j < rdvPris.size(); j++) {
                    LocalDateTime creneauRdv = LocalDateTime.parse(rdvPris.get(j).get("debut"));
                    if (rdvPris.get(j).get("veterinaire").equals(veterinaire) && creneauRdv.equals(creneau)) {
                        reserve = true;
                        break;
                    }
                }
                if (!reserve) {
                    //rdvsDispos.put(veterinaire, creneau);
                    Object[] infos = new Object[2];
                    infos[0] = veterinaire;
                    infos[1] = creneau;
                    rdvsDispos.put(rdvsDispos.size(), infos);
                }
                creneau = creneau.plusMinutes(20);
            }
        }

        return rdvsDispos;
    }
    
    public void afficherDisponibilitesJour(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Map<Integer, Object[]> rdvsDispos = this.getRdvsDisponibles(date);
        for (int i = 0; i < rdvsDispos.size(); i++) {
            Object[] dispo = rdvsDispos.get(i);
            LocalDateTime date = dispo[1];
            System.out.println(dispo[0] + " : " + dispo[1]);
        }
    }
    
}
