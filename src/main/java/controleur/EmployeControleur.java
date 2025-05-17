package controleur;

import modele.Employe;
import modele.dao.DAOEmploye;
import vue.EmployeVue;

import java.util.List;

/**
 * Contrôleur pour gérer l'affichage des employés dans la vue EmployeVue
 */
public class EmployeControleur {
    /**
     * Vue affichant les employés
     */
    private EmployeVue empView;
    /**
     * DAO pour la gestion des employés
     */
    private DAOEmploye daoEmploye;

    /**
     * Initialise le contrôleur avec la vue et le DAO employé
     * @param empView vue des employés
     * @param daoEmploye DAO des employés
     */
    public EmployeControleur(EmployeVue empView, DAOEmploye daoEmploye) {
        this.empView = empView;
        this.daoEmploye = daoEmploye;
    }
    /**
     * Charge tous les employés et les transmet à la vue
     */
    public void loadEmploye() {
        List<Employe> emp = daoEmploye.findAll();
        empView.setEmploye(emp);
    }
}