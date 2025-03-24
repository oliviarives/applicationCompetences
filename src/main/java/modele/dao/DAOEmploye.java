package modele.dao;

import modele.dao.requetes.Mission.RequeteSelectCollaborer;
import utilitaires.Config;
import modele.Competence;
import modele.Employe;
import modele.dao.requetes.Employe.*;
import modele.connexion.CictOracleDataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOEmploye {
    private final Connection cn;
    private ArrayList<Employe> listeEmployeByCmp;
    private static String dbUser = Config.get("db.user");
    private static String dbPwd = Config.get("db.password");
    private ArrayList<Employe> listeEmployeStatutActif;

    public DAOEmploye() throws SQLException {
        this.cn = CictOracleDataSource.getConnectionBD();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectByCmp().requete());
            this.listeEmployeByCmp = resultSetToArray(req.executeQuery());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    protected Employe creerInstance(ResultSet rset) throws SQLException {
        return new Employe(
                rset.getString("prenomEmp"),
                rset.getString("nomEmp"),
                rset.getString("loginEmp"),
                rset.getString("mdpEmp"),
                rset.getString("posteEmp"),
                rset.getDate("dateEntreeEmp")
        );
    }

    public void ajouterPersonnel(Employe employe) throws SQLException {
        RequeteEmployeAjouter req = new RequeteEmployeAjouter();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, employe);
        ps.executeQuery();
    }

    public void ajouterPossession(String loginEmp, Competence cmp) throws SQLException {
        RequeteEmployeAjouterCmp req = new RequeteEmployeAjouterCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp, cmp);
        ps.executeUpdate();
    }

    public boolean loginExist(String login) throws SQLException {
        RequeteLoginExist req = new RequeteLoginExist();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, login);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Retourne vrai si un login existe déjà
    }

    protected Employe creerInstanceCmp(ResultSet rset) throws SQLException {
        return new Employe(
                rset.getString("prenomEmp"),
                rset.getString("nomEmp"),
                rset.getString("loginEmp"),
                rset.getString("mdpEmp"),
                rset.getString("posteEmp"),
                rset.getDate("dateEntreeEmp"),
                rset.getString("idCatCmp"),
                rset.getInt("idCmp")
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
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultats;
    }

    public List<Employe> findEmpByCmp() {
        return findAll();
    }

    /*public List<Employe> findEmpByCmp() {
        List<Employe> resultats = new ArrayList<>();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectAll().requete());
            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    Employe instance = creerInstance(curseur);
                    resultats.add(instance);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultats;
    }*/

    public List<Employe> findEmpByCompetences(List<Competence> competences) {
        Set<Employe> lemp = new HashSet<>();
        List<String> stringCmpAjoutes = new ArrayList<>();

        for (Competence cmp : competences) {
            stringCmpAjoutes.add(cmp.getIdCatCmp() + "." + cmp.getIdCmp());
        }

        for (Employe emp : listeEmployeByCmp) {
            if (stringCmpAjoutes.contains(emp.getIdCatCmp() + "." + emp.getIdcmp())) {
                lemp.add(emp);
            }
        }

        return new ArrayList<>(lemp);
    }

    public ArrayList<Employe> resultSetToArray(ResultSet resultSet) throws SQLException {
        ArrayList<Employe> resultats = new ArrayList<>();
        while (resultSet.next()) {
            Employe instance = creerInstanceCmp(resultSet);
            resultats.add(instance);
        }
        return resultats;
    }

    public void miseAJourEmpByCmpByDate(Date dateD, Date dateF) {
        List<String> resultat = new ArrayList<>();
        try {
            PreparedStatement requete = cn.prepareStatement(new RequeteSelectCollaborer().requete());
            ResultSet curseur = requete.executeQuery();
            while (curseur.next()) {
                if ((curseur.getDate("dateDebutMis").compareTo(dateD) >= 0 && curseur.getDate("dateDebutMis").compareTo(dateF) <= 0) ||
                        (curseur.getDate("dateFinMis").compareTo(dateD) >= 0 && curseur.getDate("dateFinMis").compareTo(dateF) <= 0)) {
                    resultat.add(curseur.getString("loginEmp"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        setListeEmpCmp();
        listeEmployeByCmp.removeIf(emp -> resultat.contains(emp.getLogin()));
    }

    public void setListeEmpCmp() {
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectByCmp().requete());
            this.listeEmployeByCmp = resultSetToArray(req.executeQuery());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Employe getEmployeByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM employe WHERE loginEmp = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return creerInstance(rs);
                }
            }
        }
        return null;
    }

}
