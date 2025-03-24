package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionAjouter extends RequeteMission {
    //Ne retourne qu'un string contenant la requete avec valeurs vides
    @Override
    public String requete() {
        return "INSERT INTO MISSION (IDMIS, TITREMIS, NBEMPMIS, DATEDEBUTMIS, DATEFINMIS, DESCRIPTION, DATECREATION, LOGINEMP, IDSTA) VALUES (SEQ_MISSION.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idMis INTO ?";
    }


    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.parseInt(id[0])); //idMis
        prSt.setString(2, id[1]);//titreMis
        prSt.setInt(3, Integer.parseInt(id[2])); //nbEmpMis
        prSt.setDate(4, Date.valueOf(id[3])); //DateDebutMis
        prSt.setDate(5, Date.valueOf(id[4])); //DateFinMis
        prSt.setString(6, id[5]);//description
        prSt.setDate(7, Date.valueOf(id[6])); //DateCreation
        prSt.setString(8, id[7]);//loginEmp
        prSt.setInt(9, Integer.parseInt(id[8])); //idSta
    }

    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        ps.setString(1, mis.getTitreMis());        // TITREMIS
        ps.setInt(2, mis.getNbEmpMis());             // NBEMPMIS
        ps.setDate(3, mis.getDateDebutMis());          // DATEDEBUTMIS
        ps.setDate(4, mis.getDateFinMis());            // DATEFINMIS
        ps.setString(5, mis.getDescription());       // DESCRIPTION
        ps.setDate(6, mis.getDateCreation());          // DATECREATION
        ps.setString(7, mis.getLoginEmp());          // LOGINEMP
        ps.setInt(8, mis.getIdSta());                // IDSTA
    }







}