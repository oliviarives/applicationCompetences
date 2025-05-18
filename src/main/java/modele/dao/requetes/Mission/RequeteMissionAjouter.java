package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Requête permettant d'ajouter une mission dans la base de données
 */
public class RequeteMissionAjouter extends RequeteMission {
    /**
     * Retourne la requête d'insertion d'une mission
     * @return requête SQL
     */
    @Override
    public String requete() {
        return "INSERT INTO MISSION (IDMIS, TITREMIS, NBEMPMIS, DATEDEBUTMIS, DATEFINMIS, DESCRIPTION, DATECREATION, LOGINEMP, IDSTA) VALUES (SEQ_MISSION.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idMis INTO ?";
    }
    /**
     * Définit les paramètres
     * @param prSt statement préparé
     * @param id tableau contenant les données de la mission
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.parseInt(id[0]));
        prSt.setString(2, id[1]);
        prSt.setInt(3, Integer.parseInt(id[2]));
        prSt.setDate(4, Date.valueOf(id[3]));
        prSt.setDate(5, Date.valueOf(id[4]));
        prSt.setString(6, id[5]);
        prSt.setDate(7, Date.valueOf(id[6]));
        prSt.setString(8, id[7]);
        prSt.setInt(9, Integer.parseInt(id[8]));
    }
    /**
     * Définit les paramètres du PreparedStatement à partir d'un objet Mission
     * @param ps statement préparé
     * @param mis mission à insérer
     * @throws SQLException en cas d'erreur lors de l'injection
     */
    @Override
    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        ps.setString(1, mis.getTitreMis());
        ps.setInt(2, mis.getNbEmpMis());
        ps.setDate(3, mis.getDateDebutMis());
        ps.setDate(4, mis.getDateFinMis());
        ps.setString(5, mis.getDescription());
        ps.setDate(6, mis.getDateCreation());
        ps.setString(7, mis.getLoginEmp());
        ps.setInt(8, mis.getIdSta());
    }
}