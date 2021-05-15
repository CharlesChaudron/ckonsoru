package com.fges.ckonsoru.dao;

import java.util.List;

import com.fges.ckonsoru.model.Annulation;

public interface AnnulationDAO {

    /**
     * Trace l'annulation dans la table annulation si elle est faite dans les 24 heures avant le rdv.
     * @param annulation
     */
    public void tracerAnnulation(Annulation annulation);

    public List<Annulation> listeAnnulations();
}
