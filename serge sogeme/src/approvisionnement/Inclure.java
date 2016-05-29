/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;
/**
 *
 * @author pc
 */
public class Inclure {
    
    private Planche planche;
    private Commande commande;
    private int quantitecommande;
    private int quantiterestante;
    private double prixunitaire;

    public Inclure(Planche planche, Commande commande, int quantitecommande, double prixunitaire) {
        this.planche = planche;
        this.commande = commande;
        this.quantitecommande = quantitecommande;
        this.prixunitaire = prixunitaire;
    }

    public void setQuantiterestante(int quantiterestante) {
        this.quantiterestante = quantiterestante;
    }

    public int getQuantiterestante() {
        return quantiterestante;
    }

    public Inclure(Planche planche, int quantitecommande) {
        this.planche = planche;
        this.quantitecommande = quantitecommande;
    }

    public Inclure(Planche planche, Commande commande, int quantitecommande) {
        this.planche = planche;
        this.commande = commande;
        this.quantitecommande = quantitecommande;
    }

    public Commande getCommande() {
        return commande;
    }


    public Planche getPlanche() {
        return planche;
    }

    public double getPrixunitaire() {
        return prixunitaire;
    }

    public int getQuantitecommande() {
        return quantitecommande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public void setPlanche(Planche planche) {
        this.planche = planche;
    }

    public void setPrixunitaire(double prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public void setQuantitecommande(int quantitecommande) {
        this.quantitecommande = quantitecommande;
    }
    
    public void saveInclure(){
        Statement s;
        try {
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into inclure (idPlanche,idCommande,qteCommande,pu,suppr) VALUES("+this.planche.getIdPlanche()+","+this.commande.getIdCommande()+","+this.quantitecommande+","+this.prixunitaire+",0)");
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex);
            }
       
    }
    
    public void updateInclure(Planche idplanche,Commande idcommande, int qte, double pu){
        Statement deleteCommande;
        try{
            deleteCommande=SOGEMEE.c.getConn().createStatement();
            deleteCommande.executeUpdate("Update inclure set  pu="+pu+" where idCommande ="+this.commande.getIdCommande()+" and idPlanche="+idplanche.getIdPlanche()); 
            JOptionPane.showMessageDialog(null,"Modification reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
}
