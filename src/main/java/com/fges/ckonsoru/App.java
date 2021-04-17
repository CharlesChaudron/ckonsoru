/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

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


        
        
        //tests
        
        /*String[] tags = { "jour", "debut", "fin", "veterinaire" };
        Map<Integer, Map<String, String>> disponibilites = db.selectElementsFromWhere(tags, "disponibilite", "jour",
                "samedi");
        System.out.println(disponibilites.size());
        for (int i = 0; i < disponibilites.size(); i++) {
            Map<String, String> tag = disponibilites.get(i);
            System.out.println("tag numero " + i + " :\n" + "jour : " + tag.get("jour") + "\n" + "debut : "
                    + tag.get("debut") + "\n" + "fin : " + tag.get("fin") + "\n" + "veterinaire : "
                    + tag.get("veterinaire") + "\n");
        }*/
        String[] tags = { "debut", "client", "veterinaire" };
        Map<Integer, Map<String, String>> disponibilites = db.selectElementsFromWhere(tags, "rendezvous", "debut",
                "2021-03-18");
        System.out.println(disponibilites.size());
        for (int i = 0; i < disponibilites.size(); i++) {
            Map<String, String> tag = disponibilites.get(i);
            System.out.println("tag numero " + i + " :\n" + "debut : " + tag.get("debut") + "\n" + "client : "
                    + tag.get("client") + "\n" + "veterinaire : " + tag.get("veterinaire") + "\n");
        }
        //fin tests
    }

    
}
