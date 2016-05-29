/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import approvisionnement.Sortie;
import java.sql.Statement;
import javax.swing.JOptionPane;
import vente.FactureClient;

/**
 *
 * @author Aibor
 */
public class Meuble {
    private int idMeuble;
    private Modele modele;
    private FactureClient factureClient;

    public Meuble(int idMeuble, Modele modele, FactureClient factureClient) {
        this.idMeuble = idMeuble;
        this.modele = modele;
        this.factureClient = factureClient;
    }

    public Meuble() {
    }

    public Meuble(int meuble, Modele modele) {
        this.idMeuble = meuble;
        this.modele = modele;
    }

    public Meuble(int idMeuble, FactureClient factureClient) {
        this.idMeuble = idMeuble;
        this.factureClient = factureClient;
    }

    public Meuble(Modele modele, FactureClient factureClient) {
        this.modele = modele;
        this.factureClient = factureClient;
    }

    public FactureClient getFactureClient() {
        return factureClient;
    }

    public int getIdMeuble() {
        return idMeuble;
    }

    public Modele getModele() {
        return modele;
    }

    public void setFactureClient(FactureClient factureClient) {
        this.factureClient = factureClient;
    }

    public void setIdMeuble(int idMeuble) {
        this.idMeuble = idMeuble;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public Meuble(Modele modele) {
        this.modele = modele;
    }
    
    
    
       public void saveMeuble(){
Statement z;
try
{
    z=sogemee.SOGEMEE.c.getConn().createStatement();
    z.executeUpdate("insert into meuble (idModele,idFactureClient,suppr) values("+this.modele.getIdModele()+",0,0)");
    //JOptionPane.showMessageDialog(null,"Ajout Reussi");
}
 catch(Exception ex)
 {
 JOptionPane.showMessageDialog(null, ex);
 }
}
  public void deleteMeuble(){
        Statement z;
        try{
            z=sogemee.SOGEMEE.c.getConn().createStatement();
            z.executeUpdate("update Modele set suppr=1  where idMeuble="+ this.idMeuble);
             JOptionPane.showMessageDialog(null,"Suppression Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec suppression");
        }
        }
  
  public void UpdateMeuble(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update meuble set idModele ="+this.modele.getIdModele()+",idFactureClient="+this.factureClient.getIdFactureClient()+" where idMeuble ="+ this.idMeuble);
      //       JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec modification");
        }
        }
    
 public void VoleMeuble(Sortie sortie){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update meuble set idSortie ="+sortie.getIdSortie()+" where idMeuble ="+ this.idMeuble);
      //       JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
 

}
