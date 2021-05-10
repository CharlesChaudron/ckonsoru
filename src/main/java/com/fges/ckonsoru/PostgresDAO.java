package com.fges.ckonsoru;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;

public class PostgresDAO implements DAOInterface {

    private static PostgresDAO uniqueInstance;
    private Connection c;

    private PostgresDAO() {
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        String url = properties.getProperty("bdd.url");
        String login = properties.getProperty("bdd.mdp");
        String mdp = properties.getProperty("bdd.login");
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, login, mdp);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        this.c = c;
    }

    public static PostgresDAO getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new PostgresDAO();
        }
        return uniqueInstance;
    }

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

    @Override
    public Integer delete(String table, String[] columns, String[] values) {
        return 1;
    }
    
}
