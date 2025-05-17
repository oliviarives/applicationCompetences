package modele.dao.requetes.Mission;

import modele.Competence;
import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'associer une compétence à une mission dans la table NECESSITER
 */
public class RequeteMissionNecessiterCmp extends RequeteMission {
    /**
     * Retourne la requête pour insérer une ligne dans NECESSITER
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "INSERT INTO NECESSITER (IDCATCMP,IDCMP,IDMIS,NBEMPCMP) VALUES (?,?,?,?)";
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
     * Définit les paramètres pour insérer une compétence requise dans une mission
     * @param prSt statement préparé
     * @param mis mission concernée
     * @param cmpA compétence à associer
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    public void parametresRequeteMissionNecessiterCmp(PreparedStatement prSt, Mission mis, Competence cmpA) throws SQLException {
        prSt.setString(1, cmpA.getIdCatCmp());
        prSt.setInt(2, cmpA.getIdCmp());
        prSt.setInt(3, mis.getIdMission());
        prSt.setInt(4, 1);
    }

}
