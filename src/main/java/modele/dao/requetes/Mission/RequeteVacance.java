package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteVacance extends RequeteMission{

    @Override
    public String requete() {
        return "INSERT INTO MISSION (IDMIS, TITREMIS, NBEMPMIS, DATEDEBUTMIS, DATEFINMIS, DESCRIPTION, DATECREATION, LOGINEMP, IDSTA) " +
                "VALUES (SEQ_MISSION.NEXTVAL, 'Vacance', 1, ?, ?, 'Vacance', ?, ?, 5) ";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement ps, Mission mis) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
