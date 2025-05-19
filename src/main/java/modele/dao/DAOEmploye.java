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
/**
 * DAO pour gérer l'accès aux données liées aux employés
 * Fournit des méthodes pour ajouter, modifier, filtrer ou récupérer des employés et leurs compétences
 */
public class DAOEmploye {
    /**
     * Connexion à la base de données
     */
    private final Connection cn;
    /**
     * Liste de tous les employés
     */
    private ArrayList<Employe> dataAllEmploye;
    /**
     * Résultat des employés et leurs compétences
     */
    private ResultSet dataEmployeByCmp;
    /**
     * Résultat des employés et leurs missions
     */
    private ResultSet dataEmployeCollaborer;
    /**
     * Map des employés associés à leurs compétences
     */
    private HashMap<Employe,Competence> mapEmpCmp;
    /**
     * Map des employés associés à leurs missions
     */
    private HashMap<Employe,Date[]> mapEmpCollaborer;
    /**
     * Liste des employés qui effectuent des missions
     */
    private ArrayList<Employe> listeEmployeCollaborer;
    /**
     * Liste des employés selon leurs compétences
     */
    private ArrayList<Employe> listeEmployeByCmp;
    /**
     * Liste finale filtrée selon les compétences et les disponibilités des employés
     */
    private ArrayList<Employe> listeFinaleEmpCmpDates;

    /**
     * Initialise le DAO
     */
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
    /**
     * Remplit la map employé/compétence
     * @throws SQLException en cas d’erreur SQL
     */
    private void remplirMapEmpCmp() throws SQLException {
        this.listeEmployeByCmp = new ArrayList<>();
        this.mapEmpCmp = new HashMap<>();
        ResultSet curseur = this.dataEmployeByCmp;
        while (curseur.next()) {
            Competence cmp = new Competence(curseur.getInt("idcmp"),curseur.getString("idcatcmp"),curseur.getString("nomCmpEn"),curseur.getString("nomCmpFr"));
            this.mapEmpCmp.put(creerInstance(curseur),cmp);
        }
        for (Employe e : this.mapEmpCmp.keySet()) {
            this.listeEmployeByCmp.add(e);
        }
    }
    /**
     * Remplit la map Employe/Périodes d'une mission
     * @throws SQLException en cas d’erreur SQL
     */
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
    /**
     * Ajoute un employé à la map des collaborateurs avec ses dates de mission
     * @param login login de l’employé
     * @param dateD date de début
     * @param dateF date de fin
     * @throws SQLException en cas d’erreur SQL
     */
    public void addEmpCollaborerToMap(String login, Date dateD, Date dateF) throws SQLException {
        for (Employe e : this.dataAllEmploye) {
            if(e.getLogin().equals(login)) {
                this.mapEmpCollaborer.put(e, new Date[]{dateD,dateF});
            }
        }

    }
    /**
     * Crée une instance d'employé à partir d’un ResultSet
     * @param rset résultat SQL
     * @return un objet Employe
     * @throws SQLException en cas d’erreur d’accès aux données
     */
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
    /**
     * Ajoute un employé dans la base
     * @param employe employé à ajouter
     * @throws SQLException en cas d’erreur SQL
     */
    public void ajouterEmploye(Employe employe) throws SQLException {
        RequeteEmployeAjouter req = new RequeteEmployeAjouter();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, employe);
        ps.executeQuery();
    }
    /**
     * Modifie les informations d’un employé
     * @param employe employé à modifier
     * @throws SQLException en cas d’erreur SQL
     */
    public void modifierEmploye(Employe employe) throws SQLException {
        RequeteEmployeModifier req = new RequeteEmployeModifier();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, employe);
        ps.executeUpdate();
    }
    /**
     * Supprime toutes les compétences associées à un employé
     * @param loginEmp login de l’employé
     * @throws SQLException en cas d’erreur SQL
     */
    public void retirerAllCmpFromEmp(String loginEmp) throws SQLException {
        RequeteEmployeRetirerCmp req = new RequeteEmployeRetirerCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp);
        ps.executeUpdate();
    }
    /**
     * Recherche un employé par son login
     * @param login identifiant de l’employé
     * @return employé trouvé ou null
     * @throws SQLException en cas d’erreur SQL
     */
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
    /**
     * Ajoute une compétence à un employé
     * @param loginEmp login de l’employé
     * @param cmp compétence à associer
     * @throws SQLException en cas d’erreur SQL
     */
    public void ajouterCmpToEmp(String loginEmp, Competence cmp) throws SQLException {
        RequeteEmployeAjouterCmp req = new RequeteEmployeAjouterCmp();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, loginEmp, cmp);
        ps.executeUpdate();
    }
    /**
     * Vérifie si un login employé existe déjà
     * @param login identifiant à tester
     * @return true si le login est déjà utilisé
     * @throws SQLException en cas d’erreur SQL
     */
    public boolean isLoginExists(String login) throws SQLException {
        RequeteLoginExist req = new RequeteLoginExist();
        PreparedStatement ps = cn.prepareStatement(req.requete());
        req.parametres(ps, login);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    /**
     * Récupère tous les employés de la base
     * @return liste d’employés
     */
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
    /**
     * Recherche les employés disponibles qui possèdent des compétences précises
     * @param competences liste de compétences requises
     * @return liste d’employés disponibles et compatibles
     */
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
    /**
     * Met à jour la liste des employés disponibles
     * @param dateD date de début
     * @param dateF date de fin
     * @return liste des employés disponibles et compétents
     * @throws SQLException en cas d’erreur SQL
     */
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
    /**
     * Retourne la map employe/compétence
     * @return map employé/compétence
     */
    public HashMap<Employe,Competence> getHashMapEmpCmp(){
        return this.mapEmpCmp;
    }
    /**
     * Crée une mission de type vacance pour un employé
     * @param dateDebut début des vacances
     * @param dateFin fin des vacances
     * @param loginEmp login de l’employé
     * @throws SQLException en cas d’erreur SQL
     */
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
