/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    
    public static void main(String args[]){
        
        System.out.println("Bienvenue sur Clinique Konsoru !");
        
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        String persistence = properties.getProperty("persistence");
        DatabaseInteraction db;
        if (persistence.equals("xml")) {
            db = new XmlInteraction("src/main/resources/datas.xml");
        } else if (persistence.equals("bdd")) {
            db = new PostgresInteraction();
        } else {
            System.out.println("Mode de persistence inconnu.");
            return;
        }
                
        Menu menu = new Menu();
        Manager manager = new Manager();
        
        
        int choix;
        int action;
        do {
            menu.afficher();
            choix = menu.attendreChoix();
            action = manager.executerAction(choix);
        } while (action != 0);
        
        RdvManager rdvManager = new RdvManager(db);
        rdvManager.afficherDisponibilitesJour("18/03/2021");
        //fin tests
    }

    
}
