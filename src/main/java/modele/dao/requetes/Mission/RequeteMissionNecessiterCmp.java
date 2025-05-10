package modele.dao.requetes.Mission;

import modele.Competence;
import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionNecessiterCmp extends RequeteMission {
    @Override
    public String requete() {
        return "INSERT INTO NECESSITER (IDCATCMP,IDCMP,IDMIS,NBEMPCMP) VALUES (?,?,?,?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Mission mission) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }


    /*public void parametresRequeteMissionNecessiterCmp(PreparedStatement prSt, String idcatc, int idc, int idm, int nbemp) throws SQLException {
        prSt.setString(1, idcatc);
        prSt.setInt(2, idc);
        prSt.setInt(3, idm);
        prSt.setInt(4, nbemp);
    }*/


    public void parametresRequeteMissionNecessiterCmp(PreparedStatement prSt, Mission mis, Competence cmpA) throws SQLException {
        prSt.setString(1, cmpA.getIdCatCmp());
        prSt.setInt(2, cmpA.getIdCmp());
        prSt.setInt(3, mis.getIdMission());
        prSt.setInt(4, 1);
    }

}
