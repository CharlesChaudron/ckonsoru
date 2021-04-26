package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RdvManager {

    DatabaseInteraction dbInteraction;

    public RdvManager(DatabaseInteraction dbInteraction) {
        this.dbInteraction = dbInteraction;
    }

    public List<String> getRdvsDisponibles(String dateText) {
        List<String> rdvsDispos = new ArrayList<String>();
        /*
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime date = LocalDateTime.parse(dateText, formatter);
        
        String jour = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        //String jourEnChiffres = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        Map<Integer, Map<String, String>> disponibilites;
        String[] tagsDispos = { "jour", "debut", "fin", "veterinaire" };
        disponibilites = dbInteraction.selectElementsFromWhere(tagsDispos, "disponibilite", "jour", jour);
        
        Map<Integer, Map<String, String>> rdvPris;
        String[] tagsRdvs = { "debut", "veterinaire" };
        rdvPris = dbInteraction.selectElementsFromWhere(tagsRdvs, "rendezvous", "debut", jourEnChiffres);
        
        for (int i = 0; i < disponibilites.size(); i++) {
            
        }
        */
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(dateText + " 00:00:00", formatter);
        String jour = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
        String jourEnChiffres = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();

        Map<Integer, Map<String, String>> disponibilites;
        String[] tagsDispos = { "jour", "debut", "fin", "veterinaire" };
        disponibilites = dbInteraction.selectElementsFromWhere(tagsDispos, "disponibilite", "jour", jour);

        Map<Integer, Map<String, String>> rdvPris;
        String[] tagsRdvs = { "debut", "veterinaire" };
        rdvPris = dbInteraction.selectElementsFromWhere(tagsRdvs, "rendezvous", "debut", jourEnChiffres);

        for (int i = 0; i < disponibilites.size(); i++) {
            String creneau = disponibilites.get(i).get("debut");
            String fin = disponibilites.get(i).get("fin");
            String veterinaire = disponibilites.get(i).get("veterinaire");

            LocalDateTime creneauLocalDateTime = LocalDateTime.parse(jourEnChiffres + "T" + creneau, formatter);
            LocalDateTime finLocalDateTime = LocalDateTime.parse(jourEnChiffres + "T" + fin, formatter);
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
        
        return rdvsDispos;
    }
    
}
