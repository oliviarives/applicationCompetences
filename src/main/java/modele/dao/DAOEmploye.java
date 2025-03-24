package modele.dao;

import modele.dao.requetes.Employe.*;
import utilitaires.Config;
import modele.Competence;
import modele.Employe;
import modele.connexion.CictOracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOEmploye {
    private final Connection cn;
    private ArrayList<Employe> listeEmployeByCmp;
    private static String dbUser = Config.get("db.user");
    private static String dbPwd = Config.get("db.password");

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

    public boolean loginExist(String login) throws SQLException {
        RequeteLoginExist req = new RequeteLoginExist();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, login);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Retourne vrai si un login existe déjà
    }

    public void ajouterPossession(String loginEmp, Competence cmp) throws SQLException {
        RequeteEmployeAjouterCmp req = new RequeteEmployeAjouterCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp, cmp);
        ps.executeUpdate();
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

    /* public List<Employe> findEmpByCompetences(List<Competence> competences) {
        List<Employe> resultats = new ArrayList<>();

        if (competences.isEmpty()) {
            return resultats; // Retourne une liste vide si aucune compétence n'est sélectionnée
        }

        String query = new RequeteEmployeSelectByCmp().requete();

        for (int i = 0; i < competences.size(); i++) {
            if (i > 0) {
                query += (" OR ");
            }
            query += ("(C.IDCMP = ?)");
        }
        System.out.println(query);

        try (PreparedStatement req = cn.prepareStatement(query.toString())) {
            int index = 1;
            for (Competence cmp : competences) {
                System.out.println("➡️ Paramètre " + index + " = " + cmp.getIdCmp()); // Vérifier la valeur insérée
                req.setInt(index, cmp.getIdCmp());
                index++;
            }

            try (ResultSet curseur = req.executeQuery()) {
                while (curseur.next()) {
                    System.out.println("✔️ Employé trouvé : " + curseur.getString("LOGINEMP"));
                    System.out.println("ok");
                    Employe instance = creerInstance(curseur);
                    resultats.add(instance);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }

        return resultats;
    } */
}
