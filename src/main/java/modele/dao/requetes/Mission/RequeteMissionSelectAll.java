package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionSelectAll extends RequeteMission {
    @Override
    public String requete() {
        return "SELECT * FROM MISSION, STATUT WHERE MISSION.idSta=STATUT.idSta ORDER BY IDMIS DESC";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Mission mission) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}