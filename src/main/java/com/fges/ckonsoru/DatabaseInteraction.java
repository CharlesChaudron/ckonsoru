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
    public Map<Integer, Map<String, String>> selectElementsFromWhere(String[] tags, String from, String where, String equals);
}
