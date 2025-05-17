package vue;

import utilitaires.StyleManager;

import javax.swing.*;
import java.awt.*;
/**
 * Fenêtre principale de l'application
 * Gère la navigation entre les différentes vues
 */
public class NavigationVue extends JFrame {
    private final JButton buttonMissions;
    private final JButton buttonCompetences;
    private final JButton buttonAccueil;
    private final JButton buttonEmploye;
    private final CardLayout cardLayout;
    private final JPanel panelCards;
    /**
     * Construit la fenêtre principale et initialise les composants de navigation
     */
    public NavigationVue() {
        StyleManager.setupFlatLaf();
        setTitle("Mission im-Possible");
        setSize(1400, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Couleur du fond du contentPane
        getContentPane().setBackground(new Color(0, 101, 255));

        // Panel de navigation principal
        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BorderLayout());
        panelNavigation.setBackground(new Color(255, 0, 0));

        // Navigation du menu
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navigationPanel.setBackground(new Color(40, 62, 80));

        // Boutons permettant la navigation
        this.buttonAccueil = new JButton("ACCUEIL");
        this.buttonMissions = new JButton("MISSIONS");
        this.buttonCompetences = new JButton("COMPETENCES");
        this.buttonEmploye = new JButton("EMPLOYES");

        // Ajout des boutons au panel
        navigationPanel.add(buttonAccueil);
        navigationPanel.add(buttonMissions);
        navigationPanel.add(buttonCompetences);
        navigationPanel.add(buttonEmploye);

        // Panel pour le changement d'une vue
        this.cardLayout = new CardLayout(40, 40);
        panelCards = new JPanel(cardLayout);
        panelCards.setBackground(new Color(255, 255, 255));
        panelCards.setSize(new Dimension(1100, 600));

        panelNavigation.add(navigationPanel, BorderLayout.NORTH);
        panelNavigation.add(panelCards, BorderLayout.CENTER);

        add(panelNavigation);

        setLocationRelativeTo(null);
    }

    // Getters
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

    /**
     * Ajoute une vue
     * @param name nom associé à la vue
     * @param page panneau représentant la vue
     */
    public void addPage(String name, JPanel page) {
        panelCards.add(page, name);
    }

    /**
     * Affiche la vue correspondant au nom spécifié
     * @param pageName nom de la vue à afficher
     */
    public void showPage(String pageName) {
        cardLayout.show(panelCards, pageName);
    }
}