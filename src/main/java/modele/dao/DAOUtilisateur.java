package modele.dao;

import java.sql.*;
import modele.MdpUtils;
import modele.connexion.CictOracleDataSource;

public class DAOUtilisateur {

    private Connection connexion;

    /**
     * Constructeur avec injection de connexion à la base de données.
     */
    public DAOUtilisateur() {
        this.connexion = CictOracleDataSource.getConnectionBD();
    }

    /**
     * Vérifie si un utilisateur existe avec les identifiants fournis.
     * @param identifiant Identifiant saisi
     * @param motDePasse Mot de passe saisi
     * @return true si l'utilisateur est authentifié, false sinon
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
