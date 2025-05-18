package modele.dao;

import modele.MdpUtils;
import modele.connexion.CictOracleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * DAO pour la gestion de l'authentification des utilisateurs
 */
public class DAOUtilisateur {
    /**
     * Connexion à la base de données
     */
    private Connection connexion;
    /**
     * Initialise la connexion à la base
     */
    public DAOUtilisateur() {
        this.connexion = CictOracleDataSource.getConnectionBD();
    }
    /**
     * Vérifie si un utilisateur existe avec l'identifiant et le mot de passe fournis
     * @param identifiant identifiant de l'utilisateur
     * @param motDePasse mot de passe de l'utilisateur
     * @return true si les identifiants sont valides, false sinon
     */
    public boolean verifierUtilisateur(String identifiant, String motDePasse) {
        String query = "SELECT mdpEmp FROM Employe WHERE loginEmp = ?";

        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, identifiant);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String motDePasseHashe = rs.getString("mdpEmp");
                return MdpUtils.verifyPassword(motDePasse, motDePasseHashe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
