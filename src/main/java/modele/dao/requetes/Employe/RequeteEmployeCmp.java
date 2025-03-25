package modele.dao.requetes.Employe;

import modele.Employe;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeCmp extends RequeteEmploye{

    @Override
    public String requete(){
        return "SELECT c.NOMCMPFR, e.loginemp FROM competence, posseder p, employe e WHERE c.idcatcmp = p.idcatcmp AND p.loginemp = e.loginemp";
    }


    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }


    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
