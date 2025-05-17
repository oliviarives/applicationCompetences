package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de retirer toutes les compétences associées à un employé
 */
public class RequeteEmployeRetirerCmp extends RequeteEmploye {
    /**
     * Retourne la requête pour supprimer les compétences liées à un employé dans POSSEDER
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "DELETE FROM POSSEDER WHERE LOGINEMP = ?";
    }
    /**
     * Définit le login de l'employé comme paramètre à partir d'un tableau d'identifiants
     * @param prSt statement préparé
     * @param id tableau d'identifiants
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        // id[0] correspond au login de l'employé
        prSt.setString(1, id[0]);
    }
    /**
     * Définit le login de l'employé comme paramètre à partir d'un objet Employe
     * @param prSt statement préparé
     * @param obj employé concerné
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
    }
}
