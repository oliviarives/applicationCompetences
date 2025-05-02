package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeSelectMis extends RequeteEmploye{
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE E, COLLABORER C, MISSION M WHERE C.IDMIS=M.IDMIS AND E.LOGINEMP=C.LOGINEMP";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Employe employe) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }


}
