package gesperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.*;

public class Atelier {
  private  int idAtelier;
  private String libelleAtelier;

public Atelier(){
}

public Atelier(int a,String b){
idAtelier=a;
libelleAtelier=b;
}

    public Atelier(String libelleAtelier) {
        this.libelleAtelier = libelleAtelier;
    }

 public void setidAtelier(int p){
        idAtelier=p;
 }
  public void setlibelleAtelier(String q){
        libelleAtelier=q;
 }

    public int getidAtelier(){
      return idAtelier;
   }
    public String getlibelleAtelier(){
      return libelleAtelier;
      
}
  public void saveAtelier(){
Statement z;
try
{
    z=sogemee.SOGEMEE.c.getConn().createStatement();
    z.executeUpdate("insert into serviceAtelier ( libelleServiceAtelier,suppr) values('"+libelleAtelier.toUpperCase()+"',0)");
    JOptionPane.showMessageDialog(null,"Ajout Reussi");
}
 catch(Exception ex)
 {
 JOptionPane.showMessageDialog(null, ex);
 }
}
  public void deleteAtelier(){
        Statement z;
        try{
            z=sogemee.SOGEMEE.c.getConn().createStatement();
            z.executeUpdate("update occuper o set suppr=1, datefinOccuper=(select DATE_FORMAT(SYSDATE(),'%y/%m/%d'))  where datefinOccuper is null and idServiceAtelier="+ this.idAtelier);
            z.executeUpdate("update serviceAtelier a set suppr=1  where idServiceAtelier="+ this.idAtelier);
             
            JOptionPane.showMessageDialog(null,"Suppression Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        }
  public void UpdateAtelier(){
        Statement s;
        try{
            s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update Atelier set libelleAtelier='"+this.libelleAtelier+"' where idAtelier ="+ this.idAtelier);
             JOptionPane.showMessageDialog(null,"Modification Reussie");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Echec modification");
        }
        }
  
public boolean verificationSaisie(){
      if(this.libelleAtelier.isEmpty()){
          return false;
      }
      else return true;
  }

    public boolean verificationExistence() throws SQLException{
      
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from serviceAtelier where libelleServiceAtelier='"+this.libelleAtelier+"' and suppr=0");
            if (r.next()){
                return true;
            }
            else return false;
    }
}
