package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteDeleteEmpMis extends RequeteMission{
    @Override
    public String requete() {
        return "DELETE FROM COLLABORER WHERE IDMIS=?";
    }

    public void parametres(PreparedStatement ps, Mission m) throws SQLException {
        ps.setInt(1,m.getIdMission());
    }
}
