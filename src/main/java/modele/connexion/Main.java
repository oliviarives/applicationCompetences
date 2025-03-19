/*package modele.connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


import modele.connexion.CictOracleDataSource;
import utilitaires.Config;

public class Main {
    private static String dbUser = Config.get("db.user");
    private static String dbPwd = Config.get("db.password");



    public static void main(String[] args) {
        try {
            // Crée une connexion à la base de données
            CictOracleDataSource.creerAcces(dbUser, dbPwd);
 
            // Récupère la connexion pour vérifier si elle fonctionne
            Connection connection = CictOracleDataSource.getConnectionBD();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion réussie à la base de données !");
              
            }
            //Test de requete
           String query="SELECT * FROM PERSONNELS";
            try (Statement statement = connection.createStatement();
            		ResultSet resultSet = statement.executeQuery(query)) {
            	while (resultSet.next()) {
            		int id = resultSet.getInt("IDPERSONNEL");
            		String nom = resultSet.getString("NOMPERSONNEL");
            		String prenom = resultSet.getString("PRENOMPERSONNEL");
            		System.out.println("iD personnel : "+id+", NOM : "+nom+", Prenom : "+prenom);
            	}
            }catch (SQLException e) {
            	e.printStackTrace();
            }
 
        } catch (SQLException e) {
            // Gère les erreurs de connexion
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Ferme la connexion à la base de données
            CictOracleDataSource.closeConnection();
        }
    }
}*/