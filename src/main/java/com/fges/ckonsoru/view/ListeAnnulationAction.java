package com.fges.ckonsoru.view;


import java.util.List;
import java.util.Scanner;

import com.fges.ckonsoru.dao.AnnulationDAO;
import com.fges.ckonsoru.model.Annulation;

public class ListeAnnulationAction extends ActionConsole {
    protected AnnulationDAO annDao;

    public ListeAnnulationAction(int numero, String description, AnnulationDAO rdvDao) {
        super(numero, description);
        this.annDao = rdvDao;
    }

    @Override
    public void executer(Scanner scanner) {
        System.out.println("Affichage des annulations");
        List<Annulation> anns = annDao.listeAnnulations();
        System.out.println("Liste des annulations");
        for (Annulation ann : anns) {
            System.out.println(ann.getNomClient() + " le " + ann.getCreneau() + " (" + ann.getDelai().toString() + " avant)");
        }

    }
}
