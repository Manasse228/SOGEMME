
package approvisionnement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;


public class Fournisseur {
    private int idFournisseur;
    private String nomFournisseur;
    private String bpFournisseur;
    private String telFournisseur;
    private String villeFournisseur;
    private String rueFournisseur;
    public Fournisseur(){
        
    }
    
    public Fournisseur(int idf, String nmf, String adf, String tf){
        idFournisseur =idf;
        nomFournisseur=nmf.toUpperCase();
        bpFournisseur=adf;
        telFournisseur=tf;
    }
    
    public Fournisseur(String nm, String ad, String tel){
        nomFournisseur=nm.toUpperCase();
        bpFournisseur=ad;
        telFournisseur=tel;
    }

    public Fournisseur(int idFournisseur, String nomFournisseur) {
        this.idFournisseur = idFournisseur;
        this.nomFournisseur = nomFournisseur.toUpperCase();
    }
    
    
    public int getIdFournisseur(){
        return idFournisseur;
    }
    
    public String getNomFournisseur(){
        return nomFournisseur;
    }
    
    public String getBpFournisseur(){
        return bpFournisseur;
    }

    public Fournisseur(int idFournisseur, String nomFournisseur, String bpFournisseur, String telFournisseur, String villeFournisseur, String rueFournisseur) {
        this.idFournisseur = idFournisseur;
        this.nomFournisseur = nomFournisseur.toUpperCase();
        this.bpFournisseur = bpFournisseur;
        this.telFournisseur = telFournisseur;
        this.villeFournisseur = villeFournisseur;
        this.rueFournisseur = rueFournisseur;
    }

    public Fournisseur(String nomFournisseur, String bpFournisseur, String telFournisseur, String villeFournisseur, String rueFournisseur) {
        this.nomFournisseur = nomFournisseur.toUpperCase();
        this.bpFournisseur = bpFournisseur;
        this.telFournisseur = telFournisseur;
        this.villeFournisseur = villeFournisseur;
        this.rueFournisseur = rueFournisseur;
    }

    public String getRueFournisseur() {
        return rueFournisseur;
    }

    public String getVilleFournisseur() {
        return villeFournisseur;
    }

    public void setRueFournisseur(String rueFournisseur) {
        this.rueFournisseur = rueFournisseur;
    }

    public void setVilleFournisseur(String villeFournisseur) {
        this.villeFournisseur = villeFournisseur;
    }
    
    
    public String getTelFournisseur(){
        return telFournisseur;
    }
    
     public void setIdFournisseur(int id){
        idFournisseur= id;
    }
    
    public void setNomFournisseur(String nm){
        nomFournisseur=nm;
    }
    
    public void setBpFournisseur(String ad){
        bpFournisseur=ad;
    }
    
    public void setTelFournisseur(String tel){
        telFournisseur=tel;
    }
    
    public void saveFournisseur(){
        Statement savefournisseur;
        try{
            savefournisseur=SOGEMEE.c.getConn().createStatement();
            savefournisseur.executeUpdate("insert into fournisseur (nomFournisseur,bpFournisseur,telFournisseur,villeFournisseur,rueFournisseur,suppr) Values('"+nomFournisseur.toUpperCase()+"','"+bpFournisseur+"','"+telFournisseur+"','"+this.villeFournisseur+"','"+this.rueFournisseur+"',0)");
            JOptionPane.showMessageDialog(null,"Enregistrement du fournisseur reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
    
     public void deleteFournisseur(){
        Statement deletefournisseur;
        try{
            deletefournisseur=SOGEMEE.c.getConn().createStatement();
            deletefournisseur.executeUpdate("Update fournisseur set suppr=1 where idFournisseur= "+this.idFournisseur);
            JOptionPane.showMessageDialog(null,"Suppression reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Suppression échoué");
        }
        }
     
     public void updateFournisseur(){
        Statement deletefournisseur;
        try{
            deletefournisseur=SOGEMEE.c.getConn().createStatement();
            deletefournisseur.executeUpdate("Update fournisseur set nomFournisseur = '"+nomFournisseur+"', telFournisseur ='"+telFournisseur+"', villeFournisseur = '"+villeFournisseur+"',rueFournisseur='"+rueFournisseur+"',bpFournisseur='"+bpFournisseur+"' where idFournisseur= "+this.idFournisseur);
            JOptionPane.showMessageDialog(null,"Modification reussie");
        }  
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
     public boolean verifexistance() throws SQLException{
         Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
         ResultSet r;
        
         r= s.executeQuery("select * from fournisseur where nomFournisseur ='"+this.nomFournisseur+"' and suppr=0");
         
         if (r.next()){
             return true;   
         }
         else
             return false;
     }
     
}
