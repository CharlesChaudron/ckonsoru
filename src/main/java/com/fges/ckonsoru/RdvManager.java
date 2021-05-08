package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RdvManager {

    DAOInterface dbInteraction;

    public RdvManager(DAOInterface dbInteraction) {
        this.dbInteraction = dbInteraction;
    }

    public List<String> getRdvsDisponibles(String dateText) {
        List<String> rdvsDispos = new ArrayList<String>();

        //créé les différents patterns pour interpreter les dates saisies en string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        //création des jours en lettre et en datetime pour les réutiliser plus tard 
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

        //récupération des crénaux déjà pris et des disponibilités pour recroiser les infos et en déduire les crénaux disponibles
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
                    //si le créneau est dans les rdv déjà pris alors il n'est pas disponible
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
    
    public List<String> getRdvsClient(String client) {
        List<String> rdvsClient = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // récupération de toutes les disponibilités au jour donné
        Map<Integer, Map<String, String>> rdvs;
        String[] tagsDispos = { "client", "debut", "veterinaire" };
        rdvs = dbInteraction.selectElementsFromWhere(tagsDispos, "rendezvous", "client", client);

        for (int i = 0; i < rdvs.size(); i++) {
            String debutText = rdvs.get(i).get("debut");
            LocalDateTime debut = LocalDateTime.parse(debutText);
            debutText = debut.format(formatter);
            String veterinaire = rdvs.get(i).get("veterinaire");
            rdvsClient.add(debutText + " avec " + veterinaire);
        }

        return rdvsClient;
    }

    //affiche la liste des disponibilités 
    public void afficherRdvsClient(String client) {
        List<String> rdvsDispos = this.getRdvsClient(client);
        String nbRdvs = Integer.toString(rdvsDispos.size());
        System.out.println(nbRdvs + " rendez-vous trouvé(s) pour " + client);

        if (rdvsDispos.size() > 0) {
            for (String dispo : rdvsDispos) {
                System.out.println(dispo);
            }
            System.out.println("\n");
        } else {
            System.out.println("Pas de disponibilités à cette date.");
        }
    }

    // ajoute un rdv en fonction de la date et du client
    public void addRdv(String date, String veterinaire, String client) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        String[] columns = { "debut", "client", "veterinaire" };
        String[] values = { dateTime.toString(), client, veterinaire };
        this.dbInteraction.insert("rendezvous", columns, values);
        System.out.println("Un rendez-vous pour " + client + " avec " + veterinaire + " a été réservé le "
                + dateTime.toString() + "\n");
    }
    
    //supprime un rdv en fonction de la date et du client
    public void deleteRdv(String date, String client) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        String[] columns = { "debut", "client" };
        String[] values = { dateTime.toString(), client };
        this.dbInteraction.delete("rendezvous", columns, values);
        System.out.println("Un rendez-vous pour " + client + " le " + dateTime.toString() + " a été supprimé "
                + "\n");
    }
}
