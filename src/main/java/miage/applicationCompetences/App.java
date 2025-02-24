package miage.applicationCompetences;

import modele.Mission;
import modele.Statut;
import modele.Responsable;
import vue.MissionVue;
import controleur.MissionControleur;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        // Création d'un contrôleur
        MissionControleur controleur = new MissionControleur();

        // Création d'un responsable fictif
        Responsable responsable = new Responsable("Dupont", "Jean", null, null, null, null, null, null);

        // Création de la vue
        MissionVue missionVue = new MissionVue(controleur);

        // Création d'une mission fictive avec toutes les données nécessaires
        Mission missionTest = new Mission(
            "Développement Web",
            LocalDate.of(2024, 2, 20),  // Date de début
            LocalDate.of(2024, 3, 20),  // Date de fin
            "Développement d'une plateforme e-commerce",
            5,  // Nombre d'employés
            "Créer une plateforme pour la vente en ligne",
            responsable,  // Responsable fictif
            Statut.EN_COURS // Statut de la mission
        );

        // Ajout de la mission au contrôleur
        controleur.ajouterMission(
            missionTest.getTitre(),
            missionTest.getDateDebut(),
            missionTest.getDateFin(),
            missionTest.getCommentaires(),
            missionTest.getNbEmployeTotal(),
            missionTest.getDescription(),
            missionTest.getResponsable(),
            missionTest.getStatut()
        );

        // Affichage de la mission dans la vue
        missionVue.afficherMission(missionTest);
    }
}
