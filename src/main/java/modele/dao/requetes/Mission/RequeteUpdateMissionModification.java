package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteUpdateMissionModification extends RequeteMission{
    @Override
    public String requete() {
        return "UPDATE MISSION SET TITREMIS=?, DESCRIPTION=?, DATEDEBUTMIS=?, DATEFINMIS=?, LOGINEMP=?, NBEMPMIS=? WHERE IDMIS=? ";
    }

    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        ps.setString(1, mis.getTitreMis());        // TITREMIS
        ps.setString(2, mis.getDescription());       // DESCRIPTION
        ps.setDate(3, mis.getDateDebutMis());          // DATEDEBUTMIS
        ps.setDate(4, mis.getDateFinMis());            // DATEFINMIS
        ps.setString(5, mis.getLoginEmp());          // LOGINEMP
        ps.setInt(6, mis.getNbEmpMis());             // NBEMPMIS
        ps.setInt(7, mis.getIdMission());   //idmis
    }
}
