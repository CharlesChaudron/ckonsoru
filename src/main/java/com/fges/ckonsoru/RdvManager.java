package com.fges.ckonsoru;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class RdvManager {

    DatabaseInteraction dbInteraction;

    public RdvManager(DatabaseInteraction dbInteraction) {
        this.dbInteraction = dbInteraction;
    }

    public String[] getRdvsDisponibles(LocalDateTime date) {
        String[] rdvsDispos = {};
        try {
            String jour = date.format(DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH));
            String jourEnChiffres = date.format(DateTimeFormatter.ofPattern("aaaa-mm-dd"));

            Map<Integer, Map<String, String>> disponibilites;
            String[] tagsDispos = { "jour", "debut", "fin", "veterinaire" };
            disponibilites = dbInteraction.selectElementsFromWhere(tagsDispos, "disponibilite", "jour", jour);

            Map<Integer, Map<String, String>> rdvPris;
            String[] tagsRdvs = { "debut", "veterinaire" };
            rdvPris = dbInteraction.selectElementsFromWhere(tagsRdvs, "rendezvous", "debut", jourEnChiffres);

            for (int i = 0; i < disponibilites.size(); i++) {
                
            }

        } catch (Exception e) {
            System.out.println("Veuillez donner une date valide.");
        }

        
        


        return rdvsDispos;
    }
    
}
