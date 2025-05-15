package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.ModificationMissionVue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModifierMissionControleur {
    private ModificationMissionVue modificationMV;
    private DAOMission daoMission;
    private DAOCompetence daoCompetence;
    private DAOEmploye daoEmploye;
    private Mission mission;
    private List<Competence> listeCompetencesSelectionnees;
    private List<Employe> listeEmployesSelectiones;
    private int idMissionSelectMissionView;
    private static final String MOT_TAB = "tabCompetences";
    private static final String MOT_ERREUR = "Erreur";

    public ModifierMissionControleur(ModificationMissionVue modificationMV, DAOMission daoMission, DAOCompetence daoComp, DAOEmploye daoEmp, Mission mission) {
        this.modificationMV = modificationMV;
        this.daoMission = daoMission;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;
        this.mission = mission;


       /* modificationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Mission misInsert= new Mission(
                                modificationMV.getTitreMisField(),
                                modificationMV.getDateDebutMisField(),
                                modificationMV.getDateFinMisField(),
                                //Date.valueOf("1970-01-01"),
                                //Date.valueOf("1970-01-01"),
                                modificationMV.getDescriptionMisField(),
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
        );*/
        modificationMV.getButtonConfirmer().addActionListener(e -> {
            // Récupération des valeurs saisies
            String titre = modificationMV.getTitreMisField().trim();
            String login = modificationMV.getLogEmpField().trim();
            java.sql.Date dateDebut = modificationMV.getDateDebutMisField();
            java.sql.Date dateFin = modificationMV.getDateFinMisField();
            String description = modificationMV.getDescriptionMisField();

            // Vérification des champs obligatoires
            if(titre.isEmpty() || login.isEmpty() || dateDebut == null || dateFin == null) {
                JOptionPane.showMessageDialog(null,
                        "Les champs Titre, Login Employé, Date de début et Date de fin sont obligatoires.",
                        MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de début et la date de fin ne sont pas antérieures à aujourd'hui
            java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
            if(dateDebut.before(today) || dateFin.before(today)) {
                JOptionPane.showMessageDialog(null,
                        "Les dates de début et de fin doivent être supérieures à la date d'aujourd'hui.",
                        MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que la date de fin n'est pas antérieure à la date de début
            if(dateFin.before(dateDebut)) {
                JOptionPane.showMessageDialog(null,
                        "La date de fin ne peut pas être antérieure à la date de début.",
                        MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérification de la validité du login employé via la méthode loginExist()
            try {
                if(!daoEmploye.isLoginExists(login)) {
                    JOptionPane.showMessageDialog(null,
                            "Le login employé saisi n'est pas valide. Veuillez entrer un login existant.",
                            MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                        "Erreur lors de la vérification du login employé.",
                        MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier que le titre de mission n'existe pas déjà dans une mission non terminée
           /*if(daoMission.missionTitleExists(titre)) {
                JOptionPane.showMessageDialog(null,
                        "Le titre de la mission existe déjà pour une mission non terminée. Veuillez choisir un autre titre.",
                        MOT_ERREUR, JOptionPane.ERROR_MESSAGE);
                return;
            }*/

            // Tout est validé, on peut créer la mission avec le statut "Préparation" (idSta = 1)
            Mission misInsert = new Mission(
                    titre,
                    description,
                    dateDebut,
                    dateFin,
                    login,
                    modificationMV.getNbEmpField(),
                    idMissionSelectMissionView
            );

            List<Competence> cmpAjoutees = modificationMV.getCompetencesAjoutees();
            List<String> logEmpAjoutes = modificationMV.getLogEmployeAjoutees();

            try {
                daoMission.updateMissionModifier(misInsert);
                daoMission.ajouterCmpToMission(misInsert, cmpAjoutees);
                daoMission.ajouterEmpToMission(misInsert, logEmpAjoutes);

                // Si on a affecté au moins un employé, on met à jour le statut à "Planifiée" (idSta = 2)
                if(!logEmpAjoutes.isEmpty()) {
                    daoMission.updateMissionStatus(misInsert, 2);
                }
                modificationMV.showPage(MOT_TAB);
                NavigationControleur.getVueV().getButtonMissions().doClick();
                for (String logEmp : logEmpAjoutes) {
                    daoEmp.addEmpCollaborerToMap(logEmp,dateDebut,dateFin);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //boutons pour afficher les compétences disponibles ds creation mission
        modificationMV.getAjouterCompetences().addActionListener(
                e -> modificationMV.showPage(MOT_TAB)
        );
        //boutons pour afficher les employés dispo ds creation mission
        /*modificationMV.getAjouterEmployes().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        modificationMV.showPage("tabEmployes");
                    }
                }
        );*/

        modificationMV.getAjouterEmployes().addActionListener(
                e -> {
                    if(modificationMV.getCompetencesAjoutees().isEmpty() || modificationMV.getDateDebutMisField()==null || modificationMV.getDateFinMisField()==null) {
                        JOptionPane.showMessageDialog(null, "Veuillez d'abord saisir des compétences et des dates à la mission!",
                                "Erreur de saisie!", JOptionPane.WARNING_MESSAGE);}
                    else{
                        modificationMV.showPage("tabEmployes");
                    }
                }
        );
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
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCmp(lcmpAjout);
                    modificationMV.setEmploye(listeEmployesSelectiones2); // Mise à jour de la table des employés
                }
            }
        });


        // Ajout employé à table des employés ajoutés
        modificationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int nbEmpMax = modificationMV.getNbEmpField(); // valeur du champ nbEmp
                    int nbEmpAjoutes = modificationMV.getTableEmployesAjoutes().getRowCount(); // nbr d'employé ajouté à mision

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
        modificationMV.getTableCompetencesAjoutees().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {//déclenchement au double click
                    int selectedRow = modificationMV.getTableCompetencesAjoutees().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) modificationMV.getTableCompetencesAjoutees().getModel();
                        model.removeRow(selectedRow);
                    }
                    listeCompetencesSelectionnees = modificationMV.getCompetencesAjoutees();
                    listeEmployesSelectiones = daoEmploye.findEmpByCmp(listeCompetencesSelectionnees);
                    modificationMV.setEmploye(listeEmployesSelectiones);
                }
            }
        });
        //retirer employé des employés ajoutés
        modificationMV.getTableEmployesAjoutes().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { //déclenchement au double click
                    int selectedRow = modificationMV.getTableEmployesAjoutes().getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel model = (DefaultTableModel) modificationMV.getTableEmployesAjoutes().getModel();
                        model.removeRow(selectedRow);
                    }
                }
            }
        });

        modificationMV.getBoutonModifierDates().addActionListener(
                e -> modificationMV.setDatesModifiables(true)
        );

        modificationMV.getBouttonConfirmerDates().addActionListener(
                e -> {
                    modificationMV.setDatesModifiables(false);
                    //daoEmp.setListeEmpCmp();
                    //daoEmp.miseAJourEmpByCmpByDate(creationMV.getDateDebutMisField(), creationMV.getDateFinMisField());
                    try {
                        modificationMV.setEmploye(daoEmp.miseAJourEmpByCmpByDate(modificationMV.getDateDebutMisField(), modificationMV.getDateFinMisField()));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );


    }

    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        modificationMV.setCompetencesAjout(competencesTable);
    }
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        modificationMV.setEmploye(employeTable);
    }

    public void preRemplirFormulaire() throws SQLException {
        // Pré-remplissage du formulaire avec les données de la mission
        modificationMV.setMissionData(mission);

        // Récupération et affichage des compétences associées à la mission
        List<Competence> competencesAssociees = daoMission.getCompetencesForMission(mission.getIdMission());
        modificationMV.remplirTableauCompetences(competencesAssociees);

        // Récupération et affichage des employés associés à la mission
        /*List<String> logins = daoMission.getLogEmployesForMission(mission.getIdMission());
        List<Employe> employesAssocies = new ArrayList<>();
        for (String login : logins) {
            Employe emp = daoEmploye.getEmployeByLogin(login);
            if (emp != null) {
                employesAssocies.add(emp);
            }
        }
        modificationMV.setEmploye(employesAssocies);*/

        // Chargement des listes disponibles pour la sélection
        loadCompetences();
        loadEmployes();

        // Affiche par défaut la vue des compétences associées (ou selon ton choix)
        modificationMV.showPage(MOT_TAB);
    }

    public void setIdMissionSelect(int is){
        this.idMissionSelectMissionView=is;
    }



}

