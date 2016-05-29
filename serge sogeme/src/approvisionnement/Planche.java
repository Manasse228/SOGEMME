
package approvisionnement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;


public class Planche {
    
   private int idPlanche;
   private FormePlanche forme;
   private Type_Bois typebois;
   private LongueurPlanche longueur;
   private int coutTraitement;
   private int coutLissage;
   private double prixVentePlanche;
   
   public Planche() {
    }

    public double getPrixVentePlanche() {
        return prixVentePlanche;
    }

    public void setPrixVentePlanche(double prixVentePlanche) {
        this.prixVentePlanche = prixVentePlanche;
    }

    public Planche(FormePlanche forme, Type_Bois typebois, LongueurPlanche longueur, int coutTraitement, int coutLissage, float prixVentePlanche) {
        this.forme = forme;
        this.typebois = typebois;
        this.longueur = longueur;
        this.coutTraitement = coutTraitement;
        this.coutLissage = coutLissage;
        this.prixVentePlanche = prixVentePlanche;
    }

   
    public Planche(FormePlanche forme, Type_Bois typebois, LongueurPlanche longueur, int coutTraitement, int coutLissage) {
        this.forme = forme;
        this.typebois = typebois;
        this.longueur = longueur;
        this.coutTraitement = coutTraitement;
        this.coutLissage = coutLissage;
    }

    public Planche(int idPlanche, FormePlanche forme, Type_Bois typebois, LongueurPlanche longueur, int coutTraitement, int coutLissage) {
        this.idPlanche = idPlanche;
        this.forme = forme;
        this.typebois = typebois;
        this.longueur = longueur;
        this.coutTraitement = coutTraitement;
        this.coutLissage = coutLissage;
    }

    public int getCoutLissage() {
        return coutLissage;
    }

    public int getCoutTraitement() {
        return coutTraitement;
    }

    public void setCoutLissage(int coutLissage) {
        this.coutLissage = coutLissage;
    }

    public void setCoutTraitement(int coutTraitement) {
        this.coutTraitement = coutTraitement;
    }

    
    public Planche(int idPlanche, FormePlanche forme, Type_Bois typebois, LongueurPlanche longueur) {
        this.idPlanche = idPlanche;
        this.forme = forme;
        this.typebois = typebois;
        this.longueur = longueur;
    }

    public Planche(FormePlanche forme, Type_Bois typebois, LongueurPlanche longueur) {
        this.forme = forme;
        this.typebois = typebois;
        this.longueur = longueur;
    }

    public FormePlanche getForme() {
        return forme;
    }

    public int getIdPlanche() {
        return idPlanche;
    }

    public LongueurPlanche getLongueur() {
        return longueur;
    }

    public Type_Bois getTypebois() {
        return typebois;
    }

    public void setForme(FormePlanche forme) {
        this.forme = forme;
    }

    public void setIdPlanche(int idPlanche) {
        this.idPlanche = idPlanche;
    }

    public void setLongueur(LongueurPlanche longueur) {
        this.longueur = longueur;
    }

    public void setTypebois(Type_Bois typebois) {
        this.typebois = typebois;
    }
   
     public void savePlanche(){
        Statement savePlanche;
        try{
            savePlanche=SOGEMEE.c.getConn().createStatement();
            savePlanche.executeUpdate("insert into planche (idFormePlanche,idTypeBois,idLongeurPlanche,coutTraitement,coutLissage,prixVentePlanche,suppr) Values('"+forme.getIdFormePlanche()+"','"+typebois.getIdtypebois()+"','"+longueur.getIdLongueurPlanche()+"',"+coutTraitement+","+coutLissage+","+prixVentePlanche+",0)");
            JOptionPane.showMessageDialog(null, "Enregistrement reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
     public void deletePlanche(){
        Statement deletePlanche;
        try{
            deletePlanche=SOGEMEE.c.getConn().createStatement();
            deletePlanche.executeUpdate("Update planche set suppr=1 where idPlanche="+this.idPlanche);
            JOptionPane.showMessageDialog(null, "Suppression reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de suppression");
        }
        }
     
     public void updatePlanche(FormePlanche fr,Type_Bois typ,LongueurPlanche lon){
        Statement deletePlanche;
        try{
            deletePlanche=SOGEMEE.c.getConn().createStatement();
            deletePlanche.executeUpdate("Update planche set idFormePlanche="+fr.getIdFormePlanche()+", idLongeurPlanche="+lon.getIdLongueurPlanche()+", idTypeBois="+typ.getIdtypebois()+",coutTraitement="+coutTraitement+",coutLissage="+coutLissage+" where idPlanche="+this.idPlanche);
            JOptionPane.showMessageDialog(null, "Modification reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
     
     public boolean verifexistance() {
         boolean test=false;
        try {
            Statement s = sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r;
            
            r= s.executeQuery("select * from planche where idTypeBois ="+this.typebois.getIdtypebois()+" and idFormePlanche ="+this.forme.getIdFormePlanche()+" and idLongeurPlanche="+this.longueur.getIdLongueurPlanche()+" and suppr=0"
                    + "");
            
            if (r.next()){
                test= true;   
            }
            else
                test= false;
        } catch (SQLException ex) {
            
        }
        return test;
     }
}
