package modele.connexion;

import java.sql.Connection;

import java.sql.SQLException;
 
import oracle.jdbc.datasource.impl.OracleDataSource;
import utilitaires.Config;

public class CictOracleDataSource extends OracleDataSource {

     private static CictOracleDataSource cod;
     private static Connection conn;
     private static String dbHost = Config.get("db.host");
     private static String dbPort = Config.get("db.port");
     private static String dbName = Config.get("db.name");
     private static String dbPwd = Config.get("db.password");
     private static String dbUser = Config.get("db.user");

     private CictOracleDataSource(String login, String pwd) throws SQLException{
         this.setURL("jdbc:oracle:thin:@" + dbHost + ":" + dbPort + ":" + dbName);
         this.setUser(login);
         this.setPassword(pwd);
     }
 
    private static void creerAcces(String login, String pwd) throws SQLException {
             CictOracleDataSource.cod = new CictOracleDataSource(login, pwd);
             System.out.println("Connexion en cours");
             CictOracleDataSource.conn = CictOracleDataSource.cod.getConnection();
             System.out.println("Connexion établie");
     }
 
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
 
    public static void closeConnection() {
         try {
             CictOracleDataSource.conn.close();
             System.out.println("Connexion fermée");
         } catch (Exception e) {
             System.err.println(e.getMessage());
         }
         CictOracleDataSource.cod = null;
         CictOracleDataSource.conn = null;
     }
}