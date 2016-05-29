/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;

/**
 *
 * @author el-diablo
 */
public class Livraison_Planche {
    private Planche p;
    private Livraison livraison;
    private int qteLivraison;

    public Livraison_Planche(Planche p, Livraison livraison, int qteLivraison) {
        this.p = p;
        this.livraison = livraison;
        this.qteLivraison = qteLivraison;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public Planche getP() {
        return p;
    }

    public int getQteLivraison() {
        return qteLivraison;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public void setP(Planche p) {
        this.p = p;
    }

    public void setQteLivraison(int qteLivraison) {
        this.qteLivraison = qteLivraison;
    }
    
    public void saveLivraisonPlanche(){
         Statement saveForme;
        try{
            saveForme=SOGEMEE.c.getConn().createStatement();
            saveForme.executeUpdate("insert into livraison_planche Values("+p.getIdPlanche()+","+livraison.getIdLivraison()+","+qteLivraison+",0)");
          
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
