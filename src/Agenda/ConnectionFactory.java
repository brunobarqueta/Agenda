
package Agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:derby://localhost:1527/dbagenda";
    private static final String USER = "root";
    private static final String PASS = "";
    
    
    public static Connection getConnection(){
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/agenda", "agenda", "agenda");
            return conn;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro na conexao"+ex.toString());
        }
    }
    
    public static void closeConnection(Connection conn){
        if(conn != null) try {
            conn.close();
        } catch (SQLException ex) {
            System.err.println("Erro closeConnection");
        }
    }
    public static void closeConnection(Connection conn, PreparedStatement stmt){
        
        if(stmt != null) try {
            stmt.close();
        } catch (SQLException ex) {
            System.err.println("Erro statement");
        }
        
        closeConnection(conn);
    }
    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs){
        
        if(rs != null) try {
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Erro resultset");
        }
        
        closeConnection(conn, stmt);
    }
}
