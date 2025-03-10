package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;

public class NavigationView extends JFrame {
    private JButton buttonMissions;
    private JButton buttonCompetences;
    private JPanel panelNavigation;
    private CardLayout cardLayout;
    private JPanel panelCards;

    public NavigationView() {
        setTitle("Barre de navigation");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.panelNavigation = new JPanel();
        this.panelNavigation.setLayout(new BorderLayout());

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 10));
        this.buttonMissions = new JButton("Missions");
        this.buttonCompetences = new JButton("Competences");
        navigationPanel.add(buttonMissions);
        navigationPanel.add(buttonCompetences);

        this.cardLayout = new CardLayout(40,40);
        panelCards = new JPanel( cardLayout);
        panelCards.setSize(new Dimension(1100,600));

        panelNavigation.add(navigationPanel, BorderLayout.NORTH);
        panelNavigation.add(panelCards, BorderLayout.CENTER);

        add(panelNavigation);

        setLocationRelativeTo(null);

    }

    public JButton getButtonMissions() {
        return buttonMissions;
    }

    public JButton getButtonCompetences() {
        return buttonCompetences;
    }

    public void addPage(String name, JPanel page) {
        panelCards.add(page, name);
    }

    // Méthode pour afficher une page en fonction de son nom
    public void showPage(String pageName) {
        cardLayout.show(panelCards, pageName);
    }
}