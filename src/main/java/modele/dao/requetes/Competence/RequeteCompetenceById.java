package modele.dao.requetes.Competence;

import modele.Competence;
import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteCompetenceById extends RequeteCompetence {
    @Override
    public String requete(){
        return "SELECT * FROM competence WHERE idCatCmp = ? AND idCmp = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.parseInt(id[0]));
        prSt.setInt(2, Integer.parseInt(id[1]));
    }

    @Override
    public void parametres(PreparedStatement prSt, Competence obj) throws SQLException {
        prSt.setInt(1,Integer.valueOf(((modele.Competence) obj).getIdCatCmp()));
        prSt.setInt(2,Integer.valueOf(((modele.Competence) obj).getIdCmp()));
    }
}