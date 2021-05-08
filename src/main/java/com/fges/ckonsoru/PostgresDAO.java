package com.fges.ckonsoru;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

public class PostgresDAO implements DAOInterface {

    private static PostgresDAO uniqueInstance;

    private PostgresDAO() {
        
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
    String url = "jdbc:postgresql://localhost:5432/konsoru/";
    Connection conn = DriverManager.getConnection(url);
    
    int foovalue = 1;
    PreparedStatement st = conn.prepareStatement("SELECT * FROM disponibilite WHERE columnfoo = ?");
    st.setInt(1, foovalue);
    ResultSet rs = st.executeQuery();
    while (rs.next())
    {
        System.out.print("Column 1 returned ");
        System.out.println(rs.getString(1));
    }
    rs.close();
    st.close();
    
}
