/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Occuper {
    
    private Agent magent;
    private Atelier mservice;
    private Fonction mfonction;
    private String datedebOccuper;
    private String datefinOccuper;

    public Occuper(Agent magent, Atelier mservice, Fonction mfonction, String datedebOccuper, String datefinOccuper) {
        this.magent = magent;
        this.mservice = mservice;
        this.mfonction = mfonction;
        this.datedebOccuper = datedebOccuper;
        this.datefinOccuper = datefinOccuper;
    }

    public Occuper(Agent magent, Atelier mservice, Fonction mfonction, String datedebOccuper) {
        this.magent = magent;
        this.mservice = mservice;
        this.mfonction = mfonction;
        this.datedebOccuper = datedebOccuper;
    }

    public String getDatedebOccuper() {
        return datedebOccuper;
    }

    public String getDatefinOccuper() {
        return datefinOccuper;
    }

    public Agent getMagent() {
        return magent;
    }

    public Fonction getMfonction() {
        return mfonction;
    }

    public Atelier getMservice() {
        return mservice;
    }

    public void setDatedebOccuper(String datedebOccuper) {
        this.datedebOccuper = datedebOccuper;
    }

    public void setDatefinOccuper(String datefinOccuper) {
        this.datefinOccuper = datefinOccuper;
    }

    public void setMagent(Agent magent) {
        this.magent = magent;
    }

    public void setMfonction(Fonction mfonction) {
        this.mfonction = mfonction;
    }

    public void setMservice(Atelier mservice) {
        this.mservice = mservice;
    }
    
     public void saveOccuper(){
        Statement z;
            try
            {
              
                //String datefin=this.dateFinEvenement.substring(6,10)+"-"+this.dateFinEvenement.substring(3,5)+"-"+this.dateFinEvenement.substring(0,2);
              
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into occuper ( idAgent,idServiceAtelier,idFonction,datedebOccuper,suppr) values("+this.magent.getIdAgent()+","+this.mservice.getidAtelier()+","+this.mfonction.getIdFonction()+",'"+this.datedebOccuper+"',0)");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
     
     public void mettrefinOccuper() {
        try {
            Statement z;
              String datefin=this.datefinOccuper.substring(6,10)+"-"+this.datefinOccuper.substring(3,5)+"-"+this.datefinOccuper.substring(0,2);
                      
                        z=sogemee.SOGEMEE.c.getConn().createStatement();
                        z.executeUpdate("update occuper set datefinOccuper ='"+datefin+"' where idAgent="+this.magent.getIdAgent()+" and datefinOccuper is null");
        } catch (SQLException ex) {
            Logger.getLogger(Occuper.class.getName()).log(Level.SEVERE, null, ex);
        }
            }

    public Occuper(Agent magent, String datedebOccuper, String datefinOccuper) {
        this.magent = magent;
        this.datedebOccuper = datedebOccuper;
        this.datefinOccuper = datefinOccuper;
    }
     
     
     public void updateOccuper(){
            Statement z;
            try
            {
                String datefin=this.datefinOccuper.substring(6,10)+"-"+this.datefinOccuper.substring(3,5)+"-"+this.datefinOccuper.substring(0,2);
              
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("update occuper set datefinOccuper ='"+datefin+"' where idAgent="+this.magent.getIdAgent()+" and idFonction="+this.mfonction.getIdFonction()+" and idServiceAtelier="+this.mservice.getidAtelier());
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }
     
     }
    
}
