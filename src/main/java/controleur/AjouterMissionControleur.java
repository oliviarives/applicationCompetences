package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.CreationMissionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class AjouterMissionControleur {
    private final CreationMissionView creationMV;
    private final NavigationControleur navigationC;
    private final DAOCompetence daoCompetence;
    private final DAOEmploye daoEmploye;
    private List<Competence> listeCompetencesSelectionnees;
    private List<Employe> listeEmployesSelectiones;
    static final String MESSAGEERREUR = "ERREUR";

    public AjouterMissionControleur(CreationMissionView creationMV, DAOMission daoMission, NavigationControleur navigationC,DAOCompetence daoComp,DAOEmploye daoEmp) {
        this.creationMV = creationMV;
        this.navigationC = navigationC;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;

        creationMV.getButtonConfirmer().addActionListener(e -> {
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
                        MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de début et la date de fin ne sont pas antérieures à aujourd'hui
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            if(dateDebut.before(today) || dateFin.before(today)) {
                JOptionPane.showMessageDialog(null,
                        "Les dates de début et de fin doivent être supérieures à la date d'aujourd'hui.",
                        MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de fin n'est pas antérieure à la date de début
            if(dateFin.before(dateDebut)) {
                JOptionPane.showMessageDialog(null,
                        "La date de fin ne peut pas être antérieure à la date de début.",
                        MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification de la validité du login employé via la méthode loginExist()
            try {
                if(!daoEmploye.loginExist(login)) {
                    JOptionPane.showMessageDialog(null,
                            "Le login employé saisi n'est pas valide. Veuillez entrer un login existant.",
                            MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Erreur lors de la vérification du login employé.",
                        MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que le titre de mission n'existe pas déjà dans une mission non terminée
            if(daoMission.missionTitleExists(titre)) {
                JOptionPane.showMessageDialog(null,
                        "Le titre de la mission existe déjà pour une mission non terminée. Veuillez choisir un autre titre.",
                        MESSAGEERREUR, JOptionPane.ERROR_MESSAGE);
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
                daoMission.ajouterMissionCmp(misInsert, cmpAjoutees);
                daoMission.ajouterMissionEmp(misInsert, logEmpAjoutes);

                // Si on a affecté au moins un employé, on met à jour le statut à "Planifiée" (idSta = 2)
                if(!logEmpAjoutes.isEmpty()) {
                    daoMission.updateMissionStatus(misInsert, 2);
                }

                NavigationControleur.getVueV().getButtonMissions().doClick();
                creationMV.resetFields();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //boutons pour afficher les compétences disponibles ds creation mission
        creationMV.getAjouterCompetences().addActionListener(
                e -> creationMV.showPage("tabCompetences")
        );
        //boutons pour afficher les employés disponibles dans la création mission
        creationMV.getAjouterEmployes().addActionListener(
                e -> creationMV.showPage("tabEmployes")
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
                    List<Competence>  lcmpAjout= creationMV.getCompetencesAjoutees();
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCompetences(lcmpAjout);
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
                    int nbEmpAjoutes = creationMV.getListeEmployesAjoutee().getRowCount(); // nbr d'employé ajouté à mision

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
        creationMV.getListeCompetenceAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {//déclenchement au double click
                    int selectedRow = creationMV.getListeCompetenceAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getListeCompetenceAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                    listeCompetencesSelectionnees = creationMV.getCompetencesAjoutees();
                    listeEmployesSelectiones = daoEmploye.findEmpByCompetences(listeCompetencesSelectionnees);
                    creationMV.setEmploye(listeEmployesSelectiones);
                }
            }
        });
        //retirer employé des employés ajoutés
        creationMV.getListeEmployesAjoutee().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int selectedRow = creationMV.getListeEmployesAjoutee().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) creationMV.getListeEmployesAjoutee().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        creationMV.getBoutonModifierDates().addActionListener(
                e -> creationMV.setDatesModifiables(true)
        );

        creationMV.getBouttonConfirmerDates().addActionListener(
                e -> {
                    creationMV.setDatesModifiables(false);
                    creationMV.setEmploye(daoEmp.miseAJourEmpByCmpByDate(creationMV.getDateDebutMisField(), creationMV.getDateFinMisField()));
                }
        );
    }

    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        creationMV.setCompetencesAjout(competencesTable);
    }
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        creationMV.setEmploye(employeTable);
    }
}