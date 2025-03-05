package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionAjouter extends RequeteMission {
    //Ne retourne qu'un string contenant la requete avec valeurs vides
    @Override
    public String requete() {
        return "INSERT INTO MISSION (IDMIS,TITREMIS,NBEMPMIS,DATEDEBUTMIS,DATEFINMIS,DESCRIPTION,DATECREATION,LOGINEMP,ISDTA) VALUES (?,?,?,…)";
    }

    @Override
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

    @Override
    public void parametres(PreparedStatement prSt, Mission obj) throws SQLException {
        prSt.setInt(1, obj.getIdMission()); //idMis
        prSt.setString(2, obj.getTitreMis());//titreMis
        prSt.setInt(3, obj.getNbEmpMis()); //nbEmpMis
        prSt.setDate(4, obj.getDateDebutMis()); //DateDebutMis
        prSt.setDate(5, obj.getDateFinMis()); //DateFinMis
        prSt.setString(6, obj.getDescription());//description
        prSt.setDate(7, obj.getDateCreation()); //DateCreation
        prSt.setInt(8, obj.getNbEmpMis());//loginEmp
        prSt.setInt(9, 1); //idSta
    }

}