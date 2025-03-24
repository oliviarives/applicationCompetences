package controleur;

import modele.Competence;
import modele.Employe;
import modele.MdpUtils;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import vue.AjoutPersonnelVue;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AjouterPersonnelControleur {
    private final AjoutPersonnelVue ajoutPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final DAOCompetence daoCompetence;
    private final NavigationControleur navC;

    public AjouterPersonnelControleur(AjoutPersonnelVue ajoutPersonnelVue, DAOEmploye daoEmp, DAOCompetence daoCmp, NavigationControleur navigationC) {
        this.ajoutPersonnelVue = ajoutPersonnelVue;
        this.daoEmploye = daoEmp;
        this.navC = navigationC;
        this.daoCompetence = daoCmp;

        // Ajout des listener
        ajoutPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            ajouterPersonnel();
            effacerChamps();
            navC.loadEmploye();
            navC.getVueV().getButtonEmploye().doClick();

        });

        ajoutPersonnelVue.getButtonEffacer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effacerChamps();
            }
        });

        ajoutPersonnelVue.getButtonRetirer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retirerCompetenceEmploye();
            }
        });

        ajoutPersonnelVue.getButtonAjouter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterCompetenceEmploye();
            }
        });
    }

    public void ajouterPersonnel() {
        String prenom = ajoutPersonnelVue.getPrenomField().getText();
        String nom = ajoutPersonnelVue.getNomField().getText();
        String login = ajoutPersonnelVue.getLoginField().getText();
        String mdp = ajoutPersonnelVue.getMdpField().getText();
        String poste = ajoutPersonnelVue.getPosteField().getText();
        java.util.Date utilDate = (java.util.Date) ajoutPersonnelVue.getDateEntreeField().getValue();
        Date dateEntree = new Date(utilDate.getTime());

        // Vérification des champs obligatoires
        if (prenom.isEmpty() || nom.isEmpty() || login.isEmpty() || mdp.isEmpty() || poste.isEmpty()) {
            ajoutPersonnelVue.afficherMessage("Tous les champs sont obligatoires !");
            return;
        }

        try {
            if (daoEmploye.loginExist(login)) {
                ajoutPersonnelVue.afficherMessage("Ce login est déjà utilisé, veuillez en choisir un autre.");
                return;
            }
            // Hashage du mot de passe
            String mdpHashed = MdpUtils.hashPassword(mdp);
            // Création de l'employé
            Employe employe = new Employe(prenom, nom, login, mdpHashed, poste, dateEntree);
            daoEmploye.ajouterPersonnel(employe);
            for (Competence cmp : ajoutPersonnelVue.getCompetencesAjoutees()) {
                daoEmploye.ajouterPossession(employe.getLogin(), cmp);
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

    public void ajouterCompetenceEmploye() {
        Competence selected = ajoutPersonnelVue.getCompetenceSelectionneeToutesCmp();
        if (selected != null) {
            DefaultTableModel modelToutes = (DefaultTableModel) ajoutPersonnelVue.getTableToutesCompetences().getModel();
            DefaultTableModel modelEmploye = (DefaultTableModel) ajoutPersonnelVue.getTableCompetencesEmploye().getModel();

            // Supprimer la ligne sélectionnée du tableau "Toutes compétences"
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

            // Supprimer la ligne sélectionnée du tableau "Compétences employé"
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
