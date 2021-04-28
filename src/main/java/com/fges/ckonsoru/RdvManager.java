package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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
                    rdvsDispos.add(veterinaire + " : " + creneau.format(formatter2));
                }
                creneau = creneau.plusMinutes(20);
            }
        }

        return rdvsDispos;
    }
    
    public void afficherDisponibilitesJour(String date) {
        System.out.println("Disponibilités pour le " + date);
        List<String> rdvsDispos = this.getRdvsDisponibles(date);

        if (rdvsDispos.size() > 0) {
            for (String dispo : rdvsDispos) {
                System.out.println(dispo);
            }
            System.out.println("\n");
        } else {
            System.out.println("Pas de disponibilités à cette date");
        }
    }
    
}
