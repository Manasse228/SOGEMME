/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Evenement {
    private Agent agent;
    private TypeEvenement typeEvenement;
    private String dateDebEvenement;
    private String dateFinEvenement;
    private double dureEvenement;
    private String motif;

    public Evenement(Agent agent, TypeEvenement typeEvenement, String dateDebEvenement, String dateFinEvenement, double dureEvenement, String motif) {
        this.agent = agent;
        this.typeEvenement = typeEvenement;
        this.dateDebEvenement = dateDebEvenement;
        this.dateFinEvenement = dateFinEvenement;
        this.dureEvenement = dureEvenement;
        this.motif = motif;
    }

    public Evenement(Agent agent, TypeEvenement typeEvenement, String dateDebEvenement, String dateFinEvenement) {
        this.agent = agent;
        this.typeEvenement = typeEvenement;
        this.dateDebEvenement = dateDebEvenement;
        this.dateFinEvenement = dateFinEvenement;
    }

    public Agent getAgent() {
        return agent;
    }

    public String getDateDebEvenement() {
        return dateDebEvenement;
    }

    public String getDateFinEvenement() {
        return dateFinEvenement;
    }

    public double getDureEvenement() {
        return dureEvenement;
    }

    public String getMotif() {
        return motif;
    }

    public TypeEvenement getTypeEvenement() {
        return typeEvenement;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setDateDebEvenement(String dateDebEvenement) {
        this.dateDebEvenement = dateDebEvenement;
    }

    public void setDateFinEvenement(String dateFinEvenement) {
        this.dateFinEvenement = dateFinEvenement;
    }

    public void setDureEvenement(double dureEvenement) {
        this.dureEvenement = dureEvenement;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setTypeEvenement(TypeEvenement typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    public void saveEvenement(){
        Statement z;
            try
            {String datedeb=this.dateDebEvenement.substring(6, 10)+"-"+this.dateDebEvenement.substring(3,5)+"-"+this.dateDebEvenement.substring(0,2);
             String datefin=this.dateFinEvenement.substring(6,10)+"-"+this.dateFinEvenement.substring(3,5)+"-"+this.dateFinEvenement.substring(0,2);
               
             
             z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into evenement ( idAgent,idTypeEvenement,dateDebEvenement,dateFinEvenement,dureEvenement,motif,suppr) values("+this.agent.getIdAgent()+","+this.typeEvenement.getIdTypeEvenement()+",'"+datedeb+"','"+datefin+"',"+this.dureEvenement+",'"+this.motif+"',0)");
            //JOptionPane.showMessageDialog(null,this.typeEvenement.getLibelleTypeEvenement()+" ajouté avec succès");
           
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    
    public void updateEvenement(String datedeb,String datefin){
        Statement z;
            try
            {//String datedeb=this.dateDebEvenement.substring(6, 10)+"-"+this.dateDebEvenement.substring(3,5)+"-"+this.dateDebEvenement.substring(0,2);
             //String datefin=this.dateFinEvenement.substring(6,10)+"-"+this.dateFinEvenement.substring(3,5)+"-"+this.dateFinEvenement.substring(0,2);
               
             //JOptionPane.showMessageDialog(null, datedeb);
             z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("update  evenement set  datedebEvenement='"+datedeb+"',motif='"+motif+"',datefinEvenement='"+datefin+"'   where  idAgent ="+this.agent.getIdAgent()+" and idTypeEvenement="+this.typeEvenement.getIdTypeEvenement()+" and datedebEvenement='"+dateDebEvenement+"' and datefinEvenement='"+dateFinEvenement+"'");
           
           
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }
 
    }

    public Evenement(Agent agent, TypeEvenement typeEvenement, String dateDebEvenement, double dureEvenement, String motif) {
        this.agent = agent;
        this.typeEvenement = typeEvenement;
        this.dateDebEvenement = dateDebEvenement;
        this.dureEvenement = dureEvenement;
        this.motif = motif;
    }

    
    
    public void deleteEvenement(){
        Statement z;
            try
            {//String datedeb=this.dateDebEvenement.substring(6, 10)+"-"+this.dateDebEvenement.substring(3,5)+"-"+this.dateDebEvenement.substring(0,2);
             //String datefin=this.dateFinEvenement.substring(6,10)+"-"+this.dateFinEvenement.substring(3,5)+"-"+this.dateFinEvenement.substring(0,2);
               
             //JOptionPane.showMessageDialog(null, datedeb);
             z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("update  evenement set suppr=1 where idAgent="+this.agent.getIdAgent()+" and idTypeEvenement="+this.typeEvenement.getIdTypeEvenement()+" and datedebEvenement='"+this.dateDebEvenement+"'");
//            JOptionPane.showMessageDialog(null,"Modification réussie");
           
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }
 
    }
    
    
    
    
    
    
}
