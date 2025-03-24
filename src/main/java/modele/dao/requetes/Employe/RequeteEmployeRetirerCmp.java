package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeRetirerCmp extends RequeteEmploye {

    @Override
    public String requete() {
        return "DELETE FROM POSSEDER WHERE LOGINEMP = ?";
    }

    //@Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]); // id[0] correspond au login de l'employé
    }

    //@Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
    }
}
