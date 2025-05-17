package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de récupérer toutes les missions avec leur statut associé
 */
public class RequeteMissionSelectAll extends RequeteMission {
    /**
     * Retourne la requête pour sélectionner toutes les missions et leurs statuts
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "SELECT * FROM MISSION, STATUT WHERE MISSION.idSta=STATUT.idSta ORDER BY IDMIS DESC";
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
}