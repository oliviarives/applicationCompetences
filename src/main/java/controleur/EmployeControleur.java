package controleur;

import modele.Competence;
import modele.Employe;
import modele.dao.DAOEmploye;
import vue.CreationMissionView;
import vue.EmployeView;

import java.util.List;

public class EmployeControleur {
    private EmployeView empView;
    private DAOEmploye daoEmploye;


    public EmployeControleur(EmployeView vue,DAOEmploye daoEmp) {
        this.daoEmploye =daoEmp;
        this.empView = vue;
    }

    public void loadEmploye() {
        List<Employe> emp = daoEmploye.findAll();
        empView.setEmploye(emp);
    }

}
