/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Reglement {
    private int idReglement;
    private double montantReglement;
    private FactureClient factureClient;
    private ModeReglement modeReglement;
    private String dateReglement;

    public String getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(String dateReglement) {
        this.dateReglement = dateReglement;
    }
    
    
    public Reglement(int idReglement, double montantReglement, FactureClient factureClient, ModeReglement modeReglement) {
        this.idReglement = idReglement;
        this.montantReglement = montantReglement;
        this.factureClient = factureClient;
        this.modeReglement = modeReglement;
    }

    public Reglement(double montantReglement, FactureClient factureClient, ModeReglement modeReglement) {
        this.montantReglement = montantReglement;
        this.factureClient = factureClient;
        this.modeReglement = modeReglement;
    }

    public FactureClient getFactureClient() {
        return factureClient;
    }

    public int getIdReglement() {
        return idReglement;
    }

    public ModeReglement getModeReglement() {
        return modeReglement;
    }

    public double getMontantReglement() {
        return montantReglement;
    }

    public void setFactureClient(FactureClient factureClient) {
        this.factureClient = factureClient;
    }

    public void setIdReglement(int idReglement) {
        this.idReglement = idReglement;
    }

    public void setModeReglement(ModeReglement modeReglement) {
        this.modeReglement = modeReglement;
    }

    public void setMontantReglement(double montantReglement) {
        this.montantReglement = montantReglement;
    }
    
     public void saveReglement(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into reglement ( montantReglement,idFactureClient,idModeReglement,dateReglement,suppr) values("+this.montantReglement+","+this.factureClient.getIdFactureClient()+","+this.modeReglement.getIdModeReglement()+",'"+dateReglement+"',0)");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    public void updateReglement(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("update reglement set montantReglement="+this.montantReglement+" where idReglement="+idReglement);
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    public void deleteReglement(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("update reglement set suppr=1 where idReglement="+idReglement);
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }    
}
