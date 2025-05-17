package controleur;

import modele.Competence;
import modele.Employe;
import modele.MdpUtils;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.AjoutEmployeVue;

import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
/**
 * Contrôleur chargé de gérer l'ajout d'un nouvel employé
 */
public class AjouterEmployeControleur {
    /**
     * Vue d'ajout d'un employé
     */
    private final AjoutEmployeVue ajoutPersonnelVue;
    /**
     * DAO pour la gestion des employés
     */
    private final DAOEmploye daoEmploye;
    /**
     * DAO pour la gestion des compétences
     */
    private final DAOCompetence daoCompetence;

    /**
     * Constructeur pour initialiser le contrôleur et configurer les actions des boutons de la vue
     * @param ajoutPersonnelVue la vue qui permet d'ajouter un employé
     * @param daoEmp DAO des employés
     * @param daoCmp DAO des compétences
     */
    public AjouterEmployeControleur(AjoutEmployeVue ajoutPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp) {
        this.ajoutPersonnelVue = ajoutPersonnelVue;
        this.daoEmploye = daoEmp;
        this.daoCompetence = daoCmp;

        ajoutPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            ajouterPersonnel();
            effacerChamps();
            NavigationControleur.loadEmploye();
            NavigationControleur.getVueV().getButtonEmploye().doClick();

        });

        ajoutPersonnelVue.getButtonEffacer().addActionListener (e ->
                effacerChamps());

        ajoutPersonnelVue.getButtonRetirer().addActionListener(e ->
                retirerCompetenceEmploye());

        ajoutPersonnelVue.getButtonAjouter().addActionListener(e ->
                ajouterCompetenceEmploye());
    }

    /**
     * Ajoute un nouvel employé en base de données avec ses informations et ses compétences
     */
    private void ajouterPersonnel() {
        String prenom = ajoutPersonnelVue.getPrenomField().getText();
        String nom = ajoutPersonnelVue.getNomField().getText();
        String login = ajoutPersonnelVue.getLoginField().getText();
        String mdp = ajoutPersonnelVue.getMdpField().toString();
        String poste = ajoutPersonnelVue.getPosteField().getText();
        java.util.Date utilDate = (java.util.Date) ajoutPersonnelVue.getDateEntreeField().getValue();
        Date dateEntree = new Date(utilDate.getTime());

        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || mdp.isEmpty() || poste.isEmpty()) {
            ajoutPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            if (daoEmploye.isLoginExists(login)) {
                ajoutPersonnelVue.afficherMessage("Ce login est déjà utilisé, veuillez en choisir un autre.");
                return;
            }
            String mdpHashed = MdpUtils.hashPassword(mdp);
            Employe employe = new Employe(prenom, nom, login, mdpHashed, poste, dateEntree);
            daoEmploye.ajouterEmploye(employe);
            for (Competence cmp : ajoutPersonnelVue.getCompetencesAjoutees()) {
                daoEmploye.ajouterCmpToEmp(employe.getLogin(), cmp);
                daoEmploye.getHashMapEmpCmp().put(employe, cmp);
            }
        } catch (SQLException ex) {
            ajoutPersonnelVue.afficherMessage("Erreur lors de l'ajout de l'employé.");
            ex.printStackTrace();
        }
    }

    /**
     * Charge la liste des compétences depuis la base de données
     */
    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        ajoutPersonnelVue.setToutesCompetences(competences);
    }

    /**
     * Ajoute une compétence à un employé
     */
    private void ajouterCompetenceEmploye() {
        Competence selected = ajoutPersonnelVue.getCompetenceSelectionneeToutesCmp();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel();

            int selectedRow = ajoutPersonnelVue.getTableToutesCompetences().getSelectedRow();
            if (selectedRow != -1) {
                modelToutes.removeRow(selectedRow);
                modelEmploye.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

    /**
     * Retire une compétence à un employé
     */
    public void retirerCompetenceEmploye() {
        Competence selected = ajoutPersonnelVue.getCompetenceSelectionneeEmploye();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel();

            int selectedRow = ajoutPersonnelVue.getTableCompetencesEmploye().getSelectedRow();
            if (selectedRow != -1) {
                modelEmploye.removeRow(selectedRow);
                modelToutes.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

    /**
     * Réinitialise tous les champs de la vue et recharge les compétences disponibles
     */
    private void effacerChamps() {
        ajoutPersonnelVue.getPrenomField().setText("");
        ajoutPersonnelVue.getNomField().setText("");
        ajoutPersonnelVue.getLoginField().setText("");
        ajoutPersonnelVue.getMdpField().setText("");
        ajoutPersonnelVue.getDateEntreeField().setValue(new java.util.Date());
        ((DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel()).setRowCount(0);
        ((DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel()).setRowCount(0);
        loadCompetences();
    }

}
