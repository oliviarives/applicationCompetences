package modele.connexion;

import oracle.jdbc.datasource.impl.OracleDataSource;
import utilitaires.Config;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * Classe utilitaire pour gérer la connexion à une base de données Oracle
 * Implémente un singleton pour établir une connexion unique
 */
public class CictOracleDataSource extends OracleDataSource {
    /**
     * Instance de la source de données
     */
    private static CictOracleDataSource cod;
    /**
     * Connexion à la base de données
     */
     private static Connection conn;
    /**
     * Adresse du serveur
     */
     private static String dbHost = Config.get("db.host");
    /**
     * Port utilisé pour la connexion
     */
     private static String dbPort = Config.get("db.port");
    /**
     * Nom de la base de données
     */
     private static String dbName = Config.get("db.name");
    /**
     * Mot de passe pour l'utilisateur de la base
     */
     private static String dbPwd = Config.get("db.password");
    /**
     * Identifiant de l'utilisateur
     */
     private static String dbUser = Config.get("db.user");

    /**
     * Constructeur privé pour initialiser la source avec les identifiants de l'utilisateur
     * @param login identifiant utilisateur
     * @param pwd mot de passe utilisateur
     * @throws SQLException si une erreur survient lors de la configuration
     */
     private CictOracleDataSource(String login, String pwd) throws SQLException{
         this.setURL("jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":" + dbName);
         this.setUser(login);
         this.setPassword(pwd);
     }
    /**
     * Crée l'accès à la base et initialise la connexion
     * @param login identifiant utilisateur
     * @param pwd mot de passe utilisateur
     * @throws SQLException si la connexion échoue
     */
    private static void creerAcces(String login, String pwd) throws SQLException {
             CictOracleDataSource.cod = new CictOracleDataSource(login, pwd);
             System.out.println("Connexion en cours");
             CictOracleDataSource.conn = CictOracleDataSource.cod.getConnection();
             System.out.println("Connexion établie");
     }
    /**
     * Retourne la connexion à la base de données
     * Initialise la connexion si elle est absente
     * @return connexion JDBC active
     */
    public static Connection getConnectionBD() {
         if (conn == null) {
             try {
                 creerAcces(dbUser, dbPwd);
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         return conn;
     }
}