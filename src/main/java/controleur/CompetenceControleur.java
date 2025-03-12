package controleur;

import modele.Competence;
import modele.dao.DAOCompetence;
import vue.CompetencesView;

import java.util.List;

public class CompetenceControleur {

    private CompetencesView vueC;
    private DAOCompetence competenceDAO;

    public CompetenceControleur(CompetencesView vue, DAOCompetence daoC) {
        this.vueC = vue;
        this.competenceDAO = daoC;

    }

    public void loadCompetences() {
        List<Competence> competences = competenceDAO.findAll();
        vueC.setCompetences(competences);
    }

}