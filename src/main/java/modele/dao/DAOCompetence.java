package modele.dao;

import modele.Competence;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Competence.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOCompetence {
    private Connection cn;

    public DAOCompetence() throws SQLException {
        this.cn = CictOracleDataSource.getConnectionBD();

    }

    protected Competence creerInstance(ResultSet rset) throws SQLException{
        return new Competence(
                rset.getInt("idCmp"),
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

    public List<Competence> findByCompetencesEmploye (String login) throws SQLException {
        RequeteCompetenceEmploye req = new RequeteCompetenceEmploye();
        List<Competence> liste = new ArrayList<>();
            PreparedStatement ps = cn.prepareStatement(req.requete());
            req.parametres(ps, login);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Competence(
                        rs.getInt("IDCMP"),
                        rs.getString("IDCATCMP"),
                        rs.getString("NOMCMPEN"),
                        rs.getString("NOMCMPFR")
                ));
            }
        return liste;
    }


    public List<Competence> findAll() {
        List<Competence> resultats = new ArrayList<>();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteCompetenceSelectAll().requete());
            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    Competence instance = creerInstance(curseur);
                    resultats.add(instance);
                    //System.out.println("Compétence récupérée: " + instance.getNomCmpFr()); // Debug
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