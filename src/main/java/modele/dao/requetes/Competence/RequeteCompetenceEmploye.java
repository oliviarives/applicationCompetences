package modele.dao.requetes.Competence;

import modele.Competence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête qui permet de récupérer les compétences associées à un employé via son login
 */
public class RequeteCompetenceEmploye extends RequeteCompetence {
    /**
     * Retourne la requête sélectionnant les compétences liées à un employé
     * @return chaîne de la requête
     */
    @Override
    public String requete() {
        return "SELECT c.IDCMP, c.IDCATCMP, c.NOMCMPEN, c.NOMCMPFR FROM COMPETENCE c, POSSEDER p WHERE c.IDCMP = p.IDCMP AND c.IDCATCMP = p.IDCATCMP AND p.LOGINEMP = ?";
    }
    /**
     * Définit le login de l'employé comme paramètre de la requête
     * @param prSt statement préparé
     * @param id login de l'employé
     * @throws SQLException en cas d'erreur lors de l'injection du paramètre
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
    /**
     * Méthode non utilisée dans cette requête, lève une exception
     * @param prSt statement préparé
     * @param obj objet compétence
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
