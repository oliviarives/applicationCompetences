package vue;

import utilitaires.StyleManager;

import javax.swing.*;
import java.awt.*;

public class NavigationView extends JFrame {
    private final JButton buttonMissions;
    private final JButton buttonCompetences;
    private final JButton buttonAccueil;
    private final JButton buttonEmploye;
    private final CardLayout cardLayout;
    private final JPanel panelCards;

    public NavigationView() {
        StyleManager.setupFlatLaf();
        setTitle("Mission im-Possible");
        setSize(1400, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Définir la couleur de fond du contentPane
        getContentPane().setBackground(new Color(237, 227, 228)); // Couleur de fond personnalisée

        JPanel panelNavigation = new JPanel();
        panelNavigation.setLayout(new BorderLayout());
        panelNavigation.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le panelNavigation

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        navigationPanel.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le navigationPanel

        this.buttonAccueil = new JButton("ACCUEIL");
        this.buttonMissions = new JButton("MISSIONS");
        this.buttonCompetences = new JButton("COMPETENCES");
        this.buttonEmploye = new JButton("EMPLOYES");

        navigationPanel.add(buttonAccueil);
        navigationPanel.add(buttonMissions);
        navigationPanel.add(buttonCompetences);
        navigationPanel.add(buttonEmploye);

        this.cardLayout = new CardLayout(40, 40);
        panelCards = new JPanel(cardLayout);
        panelCards.setBackground(new Color(237, 227, 228)); // Couleur de fond pour le panelCards
        panelCards.setSize(new Dimension(1100, 600));

        panelNavigation.add(navigationPanel, BorderLayout.NORTH);
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