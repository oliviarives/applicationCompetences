package vue;

import utilitaires.StyleManager;

import javax.swing.*;
import java.awt.*;

public class NavigationVue extends JFrame {
    private final JButton buttonMissions;
    private final JButton buttonCompetences;
    private final JButton buttonAccueil;
    private final JButton buttonEmploye;
    private final CardLayout cardLayout;
    private final JPanel panelCards;

    public NavigationVue() {
        StyleManager.setupFlatLaf();
        setTitle("Mission im-Possible");
        setSize(1400, 700);//taille de l'application
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Définir la couleur de fond du contentPane
        getContentPane().setBackground(new Color(237, 227, 228)); // Couleur de fond personnalisée

        //definition du panel de navigation contenant l'ensemble de l'application
        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BorderLayout());
        panelNavigation.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le panelNavigation

        //definition du panel accueillant les boutons de navigation
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navigationPanel.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le navigationPanel

        //definition des boutons servant à la navigation entres les vues
        this.buttonAccueil = new JButton("ACCUEIL");
        this.buttonMissions = new JButton("MISSIONS");
        this.buttonCompetences = new JButton("COMPETENCES");
        this.buttonEmploye = new JButton("EMPLOYES");

        //ajout des boutons de navigations au panel de boutons
        navigationPanel.add(buttonAccueil);
        navigationPanel.add(buttonMissions);
        navigationPanel.add(buttonCompetences);
        navigationPanel.add(buttonEmploye);


        //panel permettant le changement de vue
        this.cardLayout = new CardLayout(40, 40);
        panelCards = new JPanel(cardLayout);
        panelCards.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le panelCards
        panelCards.setSize(new Dimension(1100, 600));

        //ajout des elements de navigation, boutons et panels de vues
        panelNavigation.add(navigationPanel, BorderLayout.NORTH);//border layout place les boutons en haut
        panelNavigation.add(panelCards, BorderLayout.CENTER);

        add(panelNavigation);

        setLocationRelativeTo(null);
    }

    public JButton getButtonMissions() {
        return this.buttonMissions;
    }

    public JButton getButtonCompetences() {
        return this.buttonCompetences;
    }

    public JButton getButtonEmploye() {
        return this.buttonEmploye;
    }

    public JButton getButtonAccueil() {
        return buttonAccueil;
    }

    public void addPage(String name, JPanel page) {
        panelCards.add(page, name);
    }

    // Méthode pour afficher une page en fonction de son nom
    public void showPage(String pageName) {
        cardLayout.show(panelCards, pageName);
    }
}