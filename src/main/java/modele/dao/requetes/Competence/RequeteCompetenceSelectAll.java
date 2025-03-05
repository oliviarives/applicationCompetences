package modele.dao.requetes.Competence;

import modele.Competence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCompetenceSelectAll extends RequeteCompetence {
    @Override
    public String requete() {
        return "SELECT * FROM COMPETENCE";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {

    }

    @Override
    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {

    }
}