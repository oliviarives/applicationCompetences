package modele.dao.requetes.Employe;

import modele.Employe;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequeteEmployeAjouter extends RequeteEmploye {
    @Override
    public String requete() {
        return "INSERT INTO EMPLOYE (LOGINEMP,PRENOMEMP,NOMEMP,DATEENTREEEMP,MDPEMP,ACTIF,POSTEEMP) VALUES (?,?,?,?,?,?,?)";
    }


    public void parametres(PreparedStatement prSt, String... id) throws SQLException {
    	

    }


    public void parametres(PreparedStatement prSt, Employe obj) throws SQLException {
        prSt.setString(1, obj.getLogin());
        prSt.setString(2, obj.getPrenom());
        prSt.setString(3, obj.getNom());
        prSt.setDate(4, new java.sql.Date(obj.getDateEntree().getTime()));
        prSt.setString(5, obj.gethashedPwd());
        prSt.setInt(6, 1); 
        prSt.setString(7, obj.getPoste());
    }

}
