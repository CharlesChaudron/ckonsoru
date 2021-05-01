package com.fges.ckonsoru;

import java.util.Map;

public class PostgresInteraction implements DatabaseInteraction {

    @Override
    public Map<Integer, Map<String, String>> selectElementsFromWhere(String[] tags, String from, String where,
            String equals) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String dbMode() {
        return "bdd";
    }

    @Override
    public Integer insert(String table, String[] columns, String[] values) {
        return 1;
    }
}
