package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de récupérer un employé en fonction de son login
 */
public class RequeteEmployeById extends RequeteEmploye {
    /**
     * Retourne la requête de sélection d'un employé par son login
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE WHERE LOGINEMP = ?";
    }
    /**
     * Définit le login employé comme paramètre de la requête via un tableau d'identifiants
     * @param prSt statement préparé
     * @param id tableau d'identifiants
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }
    /**
     * Définit le login employé comme paramètre de la requête à partir d'un objet Employe
     * @param prSt statement préparé
     * @param obj employé dont on veut récupérer les infos
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
    }
}
