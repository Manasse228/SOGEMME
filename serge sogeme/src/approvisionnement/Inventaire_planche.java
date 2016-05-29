/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Inventaire_planche {
    private Inventaire inventaire;
    private Planche planche;
    private int qteInventaire;

    public Inventaire_planche(Inventaire inventaire, Planche planche, int qteInventaire) {
        this.inventaire = inventaire;
        this.planche = planche;
        this.qteInventaire = qteInventaire;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public Planche getPlanche() {
        return planche;
    }

    public int getQteInventaire() {
        return qteInventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public void setPlanche(Planche planche) {
        this.planche = planche;
    }

    public void setQteInventaire(int qteInventaire) {
        this.qteInventaire = qteInventaire;
    }
    
    public void saveInventairePlanche(){
         try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into inventaire_planche values("+inventaire.getIdInventaire()+","+planche.getIdPlanche()+","+qteInventaire+")");
        }
        catch(Exception ex){
        JOptionPane.showMessageDialog(null,ex);    
        }
    }
}
