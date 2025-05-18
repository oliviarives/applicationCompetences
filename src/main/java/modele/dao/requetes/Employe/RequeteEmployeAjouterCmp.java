package modele.dao.requetes.Employe;
import modele.Competence;
import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'associer une compétence à un employé dans la table POSSEDER
 */
public class RequeteEmployeAjouterCmp extends RequeteEmploye {
    /**
     * Retourne la requête pour insérer une association employé-compétence
     * @return requête d'insertion dans POSSEDER
     */
    @Override
    public String requete() {
            return "INSERT INTO POSSEDER (LOGINEMP, IDCATCMP, IDCMP) VALUES (?, ?, ?)";
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
     * @param employe objet employé
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe employe) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
    /**
     * Définit les paramètres de l'insertion d'une compétence pour un employé
     * @param prSt statement préparé
     * @param loginEmp login de l'employé
     * @param cmp compétence à associer
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    public void parametres(PreparedStatement prSt, String loginEmp, Competence cmp) throws SQLException {
            prSt.setString(1, loginEmp);
            prSt.setString(2, cmp.getIdCatCmp());
            prSt.setInt(3, cmp.getIdCmp());
    }

}
