package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête générique abstraite pour les entités manipulées par les DAO
 * @param <T> type du modèle lié à la requête
 */
public abstract class Requete<T> {
    /**
     * Retourne la requête sous forme de chaîne
     * @return requête SQL
     */
    public abstract String requete();
    /**
     * Définit les paramètres du PreparedStatement à partir des identifiants
     * @param prSt statement préparé
     * @param id identifiants à insérer
     * @throws SQLException en cas d'erreur SQL
     */
    public abstract void parametres(PreparedStatement prSt, String... id) throws SQLException;
    /**
     * Définit les paramètres du PreparedStatement à partir d'un objet du modèle
     * @param prSt statement préparé
     * @param t objet métier
     * @throws SQLException en cas d'erreur SQL
     */
    public abstract void parametres(PreparedStatement prSt, T t) throws SQLException;


}
