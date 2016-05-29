package approvisionnement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;

public class Type_Bois {
    private int idtypebois;
    private String libelleTypeBois;

    public int getIdtypebois() {
        return idtypebois;
    }

    public String getLibelleTypeBois() {
        return libelleTypeBois;
    }

    public Type_Bois(int idtypebois, String libelleTypeBois) {
        this.idtypebois = idtypebois;
        this.libelleTypeBois = libelleTypeBois.toUpperCase();
    }

    public Type_Bois(int idtypebois) {
        this.idtypebois = idtypebois;
    }

    public Type_Bois(String libelleTypeBois) {
        this.libelleTypeBois = libelleTypeBois.toUpperCase();
    }

    public void setIdtypebois(int idtypebois) {
        this.idtypebois = idtypebois;
    }

    public void setLibelleTypeBois(String libelleTypeBois) {
        this.libelleTypeBois = libelleTypeBois.toUpperCase();
    }
   


   
 public void saveType(){
        Statement saveType;
        try{
            saveType=SOGEMEE.c.getConn().createStatement();
            saveType.executeUpdate("insert into typeBois (libelleTypeBois,suppr) Values('"+this.libelleTypeBois.toUpperCase()+"',0)");
            JOptionPane.showMessageDialog(null, "Enregistrement reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec d'enregistrement");
        }
        }
    
    public void deleteType(){
        Statement deleteType;
        try{
            deleteType=SOGEMEE.c.getConn().createStatement();
            deleteType.executeUpdate("Update typeBois set suppr=1 where idtypebois= "+this.idtypebois );
            JOptionPane.showMessageDialog(null, "Suppression reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de suppression");
        }
        }
    
    public void updateType(String typ){
        Statement deleteType;
        try{
            deleteType=SOGEMEE.c.getConn().createStatement();
            deleteType.executeUpdate("Update typeBois set libelleTypeBois="+typ+" where idtypebois= "+this.idtypebois );
            JOptionPane.showMessageDialog(null, "Modification reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de modification");
        }
        }
    
    public boolean verifsaisi(){
        if (this.libelleTypeBois.isEmpty())
            return false;
        else
            return true;
    }
    
    public boolean verifexit() throws SQLException{
        Statement s =sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet r;
        r=s.executeQuery("select * from typeBois where libelleTypeBois='"+this.libelleTypeBois+"' and suppr=0");
        
        if (r.next())
            return true;
        else
            return false;
    }
}
