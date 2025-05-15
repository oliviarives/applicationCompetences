package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.CreationMissionVue;
import vue.InformationEmpVue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AjouterMissionControleur {
    private CreationMissionVue creationMV;
    private DAOMission daoMission;
    private NavigationControleur navC;
    private DAOCompetence daoCompetence;
    private DAOEmploye daoEmploye;
    private InformationEmpVue informationEmpView;
    private List<Competence> listeCompetencesSelectionnees;
    private List<Employe> listeEmployesSelectiones;
    final String MESSAGE_ERREUR = "ERREUR";

    public AjouterMissionControleur(CreationMissionVue creationMV, DAOMission daoMission, NavigationControleur navigationC, DAOCompetence daoComp, DAOEmploye daoEmp, InformationEmpVue infoEmpV) {
        this.creationMV = creationMV;
        this.daoMission = daoMission;
        this.navC = navigationC;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;
        this.informationEmpView = infoEmpV;


        creationMV.getButtonConfirmer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupération des valeurs saisies
                String titre = creationMV.getTitreMisFieldValue().trim();
                String login = creationMV.getLogEmpField().trim();
                java.sql.Date dateDebut = creationMV.getDateDebutMisField();
                java.sql.Date dateFin = creationMV.getDateFinMisField();
                String description = creationMV.getDescriptionMisFieldValue();

                // Vérification des champs obligatoires
                if(titre.isEmpty() || login.isEmpty() || dateDebut == null || dateFin == null) {
                    JOptionPane.showMessageDialog(null,
                            "Les champs Titre, Login Employé, Date de début et Date de fin sont obligatoires.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérifier que la date de début et la date de fin ne sont pas antérieures à aujourd'hui
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                if(dateDebut.before(today) || dateFin.before(today)) {
                    JOptionPane.showMessageDialog(null,
                            "Les dates de début et de fin doivent être supérieures à la date d'aujourd'hui.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérifier que la date de fin n'est pas antérieure à la date de début
                if(dateFin.before(dateDebut)) {
                    JOptionPane.showMessageDialog(null,
                            "La date de fin ne peut pas être antérieure à la date de début.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérification de la validité du login employé via la méthode loginExist()
                try {
                    if(!daoEmploye.isLoginExists(login)) {
                        JOptionPane.showMessageDialog(null,
                                "Le login employé saisi n'est pas valide. Veuillez entrer un login existant.",
                                MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Erreur lors de la vérification du login employé.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérifier que le titre de mission n'existe pas déjà dans une mission non terminée
                if(daoMission.missionTitleExists(titre)) {
                    JOptionPane.showMessageDialog(null,
                            "Le titre de la mission existe déjà pour une mission non terminée. Veuillez choisir un autre titre.",
                            MESSAGE_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tout est validé, on peut créer la mission avec le statut "Préparation" (idSta = 1)
                Mission misInsert = new Mission(
                        titre,
                        dateDebut,
                        dateFin,
                        description,
                        new java.sql.Date(System.currentTimeMillis()),
                        creationMV.getNbEmpField(),
                        login,
                        1
                );

                List<Competence> cmpAjoutees = creationMV.getCompetencesAjoutees();
                List<String> logEmpAjoutes = creationMV.getLogEmployeAjoutees();

                try {
                    daoMission.ajouterMission(misInsert);
                    daoMission.ajouterCmpToMission(misInsert, cmpAjoutees);
                    daoMission.ajouterEmpToMission(misInsert, logEmpAjoutes);

                    // Si on a affecté au moins un employé, on met à jour le statut à "Planifiée" (idSta = 2)
                    if (!logEmpAjoutes.isEmpty()) {
                        daoMission.updateMissionStatus(misInsert, 2);
                    }
                    creationMV.showPage("tabCompetences");
                    navC.getVueV().getButtonMissions().doClick();
                    creationMV.resetFields();
                    //daoEmp.updateDataSetCollaborer();
                    for (String logEmp : logEmpAjoutes) {
                        daoEmp.addEmpCollaborerToMap(logEmp,dateDebut,dateFin);
                    }
                    //daoEmp.updateListeEmployeCollaborer();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //boutons pour afficher les compétences disponibles ds creation mission
        creationMV.getAjouterCompetences().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        creationMV.showPage("tabCompetences");
                    }
                }
        );
        //boutons pour afficher les employés dispo ds creation mission
        creationMV.getAjouterEmployes().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(creationMV.getCompetencesAjoutees().isEmpty() || creationMV.getDateDebutMisField()==null || creationMV.getDateFinMisField()==null) {
                            JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir des compétences et des dates à la mission!",
                                    "Erreur de saisie!", JOptionPane.WARNING_MESSAGE);}
                        else{
                            creationMV.showPage("tabEmployes");
                        }
                    }
                }
        );
        //ajout compétences à table des compétences ajoutées
        creationMV.getCompetenceTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    Competence cmp = creationMV.getCompetenceSelectionnee();
                    if (cmp != null) {
                        creationMV.ajouterCompetenceAjoutee(cmp);
                    }
                    List<Competence> lcmpAjout = creationMV.getCompetencesAjoutees();
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCmp(lcmpAjout);
                    creationMV.setEmploye(listeEmployesSelectiones2); // Mise à jour de la table des employés
                }
            }
        });

        // Ajout employé à table des employés ajoutés
        creationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int nbEmpMax = creationMV.getNbEmpField(); // valeur du champ nbEmp
                    int nbEmpAjoutes = creationMV.getTableEmployesAjoutee().getRowCount(); // nbr d'employé ajouté à mision

                    if (nbEmpAjoutes < nbEmpMax) { //si nbEmp nécessaire pas encore atteind
                        Employe emp = creationMV.getEmployeSelectionne();
                        if (emp != null) {
                            creationMV.ajouterEmployesAjoutee(emp);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter plus d'employés que le nombre spécifié !",
                                "Limite employés atteinte", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        //retirer compétence des compétences ajoutées
        creationMV.getTableCompetencesAjoutees().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {//déclenchement au double click
                    int selectedRow = creationMV.getTableCompetencesAjoutees().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getTableCompetencesAjoutees().getModel();
                        model.removeRow(selectedRow);
                    }
                    listeCompetencesSelectionnees = creationMV.getCompetencesAjoutees();
                    //daoEmploye.finalListeEmp();
                    listeEmployesSelectiones = daoEmploye.findEmpByCmp(listeCompetencesSelectionnees);
                    for(Employe emp : listeEmployesSelectiones) {
                        System.out.println("empdslistecontroleur apres ajout cmp"+emp.getLogin());
                    }
                    creationMV.setEmploye(listeEmployesSelectiones);
                }
            }
        });
        //retirer employé des employés ajoutés
        creationMV.getTableEmployesAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int selectedRow = creationMV.getTableEmployesAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getTableEmployesAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        creationMV.getBoutonModifierDates().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        creationMV.setDatesModifiables(true);
                    }
                }
        );

        creationMV.getBouttonConfirmerDates().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        creationMV.setDatesModifiables(false);
                        //daoEmp.setListeEmpCmp();
                        //daoEmp.miseAJourEmpByCmpByDate(creationMV.getDateDebutMisField(), creationMV.getDateFinMisField());
                        try {
                            creationMV.setEmploye(daoEmp.miseAJourEmpByCmpByDate(creationMV.getDateDebutMisField(), creationMV.getDateFinMisField()));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );

        /*creationMV.getInfoButton().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        creationMV.setDatesModifiables(true);
                    }
                }
        );*/
    }

    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        //System.out.println("Compétences chargées: " + competencesTable.size());
        creationMV.setCompetencesAjout(competencesTable);
    }
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        creationMV.setEmploye(employeTable);
    }
}