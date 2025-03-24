package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeSelectAll extends RequeteEmploye{
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE";
        //return "SELECT * FROM EMPLOYE";
    }

    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
