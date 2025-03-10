package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeAjouter extends RequeteEmploye {
    @Override
    public String requete() {
        return "INSERT INTO PERSONNEL (…………………;) VALUES (?,?,?…)";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {

    }
}
