/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import fabrication.Modele;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Inventaire_Meuble {
    private Inventaire inventaire;
    private Modele modele;
    private int qteInventaire;

    public Inventaire_Meuble(Inventaire inventaire, Modele modele, int qteInventaire) {
        this.inventaire = inventaire;
        this.modele = modele;
        this.qteInventaire = qteInventaire;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public Modele getModele() {
        return modele;
    }

    public int getQteInventaire() {
        return qteInventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public void setQteInventaire(int qteInventaire) {
        this.qteInventaire = qteInventaire;
    }
    
    public void saveInventaireModele(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into inventaire_modele values("+inventaire.getIdInventaire()+","+modele.getIdModele()+","+qteInventaire+")");
        }
        catch(Exception ex){
        JOptionPane.showMessageDialog(null,ex);    
        }
    }
}
