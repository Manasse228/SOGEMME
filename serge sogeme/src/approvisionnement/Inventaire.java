/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Inventaire {
    private int idInventaire;
    private String dateInventaire;

    public Inventaire(int idInventaire, String dateInventaire) {
        this.idInventaire = idInventaire;
        this.dateInventaire = dateInventaire;
    }

    public Inventaire(String dateInventaire) {
        this.dateInventaire = dateInventaire;
    }

    public String getDateInventaire() {
        return dateInventaire;
    }

    public int getIdInventaire() {
        return idInventaire;
    }

    public void setDateInventaire(String dateInventaire) {
        this.dateInventaire = dateInventaire;
    }

    public void setIdInventaire(int idInventaire) {
        this.idInventaire = idInventaire;
    }
    
    public void saveInventaire(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into inventaire(dateInventaire,suppr) values('"+dateInventaire+"',0)");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

        
    public void deleteInventaire(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update inventaire set suppr=1 where idInventaire="+idInventaire);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public int rechercheID(){
        int id=0;
        
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idInventaire from inventaire where suppr=0 and dateInventaire='"+dateInventaire+"'");
            if(r.next()){
                id=r.getInt(1);
            }
        }
        catch(Exception ex){
            
        }
        
        return id;
    }
    
}
