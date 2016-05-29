/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import approvisionnement.FormePlanche;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Alouer {
    
    private FormePlanche forme;
    private Atelier aservice;
    private Trimestre trimestre;

    public Alouer(FormePlanche forme, Atelier aservice, Trimestre trimestre) {
        this.forme = forme;
        this.aservice = aservice;
        this.trimestre = trimestre;
    }

    public Atelier getAservice() {
        return aservice;
    }

    public FormePlanche getForme() {
        return forme;
    }

    public Trimestre getTrimestre() {
        return trimestre;
    }

    public void setAservice(Atelier aservice) {
        this.aservice = aservice;
    }

    public void setForme(FormePlanche forme) {
        this.forme = forme;
    }

    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }
    
     public void saveAlouer(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into alouer (idFormePlanche idServiceAtelier,idTrimestre,suppr) values("+this.forme.getIdFormePlanche()+","+this.aservice.getidAtelier()+","+this.trimestre.getIdTrimestre()+",0)");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    
    
    
}
