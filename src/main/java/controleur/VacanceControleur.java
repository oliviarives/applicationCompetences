package controleur;

import modele.dao.DAOEmploye;
import vue.VacanceVue;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Contrôleur responsable de l'ajout de périodes de vacances pour un employé depuis la vue VacanceVue
 */
public class VacanceControleur {
    /**
     * Vue de gestion des vacances
     */
    private final VacanceVue vacanceVue;
    /**
     * DAO pour la gestion des employés
     */
    private final DAOEmploye daoEmp;
    /**
     * Initialise le contrôleur et configure l'action de validation des vacances
     * @param vacanceVue vue des vacances
     * @param daoEmp DAO des employés
     */
    public VacanceControleur(VacanceVue vacanceVue, DAOEmploye daoEmp) {
        this.vacanceVue = vacanceVue;
        this.daoEmp = daoEmp;
        vacanceVue.getValiderBoutton().addActionListener(e -> ajouterVacance());
    }
    /**
     * Récupère les dates saisies dans la vue et ajoute une période de vacances pour l'employé concerné
     * Vérifie les champs, convertit les dates, insère dans la base de données et affiche une confirmation
     */
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
            NavigationControleur.getVueV().showPage("Employe");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Format de date invalide. Utilisez le format YYYY-MM-DD.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout des vacances.");
        }
    }
}
