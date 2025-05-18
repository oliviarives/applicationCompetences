package modele.dao;

import modele.Competence;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Competence.RequeteCompetenceEmploye;
import modele.dao.requetes.Competence.RequeteCompetenceSelectAll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * DAO pour gérer l'accès aux données de la table COMPETENCE
 * Fournit des méthodes pour récupérer les compétences en général ou par employé
 */
public class DAOCompetence {
    /**
     * Connexion à la base de données
     */
    private Connection cn;
    /**
     * Initialise le DAO en récupérant une connexion à la base
     * @throws SQLException si la connexion échoue
     */
    public DAOCompetence() throws SQLException {
        this.cn = CictOracleDataSource.getConnectionBD();

    }
    /**
     * Crée une instance de Competence à partir d'un ResultSet
     * @param rset résultat SQL
     * @return une compétence construite à partir des données du résultat
     * @throws SQLException en cas d'erreur d'accès aux colonnes
     */
    protected Competence creerInstance(ResultSet rset) throws SQLException{
        return new Competence(
                rset.getInt("idCmp"),
                rset.getString("idCatCmp"),
                rset.getString("nomCmpEn"),
                rset.getString("nomCmpFr")
        );
    }
    /**
     * Récupère la liste des compétences possédées par un employé
     * @param login identifiant de l'employé
     * @return liste des compétences
     * @throws SQLException en cas d'erreur SQL
     */
    public List<Competence> findCmpByLoginEmp(String login) throws SQLException {
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
    /**
     * Récupère toutes les compétences de la table COMPETENCE
     * @return liste de toutes les compétences
     */
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