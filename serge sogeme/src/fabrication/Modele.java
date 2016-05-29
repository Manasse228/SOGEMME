/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibor
 */
public class Modele {
    private int idModele;
    private String libelleModele;
    private CategorieModele categorieModele;
    private double prixVente;

    public Modele() {
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public Modele(String libelleModele, CategorieModele categorieModele, double prixVente) {
        this.libelleModele = libelleModele;
        this.categorieModele = categorieModele;
        this.prixVente = prixVente;
    }
    
    

    public Modele(int idModele, String libelleModele, CategorieModele categorieModele) {
        this.idModele = idModele;
        this.libelleModele = libelleModele;
        this.categorieModele = categorieModele;
    }

    public Modele(String libelleModele, CategorieModele categorieModele) {
        this.libelleModele = libelleModele;
        this.categorieModele = categorieModele;
    }

    public Modele(CategorieModele categorieModele) {
        this.categorieModele = categorieModele;
    }

    public Modele(String libelleModele) {
        this.libelleModele = libelleModele;
    }

    public CategorieModele getCategorieModele() {
        return categorieModele;
    }

    public int getIdModele() {
        return idModele;
    }

    public String getLibelleModele() {
        return libelleModele;
    }

    public void setCategorieModele(CategorieModele categorieModele) {
        this.categorieModele = categorieModele;
    }

    public void setIdModele(int idModele) {
        this.idModele = idModele;
    }

    public void setLibelleModele(String libelleModele) {
        this.libelleModele = libelleModele;
    }

    public Modele(int idModele, String libelleModele) {
        this.idModele = idModele;
        this.libelleModele = libelleModele;
    }

public int  rechercheID(){
    int id=0;
    try{
        Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet r=s.executeQuery("select idModele from modele where suppr=0 and libelleModele='"+libelleModele+"'");
        if(r.next()){
        id=r.getInt(1);    
        }
        
    }
    catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
    }
    return id;
}
    
    
       public void savemodele(){
Statement z;
try
{
    z=sogemee.SOGEMEE.c.getConn().createStatement();
    z.executeUpdate("insert into modele (libelleModele,idCategorieModele,prixVente,suppr) values('"+this.libelleModele.toUpperCase()+"','"+this.categorieModele.getIdCategorieModele()+"',"+prixVente+",0)");
    JOptionPane.showMessageDialog(null,"Ajout Reussi");
}
 catch(Exception ex)
 {
 JOptionPane.showMessageDialog(null, "Ajout echoué");
 }
}
  public void deleteModele(){
        Statement z;
        try{
            z=sogemee.SOGEMEE.c.getConn().createStatement();
            z.executeUpdate("update modele set suppr=1  where idModele="+ this.idModele);
             JOptionPane.showMessageDialog(null,"Suppression Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec suppression");
        }
        }
  public void UpdateModele(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update modele set libelleModele ='"+this.libelleModele+"',idCategorieModele="+this.categorieModele.getIdCategorieModele()+",prixVente="+prixVente+" where idModele ="+ this.idModele);
             JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
    
    
}
