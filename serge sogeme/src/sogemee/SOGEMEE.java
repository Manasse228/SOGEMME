/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sogemee;

import approvisionnement.*;
import fabrication.Fen_Fabrication;
import gesperson.*;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sogemee.User.Utilisateur;
import vente.*;



/**
 *
 * @author el-diablo
 */
public class SOGEMEE {
    /**
     * @param args the command line arguments
     */
    public static Connexion c;
    public static Utilisateur user;
    public static Agent agentDep;
    private static Fen_Login log;
    
    public static void main(String[] args) throws SQLException {
        try {
            // TODO code application logic here
         UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SOGEMEE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SOGEMEE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SOGEMEE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SOGEMEE.class.getName()).log(Level.SEVERE, null, ex);
        }
       c=new Connexion();
       log=new Fen_Login();
       log.setLocation(500,200);
       log.setVisible(true);
    }

    public static Fen_Login getLog() {
        return log;
    }

    
}
