package controleur;

import modele.Competence;
import modele.Employe;
import modele.Mission;
import modele.dao.DAOCompetence;
import modele.dao.DAOEmploye;
import modele.dao.DAOMission;
import vue.CreationMissionView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AjouterMissionControleur {
    private CreationMissionView creationMV;
    private DAOMission daoMission;
    private NavigationControleur navC;
    private DAOCompetence daoCompetence;
    private DAOEmploye daoEmploye;

    public AjouterMissionControleur(CreationMissionView creationMV, DAOMission daoMission, NavigationControleur navigationC,DAOCompetence daoComp,DAOEmploye daoEmp) {
        this.creationMV = creationMV;
        this.daoMission = daoMission;
        this.navC = navigationC;
        this.daoCompetence = daoComp;
        this.daoEmploye = daoEmp;


        creationMV.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        Mission misInsert= new Mission(
                                creationMV.getTitreMisFieldValue(),
                                creationMV.getDateDebutMisField(),
                                creationMV.getDateFinMisField(),
                                //Date.valueOf("1970-01-01"),
                                //Date.valueOf("1970-01-01"),
                                creationMV.getDescriptionMisFieldValue(),
                                new Date(System.currentTimeMillis()),
                                //Date.valueOf("1970-01-01"),
                                creationMV.getNbEmpField(),
                                creationMV.getLogEmpField(),
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

        creationMV.getAjouterCompetences().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        creationMV.showPage("tabCompetences");
                    }
                }
        );

        creationMV.getAjouterEmployes().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        creationMV.showPage("tabEmployes");
                    }
                }
        );




    }

    public void loadCompetences(){
        List<Competence> competencesTable = daoCompetence.findAll();
        //System.out.println("Compétences chargées: " + competencesTable.size()); // Debug
        creationMV.setCompetencesAjout(competencesTable);
    }
    public void loadEmployes(){
        List<Employe> employeTable = daoEmploye.findAll();

        creationMV.setEmploye(employeTable);
    }
}
