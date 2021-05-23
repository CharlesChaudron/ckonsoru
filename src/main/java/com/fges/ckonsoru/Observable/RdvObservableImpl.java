package com.fges.ckonsoru.Observable;

import java.util.LinkedList;
import java.util.List;

import com.fges.ckonsoru.Observer.RdvObserver;
import com.fges.ckonsoru.model.RendezVous;

public class RdvObservableImpl implements RdvObservable {
    List<RdvObserver> observateurs = new LinkedList<>();

    @Override
    public void enregistrerObservateur(RdvObserver observateur) {
        observateurs.add(observateur);
        
    }

    @Override
    public void supprimerObservateur(RdvObserver observateur) {
        observateurs.remove(observateur);
        
    }

    @Override
    public void notifierObservateurs(RendezVous rendezVous) {
        for (RdvObserver observer : observateurs) {
            observer.ajouterAnnulation(rendezVous);
        }
        
    }
    
}
