package modele.dao.requetes.Employe;
import modele.Competence;
import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeAjouterCmp extends RequeteEmploye {

    @Override
    public String requete() {
            return "INSERT INTO POSSEDER (LOGINEMP, IDCATCMP, IDCMP) VALUES (?, ?, ?)";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    @Override
    public void parametres(PreparedStatement prSt, Employe employe) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }


    public void parametres(PreparedStatement prSt, String loginEmp, Competence cmp) throws SQLException {
            prSt.setString(1, loginEmp);
            prSt.setString(2, cmp.getIdCatCmp());
            prSt.setInt(3, cmp.getIdCmp());
    }

}
