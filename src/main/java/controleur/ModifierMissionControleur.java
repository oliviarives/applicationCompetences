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
import java.sql.SQLException;
import java.util.List;

/**
 * Contrôleur chargé de la modification d'une mission existante à partir de la vue ModificationMissionVue
 * Gère la mise à jour des champs, des compétences et des employés liés à une mission
 */
public class ModifierMissionControleur {
    /**
     * Vue de modification d'une mission
     */
    private ModificationMissionVue modificationMV;
    /**
     * DAO pour les missions
     */
    private DAOMission daoMission;
    /**
     * DAO pour les compétences
     */
    private DAOCompetence daoCompetence;
    /**
     * DAO pour les employés
     */
    private DAOEmploye daoEmploye;
    /**
     * Mission à modifier
     */
    private Mission mission;
    /**
     * Liste des compétences sélectionnées
     */
    private List<Competence> listeCompetencesSelectionnees;
    /**
     * Liste des employés sélectionnés
     */
    private List<Employe> listeEmployesSelectiones;
    /**
     * Identifiant de la mission à modifier
     */
    private int idMissionSelectMissionView;
    /**
     * Constante pour le nom de l'onglet des compétences
     */
    private static final String MOT_TAB = "tabCompetences";
    /**
     * Constante pour le message d'erreur
     */
    private static final String MOT_ERREUR = "Erreur";
    /**
     * Initialise le contrôleur et configure tous les événements de la vue
     * @param modificationMV vue de modification de mission
     * @param daoMission DAO mission
     * @param daoComp DAO compétence
     * @param daoEmp DAO employé
     * @param mission mission à modifier
     */
    public ModifierMissionControleur(ModificationMissionVue modificationMV, DAOMission daoMission, DAOCompetence daoComp, DAOEmploye daoEmp, Mission mission) {
        this.modificationMV = modificationMV;
        this.daoMission = daoMission;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;
        this.mission = mission;

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

            // Vérifier que la date de début et la date de fin ne sont pas antérieures à la date du jour
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

            // Vérifier la validité du login employé
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

            List<Competence> cmpAjoutees = modificationMV.getCompetencesAjoutees();
            List<String> logEmpAjoutes = modificationMV.getLogEmployeAjoutees();

            int idStatut = 1;
            if(!logEmpAjoutes.isEmpty()) {
                idStatut=2;
            }

            Mission misInsert = new Mission(
                    titre,
                    dateDebut,
                    dateFin,
                    description,
                    new java.sql.Date(System.currentTimeMillis()),
                    modificationMV.getNbEmpField(),
                    login,
                    1
            );



            try {
                daoMission.updateMissionModifier(misInsert);
                daoMission.ajouterCmpToMission(misInsert, cmpAjoutees);
                daoMission.ajouterEmpToMission(misInsert, logEmpAjoutes);

                // Si on a affecté au moins un employé, on met à jour le statut de la mission à "Planifiée"
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
        //bouton pour afficher les compétences disponibles
        modificationMV.getAjouterCompetences().addActionListener(
                e -> modificationMV.showPage(MOT_TAB)
        );

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
        // Ajouter des compétences à table des compétences ajoutées
        modificationMV.getCompetenceTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Competence cmp = modificationMV.getCompetenceSelectionnee();
                    if (cmp != null) {
                        modificationMV.ajouterCompetenceAjoutee(cmp);
                    }
                    List<Competence>  lcmpAjout= modificationMV.getCompetencesAjoutees();
                    List<Employe> listeEmployesSelectiones2 = daoEmploye.findEmpByCmp(lcmpAjout);
                    // Mise à jour de la table des employés
                    modificationMV.setEmploye(listeEmployesSelectiones2);
                }
            }
        });


        // Ajout d'employé à la table des employés ajoutés
        modificationMV.getEmployesTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int nbEmpMax = modificationMV.getNbEmpField();
                    int nbEmpAjoutes = modificationMV.getTableEmployesAjoutes().getRowCount();

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


        // Retirer une compétence des compétences ajoutées
        modificationMV.getTableCompetencesAjoutees().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
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
        // Retirer un employé des employés ajoutés
        modificationMV.getTableEmployesAjoutes().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
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
                    try {

                        JOptionPane.showMessageDialog(null, "Lorsque vous modifiez les dates, les employés ajoutés sont éffacés !",
                                "INFORMATION", JOptionPane.WARNING_MESSAGE);
                        modificationMV.setEmploye(daoEmp.miseAJourEmpByCmpByDate(modificationMV.getDateDebutMisField(), modificationMV.getDateFinMisField()));
                        DefaultTableModel model = new DefaultTableModel(new String[]{"login","Prenom", "Nom", "Poste"},0);
                        modificationMV.getTableEmployesAjoutees().setModel(model);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);

                    }
                }
        );


    }
    /**
     * Charge toutes les compétences disponibles dans la vue
     */
    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        modificationMV.setCompetencesAjout(competencesTable);
    }
    /**
     * Charge tous les employés disponibles dans la vue
     */
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();
        modificationMV.setEmploye(employeTable);
    }
    /**
     * Préremplit le formulaire de modification avec les données de la mission sélectionnée
     * @throws SQLException en cas d'erreur lors de la récupération des données
     */
    public void preRemplirFormulaire() throws SQLException {
        // Pré-remplissage du formulaire avec les données de la mission
        modificationMV.setMissionData(mission);

        // Récupération et affichage des compétences associées à la mission
        List<Competence> competencesAssociees = daoMission.getCompetencesForMission(mission.getIdMission());
        modificationMV.remplirTableauCompetences(competencesAssociees);

        // Chargement des listes disponibles
        loadCompetences();
        loadEmployes();

        // Affiche par défaut la vue des compétences
        modificationMV.showPage(MOT_TAB);
    }
    /**
     * Définit l'identifiant de la mission sélectionnée
     * @param is identifiant de la mission
     */
    public void setIdMissionSelect(int is){
        this.idMissionSelectMissionView=is;
    }



}

