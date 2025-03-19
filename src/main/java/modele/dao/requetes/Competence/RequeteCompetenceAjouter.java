package modele.dao.requetes.Competence;

import modele.Competence;
import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCompetenceAjouter extends RequeteCompetence{
    @Override
    public String requete() {
        return "INSERT INTO COMPETENCE () VALUES (?,?,?,…)";
    }


    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    }


    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {

    }

}