package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionDeleteCmp extends RequeteMission{
    @Override
    public String requete() {
        return "DELETE FROM NECESSITER WHERE IDMIS = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement ps,Mission m) throws SQLException {
        ps.setInt(1,m.getIdMission());
    }
}
