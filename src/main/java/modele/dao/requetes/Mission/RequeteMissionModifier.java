package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de modifier les informations d'une mission
 */
public class RequeteMissionModifier extends RequeteMission{
    /**
     * Retourne la requête de mise à jour des champs d'une mission
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "UPDATE MISSION SET TITREMIS=?, DESCRIPTION=?, DATEDEBUTMIS=?, DATEFINMIS=?, LOGINEMP=?, NBEMPMIS=? WHERE IDMIS=? ";
    }
    /**
     * Méthode non utilisée, lève une exception
     * @param prSt statement préparé
     * @param id identifiants
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
    /**
     * Définit les paramètres de la requête à partir d'un objet Mission
     * @param ps statement préparé
     * @param mis mission à mettre à jour
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        ps.setString(1, mis.getTitreMis());
        ps.setString(2, mis.getDescription());
        ps.setDate(3, mis.getDateDebutMis());
        ps.setDate(4, mis.getDateFinMis());
        ps.setString(5, mis.getLoginEmp());
        ps.setInt(6, mis.getNbEmpMis());
        ps.setInt(7, mis.getIdMission());
    }
}
