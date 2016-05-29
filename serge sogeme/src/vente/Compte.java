/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Compte {
    private int idCompte;
    private String libelleCompte;

    public Compte(int idCompte, String libelleCompte) {
        this.idCompte = idCompte;
        this.libelleCompte = libelleCompte;
    }

    public Compte(String libelleCompte) {
        this.libelleCompte = libelleCompte;
    }

    public Compte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public String getLibelleCompte() {
        return libelleCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public void setLibelleCompte(String libelleCompte) {
        this.libelleCompte = libelleCompte;
    }
    
    public void saveCompte(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into compte(libelleCompte) values ('"+libelleCompte+"')");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
