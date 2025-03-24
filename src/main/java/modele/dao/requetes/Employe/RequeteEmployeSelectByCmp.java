package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeSelectByCmp extends RequeteEmploye{
    @Override
    public String requete() {
        return "SELECT DISTINCT * FROM EMPLOYE E, POSSEDER P, COMPETENCE C WHERE E.LOGINEMP=P.LOGINEMP AND P.IDCATCMP=C.IDCATCMP AND P.IDCMP=C.IDCMP";
    }

    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }

    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
