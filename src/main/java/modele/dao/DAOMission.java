package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Competence;
import modele.Mission;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Mission.*;
import modele.dao.requetes.Requete;

public class DAOMission {

    private Connection cn;

    public DAOMission() throws SQLException {
        CictOracleDataSource.creerAcces("BSC3991A","2002Aralc.31");
        this.cn = CictOracleDataSource.getConnectionBD();

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



}
