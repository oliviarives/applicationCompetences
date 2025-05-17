package vue;

import controleur.ConnexionControleur;
import modele.connexion.CictOracleDataSource;
import modele.dao.DAOUtilisateur;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnexionVue extends JDialog {
    private static final Logger logger = Logger.getLogger(ConnexionVue.class.getName());
    private JPanel page;
    private JTextField IdentifiantJTextField;
    private JPasswordField MdpJTextFieldPwd;
    private JLabel messageLabel;
    private JButton loginButton;
    @SuppressWarnings("unused")
    private JPanel gauche;
    @SuppressWarnings("unused")
    private JLabel MdpJLabel;
    @SuppressWarnings("unused")
    private JLabel IdentifiantJLabel;
    @SuppressWarnings("unused")
    private JLabel ConnexionJLabel;
    private JLabel loadingGifLabel;
    private JPanel gifPanel;




    public ConnexionVue() {
        super();
        setContentPane(page);



        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        new ConnexionControleur(this, new DAOUtilisateur());
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            Connection connection = CictOracleDataSource.getConnectionBD();
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Impossible d'obtenir une connexion valide à la base de données.");
            }

            SwingUtilities.invokeLater(() -> new ConnexionVue());

        } catch (Exception e) {
            logger.severe("Erreur lors de la connexion : " + e.getMessage());
        }
    }


    public String getIdentifiant() {
        return IdentifiantJTextField.getText().trim();
    }

    public String getMotDePasse() {
        return new String (MdpJTextFieldPwd.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setMessage(String message, Color color) {
        messageLabel.setText(message);
        if (color != null) {
            messageLabel.setForeground(color);
        }
    }

   /* public void afficherChargement(boolean visible) {
        if (loadingGifLabel != null) {
            loadingGifLabel.setVisible(visible);
            loadingGifLabel.revalidate();
            loadingGifLabel.repaint();
        }
    }*/

}



