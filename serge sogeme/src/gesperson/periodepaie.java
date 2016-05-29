
package gesperson;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.*;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
public class periodepaie {
  private int id_periodepaie;
  private String libelle_periodepaie;
  public periodepaie(){
  }

    public periodepaie(int id_periodepaie, String libelle_periodepaie) {
        this.id_periodepaie = id_periodepaie;
        this.libelle_periodepaie = libelle_periodepaie;
    }

    public periodepaie(int id_periodepaie) {
        this.id_periodepaie = id_periodepaie;
    }

    public periodepaie(String libelle_periodepaie) {
        this.libelle_periodepaie = libelle_periodepaie;
    }

    public int getId_periodepaie() {
        return id_periodepaie;
    }

    public String getLibelle_periodepaie() {
        return libelle_periodepaie;
    }

    public void setId_periodepaie(int id_periodepaie) {
        this.id_periodepaie = id_periodepaie;
    }

    public void setLibelle_periodepaie(String libelle_periodepaie) {
        this.libelle_periodepaie = libelle_periodepaie;
    }
   public void saveperiodepaie(){
Statement z;
try
{
    z=sogemee.SOGEMEE.c.getConn().createStatement();
    z.executeUpdate("insert into periodePaie ( libellePeriodePaie,suppr) values('"+libelle_periodepaie+"',0)");
    JOptionPane.showMessageDialog(null,"Ajout Reussi");
}
 catch(Exception ex)
 {
 JOptionPane.showMessageDialog(null, ex);
 }     
   } 
  public void deleteperiodepaie(){
        Statement z;
        try{
            z=sogemee.SOGEMEE.c.getConn().createStatement();
            z.executeUpdate("update periodePaie set suppr=1  where idPeriodepaie="+ this.id_periodepaie);
             JOptionPane.showMessageDialog(null,"Suppression Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec suppression");
        }
        }
          public void Updateperiodepaie(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update periodePaie set libellePeriodePaie='"+this.libelle_periodepaie+"' where id_periodepaie ="+ this.id_periodepaie);
             JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec modification");
        }
        }
          public boolean verifisaisie(){
              if (libelle_periodepaie.isEmpty())
                  return false;
              else return true;
          }
          public boolean verificationExistence() throws SQLException{
              Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
              ResultSet d;
              d=s.executeQuery("select libellePeriodePaie from periodePaie where libellePeriodePaie='"+libelle_periodepaie+"' and suppr=0");
              
              if (d.next()){
                  return true;
              } 
                  else 
                  return false;
              }
          
          
          
          public int rechercheID(){
              int id=0;
              try{
                  Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                  ResultSet r=s.executeQuery("Select idPeriodePaie from periodePaie where libellePeriodePaie='"+libelle_periodepaie+"'");
                  r.next();
                  id=r.getInt(1);
              }catch(Exception ex){
                  
              }
              return id;
          }
              
          }
     
  
        

         
      
      
  

