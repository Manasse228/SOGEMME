/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author el-diablo
 */
public class Payer {
    private periodepaie periode;
    private Agent_Rubrique agentRubr;

    public Payer(periodepaie periode, Agent_Rubrique agentRubr) {
        this.periode = periode;
        this.agentRubr = agentRubr;
    }

    public void setAgentRubr(Agent_Rubrique agentRubr) {
        this.agentRubr = agentRubr;
    }

    public void setPeriode(periodepaie periode) {
        this.periode = periode;
    }

    public Agent_Rubrique getAgentRubr() {
        return agentRubr;
    }

    public periodepaie getPeriode() {
        return periode;
    }
    
    public void savePayer(){
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into payer values("+agentRubr.getAgent().getIdAgent()+","+agentRubr.getRubrique().getIdRubrique()+","+periode.getId_periodepaie()+","+agentRubr.getMontantRubrique()+",0)");
        } catch (SQLException ex) {
            Logger.getLogger(Payer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
