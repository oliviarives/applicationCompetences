package controleur;

import modele.Competence;
import modele.dao.DAOCompetence;
import vue.CompetencesVue;

import java.util.List;

public class CompetenceControleur {

    private CompetencesVue vueC;
    private DAOCompetence competenceDAO;

    public CompetenceControleur(CompetencesVue vue, DAOCompetence daoC) {
        this.vueC = vue;
        this.competenceDAO = daoC;
    }

    public void loadCompetences() {
        List<Competence> competences = competenceDAO.findAll();
        vueC.setCompetences(competences);
    }
}