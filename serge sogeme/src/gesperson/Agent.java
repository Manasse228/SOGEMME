/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el-diablo
 */
public class Agent {
    private int idAgent;
    private String nomAgent;
    private String prenomAgent;
    private String sexe;
    private String dateEmbaucheAgent;
    private String dateNaissAgent;
    private String telAgent;
    private String bpAgent;
    private String villeAgent;
    private String rueAgent;
    private String qualificationAgent;

    public Agent(int idAgent, String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent, String telAgent, String bpAgent, String villeAgent, String rueAgent, String qualificationAgent) {
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
        this.telAgent = telAgent;
        this.bpAgent = bpAgent;
        this.villeAgent = villeAgent;
        this.rueAgent = rueAgent;
        this.qualificationAgent = qualificationAgent;
    }

    public Agent(String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent, String telAgent, String bpAgent, String villeAgent, String rueAgent, String qualificationAgent) {
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
        this.telAgent = telAgent;
        this.bpAgent = bpAgent;
        this.villeAgent = villeAgent;
        this.rueAgent = rueAgent;
        this.qualificationAgent = qualificationAgent;
    }
    
    

    public Agent(int idAgent, String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent, String bpAgent, String villeAgent, String qualificationAgent) {
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
        this.bpAgent = bpAgent;
        this.villeAgent = villeAgent;
        this.qualificationAgent = qualificationAgent;
    }

    public String getQualificationAgent() {
        return qualificationAgent;
    }

    public String getRueAgent() {
        return rueAgent;
    }

    public String getVilleAgent() {
        return villeAgent;
    }

    public void setQualificationAgent(String qualificationAgent) {
        this.qualificationAgent = qualificationAgent;
    }

    public void setRueAgent(String rueAgent) {
        this.rueAgent = rueAgent;
    }

    public void setVilleAgent(String villeAgent) {
        this.villeAgent = villeAgent;
    }
    
    
    public Agent(){
        
    }
   

    public Agent(int idAgent, String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent, String telAgent, String domicileAgent) {
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
        this.telAgent = telAgent;
        this.bpAgent = domicileAgent;
    }

    public Agent(String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent, String telAgent, String domicileAgent) {
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
        this.telAgent = telAgent;
        this.bpAgent = domicileAgent;
    }

    public Agent(int idAgent, String nomAgent, String prenomAgent) {
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
    }
    

    public void setDateEmbaucheAgent(String dateEmbaucheAgent) {
        this.dateEmbaucheAgent = dateEmbaucheAgent;
    }

    public void setDateNaissAgent(String dateNaissAgent) {
        this.dateNaissAgent = dateNaissAgent;
    }

    public void setBpAgent(String bpAgent) {
        this.bpAgent = bpAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public void setPrenomAgent(String prenomAgent) {
        this.prenomAgent = prenomAgent;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setTelAgent(String telAgent) {
        this.telAgent = telAgent;
    }

    public String getDateEmbaucheAgent() {
        return dateEmbaucheAgent;
    }

    public String getDateNaissAgent() {
        return dateNaissAgent;
    }

    public String getBpAgent() {
        return bpAgent;
    }

    public int getIdAgent() {
        return idAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public String getPrenomAgent() {
        return prenomAgent;
    }

    public String getSexe() {
        return sexe;
    }

    public String getTelAgent() {
        return telAgent;
    }
    
    
    
    public boolean verifactionSaisie(){
        if(this.nomAgent.isEmpty() || this.prenomAgent.isEmpty() || this.qualificationAgent.isEmpty() ){
            return false;
        }
        return true;
    }

    public Agent(int idAgent, String nomAgent, String prenomAgent, String sexe, String dateEmbaucheAgent, String dateNaissAgent) {
        this.idAgent = idAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
        this.sexe = sexe;
        this.dateEmbaucheAgent = dateEmbaucheAgent;
        this.dateNaissAgent = dateNaissAgent;
    }
  
    
    
    
    public void saveAgent(){
        try{
            String ddn=this.dateNaissAgent.substring(6,10)+"-"+this.dateNaissAgent.substring(3,5)+"-"+this.dateNaissAgent.substring(0,2);
            String dem=this.dateEmbaucheAgent.substring(6, 10)+"-"+this.dateEmbaucheAgent.substring(3, 5)+"-"+this.dateEmbaucheAgent.substring(0,2);
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("insert into agent(nomAgent,prenomAgent,sexe,dateEmbaucheAgent,dateNaissAgent,telAgent, bpAgent,villeAgent,rueAgent,qualificationAgent,suppr) values ('"+this.nomAgent.toUpperCase()+"','"+this.prenomAgent+"','"+this.sexe+"','"+dem+"','"+ddn+"','"+this.telAgent+"','"+this.bpAgent+"','"+this.villeAgent+"','"+this.rueAgent+"','"+this.qualificationAgent.toUpperCase()+"',0)");
        JOptionPane.showMessageDialog(null,"Agent ajouté avec succès !");
        }   
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public int rechercheIdAgent() throws SQLException{
        
            Statement s1=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r= s1.executeQuery("select idAgent from agent where nomAgent='"+this.nomAgent+"' and prenomAgent='"+this.prenomAgent+"' and suppr=0");
            r.next();
            return r.getInt(1);
       
    }
    
    public void deleteAgent(){
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            s.executeUpdate("update agent set suppr=1 where idAgent="+this.idAgent);
        JOptionPane.showMessageDialog(null,"Agent supprimer avec succès !");
        }   
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Suppression de l'agent échoué");
        }  
     }
    public void updateAgent(){
        try {
            Statement st=sogemee.SOGEMEE.c.getConn().createStatement();
            st.executeUpdate("update agent set nomAgent='"+this.nomAgent+"',prenomAgent='"+this.prenomAgent+"',sexe='"+this.sexe+"',dateEmbaucheAgent='"+this.dateEmbaucheAgent+"',dateNaissAgent='"+this.dateNaissAgent+"',telAgent='"+this.telAgent+"',bpAgent='"+this.bpAgent+"',villeAgent='"+this.villeAgent+"',rueAgent='"+this.rueAgent+"',qualificationAgent='"+this.qualificationAgent+"' where idAgent="+this.idAgent );
            JOptionPane.showMessageDialog(null, "Modification réussie");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
      }
   public boolean verificationExistence() throws SQLException{
      
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from agent where nomAgent='"+this.nomAgent+"' and prenomAgent='"+this.prenomAgent+"' and suppr=0");
            if (r.next()){
                return true;
            }
            else return false;
    }      
}
