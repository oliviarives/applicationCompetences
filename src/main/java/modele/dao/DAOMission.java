package modele.dao;

import modele.Competence;
import modele.Mission;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Mission.*;
import oracle.jdbc.OraclePreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * DAO pour gérer l'accès aux missions dans la base de données
 * Permet d'ajouter, modifier, rechercher ou lier des missions à des compétences ou des employés
 */
public class DAOMission {
    /**
     * Connexion à la base de données
     */
    private Connection cn;
    /**
     * Initialise le DAO et établit la connexion
     * @throws SQLException en cas d'erreur de connexion
     */
    public DAOMission() throws SQLException {
        this.cn = CictOracleDataSource.getConnectionBD();

    }
    /**
     * Crée une instance de Mission à partir d’un ResultSet
     * @param rset résultat SQL
     * @return objet Mission
     * @throws SQLException en cas d’accès aux données échoué
     */
    public Mission creerInstance(ResultSet rset) throws SQLException {
        return new Mission(
                rset.getInt("idMis"),
                rset.getString("titreMis"),
                rset.getDate("dateDebutMis"),
                rset.getDate("dateFinMis"),
                rset.getString("description"),
                rset.getDate("dateCreation"),
                rset.getInt("nbEmpMis"),
                rset.getString("nomSta"),
                rset.getInt("idSta"),
                rset.getString("loginEmp")
        );
    }
    /**
     * Ajoute une mission à la base et récupère son ID
     * @param mis mission à insérer
     * @throws SQLException si l’insertion échoue
     */
    public void ajouterMission(Mission mis) throws SQLException {
        RequeteMissionAjouter req = new RequeteMissionAjouter();
        String sql = req.requete();
        try(PreparedStatement ps = cn.prepareStatement(sql)) {
            OraclePreparedStatement ops = (OraclePreparedStatement) ps;
            ops.registerReturnParameter(9, java.sql.Types.INTEGER);
            req.parametres(ps, mis);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la mission a échoué, aucune ligne affectée.");
            }
            try (ResultSet rs = ops.getReturnResultSet()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    mis.setIdMission(generatedId);
                } else {
                    throw new SQLException("L'ajout de la mission a échoué, aucun id obtenu.");
                }
            }
        }
    }
    /**
     * Supprime les compétences liées à la mission puis ajoute celles passées en paramètre
     * @param mis mission
     * @param lcmpA liste de compétences à ajouter
     * @throws SQLException en cas d’erreur SQL
     */
    public void ajouterCmpToMission(Mission mis, List<Competence> lcmpA) throws SQLException{
        RequeteMissionDeleteCmp reqDC = new RequeteMissionDeleteCmp();
        PreparedStatement psDel = cn.prepareStatement(reqDC.requete());
        reqDC.parametres(psDel, mis);
        psDel.executeQuery();
        for (Competence cmp : lcmpA) {
            RequeteMissionNecessiterCmp req = new RequeteMissionNecessiterCmp();
            PreparedStatement ps = cn.prepareStatement(req.requete());
            req.parametresRequeteMissionNecessiterCmp(ps, mis,cmp);
            ps.executeQuery();
        }
    }
    /**
     * Supprime les employés liés à la mission puis ajoute ceux passés en paramètre
     * @param mis mission
     * @param lEmpA liste des logins des employés à ajouter
     * @throws SQLException en cas d’erreur SQL
     */
    public void ajouterEmpToMission(Mission mis, List<String> lEmpA) throws SQLException{
        RequeteMissionDeleteEmp reqDE = new RequeteMissionDeleteEmp();
        PreparedStatement psDel = cn.prepareStatement(reqDE.requete());
        reqDE.parametres(psDel, mis);
        for (String l: lEmpA) {
            RequeteMissionAjouterEmp req = new RequeteMissionAjouterEmp();
            PreparedStatement ps = cn.prepareStatement(req.requete());
            req.parametres(ps, mis, l);
            ps.executeQuery();
        }
    }
    /**
     * Retourne la liste des compétences nécessaires à une mission
     * @param idMis identifiant de la mission
     * @return liste de compétences
     */
    public List<Competence> getCompetencesForMission(int idMis) {
        List<Competence> competences = new ArrayList<>();
        String sql = "SELECT cmp.idCmp, cmp.idCatCmp, cmp.nomCmpEn, cmp.nomCmpFr FROM necessiter n JOIN competence cmp ON n.idcmp = cmp.idcmp AND n.idcatcmp = cmp.idcatcmp WHERE n.idmis = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMis);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Competence cmp = new Competence(
                            rs.getInt("idCmp"),
                            rs.getString("idCatCmp"),
                            rs.getString("nomCmpEn"),
                            rs.getString("nomCmpFr")
                    );
                    competences.add(cmp);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return competences;
    }
    /**
     * Récupère toutes les missions
     * @return liste de missions
     */
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
    /**
     * Compte le nombre de missions pour un statut donné
     * @param statusId identifiant d'un statut
     * @return nombre de missions ayant ce statut
     */
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
    /**
     * Calcule le nombre de missions par mois
     * @return map mois/nombre de missions
     */
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
    /**
     * Met à jour le statut d’une mission
     * @param mission mission concernée
     * @param newStatusId nouvel identifiant de statut
     * @throws SQLException en cas d’erreur SQL
     */
    public void updateMissionStatus(Mission mission, int newStatusId) throws SQLException {
        String sql = "UPDATE mission SET idsta=? WHERE idMis=?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, newStatusId);
            ps.setInt(2, mission.getIdMission());
            ps.executeUpdate();
        }
    }
    /**
     * Vérifie si un titre de mission est déjà utilisé dans une mission non terminée
     * @param titre titre de la mission
     * @return true si le titre est déjà utilisé
     */
    public boolean missionTitleExists(String titre) {
        String sql = "SELECT * FROM mission WHERE LOWER(titreMis) = ? AND idsta <> 4";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, titre.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * Recherche une mission par son identifiant
     * @param idMis identifiant de la mission
     * @return objet Mission ou null si introuvable
     */
    public Mission getMissionById(int idMis) {
        String sql = "SELECT m.*, nomsta FROM mission m LEFT JOIN statut s ON m.idsta = s.idsta WHERE m.idMis = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMis);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return creerInstance(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * Met à jour les informations d’une mission
     * @param mis mission à modifier
     * @throws SQLException en cas d’erreur SQL
     */
    public void updateMissionModifier(Mission mis) throws SQLException {
        RequeteMissionModifier req = new RequeteMissionModifier();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, mis);
        try {
            ps.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}