package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de récupérer tous les employés de la base de données
 */
public class RequeteEmployeSelectAll extends RequeteEmploye{
    /**
     * Retourne la requête pour sélectionner tous les employés
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE";
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
