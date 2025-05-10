package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteLoginExist extends RequeteEmploye {
    @Override
    public String requete() {
        return "SELECT 1 FROM EMPLOYE WHERE LOGINEMP = ?";
    }


    @Override
    public void parametres(PreparedStatement prSt, String... params) throws SQLException {
        prSt.setString(1, params[0]);
    }


    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
