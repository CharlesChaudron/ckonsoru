package com.fges.ckonsoru;

public class Rdv {
    private String debut;
    private String client;
    private String veterinaire;


    public String getDebut() {
        return this.debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getVeterinaire() {
        return this.veterinaire;
    }

    public void setVeterinaire(String veterinaire) {
        this.veterinaire = veterinaire;
    }


    @Override
    public String toString() {
        return this.getVeterinaire() + " : " + this.getDebut();
    }


}
