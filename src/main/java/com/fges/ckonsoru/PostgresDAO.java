package com.fges.ckonsoru;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public class PostgresDAO implements DAOInterface {

    private static PostgresDAO uniqueInstance;
    private Connection c;

    private PostgresDAO() {
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        String url = properties.getProperty("bdd.url");
        String mdp = properties.getProperty("bdd.mdp");
        String login = properties.getProperty("bdd.login");
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, login, mdp);
            c.setAutoCommit(false);
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
        
        return null;
    }
    
    @Override
    public String dbMode() {
        return "bdd";
    }

    @Override
    public Integer insert(String table, String[] columns, String[] values) {
        try {
            String deleteString = "INSERT INTO ?";
            for (int i = 0; i < columns.length; i++) {
                PreparedStatement deleStatement = this.c.prepareStatement(deleteString);
                deleStatement.setString(1, table);
                deleStatement.setString(2, columns[i]);
                deleStatement.setString(3, values[i]);
                System.out.println(deleStatement.toString()); // statement ok mais erreur quand même...

                deleStatement.executeUpdate();
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 1;
    }

    @Override
    public Integer delete(String table, String[] columns, String[] values) {
        try {
            String deleteString = "DELETE FROM ? WHERE ?=?";
            for (int i = 0; i < columns.length; i++) {
                PreparedStatement deleStatement = this.c.prepareStatement(deleteString);
                deleStatement.setString(1, table);
                deleStatement.setString(2, columns[i]);
                deleStatement.setString(3, values[i]);
                System.out.println(deleStatement.toString()); // statement ok mais erreur quand même...
                
                deleStatement.executeUpdate();
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return 1;
    }
    
}
