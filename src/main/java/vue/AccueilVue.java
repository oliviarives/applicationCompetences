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
import java.util.Map;

public class AccueilVue extends JPanel {
    private final JPanel cardPanel;
    private final ChartPanel chartPanel;

    // On conserve des références aux cartes pour pouvoir les recréer lors de l'update
    private JPanel cardPrep;
    private JPanel cardEnCours;
    private JPanel cardTermine;
    final String ZERO_MISSIONS = "0 MISSIONS";
    final String MOT_MISSION = "MISSIONS";

    public AccueilVue() {
        // Appliquer les styles globaux
        StyleManager.setupFlatLaf();

        setLayout(new BorderLayout());

        // Création du panel pour les cartes
        cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setOpaque(false);

        // Création initiale des cartes avec des valeurs par défaut
        cardPrep = createCard("A VENIR", ZERO_MISSIONS, StyleManager.BLEU_VERT);
        cardEnCours = createCard("EN COURS", ZERO_MISSIONS, StyleManager.BLEU_CLAIR);
        cardTermine = createCard("TERMINÉES", ZERO_MISSIONS, StyleManager.BLEU_SITE);

        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTermine);

        add(cardPanel, BorderLayout.NORTH);

        // Création initiale du graphique (bar chart) vide
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Missions par mois",
                "Mois",
                "Nombre",
                dataset
        );
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Crée une carte (tuile) personnalisée avec un dégradé vertical.
     */
    private JPanel createCard(String title, String value, Color bgColor) {
        JPanel card = getJPanel(bgColor);

        // Label du titre
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));

        // Label de la valeur
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setForeground(Color.WHITE);
        lblValue.setFont(lblValue.getFont().deriveFont(Font.PLAIN, 16f));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        // arrondir les angles
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        return card;
    }

    @NotNull
    private JPanel getJPanel(Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Dégradé vertical du bgColor vers bgColor.darker()
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
     * Met à jour les compteurs des cartes et le graphique en fonction des données.
     */
    public void updateDashboard(int nbEnPreparation, int nbEnCours, int nbTermine, Map<String, Integer> statsMois) {
        // Recrée les cartes avec les nouvelles valeurs
        cardPanel.removeAll();
        cardPrep = createCard("A VENIR", nbEnPreparation + MOT_MISSION, StyleManager.BLEU_VERT);
        cardEnCours = createCard("EN COURS", nbEnCours + MOT_MISSION, StyleManager.BLEU_CLAIR);
        cardTermine = createCard("TERMINÉES", nbTermine + MOT_MISSION, StyleManager.BLEU_SITE);
        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTermine);
        cardPanel.revalidate();
        cardPanel.repaint();

        // Met à jour le dataset du bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : statsMois.entrySet()) {
            dataset.addValue(entry.getValue(), "Missions", entry.getKey());
        }
        JFreeChart updatedChart = ChartFactory.createBarChart(
                "Nombre de missions par mois",
                "Mois",
                "Nombre de missions",
                dataset
        );

        // Personnalisation du chart
        updatedChart.setBackgroundPaint(StyleManager.BLANC); // Fond global
        CategoryPlot plot = (CategoryPlot) updatedChart.getPlot();
        plot.setBackgroundPaint(StyleManager.BLANC); // Fond de la zone de tracé
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
        renderer.setSeriesPaint(0, StyleManager.BLEU_VERT); // Couleur des barres

        chartPanel.setChart(updatedChart);
        repaint();
    }
}
