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
public class Client {
    private int idClient;
    private String nomClient;
    private String prenomClient;
    private String telClient;

    public Client(int idClient, String nomClient, String prenomClient, String telClient) {
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.telClient = telClient;
    }

    public Client() {
    }

    public Client(String nomClient, String prenomClient, String telClient) {
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
        this.telClient = telClient;
    }

    public Client(int idClient, String nomClient, String prenomClient) {
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
    }

    public Client(int idClient, String nomClient) {
        this.idClient = idClient;
        this.nomClient = nomClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

       public void saveClient()
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
                env.executeUpdate("insert into client(nomClient,prenomClient,telClient,suppr) values ('"+this.nomClient.toUpperCase()+"','"+this.prenomClient+"','"+this.telClient+"',0)");
            JOptionPane.showMessageDialog(null,"Ajout opéré avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
     
      public void deleteClient()
    {
            
 try
      {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update set suppr=1 where idClient='"+this.idClient+"'");
            JOptionPane.showMessageDialog(null,"Suppression opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Echec de la suppression!");
        }
    
    }
  
         public void updateClient(String nomClient,String prenomClient,String telClient)
    {
        try
        {
            Statement env=sogemee.SOGEMEE.c.getConn().createStatement();
            env.executeUpdate("Update Client set nomClient ='"+nomClient+"',prenomClient='"+prenomClient+"',telClient='"+telClient+"' where idClient='"+this.idClient+"'");
            JOptionPane.showMessageDialog(null,"Mise à jour opérée avec succès!");
            
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,"Echec de la mise à jour!");
        }
    }
        
        public boolean verifSaisie(){
            
            if(nomClient.isEmpty()|| prenomClient.isEmpty())
            return false;
            else
                return true;
        }
        
        public boolean verifExistence() throws SQLException
        {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r;
            r=s.executeQuery("select * from client where nomClient='"+nomClient+"' and prenomClient= '"+prenomClient+"' and suppr=0");
            if(r.next())
            {
                return true;
            }
            else return false;
        }
     
        public int rechercheID(){
            int id=0;
            try{
                Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                ResultSet r=s.executeQuery("Select idClient from client where nomClient='"+nomClient+"' and prenomClient='"+prenomClient+"' and suppr=0");
                if(r.next()){
                    id=r.getInt(1);
                }
            }catch(Exception ex){
                
            }
            return id;
        }

   
}