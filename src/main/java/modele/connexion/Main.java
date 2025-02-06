package modele.connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;

import modele.connexion.CictOracleDataSource;
 
public class Main {
    /*public static void main(String[] args) {
        // Remplacez par votre login et mot de passe
        String login = "BSC3991A";
        String password = "2002Aralc.31";
 
        try {
            // Crée une connexion à la base de données
            CictOracleDataSource.creerAcces(login, password);
 
            // Récupère la connexion pour vérifier si elle fonctionne
            Connection connection = CictOracleDataSource.getConnectionBD();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion réussie à la base de données !");
              
            }
            //Test de requete
            String query = "SELECT m.IDMIS, m.TITREMIS, m.NBEMPMIS, m.DATEDEBUTMIS, m.DATEFINMIS, " +
                    "m.DESCRIPTION, m.DATECREATION, m.LOGINEMP FROM Mission m";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                /*System.out.println(resultSet.next());
            	while (resultSet.next()) {
            		int id = resultSet.getInt("IDMIS");
            		String titre = resultSet.getString("TITREMIS");
            		String nbemp = resultSet.getString("NBEMPMIS");
            		System.out.println("iD Mission : "+id+", NOM : "+titre+", nb employé : "+nbemp);
            	}
                if (!resultSet.next()) {
                System.out.println("Aucune mission trouvée.");
            } else {
                // Parcours des résultats
                do {
                    // Récupère les données de la mission
                    int id = resultSet.getInt("idMis");
                    String titre = resultSet.getString("titreMis");
                    int nbEmp = resultSet.getInt("nbEmpMis");
                    java.sql.Date dateDebut = resultSet.getDate("dateDebutMis");
                    java.sql.Date dateFin = resultSet.getDate("dateFinMis");
                    String description = resultSet.getString("description");
                    java.sql.Date dateCreation = resultSet.getDate("dateCreation");
                    String loginEmp = resultSet.getString("loginEmp");


                    // Affiche les résultats dans la console
                    System.out.println("ID Mission: " + id);
                    System.out.println("Titre: " + titre);
                    System.out.println("Nombre d'Employés: " + nbEmp);
                    System.out.println("Date de Début: " + dateDebut);
                    System.out.println("Date de Fin: " + dateFin);
                    System.out.println("Description: " + description);
                    System.out.println("Date de Création: " + dateCreation);
                    System.out.println("Login Employé: " + loginEmp);

                    System.out.println("-------------------------------------------------");

                } while (resultSet.next());
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
    }*/
}