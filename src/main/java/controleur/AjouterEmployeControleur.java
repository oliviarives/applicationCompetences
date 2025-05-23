package controleur;

import modele.Competence;
import modele.Employe;
import modele.MdpUtils;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.AjoutEmployeVue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
     * @param vue la vue qui permet d'ajouter un employé
     * @param daoEmp DAO des employés
     * @param daoCmp DAO des compétences
     */
    public AjouterEmployeControleur(AjoutEmployeVue vue, DAOEmploye daoEmp, DAOCompetence daoCmp) {
        this.ajoutPersonnelVue = vue;
        this.daoEmploye = daoEmp;
        this.daoCompetence = daoCmp;

        vue.getButtonConfirmer().addActionListener(e -> {
            ajouterPersonnel();
            effacerChamps();
            NavigationControleur.loadEmploye();
            //NavigationControleur.getVueV().getButtonEmploye().doClick();
        });

        vue.getButtonEffacer().addActionListener(e -> effacerChamps());

        vue.getTableToutesCompetences().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ajouterCompetenceEmploye();
                }
            }
        });

        vue.getTableCompetencesEmploye().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    retirerCompetenceEmploye();
                }
            }
        });
    }

    /**
     * Ajoute un nouvel employé en base de données avec ses informations et ses compétences
     */
    private void ajouterPersonnel() {
        String prenom = ajoutPersonnelVue.getPrenomField().getText();
        String nom = ajoutPersonnelVue.getNomField().getText();
        String login = ajoutPersonnelVue.getLoginField().getText();
        String mdp = new String(ajoutPersonnelVue.getMdpField().getPassword());
        String poste = ajoutPersonnelVue.getPosteField().getText();
        java.util.Date utilDate = (java.util.Date) ajoutPersonnelVue.getDateEntreeField().getValue();
        Date dateEntree = new Date(utilDate.getTime());

        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || mdp.isEmpty() || poste.isEmpty()) {
            ajoutPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            if (daoEmploye.isLoginExists(login)) {
                ajoutPersonnelVue.afficherMessage("Ce login est déjà utilisé.");
                return;
            }

            String mdpHashed = MdpUtils.hashPassword(mdp);
            Employe employe = new Employe(prenom, nom, login, mdpHashed, poste, dateEntree);
            daoEmploye.ajouterEmploye(employe);
            daoEmploye.getAllDataEmploye().add(employe);

            for (Competence cmp : ajoutPersonnelVue.getCompetencesAjoutees()) {
                daoEmploye.ajouterCmpToEmp(employe.getLogin(), cmp);
                daoEmploye.getHashMapEmpCmp().put(employe, cmp);
            }
            effacerChamps();
            ajoutPersonnelVue.afficherMessage("");
            NavigationControleur.getVueV().getButtonEmploye().doClick();
        } catch (SQLException e) {
            ajoutPersonnelVue.afficherMessage("Erreur lors de l'ajout.");
            e.printStackTrace();
        }
    }

    /**
     * Ajoute une compétence à un employé
     */
    private void ajouterCompetenceEmploye() {
        Competence selected = ajoutPersonnelVue.getCompetenceSelectionneeToutesCmp();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel();
            int row = ajoutPersonnelVue.getTableToutesCompetences().getSelectedRow();
            if (row != -1) {
                modelToutes.removeRow(row);
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
            int row = ajoutPersonnelVue.getTableCompetencesEmploye().getSelectedRow();
            if (row != -1) {
                modelEmploye.removeRow(row);
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
        ajoutPersonnelVue.getPosteField().setText("");
        ajoutPersonnelVue.getDateEntreeField().setValue(new java.util.Date());

        DefaultTableModel modelEmploye = (DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel();
        modelEmploye.setRowCount(0);

        DefaultTableModel modelToutes = (DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel();
        modelToutes.setRowCount(0);

        loadCompetences();
    }

        /**
         * Charge la liste des compétences depuis la base de données
         */
    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        ajoutPersonnelVue.setToutesCompetences(competences);
    }
}
