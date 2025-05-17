package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de modifier les informations d'un employé existant
 */
public class RequeteEmployeModifier extends RequeteEmploye {
    /**
     * Retourne la requête de mise à jour des champs d'un employé
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "UPDATE EMPLOYE SET PRENOMEMP = ?, NOMEMP = ?, DATEENTREEEMP = ?, POSTEEMP = ? WHERE LOGINEMP = ?";
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
     * Définit les paramètres du PreparedStatement à partir de l'objet Employe pour modifier ses données
     * @param prSt statement préparé
     * @param obj employé à mettre à jour
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getPrenom());
        prSt.setString(2, obj.getNom());
        prSt.setDate(3, new java.sql.Date(obj.getDateEntree().getTime()));
        prSt.setString(6, obj.getPoste());
        prSt.setString(7, obj.getLogin());
    }
}

