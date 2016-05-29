/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import gesperson.Agent;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Aibor
 */
public class FactureClient {
    private int idFactureClient;
    private String dateFacture;
    private int dejaLivre;
    private Agent agent;
    private Client client;
    private double montantFacture;

    public FactureClient(String dateFacture, int dejaLivre, Agent agent, Client client, double montantFacture) {
        this.dateFacture = dateFacture;
        this.dejaLivre = dejaLivre;
        this.agent = agent;
        this.client = client;
        this.montantFacture = montantFacture;
    }

    public void setMontantFacture(double montantFacture) {
        this.montantFacture = montantFacture;
    }

    public double getMontantFacture() {
        return montantFacture;
    }

    public FactureClient(int idFactureClient, String dateFacture, int dejaLivre, Agent agent, Client client) {
        this.idFactureClient = idFactureClient;
        this.dateFacture = dateFacture;
        this.dejaLivre = dejaLivre;
        this.agent = agent;
        this.client = client;
    }

    public FactureClient(int idFactureClient, String dateFacture, int dejaLivre) {
        this.idFactureClient = idFactureClient;
        this.dateFacture = dateFacture;
        this.dejaLivre = dejaLivre;
    }

    public FactureClient(int idFactureClient, int dejaLivre) {
        this.idFactureClient = idFactureClient;
        this.dejaLivre = dejaLivre;
    }

    public FactureClient(int idFactureClient, Agent agent, Client client) {
        this.idFactureClient = idFactureClient;
        this.agent = agent;
        this.client = client;
    }

    public FactureClient(String dateFacture, int dejaLivre, Agent agent, Client client) {
        this.dateFacture = dateFacture;
        this.dejaLivre = dejaLivre;
        this.agent = agent;
        this.client = client;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public int getDejaLivre() {
        return dejaLivre;
    }

    public Agent getAgent() {
        return agent;
    }

    public Client getClient() {
        return client;
    }

    public int getIdFactureClient() {
        return idFactureClient;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public void setDejaLivre(int dejaLivre) {
        this.dejaLivre = dejaLivre;
    }

    public void setAgent(Agent idAgent) {
        this.agent = idAgent;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setIdFactureClient(int idFactureClient) {
        this.idFactureClient = idFactureClient;
    }

    

    public void saveFactureClient(){
Statement z;
try
{
    z=sogemee.SOGEMEE.c.getConn().createStatement();
    z.executeUpdate("insert into factureClient ( dateFacture,dejaLivrer,idAgent,idClient,suppr,montantFacture) values('"+dateFacture+"','"+dejaLivre+"','"+agent.getIdAgent()+"','"+client.getIdClient()+"',0,"+montantFacture+")");
    JOptionPane.showMessageDialog(null,"Ajout Reussi");
}
 catch(Exception ex)
 {
 JOptionPane.showMessageDialog(null, ex);
 }
}
    
    
    public int rechercheID(){
        Statement z;
        int id=0;
        try{
           z=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r= z.executeQuery("select idFactureClient from factureClient  where idClient="+ client.getIdClient()+" and idAgent="+agent.getIdAgent()+" and dateFacture='"+dateFacture+"' order by idFactureClient desc");
            r.next();
            id=r.getInt(1);
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        return id;
    }
  public void deleteFactureClient(){
        Statement z;
        try{
            z=sogemee.SOGEMEE.c.getConn().createStatement();
            z.executeUpdate("update factureClient set suppr=1  where idFactureClient="+ this.idFactureClient);
             
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
  public void UpdateFactureClient(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update factureClient set dateFacture ='"+this.dateFacture+"',dejaLivre='"+this.dejaLivre+"',idAgent='"+this.agent.getIdAgent()+"',idClient='"+this.client.getIdClient()+"' where idFactureClient ="+ this.idFactureClient);
             JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
  
   public void LivreFactureClient(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update factureClient set dejaLivrer=1 where idFactureClient ="+ this.idFactureClient);
             JOptionPane.showMessageDialog(null,"Livraison enregistrée");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    
}
