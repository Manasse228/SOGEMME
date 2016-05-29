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
public class Composer {
    
    private Atelier service;
    private Fonction fonction;

    public Composer(Atelier service, Fonction fonction) {
        this.service = service;
        this.fonction = fonction;
    }

    public Composer(Atelier service) {
        this.service = service;
    }

    public Composer(Fonction fonction) {
        this.fonction = fonction;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Atelier getService() {
        return service;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public void setService(Atelier service) {
        this.service = service;
    }
    
    public void saveComposer(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into composer ( idServiceAtelier,idFonction,suppr) values("+this.service.getidAtelier()+","+this.fonction.getIdFonction()+",0)");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
}
