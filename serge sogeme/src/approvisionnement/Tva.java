/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;

/**
 *
 * @author AMEYIKPO
 */
public class Tva {
    private int idTva;
    private float valeurTva;

    public void setIdTva(int idTva) {
        this.idTva = idTva;
    }

    public void setValeurTva(float valeurTva) {
        this.valeurTva = valeurTva;
    }

    public int getIdTva() {
        return idTva;
    }

    public float getValeurTva() {
        return valeurTva;
    }

    public Tva(int idTva, float valeurTva) {
        this.idTva = idTva;
        this.valeurTva = valeurTva;
    }

    public Tva(int idTva) {
        this.idTva = idTva;
    }

    public Tva(float valeurTva) {
        this.valeurTva = valeurTva;
    }
    
    public int rechercheID(){
        int id=0;
        Statement saveTva;
        try{
            saveTva=SOGEMEE.c.getConn().createStatement();
            ResultSet r=saveTva.executeQuery("select idTva from tva where valeurTva="+valeurTva);
           r.next();
           id=r.getInt(1);
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
    
     public void saveTva(){
        Statement saveTva;
        try{
            saveTva=SOGEMEE.c.getConn().createStatement();
            saveTva.executeUpdate("insert into tva (valeurTva,suppr) Values("+valeurTva+",0)");
            JOptionPane.showMessageDialog(null,"Enregistrement du Tva reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
      public void deleteTva(){
        Statement deleteTva;
        try{
            deleteTva=SOGEMEE.c.getConn().createStatement();
            deleteTva.executeUpdate("Update tva set suppr=1 where idTva ="+this.idTva); 
            JOptionPane.showMessageDialog(null,"Enregistrement reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Enregistrement échoué");
        }
        }
      
       public void updateTva(float tv){
        Statement deleteTva;
        try{
            deleteTva=SOGEMEE.c.getConn().createStatement();
            deleteTva.executeUpdate("Update Tva set valeurTva ='"+tv+"' where idTva ="+this.idTva); 
            JOptionPane.showMessageDialog(null,"Modification reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Modification échoué");
        }
        }
}
