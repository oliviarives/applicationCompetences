package controleur;

import modele.Employe;
import modele.dao.DAOEmploye;
import vue.EmployeView;

import java.util.List;

public class EmployeControleur {
    private EmployeView empView;
    private DAOEmploye daoEmploye;
    private NavigationControleur navC;

    public EmployeControleur(EmployeView empView, DAOEmploye daoEmploye, NavigationControleur navC) {
        this.empView = empView;
        this.daoEmploye = daoEmploye;
        this.navC = navC; 
    }
    
    public void loadEmploye() {
        List<Employe> emp = daoEmploye.findAll();
        empView.setEmploye(emp);
    }
}