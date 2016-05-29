/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Sortie {
private int idSortie;
private String dateSortie;


public Sortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Sortie(int idSortie, String dateSortie) {
        this.idSortie = idSortie;
        this.dateSortie = dateSortie;
    }

    
    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public void setIdSortie(int idSortie) {
        this.idSortie = idSortie;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public int getIdSortie() {
        return idSortie;
    }

 public void saveSortie(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into sortie(dateSortie) values('"+dateSortie+"')");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

        
    
    public int rechercheID(){
        int id=0;
        
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idSortie from sortie where dateSortie='"+dateSortie+"'");
            if(r.next()){
                id=r.getInt(1);
            }
        }
        catch(Exception ex){
            
        }
        
        return id;
    }
}
