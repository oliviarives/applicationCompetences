package controleur;

import modele.Competence;
import modele.Employe;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.ModificationEmployeVue;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Contrôleur chargé de la modification des informations et des compétences d'un employé
 */
public class ModifierEmployeControleur {
    /**
     * Vue de modification d'un employé
     */
    private final ModificationEmployeVue modifPersonnelVue;
    /**
     * DAO pour accéder aux employés
     */
    private final DAOEmploye daoEmploye;
    /**
     * DAO pour accéder aux compétences
     */
    private final DAOCompetence daoCompetence;

    /**
     * Initialise le contrôleur, configure les actions de la vue et prépare les boutons
     * @param modifPersonnelVue vue de modification employé
     * @param daoEmp DAO employé
     * @param daoCmp DAO compétence
     */
    public ModifierEmployeControleur(ModificationEmployeVue modifPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp) {
        this.modifPersonnelVue = modifPersonnelVue;
        this.daoEmploye = daoEmp;
        this.daoCompetence = daoCmp;

        modifPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            this.modifierPersonnel();
            NavigationControleur.loadEmploye();
            NavigationControleur.getVueV().getButtonEmploye().doClick();
        });

        modifPersonnelVue.getButtonRetirer().addActionListener(e ->
                this.retirerCompetenceEmploye());

        modifPersonnelVue.getButtonAjouter().addActionListener(e ->
                ajouterCompetenceEmploye());
    }

    /**
     * Modifie les informations de l'employé et met à jour ses compétences
     */
    private void modifierPersonnel () {
        String prenom = modifPersonnelVue.getPrenomField().getText();
        String nom = modifPersonnelVue.getNomField().getText();
        String login = modifPersonnelVue.getLoginField().getText();
        String poste = modifPersonnelVue.getPosteField().getText();

        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || poste.isEmpty()) {
            modifPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            // Recherche de l'employé à mettre à jour
            Employe employe = daoEmploye.findEmpByLogin(login);
            daoEmploye.retirerAllCmpFromEmp(login);
            daoEmploye.modifierEmploye(employe);
            for (Competence cmp : modifPersonnelVue.getCompetencesAjoutees()) {
                daoEmploye.ajouterCmpToEmp(employe.getLogin(), cmp);
                daoEmploye.getHashMapEmpCmp().remove(employe);
                daoEmploye.getHashMapEmpCmp().put(employe, cmp);
            }
        } catch (SQLException e) {
            modifPersonnelVue.afficherMessage("Erreur lors de la modification de l'employé.");
        }
    }
    /**
     * Charge toutes les compétences disponibles et les affiche dans la vue
     */
    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        modifPersonnelVue.setToutesCompetences(competences);
    }
    /**
     * Charge les compétences de l'employé sélectionné et les affiche dans la vue
     */
    public void loadCompetencesEmploye() {
        List<Competence> competences;
        try {
            competences = daoCompetence.findCmpByLoginEmp(modifPersonnelVue.getLoginField().getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modifPersonnelVue.setTableCompetencesEmploye(competences);
    }
    /**
     * Ajoute une compétence sélectionnée à l'employé et met à jour les tableaux
     */
    public void ajouterCompetenceEmploye() {
        Competence selected = modifPersonnelVue.getCompetenceSelectionneeToutesCmp();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) modifPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();

            // Supprimer la ligne sélectionnée du tableau "Toutes les compétences"
            int selectedRow = modifPersonnelVue.getTableToutesCompetences().getSelectedRow();
            if (selectedRow != -1) {
                modelToutes.removeRow(selectedRow);
                modelEmploye.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }
    /**
     * Retire une compétence de l'employé et la remet dans les compétences disponibles
     */
    private void retirerCompetenceEmploye() {
        Competence selected = modifPersonnelVue.getCompetenceSelectionneeEmploye();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) modifPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();

            // Supprimer la ligne sélectionnée du tableau "Compétences employé"
            int selectedRow = modifPersonnelVue.getTableCompetencesEmploye().getSelectedRow();
            if (selectedRow != -1) {
                modelEmploye.removeRow(selectedRow);
                modelToutes.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

}
