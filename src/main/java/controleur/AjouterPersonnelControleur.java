package controleur;

import modele.Employe;
import modele.MdpUtils;
import modele.dao.DAOEmploye;
import vue.AjoutPersonnelVue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterPersonnelControleur {
    private AjoutPersonnelVue ajoutPersonnelVue;
    private DAOEmploye daoEmploye;
    private NavigationControleur navC;

    public AjouterPersonnelControleur(AjoutPersonnelVue ajoutPersonnelVue, DAOEmploye daoEmp, NavigationControleur navigationC) {
        this.ajoutPersonnelVue = ajoutPersonnelVue;
        this.daoEmploye = daoEmp;
        this.navC = navigationC;

        ajoutPersonnelVue.getButtonConfirmer().addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ajouterPersonnel();
                        navC.getVueV().getButtonEmploye().doClick();
                    }
                }
        );
    }

    private Employe getEmploye() {
        String prenom = ajoutPersonnelVue.getPrenomField().getText();
        String nom = ajoutPersonnelVue.getNomField().getText();
        String login = ajoutPersonnelVue.getLoginField().getText();
        String mdp = ajoutPersonnelVue.getMdpField().getText();
        String poste = ajoutPersonnelVue.getPosteField().getText();
        java.util.Date date = (java.util.Date) ajoutPersonnelVue.getDateEntreeSpinner().getValue();
        Date dateEntree = new Date(date.getTime());

        String mdpHashed = MdpUtils.hashPassword(mdp);

        return new Employe(prenom, nom, login, mdpHashed, poste, dateEntree);
    }

    public void ajouterPersonnel() {
        Employe employe = this.getEmploye();
        try {
            System.out.println("Je suis là");
            daoEmploye.ajouterPersonnel(employe);
            //System.out.println("Employé ajouté avec succès : " + employe.getNom());
            //ajoutPersonnelVue.afficherMessage("Employé ajouté avec succès !");
        } catch (SQLException ex) {
            ajoutPersonnelVue.afficherMessage("Erreur lors de l'ajout de l'employé : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}