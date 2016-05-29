/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import approvisionnement.Planche;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class BonSortie_Planche {
    private BonSortie bs;
    private Planche p;
    private int qteSortie;

    public BonSortie_Planche(BonSortie bs, Planche p, int qteSortie) {
        this.bs = bs;
        this.p = p;
        this.qteSortie = qteSortie;
    }

    public BonSortie getBs() {
        return bs;
    }

    public Planche getP() {
        return p;
    }

    public int getQteSortie() {
        return qteSortie;
    }

    public void setBs(BonSortie bs) {
        this.bs = bs;
    }

    public void setP(Planche p) {
        this.p = p;
    }

    public void setQteSortie(int qteSortie) {
        this.qteSortie = qteSortie;
    }
    
    public void save(){
         try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into bonSortie_planche(idBonSortie,idPlanche,qteSortie,suppr) values("+bs.getIdBonSortie()+","+p.getIdPlanche()+","+qteSortie+",0)");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
}
