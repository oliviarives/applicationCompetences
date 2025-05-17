package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de récupérer les employés associés à des missions via la table COLLABORER
 */
public class RequeteEmployeSelectMis extends RequeteEmploye{
    /**
     * Retourne la requête SQL pour sélectionner les employés liés à des missions
     * @return requête avec jointures sur EMPLOYE, COLLABORER et MISSION
     */
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE E, COLLABORER C, MISSION M WHERE C.IDMIS=M.IDMIS AND E.LOGINEMP=C.LOGINEMP";
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
     * @param employe employé
     * @throws SQLException exception personnalisée à la place
     */
    @Override
    public void parametres(PreparedStatement prSt, Employe employe) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }


}
