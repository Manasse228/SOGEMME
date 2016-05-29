/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import approvisionnement.Livraison;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class ReglementFournisseur {
    private int idReglementFour;
    private String dateReglement;
    private double montantReglementFour;
    private Livraison livraison;

    public ReglementFournisseur(int idReglementFour, String dateReglement, double montantReglementFour, Livraison livraison) {
        this.idReglementFour = idReglementFour;
        this.dateReglement = dateReglement;
        this.montantReglementFour = montantReglementFour;
        this.livraison = livraison;
    }

    public ReglementFournisseur(String dateReglement, double montantReglementFour, Livraison livraison) {
        this.dateReglement = dateReglement;
        this.montantReglementFour = montantReglementFour;
        this.livraison = livraison;
    }

    public String getDateReglement() {
        return dateReglement;
    }

    public int getIdReglementFour() {
        return idReglementFour;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public double getMontantReglementFour() {
        return montantReglementFour;
    }

    public void setDateReglement(String dateReglement) {
        this.dateReglement = dateReglement;
    }

    public void setIdReglementFour(int idReglementFour) {
        this.idReglementFour = idReglementFour;
    }

    public void setLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    public void setMontantReglementFour(double montantReglementFour) {
        this.montantReglementFour = montantReglementFour;
    }
    
    public void saveReglementFour(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into reglementFournisseur(dateReglementFournisseur,montantReglementFournisseur,idLivraison,suppr) values('"+dateReglement+"',"+montantReglementFour+","+livraison.getIdLivraison()+",0)");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void updateRegelementFournisseur(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update reglementFournisseur set dateReglementFournisseur='"+dateReglement+"',montantReglementFournisseur="+montantReglementFour+" where idReglementFournisseur="+idReglementFour);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
      public void deleteRegelementFournisseur(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update reglementFournisseur set suppr=1 where idReglementFournisseur="+idReglementFour);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
