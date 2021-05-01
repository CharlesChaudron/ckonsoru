package com.fges.ckonsoru;

import java.util.Map;

public interface DatabaseInteraction {
    /**
     * 
     * @param tags liste des tags à sélectionner
     * @param from nom de la table où rechercher
     * @param where tag à tester
     * @param equals ce à quoi le tag doit être égal
     * @return un map avec les id en clé et en valeur un map qui contient le tag et la valeur du tag
     */
    public Map<Integer, Map<String, String>> selectElementsFromWhere(String[] tags, String from, String where,
            String equals);
    
    public String dbMode();


    /**
     * 
     * @param table rendez-vous ou disponibilites
     * @param columns les tags dans lesquels ajouter les values
     * @param values values à ajouter dans les tags correspondants
     * @return int 0 si ok, 1 si echec
     */
    public Integer insert(String table, String[] columns, String[] values);
}
