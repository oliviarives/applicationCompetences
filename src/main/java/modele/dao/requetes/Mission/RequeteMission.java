package modele.dao.requetes.Mission;


import modele.Mission;
import modele.dao.requetes.Requete;
/**
 * Requête abstraite spécifique au modèle Mission
 * Sert de base aux requêtes liées aux missions
 */
public  abstract class RequeteMission extends Requete<Mission> {
    /**
     * Retourne la requête sous forme de chaîne
     * @return requête SQL
     */
    public abstract String requete();
}
