package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant de supprimer tous les employés associés à une mission dans la table COLLABORER
 */
public class RequeteMissionDeleteEmp extends RequeteMission{
    /**
     * Retourne la requête pour supprimer les lignes de COLLABORER liées à une mission
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "DELETE FROM COLLABORER WHERE IDMIS=?";
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
     * Définit l'identifiant de la mission pour supprimer les collaborateurs associés
     * @param ps statement préparé
     * @param m mission concernée
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement ps, Mission m) throws SQLException {
        ps.setInt(1,m.getIdMission());
    }
}
