package com.fges.ckonsoru.dao;

import com.fges.ckonsoru.model.ListeAttente;
import com.fges.ckonsoru.model.RendezVous;
import java.util.List;

public interface ListeAttenteDAO {
    
    public void creerListeAttente(ListeAttente listeAttente);

    public List<RendezVous> listeAttentePourClient(String client);
}
