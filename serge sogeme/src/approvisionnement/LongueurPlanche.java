
package approvisionnement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;


public class LongueurPlanche {
    private int idLongueurPlanche;
    private int valeurLongueur;

    public LongueurPlanche() {
    }

    public LongueurPlanche(int idLongueurPlanche, int libelleLongueur) {
        this.idLongueurPlanche = idLongueurPlanche;
        this.valeurLongueur = libelleLongueur;
    }

    public LongueurPlanche(int libelleLongueur) {
        this.valeurLongueur = libelleLongueur;
    }
    

    public int getIdLongueurPlanche() {
        return idLongueurPlanche;
    }

    public int getValeurLongueur() {
        return valeurLongueur;
    }

    public void setIdLongueurPlanche(int idLongueurPlanche) {
        this.idLongueurPlanche = idLongueurPlanche;
    }

    public void setValeurLongueur(int valeurLongueur) {
        this.valeurLongueur = valeurLongueur;
    }
    
   public void saveLongueur(){
        Statement saveLongueur;
        try{
            saveLongueur=SOGEMEE.c.getConn().createStatement();
            saveLongueur.executeUpdate("insert into longeurPlanche (valeurLongeur,suppr) Values('"+valeurLongueur+"',0)");
            JOptionPane.showMessageDialog(null, "Enregistrement reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        }
   
   public void deleteLongueur(){
        Statement deleteLongueur;
        try{
            deleteLongueur=SOGEMEE.c.getConn().createStatement();
            deleteLongueur.executeUpdate("Update LongueurPlanche set suppr=1 where idLongueur = "+this.idLongueurPlanche );
            JOptionPane.showMessageDialog(null, "Suppression reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de suppression");
        }
        }
   
    public void updateLongueur(int libelle){
        Statement deleteLongueur;
        try{
            deleteLongueur=SOGEMEE.c.getConn().createStatement();
            deleteLongueur.executeUpdate("Update longeurPlanche set libelle = "+this.idLongueurPlanche );
            JOptionPane.showMessageDialog(null, "Modification reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de Modification");
        }
        }
    
    public boolean verifSaisi(){
        if (this.valeurLongueur <=0){
         return false;   
        }
        else
        return true;
    }
    
    public boolean verifexit() throws SQLException{
        Statement s = sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet r;
        r =s.executeQuery("Select * from longeurPlanche where valeurLongeur='"+this.valeurLongueur+"' and suppr=0");
        
        if (r.next()){
            return true;
        }
        else
            return false;
    }
}
