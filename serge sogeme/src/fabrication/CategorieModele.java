/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibor
 */
public class CategorieModele {
    private int idCategorieModele;
    private String libelleCategorieModele;

    public CategorieModele(int idCategorieModele, String libelleCategorieModele) {
        this.idCategorieModele = idCategorieModele;
        this.libelleCategorieModele = libelleCategorieModele;
    }

    public CategorieModele(String libelleCategorieModele) {
        this.libelleCategorieModele = libelleCategorieModele;
    }

    public int getIdCategorieModele() {
        return idCategorieModele;
    }

    public String getLibelleCategorieModele() {
        return libelleCategorieModele;
    }

    public void setIdCategorieModele(int idCategorieModele) {
        this.idCategorieModele = idCategorieModele;
    }

    public void setLibelleCategorieModele(String libelleCategorieModele) {
        this.libelleCategorieModele = libelleCategorieModele;
    }
    
  
    public void saveCategorieModele()
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("insert into categorieModele(libelleCategorieModele,suppr) values ('"+libelleCategorieModele+"',0)");
            JOptionPane.showMessageDialog(null,"Ajout opéré avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public void deleteCategorieModele()
    {
            
 try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update categorieModele set suppr=1 where idCategorieModele="+this.idCategorieModele);
            JOptionPane.showMessageDialog(null,"Suppression opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    
    }
    public void updateCategorieModele()
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update categorieModele set libelleCategorieModele ='"+this.libelleCategorieModele+"' where idCategorieModele="+idCategorieModele);
            JOptionPane.showMessageDialog(null,"Modification opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public boolean verifSaisie(){
            
            if(libelleCategorieModele.isEmpty())
            return false;
            else
                return true;
        }
    
    public boolean verifExistence() throws SQLException
        {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r;
            r=s.executeQuery("select * from categorieModele where libelleCategorieModele='"+libelleCategorieModele+"' and suppr=0");
            if(r.next())
            {
                return true;
            }
            else return false;
        }
}
