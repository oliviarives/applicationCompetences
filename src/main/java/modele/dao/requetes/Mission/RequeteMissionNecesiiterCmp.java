package modele.dao.requetes.Mission;

import modele.Competence;
import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RequeteMissionNecesiiterCmp extends RequeteMission {
    @Override
    public String requete() {
        return "INSERT INTO NECESSITER (IDCATCMP,IDCMP,IDMIS,NBEMPCMP) VALUES (?,?,?,?)";
    }


    public void parametres(PreparedStatement prSt,String idcatc,int idc, int idm,int nbemp) throws SQLException {
        prSt.setString(1, idcatc);
        prSt.setInt(2, idc);
        prSt.setInt(3, idm);
        prSt.setInt(4, nbemp);
    }


    public void parametres(PreparedStatement prSt, Mission mis, Competence cmpA) throws SQLException {
        prSt.setString(1, cmpA.getIdCatCmp());
        prSt.setInt(2, cmpA.getIdCmp());
        prSt.setInt(3, mis.getIdMission());
        prSt.setInt(4, 1);
    }
}
