package modele.dao.requetes.Competence;

import modele.Competence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCompetenceSelectAll extends RequeteCompetence {
    @Override
    public String requete() {
        return "SELECT * FROM COMPETENCE";
    }

    /**
     * Définit les paramètres de la requête SQL pour la sélection des compétences.
     *
     * @param prSt PreparedStatement utilisé pour exécuter la requête SQL.
     * @param id Un tableau de chaînes de caractères représentant les identifiants à insérer.
     * @throws SQLException Si une erreur SQL survient.
     */
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    /**
     * Définit les paramètres de la requête SQL pour la sélection des compétences.
     *
     * @param prSt PreparedStatement utilisé pour exécuter la requête SQL.
     * @param obj L'objet Competence contenant les valeurs à sélectionner dans la base de données.
     * @throws SQLException Si une erreur SQL survient.
     */
    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {

    }
}