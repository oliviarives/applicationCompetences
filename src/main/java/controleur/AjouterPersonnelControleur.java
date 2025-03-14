package controleur;

import modele.Employe;
import modele.dao.DAOEmploye;
import vue.AjoutPersonnelVue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AjouterPersonnelControleur {
    private AjoutPersonnelVue ajoutPersonnelVue;
    private DAOEmploye daoEmploye;

    public AjouterPersonnelControleur(AjoutPersonnelVue ajoutPersonnelVue, DAOEmploye daoEmploye) {
        this.ajoutPersonnelVue = ajoutPersonnelVue;
        this.daoEmploye = daoEmploye;

        ajoutPersonnelVue.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ajouterPersonnel();
                    }
                }
        );
    }

    public void ajouterPersonnel() {
        Employe employe = ajoutPersonnelVue.getEmploye();
        try {
            daoEmploye.ajouterPersonnel(employe);
            //System.out.println("Employé ajouté avec succès : " + employe.getNom());
            //ajoutPersonnelVue.afficherMessage("Employé ajouté avec succès !");
        } catch (SQLException ex) {
            ajoutPersonnelVue.afficherMessage("Erreur lors de l'ajout de l'employé : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}