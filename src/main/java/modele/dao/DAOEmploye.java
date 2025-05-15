package modele.dao;

import modele.Competence;
import modele.Employe;
import modele.connexion.CictOracleDataSource;
import modele.dao.requetes.Employe.*;
import modele.dao.requetes.Mission.RequeteVacance;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOEmploye {
    private final Connection cn;
    private ArrayList<Employe> dataAllEmploye;
    private ResultSet dataEmployeByCmp;
    private ResultSet dataEmployeCollaborer;
    private HashMap<Employe,Competence> mapEmpCmp;
    private HashMap<Employe,Date[]> mapEmpCollaborer;
    private ArrayList<Employe> listeEmployeCollaborer;
    private ArrayList<Employe> listeEmployeByCmp;
    private ArrayList<Employe> listeFinaleEmpCmpDates;

    public DAOEmploye()   {
        this.cn = CictOracleDataSource.getConnectionBD();
        try {
            PreparedStatement req = cn.prepareStatement(new RequeteEmployeSelectByCmp().requete());
            this.dataEmployeByCmp =req.executeQuery();
            remplirMapEmpCmp();
            PreparedStatement reqEC = cn.prepareStatement(new RequeteEmployeSelectMis().requete());
            this.dataEmployeCollaborer = reqEC.executeQuery();
            remplirMapEmpCollaborer();
            PreparedStatement req3 = cn.prepareStatement(new RequeteEmployeSelectAll().requete());
            ResultSet curseur = req3.executeQuery();
            this.dataAllEmploye = new ArrayList<>();
            while (curseur.next()) {
                this.dataAllEmploye.add(creerInstance(curseur));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    private void remplirMapEmpCmp() throws SQLException {
        this.listeEmployeByCmp = new ArrayList<>();
        this.mapEmpCmp = new HashMap<>();
        ResultSet curseur = this.dataEmployeByCmp;
        while (curseur.next()) {
            Competence cmp = new Competence(curseur.getInt("idcmp"),curseur.getString("idcatcmp"),curseur.getString("nomCmpEn"),curseur.getString("nomCmpFr"));
            // this.mapEmpCmp.put(creerInstance(curseur), new String[]{curseur.getString("idcatcmp"),curseur.getString("idcmp"),curseur.getString("nomCmpEn"),curseur.getString("nomCmpFr")});
            this.mapEmpCmp.put(creerInstance(curseur),cmp);
        }
        for (Employe e : this.mapEmpCmp.keySet()) {
            this.listeEmployeByCmp.add(e);
        }
    }

    private void remplirMapEmpCollaborer() throws SQLException {
        this.listeEmployeCollaborer = new ArrayList<>();
        this.mapEmpCollaborer = new HashMap<>();
        ResultSet curseur = this.dataEmployeCollaborer;
        while (curseur.next()) {
            this.mapEmpCollaborer.put(creerInstance(curseur), new Date[]{curseur.getDate("dateDebutMis"),curseur.getDate("dateFinMis")});
        }
        for (Employe e : this.mapEmpCollaborer.keySet()) {
            this.listeEmployeCollaborer.add(e);
        }
    }

    public void addEmpCollaborerToMap(String login, Date dateD, Date dateF) throws SQLException {
        for (Employe e : this.dataAllEmploye) {
            if(e.getLogin().equals(login)) {
                this.mapEmpCollaborer.put(e, new Date[]{dateD,dateF});
            }
        }

    }

    public void addEmpCmpToMap(Employe emp, List<Competence> listeCmp) throws SQLException {
        for (Competence c : listeCmp) {
            this.mapEmpCmp.put(emp,c);
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

    public void ajouterEmploye(Employe employe) throws SQLException {
        RequeteEmployeAjouter req = new RequeteEmployeAjouter();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, employe);
        ps.executeQuery();
    }

    public void modifierEmploye(Employe employe) throws SQLException {
        RequeteEmployeModifier req = new RequeteEmployeModifier();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, employe);
        ps.executeUpdate();
    }

    public void retirerAllCmpFromEmp(String loginEmp) throws SQLException {
        RequeteEmployeRetirerCmp req = new RequeteEmployeRetirerCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp);
        ps.executeUpdate();
    }


    public Employe findEmpByLogin(String login) throws SQLException {
        RequeteEmployeById req = new RequeteEmployeById();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, login);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Employe(
                    rs.getString("PRENOMEMP"),
                    rs.getString("NOMEMP"),
                    rs.getString("LOGINEMP"),
                    rs.getString("MDPEMP"),
                    rs.getString("POSTEEMP"),
                    rs.getDate("DATEENTREEEMP")
            );
        }
        return null;
    }

    public void ajouterCmpToEmp(String loginEmp, Competence cmp) throws SQLException {
        RequeteEmployeAjouterCmp req = new RequeteEmployeAjouterCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp, cmp);
        ps.executeUpdate();
    }

    public boolean isLoginExists(String login) throws SQLException {
        RequeteLoginExist req = new RequeteLoginExist();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, login);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

   /* protected Employe creerInstanceCmp(ResultSet rset) throws SQLException {
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
    }*/

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

    public List<Employe> findEmpByCmp(List<Competence> competences) {
        List<String> stringCmpAjoutes = new ArrayList<>();
        for (Competence cmp : competences) {
            stringCmpAjoutes.add(cmp.getIdCatCmp() + "." + cmp.getIdCmp());
        }
        this.listeEmployeByCmp = new ArrayList<>();
        for (Map.Entry<Employe, Competence> entry: this.mapEmpCmp.entrySet()) {
            Employe emp = entry.getKey();
            Competence cmp = entry.getValue();
            if (stringCmpAjoutes.contains(cmp.getIdCatCmp() + "." + cmp.getIdCmp())) {
                this.listeEmployeByCmp.add(emp);
            }
        }
        this.listeFinaleEmpCmpDates = new ArrayList<>();
        for(Employe emp : this.listeEmployeCollaborer){
            for(Employe e : this.listeEmployeByCmp){
                if (e.getLogin().equals(emp.getLogin())){
                    this.listeFinaleEmpCmpDates.add(emp);
                }
            }
        }
        return this.listeFinaleEmpCmpDates;
    }


    public List<Employe> miseAJourEmpByCmpByDate(Date dateD, Date dateF) throws SQLException {


        this.listeEmployeCollaborer= this.dataAllEmploye;

        ArrayList<String> loginEmpActif = new ArrayList<>();
        for (Map.Entry<Employe, Date[]> entry: this.mapEmpCollaborer.entrySet()) {
            Employe emp = entry.getKey();
            Date[] date = entry.getValue();
            if (((date[0].compareTo(dateD) >= 0 && date[1].compareTo(dateF) <= 0) ||
                    (date[1].compareTo(dateD) >= 0 && date[1].compareTo(dateF) <= 0) ||
                    (date[0].compareTo(dateD) >= 0 && date[0].compareTo(dateF) <= 0))) {

                loginEmpActif.add(emp.getLogin());
            }
        }
        listeEmployeCollaborer.removeIf(emp -> loginEmpActif.contains(emp.getLogin()));
        this.listeFinaleEmpCmpDates = new ArrayList<>();
        for(Employe emp : this.listeEmployeCollaborer){
            for(Employe e : this.listeEmployeByCmp){
                if (e.getLogin().equals(emp.getLogin())){
                    this.listeFinaleEmpCmpDates.add(emp);
                }
            }
        }
        return this.listeFinaleEmpCmpDates;
    }

    /*public Employe getEmployeByLogin(String login) throws SQLException {
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
    }*/

    public HashMap<Employe,Competence> getHashMapEmpCmp(){
        return this.mapEmpCmp;
    }

    public void ajouterVacance(Date dateDebut, Date dateFin, String loginEmp) throws SQLException {
        RequeteVacance req = new RequeteVacance();
        String sql = req.requete();

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, dateDebut);
            ps.setDate(2, dateFin);
            ps.setDate(3, new Date(System.currentTimeMillis()));
            ps.setString(4, loginEmp);

            ps.executeUpdate();
            System.out.println("Vacances ajoutées");
        }
    }

    public List<Employe> getAllDataEmploye(){
        return this.dataAllEmploye;
    }
}
