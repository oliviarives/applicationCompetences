package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeSelectAll extends RequeteEmploye{
    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {

    }
}
