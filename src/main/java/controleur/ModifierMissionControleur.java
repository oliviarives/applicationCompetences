package controleur;

import modele.Competence;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOMission;
import vue.ModificationMissionView;

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

    public ModifierMissionControleur(ModificationMissionView modificationMV, DAOMission daoMission, NavigationControleur navigationC, DAOCompetence daoComp) {
        this.modificationMV = modificationMV;
        this.daoMission = daoMission;
        this.navC = navigationC;
        this.daoCompetence = daoComp;

        modificationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Mission misInsert = new Mission(
                                modificationMV.getTitreMisFieldValue(),
                                modificationMV.getDateDebutMisField(),
                                modificationMV.getDateFinMisField(),
                                modificationMV.getDescriptionMisFieldValue(),
                                new Date(System.currentTimeMillis()),
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

        modificationMV.getAjouterCompetences().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modificationMV.showPage("tabCompetences");
                    }
                }
        );

        modificationMV.getAjouterEmployes().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modificationMV.showPage("affichage");
                    }
                }
        );
    }

    public void loadCompetences() {
        List<Competence> competencesTable = daoCompetence.findAll();
        System.out.println("Compétences chargées: " + competencesTable.size()); // Debug
        modificationMV.setCompetencesAjout(competencesTable);
    }
}
