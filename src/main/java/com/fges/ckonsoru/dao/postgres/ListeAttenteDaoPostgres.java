package com.fges.ckonsoru.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.fges.ckonsoru.dao.ListeAttenteDAO;
import com.fges.ckonsoru.model.ListeAttente;

public class ListeAttenteDaoPostgres implements ListeAttenteDAO {
    protected PostgresConnexion postgresConnexion;

    public ListeAttenteDaoPostgres(PostgresConnexion postgresConnexion) {
        this.postgresConnexion = postgresConnexion;
    }

    @Override
    public void creerListeAttente(ListeAttente listeAttente) {
        try {
            PreparedStatement st = this.postgresConnexion.conn
                    .prepareStatement("INSERT INTO listeattente (la_client, la_numtel, la_dateauplustard, la_creneaupropose, la_datedemande)\n"
                            + "   VALUES(?,?,?,?,?);");
            st.setObject(1, listeAttente.getNomClient());
            st.setObject(2, listeAttente.getNumTel());
            st.setObject(3, listeAttente.getDateAuPlusTard());
            st.setObject(4, listeAttente.getCreneauPropose());
            st.setObject(5, LocalDateTime.now());
            st.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Problème lors de la requête creerLsiteAttente");
            System.err.println(e.getMessage());
        }
    }

    
}
