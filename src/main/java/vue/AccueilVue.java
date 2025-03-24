/*package vue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AccueilVue extends JPanel {
    private JLabel lblEnPreparation;
    private JLabel lblEnCours;
    private JLabel lblTerminees;
    private ChartPanel chartPanel;

    public AccueilVue() {
        setLayout(new BorderLayout());
        // Initialisation des cartes
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setOpaque(false);

        Color colorPrep = new Color(133, 167, 188);
        Color colorEnCours = new Color(255, 165, 0);
        Color colorTerminee = new Color(70, 130, 180);

        lblEnPreparation = new JLabel("A VENIR : 0 MISSIONS", SwingConstants.CENTER);
        lblEnCours = new JLabel("EN COURS : 0 MISSIONS", SwingConstants.CENTER);
        lblTerminees = new JLabel("TERMINÉES : 0 MISSIONS", SwingConstants.CENTER);

        cardPanel.add(lblEnPreparation);
        cardPanel.add(lblEnCours);
        cardPanel.add(lblTerminees);

        add(cardPanel, BorderLayout.NORTH);

        // Création initiale du graphique (bar chart)
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
     * Méthode pour mettre à jour les compteurs et le graphique.
     */
  /*  public void updateDashboard(int nbEnPreparation, int nbEnCours, int nbTerminees, Map<String, Integer> statsMois) {
        // Mettre à jour les 3 cartes
        lblEnPreparation.setText("A VENIR : " + nbEnPreparation + " MISSIONS");
        lblEnCours.setText("EN COURS : " + nbEnCours + " MISSIONS");
        lblTerminees.setText("TERMINÉES : " + nbTerminees + " MISSIONS");

        // Mettre à jour le dataset du bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : statsMois.entrySet()) {
            dataset.addValue(entry.getValue(), "Missions", entry.getKey());
        }
        JFreeChart updatedChart = ChartFactory.createBarChart(
                "Missions par mois",
                "Mois",
                "Nombre",
                dataset
        );
        chartPanel.setChart(updatedChart);

        repaint();
    }
}*/

package vue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AccueilVue extends JPanel {
    private JPanel cardPanel;
    private ChartPanel chartPanel;

    // On conserve des références aux cartes pour pouvoir les recréer lors de l'update
    private JPanel cardPrep;
    private JPanel cardEnCours;
    private JPanel cardTerminee;

    public AccueilVue() {
        setLayout(new BorderLayout());

        // Création du panel pour les cartes
        cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setOpaque(false);

        // Création initiale des cartes avec des valeurs par défaut
        cardPrep = createCard("A VENIR", "0 MISSIONS", new Color(133, 167, 188));
        cardEnCours = createCard("EN COURS", "0 MISSIONS", new Color(255, 165, 0));
        cardTerminee = createCard("TERMINÉES", "0 MISSIONS", new Color(70, 130, 180));

        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTerminee);

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

        return card;
    }

    /**
     * Met à jour les compteurs des cartes et le graphique en fonction des données.
     */
    public void updateDashboard(int nbEnPreparation, int nbEnCours, int nbTerminees, Map<String, Integer> statsMois) {
        // Recrée les cartes avec les nouvelles valeurs
        cardPanel.removeAll();
        cardPrep = createCard("A VENIR", nbEnPreparation + " MISSIONS", new Color(174, 172, 228));
        cardEnCours = createCard("EN COURS", nbEnCours + " MISSIONS", new Color(120, 164, 207));
        cardTerminee = createCard("TERMINÉES", nbTerminees + " MISSIONS", new Color(139, 173, 179));
        cardPanel.add(cardPrep);
        cardPanel.add(cardEnCours);
        cardPanel.add(cardTerminee);
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
        updatedChart.setBackgroundPaint(new Color(237, 227, 228)); // Fond global
        CategoryPlot plot = (CategoryPlot) updatedChart.getPlot();
        plot.setBackgroundPaint(new Color(237, 227, 228)); // Fond de la zone de tracé
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.getDomainAxis().setTickLabelPaint(Color.BLACK);
        plot.getDomainAxis().setLabelPaint(Color.BLACK);
        plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
        plot.getRangeAxis().setLabelPaint(Color.BLACK);
        if (updatedChart.getLegend() != null) {
            updatedChart.getLegend().setBackgroundPaint(new Color(237, 227, 228));
            updatedChart.getLegend().setItemPaint(Color.BLACK);
        }
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(164, 163, 193)); // Couleur des barres

        chartPanel.setChart(updatedChart);
        repaint();
    }
}
