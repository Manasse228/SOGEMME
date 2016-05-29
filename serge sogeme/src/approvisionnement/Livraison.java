
package approvisionnement;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;


public class Livraison {
    private int idLivraison;
    private String dateLiivraison;
    private Commande commande;

    public Livraison() {
    }

    public Livraison(int idLivraison, String dateLiivraison, Commande commande) {
        this.idLivraison = idLivraison;
        this.dateLiivraison = dateLiivraison;
        this.commande = commande;
    }

    public Livraison(String dateLiivraison, Commande commande) {
        this.dateLiivraison = dateLiivraison;
        this.commande = commande;
    }

    public Commande getCommande() {
        return commande;
    }

    public String getDateLiivraison() {
        return dateLiivraison;
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public void setDateLiivraison(String dateLiivraison) {
        this.dateLiivraison = dateLiivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }
    
    public void saveLivraison(){
        Statement saveForme;
        try{
            saveForme=SOGEMEE.c.getConn().createStatement();
            saveForme.executeUpdate("insert into livraison (dateLivraison,idCommande,suppr) Values('"+this.dateLiivraison+"','"+this.commande.getIdCommande()+"',0)");
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
    public int rechercheID(){
        Statement saveForme;
        int id=0;
        try{
            saveForme=SOGEMEE.c.getConn().createStatement();
           ResultSet r= saveForme.executeQuery("select idLivraison from livraison where dateLivraison='"+dateLiivraison+"' and idCommande="+commande.getIdCommande());
           r.next();
           id=r.getInt(1);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
    
     public void deleteLivraison(){
        Statement deleteForme;
        try{
            deleteForme=SOGEMEE.c.getConn().createStatement();
            deleteForme.executeUpdate("Update livraison set suppr=1 where idLivraison = "+this.idLivraison );
            JOptionPane.showMessageDialog(null, "Suppression reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de suppression");
        }
        }
     
     public void updateLivraison(){
        Statement deleteForme;
        try{
            deleteForme=SOGEMEE.c.getConn().createStatement();
            deleteForme.executeUpdate("Update livraison set dateLivraison="+this.dateLiivraison+", idCommande="+this.commande.getIdCommande()+"  where idLivraison = "+this.idLivraison );
            JOptionPane.showMessageDialog(null, "Modification reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de modification");
        }
        }
}
