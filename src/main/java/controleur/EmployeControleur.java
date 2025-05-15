package controleur;

import modele.Employe;
import modele.dao.DAOEmploye;
import vue.EmployeVue;

import java.util.List;

public class EmployeControleur {
    private EmployeVue empView;
    private DAOEmploye daoEmploye;

    public EmployeControleur(EmployeVue empView, DAOEmploye daoEmploye) {
        this.empView = empView;
        this.daoEmploye = daoEmploye;
    }
    
    public void loadEmploye() {
        List<Employe> emp = daoEmploye.findAll();
        empView.setEmploye(emp);
    }
}