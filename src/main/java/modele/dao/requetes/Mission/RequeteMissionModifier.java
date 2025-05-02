package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionModifier extends RequeteMission{
    @Override
    public String requete() {
        return "UPDATE MISSION SET TITREMIS=?, DESCRIPTION=?, DATEDEBUTMIS=?, DATEFINMIS=?, LOGINEMP=?, NBEMPMIS=? WHERE IDMIS=? ";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        ps.setString(1, mis.getTitreMis());
        ps.setString(2, mis.getDescription());
        ps.setDate(3, mis.getDateDebutMis());
        ps.setDate(4, mis.getDateFinMis());
        ps.setString(5, mis.getLoginEmp());
        ps.setInt(6, mis.getNbEmpMis());
        ps.setInt(7, mis.getIdMission());
    }
}
