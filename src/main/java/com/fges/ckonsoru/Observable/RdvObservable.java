package com.fges.ckonsoru.Observable;

import com.fges.ckonsoru.Observer.RdvObserver;
import com.fges.ckonsoru.model.RendezVous;

public interface RdvObservable {
    public void enregistrerObservateur(RdvObserver observateur);

    public void supprimerObservateur(RdvObserver observateur);

    public void notifierObservateurs(RendezVous rendezVous);
}
