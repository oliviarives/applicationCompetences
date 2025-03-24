package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCollaborerEmpMis extends RequeteMission{
    @Override
    public String requete() {
        return "INSERT INTO COLLABORER (LOGINEMP,IDMIS) VALUES (?,?)";
    }

    public void parametres(PreparedStatement prSt,String logemp, int idm) throws SQLException {
        prSt.setString(1, logemp);
        prSt.setInt(2, idm);
    }


    public void parametres(PreparedStatement prSt, Mission mis, String l) throws SQLException {
        prSt.setString(1, l);
        prSt.setInt(2, mis.getIdMission());
    }
}
