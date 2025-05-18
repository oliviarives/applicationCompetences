package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'associer un employé à une mission dans la table COLLABORER
 */
public class RequeteMissionAjouterEmp extends RequeteMission{
    /**
     * Retourne la requête pour insérer une ligne dans COLLABORER
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "INSERT INTO COLLABORER (LOGINEMP,IDMIS) VALUES (?,?)";
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
     * Méthode non utilisée, lève une exception
     * @param prSt statement préparé
     * @param mission mission
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, Mission mission) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
    /**
     * Définit les paramètres de la requête à partir d'un login employé et d'un identifiant mission
     * @param prSt statement préparé
     * @param logemp login de l'employé
     * @param idm identifiant de la mission
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    public void parametres(PreparedStatement prSt,String logemp, int idm) throws SQLException {
        prSt.setString(1, logemp);
        prSt.setInt(2, idm);
    }
    /**
     * Définit les paramètres à partir d'un objet Mission et du login de l'employé
     * @param prSt statement préparé
     * @param mis mission concernée
     * @param logemp login de l'employé
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    public void parametres(PreparedStatement prSt, Mission mis, String logemp) throws SQLException {
        prSt.setString(1, logemp);
        prSt.setInt(2, mis.getIdMission());
    }
}
