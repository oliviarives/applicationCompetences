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
    private final AjoutPersonnelVue ajoutPersonnelVue;
    private final DAOEmploye daoEmploye;
    private final NavigationControleur navC;

    public AjouterPersonnelControleur(AjoutPersonnelVue ajoutPersonnelVue, DAOEmploye daoEmp, NavigationControleur navigationC) {
        this.ajoutPersonnelVue = ajoutPersonnelVue;
        this.daoEmploye = daoEmp;
        this.navC = navigationC;

        // Ajout des listener
        ajoutPersonnelVue.getButtonConfirmer().addActionListener(e -> {
            ajouterPersonnel();
            navC.loadEmploye();
            navC.getVueV().getButtonEmploye().doClick();

        });

        ajoutPersonnelVue.getButtonEffacer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                effacerChamps();
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

        } catch (SQLException ex) {
            ajoutPersonnelVue.afficherMessage("Erreur lors de l'ajout de l'employé.");
            ex.printStackTrace();
        }
    }

    private void effacerChamps() {
        ajoutPersonnelVue.getPrenomField().setText("");
        ajoutPersonnelVue.getNomField().setText("");
        ajoutPersonnelVue.getLoginField().setText("");
        ajoutPersonnelVue.getMdpField().setText("");
        ajoutPersonnelVue.getDateEntreeField().setValue(new java.util.Date());
    }

}
