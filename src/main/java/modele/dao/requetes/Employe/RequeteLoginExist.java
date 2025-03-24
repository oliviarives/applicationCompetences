package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteLoginExist extends RequeteEmploye {
    @Override
    public String requete() {
        return "SELECT 1 FROM EMPLOYE WHERE LOGINEMP = ?";
    }

    
    public void parametres(PreparedStatement prSt, String... params) throws SQLException {
        prSt.setString(1, params[0]);
    }

    
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {

    }
}
