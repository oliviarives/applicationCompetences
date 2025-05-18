package modele.dao.requetes.Competence;

import modele.Competence;
import modele.dao.requetes.Requete;
/**
 * Requête SQL abstraite spécifique au modèle competence
 * Sert de base aux requêtes liées aux compétences dans le DAO
 */
public abstract class RequeteCompetence extends Requete<Competence> {
    /**
     * Retourne la requête SQL sous forme de chaîne
     * @return requête SQL
     */
    public abstract String requete();
}