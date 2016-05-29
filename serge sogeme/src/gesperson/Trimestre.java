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
class Trimestre {
    private int idTrimestre;
    private String libelleTrimestre;
    private String anneeTrimestre;

    public Trimestre(int idTrimestre, String libelleTrimestre, String anneeTrimestre) {
        this.idTrimestre = idTrimestre;
        this.libelleTrimestre = libelleTrimestre;
        this.anneeTrimestre = anneeTrimestre;
    }

    public Trimestre(String libelleTrimestre, String anneeTrimestre) {
        this.libelleTrimestre = libelleTrimestre;
        this.anneeTrimestre = anneeTrimestre;
    }

    public String getAnneeTrimestre() {
        return anneeTrimestre;
    }

    public int getIdTrimestre() {
        return idTrimestre;
    }

    public String getLibelleTrimestre() {
        return libelleTrimestre;
    }

    public void setAnneeTrimestre(String anneeTrimestre) {
        this.anneeTrimestre = anneeTrimestre;
    }

    public void setIdTrimestre(int idTrimestre) {
        this.idTrimestre = idTrimestre;
    }

    public void setLibelleTrimestre(String libelleTrimestre) {
        this.libelleTrimestre = libelleTrimestre;
    }
    
     public void saveTrimestre(){
        Statement z;
            try
            {
                z=sogemee.SOGEMEE.c.getConn().createStatement();
                z.executeUpdate("insert into trimestre ( libelleTrimestre,anneeTrimestre,suppr) values('"+this.libelleTrimestre+"','"+this.anneeTrimestre+"',0)");
            }
            catch(Exception ex)
            {
            JOptionPane.showMessageDialog(null, ex);
            }

    
    }
    
}
