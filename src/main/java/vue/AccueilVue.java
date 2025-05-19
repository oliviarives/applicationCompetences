package vue;

import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import utilitaires.StyleManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

public class AccueilVue extends JPanel {
    // Contient les 3 cartes
    private final JPanel cardPanel;
    // Contient le graphique
    private final ChartPanel chartPanel;
    // Carte pour les missions en préparation
    private JPanel cardPrep;
    // Carte pour les missions en cours
    private JPanel cardEnCours;
    // Carte pour les missions terminées
    private JPanel cardTermine;
    // Constantes pour les missions
    final String ZERO_MISSIONS = "0 MISSIONS";
    final String MOT_MISSION = "MISSIONS";

    /**
     * Constructeur de la vue Accueil
     */
    public AccueilVue() {
        StyleManager.setupFlatLaf();
        setLayout(new BorderLayout());

        // Panel des cartes
        cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setOpaque(false);

        // Création des cartes avec des valeurs par défaut
        cardPrep = createCard("A VENIR", ZERO_MISSIONS, StyleManager.BLEU_VERT);
        cardEnCours = createCard("EN COURS", ZERO_MISSIONS, StyleManager.BLEU_CLAIR);
        cardTermine = createCard("TERMINÉES", ZERO_MISSIONS, StyleManager.BLEU_SITE);

        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTermine);
        add(cardPanel, BorderLayout.NORTH);

        // Création du graphique vide
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Résumé des derniers mois",
                "Mois",
                "Nombre",
                dataset
        );
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Crée une carte avec un titret une valeur
     *
     * @param title Le titre de la carte
     * @param value Le nombre de missions
     * @param bgColor La couleur du fond
     * @return JPanel représentant la carte
     */
    private JPanel createCard(String title, String value, Color bgColor) {
        JPanel card = getJPanel(bgColor);

        // Titre
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        // Valeur
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setForeground(Color.WHITE);
        lblValue.setFont(lblValue.getFont().deriveFont(Font.PLAIN, 16f));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        // Angles
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        return card;
    }
    /**
     * Crée un JPanel
     * @param bgColor Couleur du fond du JPanel
     * @return JPanel
     */
    @NotNull
    private JPanel getJPanel(Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, bgColor,
                        0, getHeight(), bgColor.darker()
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return card;
    }

    /**
     * Met à jour les cartes et le graphique
     *
     * @param nbEnPreparation Nombre de missions en préparation
     * @param nbEnCours Nombre de missions en cours
     * @param nbTermine Nombre de missions terminées
     * @param statsMois Statistiques mensuelles
     */
    public void updateDashboard(int nbEnPreparation, int nbEnCours, int nbTermine, Map<String, Integer> statsMois) {
        // Mise à jour des cartes
        cardPanel.removeAll();
        cardPrep = createCard("A VENIR", nbEnPreparation + " " + MOT_MISSION, StyleManager.BLEU_VERT);
        cardEnCours = createCard("EN COURS", nbEnCours + " " + MOT_MISSION, StyleManager.BLEU_CLAIR);
        cardTermine = createCard("TERMINÉES", nbTermine + " " + MOT_MISSION, StyleManager.BLEU_SITE);
        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTermine);
        cardPanel.revalidate();
        cardPanel.repaint();

        // Mise à jour du graphique
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String> ordreMois = List.of(
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        );
        statsMois.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> ordreMois.indexOf(e.getKey())))
                .forEach(entry -> dataset.addValue(entry.getValue(), "Missions", entry.getKey()));
        for (Map.Entry<String, Integer> entry : statsMois.entrySet()) {
            dataset.addValue(entry.getValue(), "Missions", entry.getKey());
        }

        JFreeChart updatedChart = ChartFactory.createBarChart(
                "Résumé des derniers mois",
                "Mois",
                "Nombre de missions",
                dataset
        );

        // Personnalisation de l'apparence du graphique
        updatedChart.setBackgroundPaint(StyleManager.BLANC);
        CategoryPlot plot = (CategoryPlot) updatedChart.getPlot();
        plot.setBackgroundPaint(StyleManager.BLANC);
        plot.setDomainGridlinePaint(StyleManager.BLEU_SITE);
        plot.setRangeGridlinePaint(StyleManager.BLEU_SITE);
        plot.getDomainAxis().setTickLabelPaint(StyleManager.BLEU_SITE);
        plot.getDomainAxis().setLabelPaint(StyleManager.BLEU_SITE);
        plot.getRangeAxis().setTickLabelPaint(StyleManager.BLEU_SITE);
        plot.getRangeAxis().setLabelPaint(StyleManager.BLEU_SITE);
        if (updatedChart.getLegend() != null) {
            updatedChart.getLegend().setBackgroundPaint(StyleManager.BLANC);
            updatedChart.getLegend().setItemPaint(StyleManager.BLEU_SITE);
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, StyleManager.BLEU_VERT);

        chartPanel.setChart(updatedChart);
        repaint();
    }
}
