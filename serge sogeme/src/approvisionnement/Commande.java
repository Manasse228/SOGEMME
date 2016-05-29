
package approvisionnement;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;

public class Commande {
    private int idCommande;
    private String dateCommande;
    private String dateproformat;
    private Fournisseur fournisseur;
    private Tva tva;
    private String typeCommande;

    public Commande() {
    }

    public Tva getTva() {
        return tva;
    }

    public String getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(String typeCommande) {
        this.typeCommande = typeCommande;
    }

    
    
    public void setTva(Tva tva) {
        this.tva = tva;
    }

    public Commande(int idCommande, String dateCommande, String dateproformat, Fournisseur fournisseur, Tva tva) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.dateproformat = dateproformat;
        this.fournisseur = fournisseur;
        this.tva = tva;
    }

    
    
    public Commande(String dateproformat) {
        this.dateproformat = dateproformat;
    }

    public Commande(int idCommande, String dateCommande, Fournisseur fournisseur) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.fournisseur = fournisseur;
    }

    public Commande(int idCommande, String dateCommande, String dateproformat) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.dateproformat = dateproformat;
    }

    public Commande(String dateCommande, String dateproformat) {
        this.dateCommande = dateCommande;
        this.dateproformat = dateproformat;
    }

    public String getDateproformat() {
        return dateproformat;
    }

    public void setDateproformat(String dateproformat) {
        this.dateproformat = dateproformat;
    }

    
    
    public Commande(String dateCommande, Fournisseur fournisseur) {
        this.dateCommande = dateCommande;
        this.fournisseur = fournisseur;
    }


    public Commande(int idCommande, String dateCommande) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
    }
    
    
    
    public String getDateCommande() {
        return dateCommande;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public int getIdCommande() {
        return idCommande;
    }


    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int rechercheID(){
        
        Statement saveCommande;
        try{
         
            saveCommande=SOGEMEE.c.getConn().createStatement();
            ResultSet r=saveCommande.executeQuery("select idCommande from commande where dateCommande ='"+dateCommande+"' and idFournisseur="+this.fournisseur.getIdFournisseur());
            r.next();
            return r.getInt(1);
        }  
        catch(Exception ex){
        return 0;
        }
        
        
    }
     public void saveCommande(){
        Statement saveCommande;
        try{
       
       
            saveCommande=SOGEMEE.c.getConn().createStatement();
            saveCommande.executeUpdate("insert into commande (dateCommande,dateProformat,idFournisseur,idTva,typeCommande,suppr) Values('"+dateCommande+"','1960-01-01',"+this.fournisseur.getIdFournisseur()+",0,'"+typeCommande+"',0)");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
     public void deleteCommande(){
        Statement deleteCommande;
        try{
            deleteCommande=SOGEMEE.c.getConn().createStatement();
            deleteCommande.executeUpdate("Update Commande set suppr=1 where idCommande ="+this.idCommande); 
            JOptionPane.showMessageDialog(null,"Suppression reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Suppression échoué");
        }
        }
     
     public void updateCommande(){
        Statement deleteCommande;
        try{
            deleteCommande=SOGEMEE.c.getConn().createStatement();
            deleteCommande.executeUpdate("Update commande set dateCommande ='"+dateCommande+"', dateProformat='"+dateproformat+"',idTva="+tva.getIdTva()+" where idCommande ="+this.idCommande); 
            JOptionPane.showMessageDialog(null,"Modification reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
}
