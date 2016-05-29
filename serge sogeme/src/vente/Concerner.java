/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import approvisionnement.Planche;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Concerner {
    
    private Planche planche;
    private FactureClient factureClient;
    private int qteFacture;
    private String typeTraitement;
    private String typeAction;

    public Concerner(Planche planche, FactureClient factureClient, int qteFacture, String typeTraitement, String typeAction) {
        this.planche = planche;
        this.factureClient = factureClient;
        this.qteFacture = qteFacture;
        this.typeTraitement = typeTraitement;
        this.typeAction = typeAction;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }
    

    public Concerner(Planche planche, FactureClient factureClient, int qteFacture, String typeTraitement) {
        this.planche = planche;
        this.factureClient = factureClient;
        this.qteFacture = qteFacture;
        this.typeTraitement = typeTraitement;
    }

    public FactureClient getFactureClient() {
        return factureClient;
    }

    public Planche getPlanche() {
        return planche;
    }

    public int getQteFacture() {
        return qteFacture;
    }

    public String getTypeTraitement() {
        return typeTraitement;
    }

    public void setFactureClient(FactureClient factureClient) {
        this.factureClient = factureClient;
    }

    public void setPlanche(Planche planche) {
        this.planche = planche;
    }

    public void setQteFacture(int qteFacture) {
        this.qteFacture = qteFacture;
    }

    public void setTypeTraitement(String typeTraitement) {
        this.typeTraitement = typeTraitement;
    }
    
     public void saveConcerner(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into concerner ( idPlanche,idFactureClient,qteFacture,typeTraitement,suppr,typeAction) values("+this.planche.getIdPlanche()+","+this.factureClient.getIdFactureClient()+","+this.qteFacture+",'"+this.typeTraitement+"',0,'"+this.typeAction+"')");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    
}
