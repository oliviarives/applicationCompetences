package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.Competence;
import modele.Mission;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Mission.*;
import modele.dao.requetes.Requete;

import static java.sql.DriverManager.getConnection;
import static modele.connexion.CictOracleDataSource.getConnectionBD;

public class DAOMission {

    private Connection cn;

    public DAOMission() throws SQLException {
        this.cn = getConnectionBD();

    }

    public Mission creerInstance(ResultSet rset) throws SQLException{
        return new Mission(
                rset.getString("titreMis"),
                rset.getDate("dateDebutMis"),
                rset.getDate("dateFinMis"),
                rset.getString("description"),
                rset.getDate("dateCreation"),
                rset.getInt("nbEmpMis"),
                rset.getString("nomSta")
        );
    }

    public void ajouterMission(Mission mis) throws SQLException{
        RequeteMissionAjouter req=new RequeteMissionAjouter();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, mis);
        ps.executeQuery();
    }

    public void ajouterMissionCmp(Mission mis, List<Competence> lcmpA) throws SQLException{
        for (Competence cmp : lcmpA) {
            RequeteMissionNecesiiterCmp req = new RequeteMissionNecesiiterCmp();
            PreparedStatement ps = cn.prepareStatement(req.requete());
            req.parametres(ps, mis,cmp);
            ps.executeQuery();
        }
    }

    public void ajouterMissionEmp(Mission mis,List<String> lEmpA) throws SQLException{
        for (String l: lEmpA) {
            RequeteCollaborerEmpMis req = new RequeteCollaborerEmpMis();
            PreparedStatement ps = cn.prepareStatement(req.requete());
            req.parametres(ps, mis, l);
            ps.executeQuery();
        }
    }

   /* public Mission getMissionTitre(String titre) throws SQLException{

            try {
                PreparedStatement ps = cn.prepareStatement(new RequeteMissionById().requete());
                try (ResultSet curseur = ps.executeQuery()){
                    Mission instance = creerInstance(curseur);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
    }*/

    public List<Mission> findAll() {
        List<Mission> resultats = new ArrayList<>();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteMissionSelectAll().requete());
            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    Mission instance = creerInstance(curseur);
                    resultats.add(instance);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultats;
    }

    public void modifierMission(Mission mis) throws SQLException {
        String sql = "UPDATE MISSION SET titreMis=?, dateDebutMis=?, dateFinMis=?, description=?, nbEmpMis=? WHERE idMis=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, mis.getTitreMis());
            ps.setDate(2, mis.getDateDebutMis());
            ps.setDate(3, mis.getDateFinMis());
            ps.setString(4, mis.getDescription());
            ps.setInt(5, mis.getNbEmpMis());
            ps.setInt(6, mis.getIdMission());
            ps.executeUpdate();
        }
    }

    public int countMissionsByStatus(int statusId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM mission WHERE idsta = ?";
        try (
             PreparedStatement stmt = cn.prepareStatement(sql)) {
            stmt.setInt(1, statusId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public Map<String, Integer> getMissionsStatsParMois() {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT TO_CHAR(dateDebutMis, 'Month') AS mois, COUNT(*) FROM mission GROUP BY TO_CHAR(dateDebutMis, 'Month')";
        try (
             Statement stmt = cn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String mois = rs.getString("mois").trim();
                int count = rs.getInt(2);
                stats.put(mois, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    public List<Mission> filterMissions(String nom, java.sql.Date date, Integer statutId) {
        List<Mission> resultats = new ArrayList<>();
        // Ajout du JOIN pour récupérer nomSta
        StringBuilder sql = new StringBuilder("SELECT m.*, s.nomSta FROM mission m LEFT JOIN statut s ON m.idsta = s.idsta WHERE 1=1 ");

        if (nom != null && !nom.isEmpty()) {
            sql.append("AND LOWER(m.titreMis) LIKE ? ");
        }
        if (date != null) {
            sql.append("AND m.dateDebutMis = ? ");
        }
        if (statutId != null) {
            sql.append("AND m.idsta = ? ");
        }

        try (PreparedStatement stmt = cn.prepareStatement(sql.toString())) {
            int index = 1;
            if (nom != null && !nom.isEmpty()) {
                stmt.setString(index++, "%" + nom.toLowerCase() + "%");
            }
            if (date != null) {
                stmt.setDate(index++, date);
            }
            if (statutId != null) {
                stmt.setInt(index++, statutId);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Mission instance = creerInstance(rs);
                    resultats.add(instance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }




}