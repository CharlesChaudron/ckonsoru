package com.fges.ckonsoru.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.fges.ckonsoru.dao.ListeAttenteDAO;
import com.fges.ckonsoru.model.ListeAttente;
import com.fges.ckonsoru.model.RendezVous;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    @Override
    public List<RendezVous> listeAttentePourClient(String nomClient) {
    
        List<RendezVous> rdvs = new ArrayList<>();
        
        try {
            PreparedStatement st = this.postgresConnexion.conn.prepareStatement(
                    "SELECT la_client, la_numtel, la_dateauplustard, la_creneaupropose, la_datedemande \n" +
                    "FROM listeattente\n" +
                    "WHERE rv_client = ?\n" +
                    "ORDER BY la_creneaupropose DESC;");
            st.setObject(1, nomClient);
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                rdvs.add(parseRdv(rs));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.err.println("Problème lors de la requête listeRendezVousPourClient");
            System.err.println(ex.getMessage());
        }
        return rdvs;
    }
    private RendezVous parseRdv(ResultSet rs) throws SQLException {
        RendezVous rdv = new RendezVous(
                rs.getString("la_client"),
                (LocalDateTime) rs.getObject("la_creneaupropose",LocalDateTime.class),
                rs.getString("la_numtel"));
        return rdv;
    }

    
}
