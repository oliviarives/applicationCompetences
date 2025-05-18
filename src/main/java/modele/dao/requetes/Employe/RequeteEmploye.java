package modele.dao.requetes.Employe;

import modele.Employe;
import modele.dao.requetes.Requete;
/**
 * Requête abstraite spécifique au modèle Employé
 * Sert de base aux requêtes liées aux employés
 */
public abstract class RequeteEmploye extends Requete<Employe> {
    /**
     * Retourne la requête sous forme de chaîne
     * @return requête SQL
     */
    public abstract String requete();
}
