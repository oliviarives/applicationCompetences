package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Requete<T> {

    public abstract String requete();

    public abstract void parametres(PreparedStatement prSt, String... id) throws SQLException;

    public abstract void parametres(PreparedStatement prSt, T t) throws SQLException;


}
