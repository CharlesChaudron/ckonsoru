package com.fges.ckonsoru.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fges.ckonsoru.dao.AnnulationDAO;
import com.fges.ckonsoru.model.Annulation;

public class AnnulationDaoPostgres implements AnnulationDAO {
    protected PostgresConnexion postgresConnexion;

    public AnnulationDaoPostgres(PostgresConnexion postgresConnexion) {
        this.postgresConnexion = postgresConnexion;
    }

    public void tracerAnnulation(Annulation annulation) {
        try {
            PreparedStatement st = this.postgresConnexion.conn
                    .prepareStatement("INSERT INTO annulation (ann_client, ann_creneau, vet_id, ann_delai)\n"
                            + "   VALUES(?, ?, (SELECT vet_id FROM veterinaire WHERE vet_nom = ?),\n"
                            + "           ?);	");
            st.setObject(1, annulation.getNomClient());
            st.setObject(2, annulation.getCreneau());
            st.setObject(3, annulation.getNomVeterinaire());
            st.setObject(4, annulation.getDelai());
            st.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Problème lors de la requête tracerAnnulation");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Annulation> listeAnnulations() {
        List<Annulation> rdvs = new ArrayList<>();

        try {
            PreparedStatement st = this.postgresConnexion.conn
                    .prepareStatement("SELECT ann_client, ann_creneau, ann_delai\n" + "FROM annulation\n" 
                    );
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                rdvs.add(parseAnnulation(rs));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            System.err.println("Problème lors de la requête listeAnnulations");
            System.err.println(ex.getMessage());
        }
        return rdvs;
    }

    private Annulation parseAnnulation(ResultSet rs) throws SQLException {
        Annulation rdv = new Annulation(rs.getString("ann_client"),
                (LocalDateTime) rs.getObject("ann_creneau", LocalDateTime.class), null, (LocalTime) rs.getObject("ann_delai", LocalTime.class));
        return rdv;
    }
}
