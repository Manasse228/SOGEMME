/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author el-diablo
 */
public class Fonction {
 
    private int idFonction;
    private String libelleFonction;
    private int nbPersonne;
    public Fonction(){
        
    }

    public int getIdFonction() {
        return idFonction;
    }

    public String getLibelleFonction() {
        return libelleFonction;
    }

    public int getNbPersonne() {
        return nbPersonne;
    }

    public void setIdFonction(int idFonction) {
        this.idFonction = idFonction;
    }

    public void setLibelleFonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    public Fonction(int idFonction, String libelleFonction, int nbPersonne) {
        this.idFonction = idFonction;
        this.libelleFonction = libelleFonction;
        this.nbPersonne = nbPersonne;
    }

    public Fonction(String libelleFonction, int nbPersonne) {
        this.libelleFonction = libelleFonction;
        this.nbPersonne = nbPersonne;
    }
    
    public int recupererIdFonction() throws SQLException{
           Statement st=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=st.executeQuery("select idFonction from fonction where libelleFonction='"+libelleFonction+"'");
           r.next();
           return r.getInt(1);
    }

    public Fonction(int idFonction, String libelleFonction) {
        this.idFonction = idFonction;
        this.libelleFonction = libelleFonction;
    }

    public Fonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
    }
    
      
      public void saveFonction(){
        try {
            Statement st=sogemee.SOGEMEE.c.getConn().createStatement();
            st.executeUpdate("insert into fonction(libelleFonction,suppr,nbPersonne) values('"+libelleFonction.toUpperCase()+"',0,"+this.nbPersonne+")");
             JOptionPane.showMessageDialog(null, "Ajout de la fonction réussie");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
      }
    
            public void deleteFonction(){
        try {
            Statement st=sogemee.SOGEMEE.c.getConn().createStatement();
            st.executeUpdate("update fonction set suppr=1 where idFonction="+idFonction);
        
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
      }
            
        public void updateFonction(){
        try {
            Statement st=sogemee.SOGEMEE.c.getConn().createStatement();
            st.executeUpdate("update fonction set libelleFonction='"+libelleFonction+"',nbPersonne="+nbPersonne+" where idFonction="+idFonction+""                                                );
        JOptionPane.showMessageDialog(null, "Modification de la fonction réussie");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Modification de la fonction échouée");
        }
      }
        
        public boolean  verificationSaisie(){
            if(this.libelleFonction.isEmpty() || this.nbPersonne<0){
                return false;
            }
            else return true;
        }
        
         public boolean verificationExistence() throws SQLException{
      
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from fonction where libelleFonction='"+this.libelleFonction+"' and suppr=0");
            if (r.next()){
                return true;
            }
            else return false;
    }
        
        
}
