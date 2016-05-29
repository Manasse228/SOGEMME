/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibor
 */
public class ModeReglement {
    private int idModeReglement;
    private String libelleModeReglement;

    public ModeReglement(int idModeReglement, String libelleModeReglement) {
        this.idModeReglement = idModeReglement;
        this.libelleModeReglement = libelleModeReglement;
    }

    public ModeReglement() {
    }

    public ModeReglement(String libelleModeReglement) {
        this.libelleModeReglement = libelleModeReglement;
    }

    public int getIdModeReglement() {
        return idModeReglement;
    }

    public String getLibelleModeReglement() {
        return libelleModeReglement;
    }
    
     public void saveModeReglement()
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("insert into ModeReglement(libelleModeReglement,suppr) values ('"+libelleModeReglement.toUpperCase()+"',0)");
            JOptionPane.showMessageDialog(null,"Ajout opéré avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Echec de l'ajout!");
        }
    }
     
      public void deleteModeReglement()
    {
            
 try
      {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update set suppr=1 where idModeReglement='"+this.idModeReglement+"'");
            JOptionPane.showMessageDialog(null,"Suppression opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Echec de la suppression!");
        }
    
    }
         public void updateModeReglement()
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update ModeReglement set libelleModeReglement ='"+this.libelleModeReglement+"' where idModeReglement='"+this.libelleModeReglement+"'");
            JOptionPane.showMessageDialog(null,"Mise à jour opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Echec de la mise à jour!");
        }
    }
         
         public boolean verifsaisi(){
             if (this.libelleModeReglement.isEmpty())
                 return false;
                 else
                 return true;
         }
         
         public boolean verifExistence() throws SQLException
        {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r;
            r=s.executeQuery("select * from ModeReglement where nomClient='"+libelleModeReglement+"' and suppr=0");
            if(r.next())
            {
                return true;
            }
            else return false;
        }

    
}
