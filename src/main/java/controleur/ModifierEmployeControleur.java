package controleur;

import modele.Competence;
import modele.Employe;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.ModificationEmployeVue;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ModifierEmployeControleur {

    private final ModificationEmployeVue modifPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final DAOCompetence daoCompetence;
    private final NavigationControleur navC;

    public ModifierEmployeControleur(ModificationEmployeVue modifPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp, NavigationControleur navigationC) {
        this.modifPersonnelVue = modifPersonnelVue;
        this.daoEmploye = daoEmp;
        this.navC = navigationC;
        this.daoCompetence = daoCmp;

        modifPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            this.modifierPersonnel();
            //effacerChamps();
            navC.loadEmploye();
            navC.getVueV().getButtonEmploye().doClick();
        });

        modifPersonnelVue.getButtonRetirer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retirerCompetenceEmploye();
            }
        });

        modifPersonnelVue.getButtonAjouter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterCompetenceEmploye();
            }
        });
    }

    private void modifierPersonnel () {
        String prenom = modifPersonnelVue.getPrenomField().getText();
        String nom = modifPersonnelVue.getNomField().getText();
        String login = modifPersonnelVue.getLoginField().getText();
        String poste = modifPersonnelVue.getPosteField().getText();
        java.util.Date utilDate = (java.util.Date) modifPersonnelVue.getDateEntreeField().getValue();
        //Employe employeNv = new Employe(prenom, nom, login, mdp, poste, dateEntree);
        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || poste.isEmpty()) {
            modifPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            //Recherche de l'employé à mettre à jour
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

    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        modifPersonnelVue.setToutesCompetences(competences);
    }

    public void loadCompetencesEmploye() {
        List<Competence> competences;
        System.out.println(modifPersonnelVue.getLoginField().getText());
        try {
            competences = daoCompetence.findCmpByLoginEmp(modifPersonnelVue.getLoginField().getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        modifPersonnelVue.setTableCompetencesEmploye(competences);
    }

    public void ajouterCompetenceEmploye() {
        Competence selected = modifPersonnelVue.getCompetenceSelectionneeToutesCmp();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) modifPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();

            //Supprimer la ligne sélectionnée du tableau "Toutes compétences"
            int selectedRow = modifPersonnelVue.getTableToutesCompetences().getSelectedRow();
            if (selectedRow != -1) {
                modelToutes.removeRow(selectedRow);
                modelEmploye.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

    private void retirerCompetenceEmploye() {
        Competence selected = modifPersonnelVue.getCompetenceSelectionneeEmploye();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) modifPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) modifPersonnelVue.getTableCompetencesEmploye().getModel();

            //Supprimer la ligne sélectionnée du tableau "Compétences employé"
            int selectedRow = modifPersonnelVue.getTableCompetencesEmploye().getSelectedRow();
            if (selectedRow != -1) {
                modelEmploye.removeRow(selectedRow);
                modelToutes.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

}
