package modele.dao;
import utilitaires.Config;

import modele.Employe;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Employe.RequeteEmployeSelectAll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOEmploye {
    private Connection cn;
    private static String dbUser = Config.get("db.user");
    private static String dbPwd = Config.get("db.password");


    public DAOEmploye() throws SQLException {
        CictOracleDataSource.creerAcces(dbUser,dbPwd);
        this.cn = CictOracleDataSource.getConnectionBD();

    }

    protected Employe creerInstance(ResultSet rset) throws SQLException{
        return new Employe(
                rset.getString("prenomEmp"),
                rset.getString("nomEmp"),
                rset.getString("loginEmp"),
                rset.getString("mdpEmp"),
                rset.getString("posteEmp"),
                rset.getDate("dateEntreeEmp")
        );
    }

    public List<Employe> findAll() {
        List<Employe> resultats = new ArrayList<>();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectAll().requete());
            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    Employe instance = creerInstance(curseur);
                    resultats.add(instance);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultats;
    }
}
