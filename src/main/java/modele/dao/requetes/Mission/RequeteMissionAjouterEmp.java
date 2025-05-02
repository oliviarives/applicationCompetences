package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionAjouterEmp extends RequeteMission{
    @Override
    public String requete() {
        return "INSERT INTO COLLABORER (LOGINEMP,IDMIS) VALUES (?,?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Mission mission) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    public void parametres(PreparedStatement prSt,String logemp, int idm) throws SQLException {
        prSt.setString(1, logemp);
        prSt.setInt(2, idm);
    }


    public void parametres(PreparedStatement prSt, Mission mis, String logemp) throws SQLException {
        prSt.setString(1, logemp);
        prSt.setInt(2, mis.getIdMission());
    }
}
