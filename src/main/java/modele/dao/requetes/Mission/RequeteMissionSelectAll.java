package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionSelectAll extends RequeteMission {

    @Override
    public String requete() {
        return "SELECT * FROM MISSION, STATUT WHERE MISSION.idSta=STATUT.idSta ORDER BY IDMIS DESC";
    }


    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    public void parametres(PreparedStatement prSt, Mission obj) throws SQLException {

    }
}