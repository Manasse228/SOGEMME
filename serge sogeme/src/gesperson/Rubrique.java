
package gesperson;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sogemee.*;



   
public class Rubrique {
 private int idRubrique;
 private String libelleRubrique;
 private int etat;   
  public Rubrique(){
    
}

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getEtat() {
        return etat;
    }

    public Rubrique(int idRubrique, String libelleRubrique, int etat) {
        this.idRubrique = idRubrique;
        this.libelleRubrique = libelleRubrique;
        this.etat = etat;
    }

    public Rubrique(String libelleRubrique, int etat) {
        this.libelleRubrique = libelleRubrique;
        this.etat = etat;
    }

    public Rubrique(int idRubrique, String libelleRubrique) {
        this.idRubrique = idRubrique;
        this.libelleRubrique = libelleRubrique;
    }
      public Rubrique(int idRubrique) {
        this.idRubrique = idRubrique;
    }
    public Rubrique(String libelleRubrique) {
        this.libelleRubrique = libelleRubrique;
    }
     public int getIdRubrique() {
        return idRubrique;
    }

    public String getLibelleRubrique() {
        return libelleRubrique;
    }
    
    public void setIdRubrique(int idRubrique) {
        this.idRubrique = idRubrique;
    }

    public void setLibelleRubrique(String libelleRubrique) {
        this.libelleRubrique = libelleRubrique;
    }

   


  
public void saveRubrique(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into rubrique(libelleRubrique,etat,suppr) Values('"+libelleRubrique+"',"+etat+",0)");
             JOptionPane.showMessageDialog(null,"Ajout Reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }

public void UpdateRubrique(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update rubrique set libellerubrique='"+this.libelleRubrique+"' where idRubrique ="+ this.idRubrique);
             JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec modification");
        }
}
public boolean Verifisaisie(){
    if (this.libelleRubrique.isEmpty())
        return false;
    else
        return true;
}




public void deleteRubrique() {
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                      
                      s.executeUpdate("update rubrique set suppr=1 where idRubrique="+this.idRubrique);
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
              
}

 public boolean verificationExistence() throws SQLException{
              Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
              ResultSet d;
              d=s.executeQuery("select libelleRubrique from rubrique where libelleRubrique='"+libelleRubrique.toUpperCase()+"' and suppr=0");
              
              if (d.next()){
                  return true;
              } 
                  else 
                  return false;
              }
public int rechercheId(){
int id=0;
try{
Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
ResultSet r=s.executeQuery("select idRubrique from rubrique where libelleRubrique='"+libelleRubrique+"'");
r.next();
id=r.getInt(1);
} 
catch(Exception ex){

}
return id;
}    
  
          }
     
   
    
    
    
