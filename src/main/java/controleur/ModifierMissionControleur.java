package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.CreationMissionView;
import vue.ModificationMissionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ModifierMissionControleur {
    private ModificationMissionView modificationMV;
    private DAOMission daoMission;
    private NavigationControleur navC;
    private DAOCompetence daoCompetence;
    private DAOEmploye daoEmploye;

    public ModifierMissionControleur(ModificationMissionView modificationMV, DAOMission daoMission, NavigationControleur navigationC,DAOCompetence daoComp,DAOEmploye daoEmp) {
        this.modificationMV = modificationMV;
        this.daoMission = daoMission;
        this.navC = navigationC;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;


        modificationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Mission misInsert= new Mission(
                                modificationMV.getTitreMisFieldValue(),
                                modificationMV.getDateDebutMisField(),
                                modificationMV.getDateFinMisField(),
                                //Date.valueOf("1970-01-01"),
                                //Date.valueOf("1970-01-01"),
                                modificationMV.getDescriptionMisFieldValue(),
                                new Date(System.currentTimeMillis()),
                                //Date.valueOf("1970-01-01"),
                                modificationMV.getNbEmpField(),
                                modificationMV.getLogEmpField(),
                                1
                        );
                        try {
                            daoMission.ajouterMission(misInsert);
                            navC.getVueV().getButtonMissions().doClick();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
        );
        //boutons pour afficher les compétences disponibles ds creation mission
        modificationMV.getAjouterCompetences().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        modificationMV.showPage("tabCompetences");
                    }
                }
        );
        //boutons pour afficher les employés dispo ds creation mission
        modificationMV.getAjouterEmployes().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        modificationMV.showPage("tabEmployes");
                    }
                }
        );
        //ajout compétences à table des compétences ajoutées
        //ajout compétences à table des compétences ajoutées
        modificationMV.getCompetenceTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    Competence cmp = modificationMV.getCompetenceSelectionnee();
                    if (cmp != null) {
                        modificationMV.ajouterCompetenceAjoutee(cmp);
                    }
                    List<Competence>  lcmpAjout= modificationMV.getCompetencesAjoutees();
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCompetences(lcmpAjout);
                    modificationMV.setEmploye(listeEmployesSelectiones2); // Mise à jour de la table des employés
                }
            }
        });
        //ajout employé à table des employé ajoutés
        /*creationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Employe emp = creationMV.getEmployeSelectionne();
                    if (emp != null) {
                        creationMV.ajouterEmployesAjoutee(emp);
                    }
                }
            }
        });*/
        // Ajout employé à table des employés ajoutés
        modificationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int nbEmpMax = modificationMV.getNbEmpField(); // valeur du champ nbEmp
                    int nbEmpAjoutes = modificationMV.getListeEmployesAjoutee().getRowCount(); // nbr d'employé ajouté à mision

                    if (nbEmpAjoutes < nbEmpMax) { //si nbEmp nécessaire pas encore atteind
                        Employe emp = modificationMV.getEmployeSelectionne();
                        if (emp != null) {
                            modificationMV.ajouterEmployesAjoutee(emp);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter plus d'employés que le nombre spécifié !",
                                "Limite employés atteinte", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //retirer compétence des compétences ajoutées
        modificationMV.getListeCompetenceAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {//déclenchement au double click
                    int selectedRow = modificationMV.getListeCompetenceAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) modificationMV.getListeCompetenceAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });
        //retirer employé des employés ajoutés
        modificationMV.getListeEmployesAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int selectedRow = modificationMV.getListeEmployesAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) modificationMV.getListeEmployesAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });


    }

    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        //System.out.println("Compétences chargées: " + competencesTable.size());
        modificationMV.setCompetencesAjout(competencesTable);
    }
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        modificationMV.setEmploye(employeTable);
    }
}

