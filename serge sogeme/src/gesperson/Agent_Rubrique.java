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
public class Agent_Rubrique {
    private Agent agent;
    private Rubrique rubrique;
    private double montantRubrique;
    public Agent_Rubrique(Agent agent, Rubrique rubrique) {
        this.agent = agent;
        this.rubrique = rubrique;
    }

    public Agent_Rubrique(Agent agent, Rubrique rubrique, double montantRubrique) {
        this.agent = agent;
        this.rubrique = rubrique;
        this.montantRubrique = montantRubrique;
    }

    public double getMontantRubrique() {
        return montantRubrique;
    }

    public void setMontantRubrique(double montantRubrique) {
        this.montantRubrique = montantRubrique;
    }

     
    public Agent_Rubrique() {
    }

    public Agent getAgent() {
        return agent;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }
    public void saveAgentRubrique(){
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into agent_rubrique values("+agent.getIdAgent()+","+rubrique.getIdRubrique()+","+montantRubrique+",0)");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void updateAgentRubrique(){
try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update  agent_rubrique set montantRubrique="+montantRubrique+" where idAgent="+agent.getIdAgent()+" and idRubrique="+rubrique.getIdRubrique());
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
}
}
