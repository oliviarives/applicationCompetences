package modele.connexion;

import java.sql.Connection;

import java.sql.SQLException;
 
import oracle.jdbc.datasource.impl.OracleDataSource;
 
public class CictOracleDataSource extends OracleDataSource {
 
     private static CictOracleDataSource cod; 
 
     private static Connection conn;
 
    private CictOracleDataSource(String login, String pwd) throws SQLException{
 
         this.setURL("jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:etupre");
 
         this.setUser(login);
 
         this.setPassword(pwd);
 
     }
 
    public static void creerAcces(String login, String pwd) throws SQLException {
 
             CictOracleDataSource.cod = new CictOracleDataSource(login, pwd);
 
             System.out.println("Connexion en cours");
 
             CictOracleDataSource.conn = CictOracleDataSource.cod.getConnection();
 
             System.out.println("Connexion établie");
 
     }
 
    public static Connection getConnectionBD() {
 
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