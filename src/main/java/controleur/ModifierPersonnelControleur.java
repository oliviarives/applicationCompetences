package controleur;

import modele.Competence;
import modele.Employe;
import modele.MdpUtils;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.ModificationPersonnelVue;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ModifierPersonnelControleur {

    private final ModificationPersonnelVue modifPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final DAOCompetence daoCompetence;
    private final NavigationControleur navC;

    public ModifierPersonnelControleur (ModificationPersonnelVue modifPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp, NavigationControleur navigationC) {
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

    public void modifierPersonnel () {
        String prenom = modifPersonnelVue.getPrenomField().getText();
        String nom = modifPersonnelVue.getNomField().getText();
        String login = modifPersonnelVue.getLoginField().getText();
        String mdp = modifPersonnelVue.getMdpField().getText();
        String poste = modifPersonnelVue.getPosteField().getText();
        java.util.Date utilDate = (java.util.Date) modifPersonnelVue.getDateEntreeField().getValue();
        Date dateEntree = new Date(utilDate.getTime());
        //Employe employeNv = new Employe(prenom, nom, login, mdp, poste, dateEntree);
        // Vérification des champs obligatoires
        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || mdp.isEmpty() || poste.isEmpty()) {
            modifPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            // Hashage du mot de passe
            String mdpHashed = MdpUtils.hashPassword(mdp);
            // Recherche de l'employé à mettre à jour
            Employe employe = daoEmploye.findByLogin(login);
            daoEmploye.retirerToutesCompetences(login);
            daoEmploye.modifierPersonnel(employe);
            for (Competence cmp : modifPersonnelVue.getCompetencesAjoutees()) {
                daoEmploye.ajouterPossession(employe.getLogin(), cmp);
            }
        } catch (SQLException ex) {
            modifPersonnelVue.afficherMessage("Erreur lors de la modification de l'employé.");
            ex.printStackTrace();
        }
    }

    public void loadCompetences() {
        List<Competence> competences = daoCompetence.findAll();
        modifPersonnelVue.setToutesCompetences(competences);
    }

    public void loadCompetencesEmploye() {
        List<Competence> competences = null;
        System.out.println(modifPersonnelVue.getLoginField().getText());
        try {
            competences = daoCompetence.findByCompetencesEmploye(modifPersonnelVue.getLoginField().getText());
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

            // Supprimer la ligne sélectionnée du tableau "Toutes compétences"
            int selectedRow = modifPersonnelVue.getTableToutesCompetences().getSelectedRow();
            if (selectedRow != -1) {
                modelToutes.removeRow(selectedRow);
                modelEmploye.addRow(new Object[]{selected.getIdCatCmp(), selected.getIdCmp(), selected.getNomCmpFr()});
            }
        }
    }

    public void retirerCompetenceEmploye() {
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
