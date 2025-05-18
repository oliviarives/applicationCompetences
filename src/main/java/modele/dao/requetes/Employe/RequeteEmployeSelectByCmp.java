package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de récupérer tous les employés possédant au moins une compétence
 */
public class RequeteEmployeSelectByCmp extends RequeteEmploye{
    /**
     * Retourne la requête SQL sélectionnant les employés liés à des compétences via la table POSSEDER
     * @return requête avec jointures sur EMPLOYE, POSSEDER et COMPETENCE
     */
    @Override
    public String requete() {
        return "SELECT DISTINCT * FROM EMPLOYE E, POSSEDER P, COMPETENCE C WHERE E.LOGINEMP=P.LOGINEMP AND P.IDCATCMP=C.IDCATCMP AND P.IDCMP=C.IDCMP";
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
     * @param obj employé
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
