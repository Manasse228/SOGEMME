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
public class Operation {
    private int idOperation;
    private String libelleOperation;
    private String dateOperation;
    private double montantOperation;
    private String sensOperation;
    private Compte compte;

    public Operation(int idOperation, String libelleOperation, String dateOperation, double montantOperation, String sensOperation, Compte compte) {
        this.idOperation = idOperation;
        this.libelleOperation = libelleOperation;
        this.dateOperation = dateOperation;
        this.montantOperation = montantOperation;
        this.sensOperation = sensOperation;
        this.compte = compte;
    }

    public Operation(String libelleOperation, String dateOperation, double montantOperation, String sensOperation, Compte compte) {
        this.libelleOperation = libelleOperation;
        this.dateOperation = dateOperation;
        this.montantOperation = montantOperation;
        this.sensOperation = sensOperation;
        this.compte = compte;
    }

    public Compte getCompte() {
        return compte;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public int getIdOperation() {
        return idOperation;
    }

    public String getLibelleOperation() {
        return libelleOperation;
    }

    public double getMontantOperation() {
        return montantOperation;
    }

    public String getSensOperation() {
        return sensOperation;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public void setIdOperation(int idOperation) {
        this.idOperation = idOperation;
    }

    public void setLibelleOperation(String libelleOperation) {
        this.libelleOperation = libelleOperation;
    }

    public void setMontantOperation(double montantOperation) {
        this.montantOperation = montantOperation;
    }

    public void setSensOperation(String sensOperation) {
        this.sensOperation = sensOperation;
    }
    
    public void saveOperation(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into operation(dateOperation,libelleOperation,montantOperation,sensOperation,idCompte) values('"+dateOperation+"','"+libelleOperation+"',"+montantOperation+",'"+sensOperation+"',"+compte.getIdCompte()+")");
        }
        catch(Exception ex){
          JOptionPane.showMessageDialog(null, ex);  
        }
    }
}
