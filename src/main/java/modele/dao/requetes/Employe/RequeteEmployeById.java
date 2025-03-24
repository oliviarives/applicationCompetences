package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeById extends RequeteEmploye {

    @Override
    public String requete() {
        return "SELECT * FROM EMPLOYE WHERE LOGINEMP = ?";
    }

    //@Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }

    //@Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
    }
}
