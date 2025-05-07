package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteFindAll extends Requete {
    @Override
    public String requete() {
        return "SELECT * FROM ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    @Override
    public void parametres(PreparedStatement prSt, Object o) throws SQLException {

    }
}
