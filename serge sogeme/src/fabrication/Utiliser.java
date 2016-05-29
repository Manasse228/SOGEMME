/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import fabrication.Modele;
import approvisionnement.Planche;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Utiliser {
    Planche planche;
    Modele modele;
    int qte;

    public Utiliser(Planche planche, Modele modele, int qte) {
        this.planche = planche;
        this.modele = modele;
        this.qte = qte;
    }

    public Utiliser(Planche planche, Modele modele) {
        this.planche = planche;
        this.modele = modele;
    }

    public Modele getModele() {
        return modele;
    }

    public Planche getPlanche() {
        return planche;
    }

    public int getQte() {
        return qte;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public void setPlanche(Planche planche) {
        this.planche = planche;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
    
    public void saveUtiliser(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into utiliser values("+planche.getIdPlanche()+","+modele.getIdModele()+","+qte+",0)");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    
    /*public void updateUtiliser(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update  utiliser set "+planche.getIdPlanche()+","+modele.getIdModele()+","+qte+",0)");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }*/
}
