package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'ajouter un nouvel employé dans la base de données
 */
public class RequeteEmployeAjouter extends RequeteEmploye {
    /**
     * Retourne la requête d'insertion d'un employé
     * @return requête SQL sous forme de chaîne
     */
    @Override
    public String requete() {
        return "INSERT INTO EMPLOYE (LOGINEMP,PRENOMEMP,NOMEMP,DATEENTREEEMP,MDPEMP,ACTIF,POSTEEMP) VALUES (?,?,?,?,?,?,?)";
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
     * Définit les paramètres du PreparedStatement à partir de l'objet Employe
     * @param prSt statement préparé
     * @param obj employé à insérer
     * @throws SQLException en cas d'erreur lors de l'injection des paramètres
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
        prSt.setString(2, obj.getPrenom());
        prSt.setString(3, obj.getNom());
        prSt.setDate(4, new java.sql.Date(obj.getDateEntree().getTime()));
        prSt.setString(5, obj.gethashedPwd());
        prSt.setInt(6, 1); 
        prSt.setString(7, obj.getPoste());
    }

}
