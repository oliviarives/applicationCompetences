package modele.dao.requetes.Competence;

import modele.Competence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCompetenceEmploye extends RequeteCompetence {

    @Override
    public String requete() {
        return "SELECT c.IDCMP, c.IDCATCMP, c.NOMCMPEN, c.NOMCMPFR FROM COMPETENCE c, POSSEDER p WHERE c.IDCMP = p.IDCMP AND c.IDCATCMP = p.IDCATCMP AND p.LOGINEMP = ?";
    }

    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setString(1, id[0]);
    }

    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {
        throw new UnsupportedOperationException("Non utilisé pour cette requête.");
    }
}
