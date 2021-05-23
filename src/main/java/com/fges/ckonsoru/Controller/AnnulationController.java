package com.fges.ckonsoru.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fges.ckonsoru.Observer.RdvObserver;
import com.fges.ckonsoru.dao.AnnulationDAO;
import com.fges.ckonsoru.model.Annulation;
import com.fges.ckonsoru.model.RendezVous;

public class AnnulationController implements RdvObserver {
    protected AnnulationDAO annDao;

    public AnnulationController(AnnulationDAO annDao) {
        this.annDao = annDao;
    }


    @Override
    public void ajouterAnnulation(RendezVous rendezVous) {
        // devrait être fait dans un controller et pas dans la vue pour respecter le mvc
        Long delaiSecondes = Duration.between(LocalDateTime.now(), rendezVous.getDate()).getSeconds();
        if (delaiSecondes < 86400) {
            LocalTime delai = LocalTime.ofSecondOfDay(delaiSecondes);
            Annulation annulation = new Annulation(rendezVous.getNomClient(), rendezVous.getDate(), "", delai);
            this.annDao.tracerAnnulation(annulation);
        }
        
    }
}
