/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sogemee.User;


import gesperson.Agent;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sogemee.SOGEMEE;

/**
 *
 * @author el-diablo
 */
public class Utilisateur {
 
    private int idUtilisateur;
    private String login;
    private String password;
    private Agent agent;
    private String droit;
    private Profil profil;

    public Utilisateur(int idUtilisateur, String login, String password, Agent agent) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.password = password;
        this.agent = agent;
    }

    public Utilisateur(int idUtilisateur, String login, String password, Agent agent, String droit, Profil profil) {
        this.idUtilisateur = idUtilisateur;
        this.login = login;
        this.password = password;
        this.agent = agent;
        this.droit = droit;
        this.profil = profil;
    }

    public String getDroit() {
        return droit;
    }

    public void setDroit(String droit) {
        this.droit = droit;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    
    public Utilisateur(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Agent getAgent() {
        return agent;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public void saveUser(){
        // JOptionPane.showMessageDialog(null,profil.getIdProfil());
          
        try{
           Statement saa=SOGEMEE.c.getConn().createStatement();
           saa.executeUpdate("insert into utilisateur(login,password,idAgent,suppr,idProfil) values('"+login+"','"+password+"','"+agent.getIdAgent()+"',0,'"+profil.getIdProfil()+"')");
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, "hahha");    
        }
        
    }
    public int verification(){
            int id=0;
            try{
                Statement s=SOGEMEE.c.getConn().createStatement();
                ResultSet r=s.executeQuery("select * from utilisateur where login='"+login+"' and password='"+password+"' and suppr=0");
                if(r.next()){
                    id=r.getInt(1);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            return id;
        }
    
    
    public void modifUser(){
        try{
           Statement s=SOGEMEE.c.getConn().createStatement();
           s.executeUpdate("update  utilisateur set login='"+login+"',password='"+password+"',idProfil="+profil.getIdProfil()+" where idUtilisateur="+idUtilisateur);
           JOptionPane.showMessageDialog(null, "Modification réussie");
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);    
        }
        
    }
    
    
    public void deleteUser(){
        try{
           Statement s=SOGEMEE.c.getConn().createStatement();
           s.executeUpdate("update  utilisateur set suppr=1 where idUtilisateur="+idUtilisateur);
           JOptionPane.showMessageDialog(null, "Suppression réussie");
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);    
        }
        
    }
}
