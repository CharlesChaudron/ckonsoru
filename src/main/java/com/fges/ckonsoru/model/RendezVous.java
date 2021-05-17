/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru.model;

import java.time.LocalDateTime;

/**
 *
 * @author julie.jacques
 */
public class RendezVous {
    
    protected String nomClient;
    protected int numero;
    protected LocalDateTime date;
    protected String veterinaire;
    
    public RendezVous(String nomClient, 
                      LocalDateTime date, 
                      String veterinaire){
        this.nomClient      = nomClient;
        this.date           = date;
        this.veterinaire    = veterinaire;
    }

    public String getNomClient() {
        return nomClient;
    }
    
    public int getIdClient() {
        return numero;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getVeterinaire() {
        return veterinaire;
    }

    public void setVeterinaire(String veterinaire) {
        this.veterinaire = veterinaire;
    }
    
}
