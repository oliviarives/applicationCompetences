package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeModifier extends RequeteEmploye {
    @Override
    public String requete() {
        return "UPDATE EMPLOYE SET PRENOMEMP = ?, NOMEMP = ?, DATEENTREEEMP = ?, POSTEEMP = ? WHERE LOGINEMP = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getPrenom());
        prSt.setString(2, obj.getNom());
        prSt.setDate(3, new java.sql.Date(obj.getDateEntree().getTime()));
        prSt.setString(6, obj.getPoste());
        prSt.setString(7, obj.getLogin());
    }
}

