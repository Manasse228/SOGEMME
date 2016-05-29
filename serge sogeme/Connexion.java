/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sogemee;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author el-diablo
 */
public class Connexion {
 
    private static Connection con=null;
    private static Statement stmt=null;
    private static ResultSet resultat=null;
    
    public Connexion(){
    try{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sogeme", "roldick", "roldick");
    }   
    catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
    }
    
    }
    
    public Connection getCon(){
        return con;
    }

    public static void setCon(Connection con) {
        Connexion.con = con;
    }

    public static void setResultat(ResultSet resultat) {
        Connexion.resultat = resultat;
    }

    public static void setStmt(Statement stmt) {
        Connexion.stmt = stmt;
    }
    
    
    public Statement getStmt(){
        return stmt;
    }
    
    public ResultSet getResultat(){
        return resultat;
    }
}
