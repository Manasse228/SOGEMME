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
public class TypeEvenement {
    private int idTypeEvenement;
    private String libelleTypeEvenement;

    public TypeEvenement(int idTypeEvenement, String libelleTypeEvenement) {
        this.idTypeEvenement = idTypeEvenement;
        this.libelleTypeEvenement = libelleTypeEvenement;
    }

    public TypeEvenement(int idTypeEvenement) {
        this.idTypeEvenement = idTypeEvenement;
    }

    public TypeEvenement(String libelleTypeEvenement) {
        this.libelleTypeEvenement = libelleTypeEvenement;
    }

    public int getIdTypeEvenement() {
        return idTypeEvenement;
    }

    public void setIdTypeEvenement(int idTypeEvenement) {
        this.idTypeEvenement = idTypeEvenement;
    }

    public String getLibelleTypeEvenement() {
        return libelleTypeEvenement;
    }

    public void setLibelleTypeEvenement(String libelleTypeEvenement) {
        this.libelleTypeEvenement = libelleTypeEvenement;
    }
     public void saveTypeEvenement(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into typeEvenement(libelleTypeEvenement,suppr) values ('"+this.libelleTypeEvenement.toUpperCase()+"',0)");
        JOptionPane.showMessageDialog(null,"Type d'évenement  ajouté avec succès !");
        }   
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Ajout du type d'évenement échoué");
        }
    }
     
     public void deleteTypeEvenement(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update typeEvenement set suppr=1 where idTypeEvenement="+this.idTypeEvenement);
        JOptionPane.showMessageDialog(null,"Type d'évenement  supprimer avec succès !");
        }   
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Suppression du type d'évenement échoué");
        }  
     }
    public void updateTypeEvenement(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update typeEvenement set libelleTypeEvenement='"+this.libelleTypeEvenement+"' where idTypeEvenement="+this.idTypeEvenement);
        JOptionPane.showMessageDialog(null,"Type d'évenement  modifier avec succès !");
        }   
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Modification du type d'évenement échoué");
        }  
     }
    
    public int rechercheID(){
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idTypeEvenement from typeEvenement where libelleTypeEvenement='"+this.libelleTypeEvenement+"'");
           r.next();
           return r.getInt(1);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
           return -1;
        }
       
      
    }
    
    
    public boolean verificationSaisie(){
        if(this.libelleTypeEvenement.isEmpty()){
            return false;
        }
        else return true;
    }
    
    public boolean verificationExistence() throws SQLException{
      
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select libelleTypeEvenement from typeEvenement where libelleTypeEvenement='"+this.libelleTypeEvenement+"' and suppr=0");
            if (r.next()){
                return true;
            }
            else return false;
    }
    
}
