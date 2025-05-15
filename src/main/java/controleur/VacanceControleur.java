package controleur;

import modele.dao.DAOEmploye;
import vue.VacanceVue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class VacanceControleur {

    private final VacanceVue vacanceVue;
    private final DAOEmploye daoEmp;

    public VacanceControleur(VacanceVue vacanceVue, DAOEmploye daoEmp) {
        this.vacanceVue = vacanceVue;
        this.daoEmp = daoEmp;
        vacanceVue.getValiderBoutton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterVacance();
            }
        });
    }

    private void ajouterVacance() {
        String login = vacanceVue.getLogin();
        String dateDebutStr = vacanceVue.getDateDebut();
        String dateFinStr = vacanceVue.getDateFin();

        if (login.isEmpty() || dateDebutStr.isEmpty() || dateFinStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.");
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDateDebut = dateFormat.parse(dateDebutStr);
            java.util.Date parsedDateFin = dateFormat.parse(dateFinStr);

            Date dateDebut = new Date(parsedDateDebut.getTime());
            Date dateFin = new Date(parsedDateFin.getTime());

            daoEmp.ajouterVacance(dateDebut, dateFin, login);
            JOptionPane.showMessageDialog(null, "Vacances ajoutées pour l'employé " + login + " !");
            NavigationControleur.loadEmploye();
            NavigationControleur.getVueV().showPage("Employe"); // ferme le form vac pour ouvrir le tab emp
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Format de date invalide. Utilisez le format YYYY-MM-DD.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout des vacances.");
        }
    }
}
