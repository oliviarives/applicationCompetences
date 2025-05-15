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

public class AjouterEmployeControleur {
    private final AjoutEmployeVue ajoutPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final DAOCompetence daoCompetence;

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


    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        ajoutPersonnelVue.setToutesCompetences(competences);
    }

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
