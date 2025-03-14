package modele.dao;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Employe.RequeteEmployeSelectAll;
import modele.dao.requetes.Employe.RequeteEmployeSelectByCmp;
import modele.dao.requetes.Mission.RequeteMissionSelectAll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOEmploye {
    private Connection cn;
    private ArrayList<Employe> listeEmployeByCmp;

    public DAOEmploye() throws SQLException {
        CictOracleDataSource.creerAcces("BSC3991A","2002Aralc.31");
        this.cn = CictOracleDataSource.getConnectionBD();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectByCmp().requete());
            this.listeEmployeByCmp= resultSetToArray(req.executeQuery());
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
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

    protected Employe creerInstanceCmp(ResultSet rset) throws SQLException{
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
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resultats;
    }
    //filtrer employés par leurs compétenes
    public List<Employe> findEmpByCmp() {
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



    public List<Employe> findEmpByCompetences(List<Competence> competences) {
        Set<Employe> lemp = new HashSet<>();
        List<Integer> intCmpAjoutes = new ArrayList<>();
        List<String> stringCmpAjoutes = new ArrayList<>();
        for(Competence cmp : competences) {
            stringCmpAjoutes.add(cmp.getIdCatCmp()+"."+cmp.getIdCmp());
            System.out.println("1"+cmp.getIdCatCmp()+"."+cmp.getIdCmp());
        }
        //try {
            //PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectByCmp().requete());
            //try (ResultSet curseur = req.executeQuery()) {
                //this.listeEmployeByCmp = curseur;

                for(Employe emp: listeEmployeByCmp) {
                    System.out.println(emp.getIdCatCmp()+"."+emp.getIdcmp());
                    if (stringCmpAjoutes.contains(emp.getIdCatCmp()+"."+emp.getIdcmp()) ){
                        lemp.add(emp);
                    }
                }
        /*}catch(SQLException e){
            System.err.println(e.getMessage());
        }*/
        List<Employe> resultats = new ArrayList<>(lemp);
        return resultats;
    }


    public ArrayList<Employe> resultSetToArray(ResultSet resultSet) throws SQLException {
        ArrayList<Employe> resultats = new ArrayList<>();
        while (resultSet.next()) {
            Employe instance = creerInstanceCmp(resultSet);
            System.out.println(instance.getNom());
            resultats.add(instance);
        }
        return resultats;
    }

}




   /* public List<Employe> findEmpByCompetences(List<Competence> competences) {
        List<Employe> resultats = new ArrayList<>();

        if (competences.isEmpty()) {
            return resultats; // Retourne une liste vide si aucune compétence n'est sélectionnée
        }

        String query = new RequeteEmployeSelectByCmp().requete();


        for (int i = 0; i < competences.size(); i++) {
            if (i > 0) {
                query+=(" OR ");
            }
            query+=("(C.IDCMP = ?)");
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
    }*/
