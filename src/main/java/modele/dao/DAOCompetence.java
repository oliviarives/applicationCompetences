package modele.dao;

import modele.Competence;
import modele.Mission;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Competence.RequeteCompetence;
import modele.dao.requetes.Competence.RequeteCompetenceAjouter;
import modele.dao.requetes.Competence.RequeteCompetenceSelectAll;
import modele.dao.requetes.Mission.RequeteMissionAjouter;
import modele.dao.requetes.Mission.RequeteMissionSelectAll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCompetence {
    private Connection cn;

    public DAOCompetence() throws SQLException {
        CictOracleDataSource.creerAcces("BSC3991A","2002Aralc.31");
        this.cn = CictOracleDataSource.getConnectionBD();

    }

    protected Competence creerInstance(ResultSet rset) throws SQLException{
        return new Competence(
                rset.getString("idCatCmp"),
                rset.getString("nomCmpEn"),
                rset.getString("nomCmpFr")
        );
    }

    public void ajouterCompetence(Competence cmp) throws SQLException{
        PreparedStatement ps = cn.prepareStatement(new RequeteCompetenceAjouter().requete());
        new RequeteCompetenceAjouter().parametres(ps,cmp);
        ps.executeUpdate();
    }

    public List<Competence> findAll() {
        List<Competence> resultats = new ArrayList<>();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteCompetenceSelectAll().requete());
            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    Competence instance = creerInstance(curseur);
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