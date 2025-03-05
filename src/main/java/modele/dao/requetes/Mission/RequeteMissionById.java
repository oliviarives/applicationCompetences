package modele.dao.requetes.Mission;

import modele.Mission;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteMissionById extends RequeteMission{

    @Override
    public String requete(){
        return "SELECT * FROM mission WHERE titreMis = ?";
    }

    @Override
    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
        prSt.setInt(1, Integer.parseInt(id[0]));
    }

    @Override
    public void parametres(PreparedStatement prSt, Mission obj) throws SQLException {
        prSt.setInt(1,Integer.valueOf(((modele.Mission) obj).getIdMission()));
    }
}