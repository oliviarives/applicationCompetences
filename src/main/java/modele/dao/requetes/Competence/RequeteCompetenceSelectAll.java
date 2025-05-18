package modele.dao.requetes.Competence;

import modele.Competence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de sélectionner toutes les compétences de la table COMPETENCE
 */
public class RequeteCompetenceSelectAll extends RequeteCompetence {
    /**
     * Retourne la requête pour sélectionner toutes les compétences
     * @return requête sous forme de chaîne
     */
    @Override
    public String requete() {
        return "SELECT * FROM COMPETENCE";
    }

    /**
     * Définit les paramètres de la requête pour la sélection des compétences
     *
     * @param prSt PreparedStatement
     * @param id Un tableau de chaînes de caractères représentant les identifiants à insérer
     * @throws SQLException Si une erreur survient
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    /**
     * Définit les paramètres de la requête pour la sélection des compétences.
     *
     * @param prSt PreparedStatement
     * @param obj L'objet Competence contenant les valeurs à sélectionner
     * @throws SQLException Si une erreur survient
     */
    @Override
    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}