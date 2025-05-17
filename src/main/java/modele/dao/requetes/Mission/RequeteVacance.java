package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'insérer une mission de type vacance dans la base
 */
public class RequeteVacance extends RequeteMission{
    /**
     * Retourne la requête pour insérer une mission avec le statut Vacance
     */
    @Override
    public String requete() {
        return "INSERT INTO MISSION (IDMIS, TITREMIS, NBEMPMIS, DATEDEBUTMIS, DATEFINMIS, DESCRIPTION, DATECREATION, LOGINEMP, IDSTA) " +
                "VALUES (SEQ_MISSION.NEXTVAL, 'Vacance', 1, ?, ?, 'Vacance', ?, ?, 5) ";
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
     * @param ps statement préparé
     * @param mis mission
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
