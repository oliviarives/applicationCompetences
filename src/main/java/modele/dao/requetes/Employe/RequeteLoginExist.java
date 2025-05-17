package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de vérifier si un login employé existe déjà dans la base
 */
public class RequeteLoginExist extends RequeteEmploye {
    /**
     * Retourne la requête pour vérifier l'existence d'un login employé
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "SELECT 1 FROM EMPLOYE WHERE LOGINEMP = ?";
    }
    /**
     * Définit le login employé à vérifier comme paramètre de la requête
     * @param prSt statement préparé
     * @param params tableau des logins
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, String... params) throws SQLException {
        prSt.setString(1, params[0]);
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
