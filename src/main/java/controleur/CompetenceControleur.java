package controleur;

import modele.Competence;
import modele.dao.DAOCompetence;
import vue.CompetencesVue;

import java.util.List;

/**
 * Contrôleur pour la gestion des compétences
 * Gère le chargement des compétences dans la vue CompetencesVue
 */
public class CompetenceControleur {
    /**
     * Vue pour afficher des compétences
     */
    private CompetencesVue vueC;
    /**
     * DAO pour accéder aux compétences
     */
    private DAOCompetence competenceDAO;

    /**
     * Initialise le contrôleur avec la vue et le DAO
     * @param vue vue des compétences
     * @param daoC DAO des compétences
     */
    public CompetenceControleur(CompetencesVue vue, DAOCompetence daoC) {
        this.vueC = vue;
        this.competenceDAO = daoC;
    }

    /**
     * Charge toutes les compétences depuis la base et les transmet à la vue
     */
    public void loadCompetences() {
        List<Competence> competences = competenceDAO.findAll();
        vueC.setCompetences(competences);
    }
}