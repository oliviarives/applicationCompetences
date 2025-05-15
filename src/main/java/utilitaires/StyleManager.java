package utilitaires;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class StyleManager {

    // Définir les nouvelles couleurs
    public static final Color BLEU_SITE = new Color(40,62,80);
    public static final Color BLEU_VERT = new Color(139, 173, 179);     // #768A96
    public static final Color BLEU_CLAIR = new Color(170, 199, 216);     // #AAC7D8
    public static final Color VERT = new Color(5, 128, 7);
    public static final Color BLANC = new Color(255, 255, 255);
    public static final Color GRIS = new Color(189, 189, 189);


    public static void setupFlatLaf() {
        FlatLightLaf.setup();
        applyGlobalStyles();
    }

    private static void applyGlobalStyles() {
        Font defaultFont = new Font("Arial", Font.PLAIN, 14);

        UIManager.put("Panel.background", BLANC); // couleur du fond des JPANEL

        // Styles boutons
        UIManager.put("Button.background", BLANC); // fond de base des boutons
        UIManager.put("Button.foreground", BLEU_SITE); // couleur du texte des boutons
        UIManager.put("Button.font", defaultFont.deriveFont(Font.BOLD, 14));
        UIManager.put("Button.arc", 25);

        // Styles pour les labels
        UIManager.put("Label.foreground", Color.black);
        UIManager.put("Label.font", defaultFont.deriveFont(Font.BOLD, 16));

        // Styles pour les tables
        UIManager.put("Table.background", BLANC); // couleur de fond des tableaux
        UIManager.put("Table.foreground", BLEU_SITE); // couleur du texte de toute l'appli
        UIManager.put("Table.font", defaultFont);
        UIManager.put("Table.gridColor", BLEU_SITE); // couleur des grilles des tableaux
        UIManager.put("Table.selectionBackground", GRIS); // couleur des lignes sélectionnées dans les tableaux
        UIManager.put("Table.selectionForeground", BLANC); // couleur du texte sélectionné dans les tableaux


        // Améliorer la bordure des cellules
        UIManager.put("Table.cellFocusColor", VERT); // Couleur de la bordure de focus // ne change rien
        UIManager.put("Table.showHorizontalLines", true); // Afficher les lignes horizontales
        UIManager.put("Table.showVerticalLines", true); // Afficher les lignes verticales
        UIManager.put("Table.intercellSpacing", new Dimension(1, 1)); // Espacement entre les cellules

        // Styles pour l'en-tête des tableaux (JTableHeader)
        UIManager.put("TableHeader.background", BLEU_VERT); // couleur entête des tableaux
        UIManager.put("TableHeader.foreground", BLANC); // Texte de l'en-tête en blanc
        UIManager.put("TableHeader.font", defaultFont.deriveFont(Font.BOLD, 14)); // Police en gras
        UIManager.put("TableHeader.cellBorder", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_VERT, 1), // couleur des lignes dans l'entête des tableaux
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacement intérieur
        ));

        // Styles pour le JSpinner
        UIManager.put("Spinner.background", BLANC); // couleur de fond des jspinner (champs de saisie)
        UIManager.put("Spinner.foreground", BLEU_SITE); // couleur du text des jsppinner
        UIManager.put("Spinner.font", defaultFont); // Police par défaut
        UIManager.put("Spinner.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BLEU_SITE, 1), // couleur de la bordure des jspinner
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Espacement intérieur
        ));
        UIManager.put("Spinner.arc", 10);

        // Styles pour les boutons du Spinner (flèches haut/bas)
        UIManager.put("Spinner.buttonBackground", BLEU_VERT); // couleur du fond des boutons
        UIManager.put("Spinner.buttonForeground", BLANC); // couleur du texte des boutons en blanc
        UIManager.put("Spinner.buttonBorder", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        UIManager.put("Spinner.buttonArrowIcon", BLANC); // couleur des flèches en blanc
        UIManager.put("Spinner.buttonHoverBackground", BLEU_CLAIR); // couleur du fond des boutons au survol
    }

    public static void autoResizeTable(JTable table, JScrollPane scrollPane) {
        int rowCount = table.getRowCount();
        int totalHeight = rowCount * table.getRowHeight();
        int headerHeight = table.getTableHeader().getPreferredSize().height;

        // Limite si jamais tu veux éviter que la table grandisse trop
        int maxHeight = 400; // Ajuste cette valeur selon ton besoin

        int finalHeight = Math.min(totalHeight + headerHeight, maxHeight);
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, finalHeight));
    }

}