/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import gesperson.Atelier;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class BonSortie {
    
    private int idBonSortie;
    private Atelier atelier;
    private String dateBonSortie;
    private String heure;
    

    public BonSortie(Atelier atelier, String dateBonSortie) {
        this.atelier = atelier;
        this.dateBonSortie = dateBonSortie;
    }

    
    
    public BonSortie(int idBonSortie, Atelier atelier, String dateBonSortie) {
        this.idBonSortie = idBonSortie;
        this.atelier = atelier;
        this.dateBonSortie = dateBonSortie;
    }

    public BonSortie(int idBonSortie, String dateBonSortie) {
        this.idBonSortie = idBonSortie;
        this.dateBonSortie = dateBonSortie;
    }

    public Atelier getAtelier() {
        return atelier;
    }

    public String getDateBonSortie() {
        return dateBonSortie;
    }

    public int getIdBonSortie() {
        return idBonSortie;
    }

    public void setAtelier(Atelier atelier) {
        this.atelier = atelier;
    }

    public void setDateBonSortie(String dateBonSortie) {
        this.dateBonSortie = dateBonSortie;
    }

    public void setIdBonSortie(int idBonSortie) {
        this.idBonSortie = idBonSortie;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
    
    
    public void saveBonSortie(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
       s.executeUpdate("insert into bonSortie(dateBonSortie,idServiceAtelier,heure,suppr) values('"+dateBonSortie+"',"+atelier.getidAtelier()+",(select EXTRACT( HOUR_SECOND from SYSDATE() )),0)");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    public int rechercheID(){
        int id=0;
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idBonSortie from bonSortie where dateBonSortie='"+dateBonSortie+"' and idServiceAtelier="+atelier.getidAtelier()+" order by idBonSortie desc limit 0,1");
            if(r.next()){
            id=r.getInt(1);    
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }
    
    public void deleteBon(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update bonSortie set suppr=1 where dateBonSortie='"+dateBonSortie+"' and idServiceAtelier="+atelier.getidAtelier()+" and heure='"+heure+"'");
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
