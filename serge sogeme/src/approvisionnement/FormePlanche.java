package approvisionnement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;


public class FormePlanche {
    private int idFormePlanche;
    private String libelleFormePlanche;

    public FormePlanche(int idFormePlanche, String LibeFormPlanche) {
        this.idFormePlanche = idFormePlanche;
        this.libelleFormePlanche = LibeFormPlanche;
    }

    public FormePlanche(int idFormePlanche) {
        this.idFormePlanche = idFormePlanche;
    }

    public FormePlanche(String LibeFormPlanche) {
        this.libelleFormePlanche = LibeFormPlanche;
    }

    public String getLibeFormPlanche() {
        return libelleFormePlanche;
    }

    public int getIdFormePlanche() {
        return idFormePlanche;
    }

    public void setLibeFormPlanche(String LibeFormPlanche) {
        this.libelleFormePlanche = LibeFormPlanche;
    }

    public void setIdFormePlanche(int idFormePlanche) {
        this.idFormePlanche = idFormePlanche;
    }
    
     public void saveForme(){
        Statement saveForme;
        try{
            saveForme=SOGEMEE.c.getConn().createStatement();
            saveForme.executeUpdate("insert into formePlanche (libelleFormePlanche,suppr) Values('"+this.libelleFormePlanche.toUpperCase()+"',0)");
            JOptionPane.showMessageDialog(null, "Enregistrement reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec d'enregistrement");
        }
        }
     
     public void deleteForme(){
        Statement deleteForme;
        try{
            deleteForme=SOGEMEE.c.getConn().createStatement();
            deleteForme.executeUpdate("Update formePlanche set suppr=1 where idFormePlanche = "+this.idFormePlanche );
            JOptionPane.showMessageDialog(null, "Suppression reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de suppression");
        }
        }
     
     public void updateForme(String form){
        Statement updateForme;
        try{
            updateForme=SOGEMEE.c.getConn().createStatement();
            updateForme.executeUpdate("Update formePlanche set libelleFormePlanche="+form+" where idFormePlanche = "+this.idFormePlanche );
            JOptionPane.showMessageDialog(null, "Modification reussi");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Echec de modification");
        }
        }
        
        public boolean verificationSaisi(){
            if (this.libelleFormePlanche.isEmpty())
                return false;
            else
                return true;
        }
        
        public boolean verifexist() throws SQLException{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r;
            
            r=s.executeQuery("Select libelleFormePlanche from formePlanche where libelleFormePlanche =  '"+libelleFormePlanche+"' and suppr=0");
            if (r.next()){
                return true;
            }
            else
                return false;
            
        }
        }

