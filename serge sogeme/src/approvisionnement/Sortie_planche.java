/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Sortie_planche {
    private Sortie sortie;
    private Planche planche;
    private int qteSortie;
    private String motif;

    public Sortie_planche(Sortie sortie, Planche planche, int qteSortie, String motif) {
        this.sortie = sortie;
        this.planche = planche;
        this.qteSortie = qteSortie;
        this.motif = motif;
    }

    public String getMotif() {
        return motif;
    }

    public Planche getPlanche() {
        return planche;
    }

    public int getQteSortie() {
        return qteSortie;
    }

    public Sortie getSortie() {
        return sortie;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setPlanche(Planche planche) {
        this.planche = planche;
    }

    public void setQteSortie(int qteSortie) {
        this.qteSortie = qteSortie;
    }

    public void setSortie(Sortie sortie) {
        this.sortie = sortie;
    }
    
    
    public void saveSortiePlanche(){
         try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into sortie_planche values("+sortie.getIdSortie()+","+planche.getIdPlanche()+","+qteSortie+",'"+motif+"')");
        }
        catch(Exception ex){
        JOptionPane.showMessageDialog(null,ex);    
        }
    }
}
