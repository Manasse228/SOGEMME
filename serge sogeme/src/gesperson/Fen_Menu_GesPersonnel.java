/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gesperson;

import com.lowagie.text.Anchor;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author el-diablo
 */
public class Fen_Menu_GesPersonnel extends javax.swing.JFrame {
 
    
    private ArrayList evTypeEvenement=new ArrayList();
    private ArrayList evService=new ArrayList();//service a choisir dans le combo
    private ArrayList evFonction=new ArrayList();//fonction a choisir dans le combo
    private ArrayList evAgents=new ArrayList();//les evAgents
    private ArrayList evFonctions=new ArrayList();// * sur les evFonctions des evAgents
    private ArrayList evNomA=new ArrayList();//nom pour remplir la table
    private ArrayList evPrenomA=new ArrayList();//prenom pour remplir la table
    private ArrayList evFonctionA=new ArrayList();//libelle evFonction pour remplir la table
    private ArrayList evNomD=new ArrayList();//nom pour remplir la table de destination
    private ArrayList evPrenomD=new ArrayList();//prenom pour remplir la table de destination
    private ArrayList evFonctionD=new ArrayList();//libelle evFonction 
    private ArrayList evAgentselectionés=new ArrayList();//libelle evFonction 
    
    private ArrayList yfonctions=new ArrayList();
    private ArrayList yateliers=new ArrayList();
    
    private  ArrayList agents=new ArrayList();
    private  ArrayList  nom=new ArrayList();
    private  ArrayList prenom=new ArrayList();

    
    private  ArrayList datenaiss=new ArrayList();
    private  ArrayList dateembauche=new ArrayList();
    private  ArrayList nombreOccupant=new ArrayList();
    private  ArrayList libelleFonction=new ArrayList();
    private  ArrayList fonctions=new ArrayList();
    private  ArrayList libelleAtelier=new ArrayList();
    private  ArrayList ateliers=new ArrayList();
    private  ArrayList nombredAgent=new ArrayList();
    private  ArrayList evenements=new ArrayList();
    private  ArrayList nomAgentEvenement=new ArrayList();
    private  ArrayList prenomAgentEvenement=new ArrayList();
    private  ArrayList agentsEvenement=new ArrayList();
    private  ArrayList libelletypeEvenement=new ArrayList();
    private  ArrayList typeEvenement=new ArrayList();
    private  ArrayList dateEvenement=new ArrayList();
    private  ArrayList libelleTE=new ArrayList();
    private  ArrayList typeEvenements=new ArrayList();
    private  ArrayList rubriques=new ArrayList();
    private  ArrayList libelleRubrique=new ArrayList();
    private  ArrayList RPAgentRubrique=new ArrayList();
    private Agent RPagent=new Agent();
    private Rubrique RPrubr=new Rubrique(0);
    private ArrayList rubrSelec=new ArrayList();
    private ArrayList rubrSelecLib=new ArrayList();

        
    private ArrayList ASnomFonction=new ArrayList();
    private ArrayList ASnombreActuelDoccupant=new ArrayList();
    private ArrayList ASnomAgent=new ArrayList();
    private ArrayList ASprenomAgent=new ArrayList();
    private ArrayList ASdateOccupAgent=new ArrayList();
    private ArrayList ASatelierOccupAgent=new ArrayList();
    
    private ArrayList TEtypeEvenements=new ArrayList();
    private ArrayList TElibTypeEvenement=new ArrayList();
    
    
     private ArrayList listeDeDepart=new ArrayList();
       private ArrayList listef=new ArrayList();
       private Vector v=new Vector();
       private Object [] remp;
      
    /**
     * Creates new form Fen_Menu_GesPersonnel
     */
    public Fen_Menu_GesPersonnel() {
        RPagent.setIdAgent(0);
        initComponents();
        remplirAgent();
        remplirCombosEvenement();
        remplirRubrique();
        try {
            remplirCombos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
        remplirEvenement();
        if(this.atelierRadio.isSelected()){
        remplirAteliers();    
        }
        else{
        remplirFonction();    
        }
        
      
    }

    public JPanel getTabAgent() {
        return tabAgent;
    }

    public JTabbedPane getTabAgents() {
        return tabAgents;
    }

    public JPanel getTabAtelier() {
        return tabAtelier;
    }

    public JPanel getTabEvenement() {
        return tabEvenement;
    }

    public JPanel getTabPaye() {
        return tabPaye;
    }

    public JTable getTableAS() {
        return tableAS;
    }
    
    
    @SuppressWarnings("unchecked")
    private void remplirCombos() throws SQLException{
        Statement su=sogemee.SOGEMEE.c.getConn().createStatement();
        Statement sa=sogemee.SOGEMEE.c.getConn().createStatement();
        Statement sz=sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet rfonction =su.executeQuery("select idFonction, libelleFonction,nbPersonne from fonction where suppr=0");
     ResultSet ratelier =sa.executeQuery("select idServiceAtelier, libelleServiceAtelier from serviceAtelier where suppr=0");
          while (rfonction.next()){     
         Fonction f=new Fonction(rfonction.getInt(1),rfonction.getString(2),rfonction.getInt(3));
       yfonctions.add(f);
     }  
     
    while(ratelier.next()){
         Atelier form=new Atelier(ratelier.getInt(1),ratelier.getString(2));
         yateliers.add(form);
     }
    //Ajout des truks ds le combo 13:25
    for(int i=0;i<yateliers.size();i++){
        Atelier a=(Atelier) yateliers.get(i);
        this.service.addItem(a.getlibelleAtelier());
    }
    
    
    }
    
    @SuppressWarnings("unchecked")
    public void remplirAgentRubrique(){
        try {
            RPAgentRubrique.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from agent_rubrique where suppr=0");
            while(r.next()){
                Rubrique rb=new Rubrique(r.getInt(2));
                Agent ag=new Agent();
                ag.setIdAgent(r.getInt(1));
                Agent_Rubrique ar=new Agent_Rubrique(ag, rb, r.getDouble(3));
                RPAgentRubrique.add(ar);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    public void remplirTableFonctionsDansAtelier(){
        int ind=tableFonction.getSelectedRow();
        ASnomFonction.clear();
        ASnombreActuelDoccupant.clear();
        try{
            Atelier atelier=(Atelier) ateliers.get(ind);
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.libelleFonction,count(o.idAgent) from composer c,fonction f,occuper o where c.idFonction=f.idFonction and o.idFonction=f.idFonction and o.datefinOccuper is null and c.idServiceAtelier="+atelier.getidAtelier()+" and o.idServiceAtelier="+atelier.getidAtelier());
            while(r.next()){
                ASnomFonction.add(r.getString(1));
                ASnombreActuelDoccupant.add(r.getInt(2));
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Libelle de Fonction",ASnomFonction.toArray());
        mod.addColumn("Nombre d'occupant",ASnombreActuelDoccupant.toArray());
        tableAS.setModel(mod);
    }
    
    public void remplirTableAgentsDansFonction(){
        int ind=tableFonction.getSelectedRow();
        ASnomAgent.clear();
        ASprenomAgent.clear();
        ASdateOccupAgent.clear();
        ASatelierOccupAgent.clear();
        try{
            Fonction f=(Fonction) fonctions.get(ind);
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select a.nomAgent,a.prenomAgent,o.datedebOccuper,s.libelleServiceAtelier from agent a,occuper o,serviceAtelier s where  o.idAgent=a.idAgent and s.idServiceAtelier=o.idServiceAtelier and o.datefinOccuper is null and a.suppr=0 and o.idFonction="+f.getIdFonction());
            while(r.next()){
                ASnomAgent.add(r.getString(1));
                ASprenomAgent.add(r.getString(2));
                ASdateOccupAgent.add(r.getString(3));
                ASatelierOccupAgent.add(r.getString(4));
            }   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Nom Agent",ASnomAgent.toArray());
        mod.addColumn("Prenom Agent",ASprenomAgent.toArray());
        mod.addColumn("Date d'occupation", ASdateOccupAgent.toArray());
        mod.addColumn("Atelier/Service", ASatelierOccupAgent.toArray());
        tableAS.setModel(mod);
    }    
    
    private void remplirAteliers(){
        try {
            ateliers.clear();
            libelleAtelier.clear();
            nombredAgent.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  s.idServiceAtelier ,  s.libelleServiceAtelier,count(idFonction) from serviceAtelier s,composer c where s.idServiceAtelier=c.idServiceAtelier and s.suppr=0 and c.suppr=0 group by idServiceAtelier union (select s.idServiceAtelier ,  s.libelleServiceAtelier,suppr from serviceAtelier s where idServiceAtelier not in(select idServiceAtelier from composer where suppr=0) and suppr=0) ");
            
            while(r.next()){
               
                this.libelleAtelier.add(r.getString(2));
                this.nombredAgent.add(r.getInt(3));
                Atelier a=new Atelier(r.getInt(1),r.getString(2));
                this.ateliers.add(a);
                
                
            }
             
            model.addColumn("Libelle de le service/Atelier ",libelleAtelier.toArray());
            model.addColumn("Nombre de fonctions dans le service",nombredAgent.toArray());
            this.tableFonction.setModel(model);
           
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }
    
    public void remplirTousAgents(){
        try {
            agents.clear();
            nom.clear();
            prenom.clear();
            this.dateembauche.clear();
            this.datenaiss.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  idAgent ,  nomAgent ,  prenomAgent ,  sexe ,  dateEmbaucheAgent ,  dateNaissAgent ,  telAgent ,  bpAgent , villeAgent , rueAgent , qualificationAgent from agent where suppr=0");
            
            while(r.next()){
                this.nom.add(r.getString(2));
                this.prenom.add(r.getString(3));
                this.dateembauche.add(r.getDate(5).toString());
                this.datenaiss.add(r.getDate(6).toString());
                Agent a=new Agent(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getDate(5).toString(),r.getDate(6).toString(),r.getString(7),r.getString(8),r.getString(9),r.getString(10),r.getString(11));
                this.agents.add(a);
                
            }
            model.addColumn("Nom de l'agent",nom.toArray());
            model.addColumn("Prenoms de l'Agent",prenom.toArray());
            model.addColumn("Date de naissance ", this.datenaiss.toArray());
            model.addColumn("Date d'embauche",this.dateembauche.toArray());
            this.tableAgents.setModel(model);
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }
    
     public void remplirListeDeDepart(){
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("Select * from serviceAtelier ");
            while(r.next()){
                Atelier a=new Atelier(r.getInt(1),r.getString(2));
                listeDeDepart.add(a);
            }
            remp=new Object[listeDeDepart.size()];
           for(int i=0;i<listeDeDepart.size();i++){
               Atelier a1=(Atelier) listeDeDepart.get(i);
                remp[i]=a1.getlibelleAtelier();
           }
           this.listdepart.setListData(remp); 
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    public  void remplirAgent(){
        try {
            agents.clear();
            nom.clear();
            prenom.clear();
            dateembauche.clear();
            datenaiss.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  a.idAgent ,  nomAgent ,  prenomAgent ,  sexe ,  dateEmbaucheAgent ,  dateNaissAgent ,  telAgent ,  bpAgent , villeAgent , rueAgent , qualificationAgent from agent a, occuper o where a.idAgent=o.idAgent and datefinOccuper is null and a.suppr=0 and o.suppr=0");
            
            while(r.next()){
                nom.add(r.getString(2));
                prenom.add(r.getString(3));
                dateembauche.add(r.getDate(5).toString());
                datenaiss.add(r.getDate(6).toString());
                Agent a=new Agent(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getDate(5).toString(),r.getDate(6).toString(),r.getString(7),r.getString(8),r.getString(9),r.getString(10),r.getString(11));
                agents.add(a);
                
            }
            model.addColumn("Nom de l'agent",nom.toArray());
            model.addColumn("Prenoms de l'Agent",prenom.toArray());
            model.addColumn("Date de naissance ", datenaiss.toArray());
            model.addColumn("Date d'embauche",dateembauche.toArray());
            tableAgents.setModel(model);
            tableRPAgents.setModel(model);
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }

    
    public void remplirAgentsHorsFonction(){
        try {
            agents.clear();
            nom.clear();
            prenom.clear();
            this.dateembauche.clear();
            this.datenaiss.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  a.idAgent ,  nomAgent ,  prenomAgent ,  sexe ,  dateEmbaucheAgent ,  dateNaissAgent ,  telAgent ,  bpAgent , villeAgent , rueAgent , qualificationAgent from agent a, occuper o where a.idAgent=o.idAgent and datefinOccuper is not null and a.suppr=0 and o.suppr=0 and a.idAgent not in (select idAgent from occuper where datefinOccuper is null)");
            
            while(r.next()){
                this.nom.add(r.getString(2));
                this.prenom.add(r.getString(3));
                this.dateembauche.add(r.getDate(5).toString());
                this.datenaiss.add(r.getDate(6).toString());
                Agent a=new Agent(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getDate(5).toString(),r.getDate(6).toString(),r.getString(7),r.getString(8),r.getString(9),r.getString(10),r.getString(11));
                this.agents.add(a);
                
            }
            model.addColumn("Nom de l'agent",nom.toArray());
            model.addColumn("Prenoms de l'Agent",prenom.toArray());
            model.addColumn("Date de naissance ", this.datenaiss.toArray());
            model.addColumn("Date d'embauche",this.dateembauche.toArray());
            this.tableAgents.setModel(model);
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }
    
    private void remplirTypeEvenement(){
        try {
            typeEvenements.clear();
            libelleTE.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT idTypeEvenement,libelleTypeEvenement from typeEvenement where suppr=0");
            
            while(r.next()){
                this.libelleTE.add(r.getString(2));
                TypeEvenement te=new TypeEvenement(r.getInt(1),r.getString(2));
                this.typeEvenements.add(te);
                
            }
            model.addColumn("Type d'Evenement",libelleTE.toArray());
            this.tableEvenement.setModel(model);
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    private void remplirEvenement(){
        try {
            evenements.clear();
            nomAgentEvenement.clear();
            prenomAgentEvenement.clear();
            agentsEvenement.clear();
            libelletypeEvenement.clear();
            typeEvenement.clear();
            dateEvenement.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  a.idAgent ,  a.nomAgent , a.prenomAgent,t.idTypeEvenement,t.libelleTypeEvenement,e.datedebEvenement,e.datefinEvenement,e.motif  from agent a,typeEvenement t,evenement e where a.idAgent=e.idAgent and t.idTypeEvenement=e.idTypeEvenement and e.suppr=0 order by e.datedebEvenement LIMIT 0,50");
            
            while(r.next()){
                
                nomAgentEvenement.add(r.getString(2));
                prenomAgentEvenement.add(r.getString(3));
                Agent ag=new Agent(r.getInt(1),r.getString(2),r.getString(3));
                agentsEvenement.add(ag);
                libelletypeEvenement.add(r.getString(5));
                dateEvenement.add(r.getDate(6));
                TypeEvenement te=new TypeEvenement(r.getInt(4),r.getString(5));
                 typeEvenement.add(te);
                 Evenement e=new Evenement(ag, te,r.getDate(6).toString(),r.getDate(7).toString(),0,r.getString(8));
                evenements.add(e);
                
            }
            model.addColumn("Nom ",this.nomAgentEvenement.toArray());
            model.addColumn("Prenom ",this.prenomAgentEvenement.toArray());
            model.addColumn("Type d'Evenement ",this.libelletypeEvenement.toArray());
            model.addColumn("Date de l'Evenement ",this.dateEvenement.toArray());
            
            
            this.tableEvenement.setModel(model);
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    
    private void remplirFonction(){
        try {
            fonctions.clear();
            libelleFonction.clear();
            nombreOccupant.clear();
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  idFonction ,  libelleFonction ,  nbPersonne  from fonction where suppr=0");
            
            while(r.next()){
                nombreOccupant.add(r.getInt(3));
                libelleFonction.add(r.getString(2));
                Fonction f=new Fonction(r.getInt(1),r.getString(2),r.getInt(3));
                this.fonctions.add(f);
                
            }
            model.addColumn("Libelle de la fonction",this.libelleFonction.toArray());
            model.addColumn("Nombre max d'occupant par service",this.nombreOccupant.toArray());
            this.tableFonction.setModel(model);
        
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public void remplirRubrique(){
        try {
            rubriques.clear();
            libelleRubrique.clear();
           
            DefaultTableModel model=new DefaultTableModel();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT  idRubrique ,  libelleRubrique,etat from rubrique where suppr=0");
            
            while(r.next()){
                libelleRubrique.add(r.getString(2));
                Rubrique f=new Rubrique(r.getInt(1),r.getString(2),r.getInt(3));
                this.rubriques.add(f);
                
            }
            model.addColumn("Libelle de la rubrique",this.libelleRubrique.toArray());
            this.tableRubriques.setModel(model);
        remplirAgentRubrique();
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(rootPane, ex);
        }
    }
    
    public void remplirCombosEvenement() {
        try {
            
            evTypeEvenement.clear();
     evService.clear();//service a choisir dans le combo
     evFonction.clear();//fonction a choisir dans le combo
     evAgents.clear();//les evAgents
     evFonctions.clear();// * sur les evFonctions des evAgents
    evNomA.clear();//nom pour remplir la table
     evPrenomA.clear();//prenom pour remplir la table
     evFonctionA.clear();//libelle evFonction pour remplir la table
     evNomD.clear();//nom pour remplir la table de destination
     evPrenomD.clear();//prenom pour remplir la table de destination
     evFonctionD.clear() ;
     evAgentselectionés.clear();
         Date a=new Date();
            SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy");
            datedebut.setValue(a);
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
             Statement s1=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("SELECT * from typeEvenement where suppr=0");
            while(r.next()){
                TypeEvenement t=new TypeEvenement(r.getInt(1),r.getString(2));
                this.evTypeEvenement.add(t);
            }
            for(int i=0;i<evTypeEvenement.size();i++){
                TypeEvenement t=(TypeEvenement) this.evTypeEvenement.get(i);
                cte.addItem(t.getLibelleTypeEvenement());
            }
         
             ResultSet r1=s1.executeQuery("SELECT idServiceAtelier,libelleServiceAtelier from serviceAtelier where suppr=0");
            while(r1.next()){
                Atelier t1=new Atelier(r1.getInt(1),r1.getString(2));
                this.evService.add(t1);
              
            }
            for(int i=0;i<evService.size();i++){
                Atelier t2= (Atelier) this.evService.get(i);
                cas.addItem(t2.getlibelleAtelier());
            }
            
            
             ResultSet r2=s1.executeQuery("SELECT a.idAgent,a.nomAgent,a.prenomAgent,f.idFonction,f.libelleFonction,f.nbPersonne from agent a,fonction f,occuper o  where a.suppr=0 and f.suppr=0 and o.suppr=0 and a.idAgent=o.idAgent and o.idFonction=f.idfonction and o.datefinOccuper is null");
            while(r2.next()){
                Agent a1=new Agent(r2.getInt(1),r2.getString(2),r2.getString(3));
                Fonction f1=new Fonction(r2.getInt(4),r2.getString(5),r2.getInt(6));
                this.evAgents.add(a1);
                this.evFonctions.add(f1);
                this.evNomA.add(a1.getNomAgent());
                this.evPrenomA.add(a1.getPrenomAgent());
                this.evFonctionA.add(f1.getLibelleFonction());
            }
            DefaultTableModel model=new DefaultTableModel();
            model.addColumn("NOM", evNomA.toArray());
            model.addColumn("Prenoms", evPrenomA.toArray());
            model.addColumn("Fonction",evFonctionA.toArray());
            
            this.tableDepart.setModel(model);
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupEvenementsRecherche = new javax.swing.ButtonGroup();
        jFrame1 = new javax.swing.JFrame();
        buttonGroupAteliers = new javax.swing.ButtonGroup();
        buttonGroupEvenement = new javax.swing.ButtonGroup();
        rubriquePayes = new javax.swing.ButtonGroup();
        rubriquepayesRecherche = new javax.swing.ButtonGroup();
        EvbuttonGroup = new javax.swing.ButtonGroup();
        RPbouttonGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        tabAgent = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAgents = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnModif = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnSuppr = new javax.swing.JButton();
        btnSuppr1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        villeAgent = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        bpAgent = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        nomAgent = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rueAgent = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        prenomAgent = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        sexeAgent = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        telAgent = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ddnAgent = new javax.swing.JSpinner();
        dateEmbauche = new javax.swing.JSpinner();
        qualificationAgent = new javax.swing.JComboBox();
        service = new javax.swing.JComboBox();
        fonction = new javax.swing.JComboBox();
        tabPaye = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableRubriques = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        rubriqueRadio = new javax.swing.JRadioButton();
        payeRadio = new javax.swing.JRadioButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableRPAgents = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        RPenregistrer = new javax.swing.JButton();
        RPtt = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        RPVrubrique = new javax.swing.JTextField();
        gain = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        perte = new javax.swing.JRadioButton();
        RPVmontant = new javax.swing.JSpinner();
        RPVprenom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        RPVnom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        datePaie = new javax.swing.JSpinner();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableRPrubrSelec = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        btnRPAjouter = new javax.swing.JButton();
        btnRPretirer = new javax.swing.JButton();
        btnRPannuler = new javax.swing.JButton();
        btnRPimprimer = new javax.swing.JButton();
        tabEvenement = new javax.swing.JPanel();
        ETElabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableEvenement = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 2), new java.awt.Dimension(0, 2), new java.awt.Dimension(32767, 2));
        jPanel3 = new javax.swing.JPanel();
        btnEvEnregistrer = new javax.swing.JButton();
        btnEvModifier = new javax.swing.JButton();
        btnEvSupprimer = new javax.swing.JButton();
        btnEvNouveau = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        tabAgents = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        lte = new javax.swing.JLabel();
        lde = new javax.swing.JLabel();
        ldfe = new javax.swing.JLabel();
        ld = new javax.swing.JLabel();
        lsa = new javax.swing.JLabel();
        lf = new javax.swing.JLabel();
        lm = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        motif = new javax.swing.JTextArea();
        cf = new javax.swing.JComboBox();
        cas = new javax.swing.JComboBox();
        cd = new javax.swing.JComboBox();
        datefin = new javax.swing.JSpinner();
        datedebut = new javax.swing.JSpinner();
        cte = new javax.swing.JComboBox();
        suivant = new javax.swing.JButton();
        tabAgentsConcernes = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableDepart = new javax.swing.JTable();
        jButton13 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableFinale = new javax.swing.JTable();
        precedent = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TETlib = new javax.swing.JTextField();
        EvenementRadio = new javax.swing.JRadioButton();
        typeEvenementRadio = new javax.swing.JRadioButton();
        tabAtelier = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFonction = new javax.swing.JTable();
        atelierRadio = new javax.swing.JRadioButton();
        fonctionRadio = new javax.swing.JRadioButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableAS = new javax.swing.JTable();
        labelAS = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        ASVlib = new javax.swing.JLabel();
        ASTlib = new javax.swing.JTextField();
        ASVnbOcc = new javax.swing.JLabel();
        ASSnbO = new javax.swing.JSpinner();
        jScrollPane10 = new javax.swing.JScrollPane();
        listdepart = new javax.swing.JList();
        jScrollPane11 = new javax.swing.JScrollPane();
        listefinal = new javax.swing.JList();
        btnAjouter = new javax.swing.JButton();
        btnRetirer = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        ASbuttonSave = new javax.swing.JButton();
        btnNouveau = new javax.swing.JButton();
        btnRaf = new javax.swing.JButton();
        btnMod = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));
        setResizable(false);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1000, 641));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        tabAgent.setBackground(new java.awt.Color(228, 255, 201));
        tabAgent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabAgentMouseEntered(evt);
            }
        });
        tabAgent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabAgentFocusGained(evt);
            }
        });
        tabAgent.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tabAgentPropertyChange(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableAgents.setToolTipText("Liste des Agents");
        tableAgents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableAgents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAgentsMouseClicked(evt);
            }
        });
        tableAgents.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tableAgentsComponentShown(evt);
            }
        });
        jScrollPane1.setViewportView(tableAgents);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnModif.setText("Modifier");
        btnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifActionPerformed(evt);
            }
        });

        jButton1.setText("Enregistrer");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnSuppr.setText("Supprimer");
        btnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprActionPerformed(evt);
            }
        });

        btnSuppr1.setText("Nouveau");
        btnSuppr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuppr1ActionPerformed(evt);
            }
        });

        jButton2.setText("Imprimer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Annuler");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSuppr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuppr1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnModif, btnSuppr, btnSuppr1, jButton1});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(btnSuppr1)
                .addGap(23, 23, 23)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnModif)
                .addGap(18, 18, 18)
                .addComponent(btnSuppr)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel15.setText("Ville");

        jLabel13.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel13.setText("B.P");

        jLabel8.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel8.setText("Sexe");

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel7.setText("Date Naissance");

        jLabel14.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel14.setText("Rue");

        jLabel16.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel16.setText("Téléphone");

        jLabel11.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel11.setText("Fonction");

        jLabel6.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel6.setText("Prenom");

        sexeAgent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Féminin", "Masculin" }));

        jLabel5.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel5.setText("Nom");

        jLabel12.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel12.setText("Date Embauche");

        jLabel10.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel10.setText("Atelier");

        jLabel9.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel9.setText("Qualification");

        ddnAgent.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(-307444200000L), new java.util.Date(-307444200000L), new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        dateEmbauche.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1333551109400L), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        qualificationAgent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun", "CEPD", "BEPC", "BAC" }));

        service.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(153, 153, 153)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prenomAgent)
                            .addComponent(nomAgent)
                            .addComponent(sexeAgent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(135, 135, 135)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rueAgent)
                            .addComponent(bpAgent)
                            .addComponent(villeAgent)
                            .addComponent(telAgent)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(fonction, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 24, Short.MAX_VALUE))
                            .addComponent(service, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ddnAgent)
                            .addComponent(dateEmbauche)
                            .addComponent(qualificationAgent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(nomAgent))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(prenomAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(sexeAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ddnAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(dateEmbauche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(qualificationAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(service, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fonction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rueAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(bpAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(villeAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(telAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabAgentLayout = new javax.swing.GroupLayout(tabAgent);
        tabAgent.setLayout(tabAgentLayout);
        tabAgentLayout.setHorizontalGroup(
            tabAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAgentLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5619, 5619, 5619))
        );
        tabAgentLayout.setVerticalGroup(
            tabAgentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAgentLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAgentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );

        jTabbedPane1.addTab("Agents", tabAgent);

        tabPaye.setBackground(new java.awt.Color(228, 255, 201));

        tableRubriques.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableRubriques.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRubriquesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableRubriques);

        jButton9.setText("Nouveau");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Modifier");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setText("Supprimer");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        RPbouttonGroup.add(rubriqueRadio);
        rubriqueRadio.setSelected(true);
        rubriqueRadio.setText("Rubrique");
        rubriqueRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rubriqueRadioActionPerformed(evt);
            }
        });

        RPbouttonGroup.add(payeRadio);
        payeRadio.setText("Paye");
        payeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payeRadioActionPerformed(evt);
            }
        });

        tableRPAgents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableRPAgents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRPAgentsMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tableRPAgents);

        jLabel2.setText("Liste des agents");

        RPenregistrer.setText("Enregistrer");
        RPenregistrer.setEnabled(false);
        RPenregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RPenregistrerActionPerformed(evt);
            }
        });

        RPtt.setText("Tout le monde");
        RPtt.setVisible(false);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rubriquepayesRecherche.add(gain);
        gain.setSelected(true);
        gain.setText("Gain");
        gain.setVisible(false);

        jLabel18.setText("Montant");

        jLabel17.setText("Prenom Agent");

        rubriquepayesRecherche.add(perte);
        perte.setText("Perte");
        perte.setVisible(false);

        RPVmontant.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(25.0d)));

        jLabel3.setText("Rubrique");

        RPVnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RPVnomActionPerformed(evt);
            }
        });

        jLabel4.setText("Nom Agent");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(gain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(perte))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RPVmontant, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel17))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(RPVrubrique)
                            .addComponent(RPVnom)
                            .addComponent(RPVprenom, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(RPVrubrique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(RPVnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(RPVprenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(RPVmontant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gain)
                    .addComponent(perte))
                .addContainerGap(240, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Rubrique", jPanel8);

        jLabel19.setText("Periode paie");

        datePaie.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        tableRPrubrSelec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(tableRPrubrSelec);

        jLabel20.setText("Rubriques Sélectionnées");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(datePaie, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(datePaie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Paye", jPanel9);

        btnRPAjouter.setText("Ajouter");
        btnRPAjouter.setVisible(false);
        btnRPAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPAjouterActionPerformed(evt);
            }
        });

        btnRPretirer.setText("Retirer");
        btnRPretirer.setVisible(false);
        btnRPretirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPretirerActionPerformed(evt);
            }
        });

        btnRPannuler.setText("Annuler");
        btnRPannuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPannulerActionPerformed(evt);
            }
        });

        btnRPimprimer.setText("Imprimer");
        btnRPimprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRPimprimerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabPayeLayout = new javax.swing.GroupLayout(tabPaye);
        tabPaye.setLayout(tabPayeLayout);
        tabPayeLayout.setHorizontalGroup(
            tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPayeLayout.createSequentialGroup()
                .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPayeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addComponent(rubriqueRadio)
                                .addGap(112, 112, 112)
                                .addComponent(payeRadio))
                            .addComponent(jLabel2)))
                    .addGroup(tabPayeLayout.createSequentialGroup()
                        .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRPretirer, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRPAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(RPtt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(RPenregistrer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRPannuler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRPimprimer))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        tabPayeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RPtt, btnRPAjouter, btnRPimprimer, btnRPretirer});

        tabPayeLayout.setVerticalGroup(
            tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabPayeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rubriqueRadio)
                    .addComponent(payeRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabPayeLayout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addGroup(tabPayeLayout.createSequentialGroup()
                        .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btnRPimprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RPtt, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RPenregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(tabPayeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(tabPayeLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnRPannuler, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(btnRPAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRPretirer, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        tabPayeLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {RPenregistrer, RPtt, btnRPAjouter, btnRPannuler, btnRPimprimer, btnRPretirer, jButton10, jButton12, jButton9});

        jTabbedPane1.addTab("Rubriques et Payes", tabPaye);

        tabEvenement.setBackground(new java.awt.Color(228, 255, 201));

        ETElabel.setText("Liste des 50 derniers Evenements");

        tableEvenement.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableEvenement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEvenementMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableEvenement);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnEvEnregistrer.setText("Enregistrer");
        btnEvEnregistrer.setEnabled(false);
        btnEvEnregistrer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEvEnregistrer.setFocusable(false);
        btnEvEnregistrer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEvEnregistrer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEvEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvEnregistrerActionPerformed(evt);
            }
        });

        btnEvModifier.setText("Modifier");
        btnEvModifier.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEvModifier.setFocusable(false);
        btnEvModifier.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEvModifier.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEvModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvModifierActionPerformed(evt);
            }
        });

        btnEvSupprimer.setText("Supprimer");
        btnEvSupprimer.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEvSupprimer.setFocusable(false);
        btnEvSupprimer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEvSupprimer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEvSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvSupprimerActionPerformed(evt);
            }
        });

        btnEvNouveau.setText("Nouveau");
        btnEvNouveau.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEvNouveau.setFocusable(false);
        btnEvNouveau.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEvNouveau.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEvNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvNouveauActionPerformed(evt);
            }
        });

        jButton4.setText("Annuler");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEvNouveau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEvEnregistrer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnEvModifier, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnEvSupprimer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEvNouveau)
                .addGap(26, 26, 26)
                .addComponent(btnEvEnregistrer)
                .addGap(28, 28, 28)
                .addComponent(btnEvModifier)
                .addGap(29, 29, 29)
                .addComponent(btnEvSupprimer)
                .addGap(27, 27, 27)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tabAgents.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lte.setText("Type d'Evenement");

        lde.setText("Date Evenement");

        ldfe.setText("Date fin Evenement");

        ld.setText("Durée Evenement");

        lsa.setText("Atelier/Service");

        lf.setText("Fonction");

        lm.setText("Motif");

        motif.setColumns(20);
        motif.setRows(5);
        jScrollPane5.setViewportView(motif);

        cf.setEnabled(false);

        cas.setEnabled(false);
        cas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casActionPerformed(evt);
            }
        });

        cd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        datefin.setModel(new javax.swing.SpinnerDateModel());

        datedebut.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        datedebut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        datedebut.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                datedebutMouseMoved(evt);
            }
        });
        datedebut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                datedebutFocusLost(evt);
            }
        });
        datedebut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                datedebutKeyReleased(evt);
            }
        });

        cte.setEnabled(false);
        cte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cteActionPerformed(evt);
            }
        });

        suivant.setText("Suivant");
        suivant.setVisible(false);
        suivant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suivantActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(suivant)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ld)
                            .addComponent(ldfe)
                            .addComponent(lsa)
                            .addComponent(lf)
                            .addComponent(lde)
                            .addComponent(lte)
                            .addComponent(lm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(datedebut)
                            .addComponent(cte, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(datefin)
                            .addComponent(cd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lte)
                    .addComponent(cte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datedebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lde))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ldfe)
                    .addComponent(datefin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ld)
                    .addComponent(cd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lsa))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lf))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(lm)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(suivant, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        tabAgents.addTab("Evenement", jPanel4);

        tableDepart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableDepart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tableDepart);

        jButton13.setText("Retirer");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton7.setText("Ajouter");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tableFinale.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane6.setViewportView(tableFinale);

        precedent.setText("Précédent");
        precedent.setVisible(false);
        precedent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precedentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabAgentsConcernesLayout = new javax.swing.GroupLayout(tabAgentsConcernes);
        tabAgentsConcernes.setLayout(tabAgentsConcernesLayout);
        tabAgentsConcernesLayout.setHorizontalGroup(
            tabAgentsConcernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAgentsConcernesLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAgentsConcernesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabAgentsConcernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(precedent)
                    .addGroup(tabAgentsConcernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabAgentsConcernesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton13, jButton7});

        tabAgentsConcernesLayout.setVerticalGroup(
            tabAgentsConcernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAgentsConcernesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(tabAgentsConcernesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precedent, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        tabAgents.addTab("Choisir les Agents Concernés", tabAgentsConcernes);

        jLabel1.setText("Type d'Evenement");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(69, 69, 69)
                .addComponent(TETlib, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TETlib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(314, Short.MAX_VALUE))
        );

        tabAgents.addTab("Type Evenement", jPanel7);

        EvbuttonGroup.add(EvenementRadio);
        EvenementRadio.setSelected(true);
        EvenementRadio.setText("Evenement");
        EvenementRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EvenementRadioActionPerformed(evt);
            }
        });

        EvbuttonGroup.add(typeEvenementRadio);
        typeEvenementRadio.setText("Type Evenement");
        typeEvenementRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeEvenementRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabEvenementLayout = new javax.swing.GroupLayout(tabEvenement);
        tabEvenement.setLayout(tabEvenementLayout);
        tabEvenementLayout.setHorizontalGroup(
            tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabEvenementLayout.createSequentialGroup()
                .addGroup(tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabEvenementLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(ETElabel))
                    .addGroup(tabEvenementLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabEvenementLayout.createSequentialGroup()
                                .addComponent(EvenementRadio)
                                .addGap(147, 147, 147)
                                .addComponent(typeEvenementRadio))
                            .addComponent(tabAgents, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(703, 703, 703))
        );
        tabEvenementLayout.setVerticalGroup(
            tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabEvenementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EvenementRadio)
                    .addComponent(typeEvenementRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
            .addGroup(tabEvenementLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(ETElabel)
                .addGap(18, 18, 18)
                .addGroup(tabEvenementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabAgents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 97, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Evenements et Types d'Evenements", tabEvenement);

        tabAtelier.setBackground(new java.awt.Color(228, 255, 201));

        tableFonction.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableFonction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableFonction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFonctionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableFonction);

        buttonGroupAteliers.add(atelierRadio);
        atelierRadio.setSelected(true);
        atelierRadio.setText("Ateliers/Services");
        atelierRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atelierRadioActionPerformed(evt);
            }
        });

        buttonGroupAteliers.add(fonctionRadio);
        fonctionRadio.setText("Fonctions");
        fonctionRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fonctionRadioActionPerformed(evt);
            }
        });

        tableAS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tableAS);

        labelAS.setText("jLabel2");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ASVlib.setText("Libelle");

        ASVnbOcc.setText("Libelle");
        ASVnbOcc.setVisible(false);

        ASSnbO.setVisible(false);
        ASSnbO.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jScrollPane10.setVisible(false);

        listdepart.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        listdepart.setVisible(false);
        listdepart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane10.setViewportView(listdepart);

        jScrollPane11.setVisible(false);

        listefinal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane11.setViewportView(listefinal);
        listefinal.setVisible(false);

        btnAjouter.setText("Ajouter");
        btnAjouter.setVisible(false);
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });

        btnRetirer.setText("Retirer");
        btnRetirer.setVisible(false);
        btnRetirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(ASVlib)
                                .addGap(46, 46, 46)
                                .addComponent(ASTlib, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(ASVnbOcc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ASSnbO, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnAjouter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(19, 19, 19))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnRetirer, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)))
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ASVlib)
                    .addComponent(ASTlib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ASVnbOcc)
                    .addComponent(ASSnbO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(btnAjouter)
                        .addGap(28, 28, 28)
                        .addComponent(btnRetirer)))
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDelete.setText("Supprimer");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        ASbuttonSave.setText("Enregistrer");
        ASbuttonSave.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ASbuttonSave.setEnabled(false);
        ASbuttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ASbuttonSaveActionPerformed(evt);
            }
        });

        btnNouveau.setText("Nouveau");
        btnNouveau.setFocusPainted(false);
        btnNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNouveauActionPerformed(evt);
            }
        });

        btnRaf.setText("Annuler");
        btnRaf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRafActionPerformed(evt);
            }
        });

        btnMod.setText("Modifier");
        btnMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnDelete)
                        .addComponent(btnMod)
                        .addComponent(btnNouveau)
                        .addComponent(btnRaf))
                    .addComponent(ASbuttonSave))
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ASbuttonSave, btnDelete, btnMod, btnNouveau, btnRaf});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ASbuttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNouveau)
                .addGap(18, 18, 18)
                .addComponent(btnMod)
                .addGap(18, 18, 18)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRaf)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ASbuttonSave, btnNouveau});

        javax.swing.GroupLayout tabAtelierLayout = new javax.swing.GroupLayout(tabAtelier);
        tabAtelier.setLayout(tabAtelierLayout);
        tabAtelierLayout.setHorizontalGroup(
            tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabAtelierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAS)
                    .addGroup(tabAtelierLayout.createSequentialGroup()
                        .addGroup(tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(fonctionRadio)
                            .addComponent(atelierRadio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        tabAtelierLayout.setVerticalGroup(
            tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAtelierLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(labelAS)
                .addGroup(tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAtelierLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(341, 341, 341))
                    .addGroup(tabAtelierLayout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(atelierRadio)
                        .addGap(18, 18, 18)
                        .addComponent(fonctionRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAtelierLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabAtelierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAtelierLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(112, 112, 112))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabAtelierLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))))
        );

        jTabbedPane1.addTab("Ateliers/Services et Fonctions", tabAtelier);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabAgentPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tabAgentPropertyChange
// remplir();        // TODO add your handling code here:
    }//GEN-LAST:event_tabAgentPropertyChange

    private void tabAgentFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabAgentFocusGained


        // TODO add your handling code here:
  }//GEN-LAST:event_tabAgentFocusGained

    private void tabAgentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabAgentMouseEntered
//remplir();        // TODO add your handling code here:
    }//GEN-LAST:event_tabAgentMouseEntered

    private void tableAgentsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableAgentsComponentShown



        // TODO add your handling code here:
 }//GEN-LAST:event_tableAgentsComponentShown

    private void btnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprActionPerformed
        int ind = this.tableAgents.getSelectedRow();
        Agent a = (Agent) agents.get(ind);
        if (JOptionPane.showConfirmDialog(rootPane, "Êtes vous sûres de supprimer cet agent") == 0) {


        a.deleteAgent();
        remplirAgent();
        remplirAgentRubrique();
        remplirEvenement();
        remplirListeDeDepart();
      
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSupprActionPerformed

    private void btnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifActionPerformed

        if(this.tableAgents.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir un service svp");
            return;
        }
        
        int ind = this.tableAgents.getSelectedRow();
        Agent a = (Agent) this.agents.get(ind);
        a.setNomAgent(nomAgent.getText());
        a.setPrenomAgent(prenomAgent.getText());
        a.setSexe(sexeAgent.getSelectedItem().toString());
        a.setQualificationAgent(qualificationAgent.getSelectedItem().toString());
        a.setBpAgent(bpAgent.getText());
        a.setRueAgent(rueAgent.getText());
        a.setVilleAgent(villeAgent.getText());
        a.setTelAgent(telAgent.getText());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        a.setDateEmbaucheAgent(format.format(dateEmbauche.getValue()));
        a.setDateNaissAgent(format.format(ddnAgent.getValue()));
        if(!a.verifactionSaisie()){
            JOptionPane.showMessageDialog(null,"Veuillez remplir tous les champs nécessaires");
            return;
        }
        else{
            if(JOptionPane.showConfirmDialog(null, "Êtes vous sûres des modifications apportées à cet agent?", "Demande de confirmation", 1)==0){
            a.updateAgent();//
            
            }
            
        }
        remplirAgent();
        remplirAgentRubrique();
        remplirEvenement();
        remplirListeDeDepart();
      
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifActionPerformed

    private void atelierRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atelierRadioActionPerformed

        remplirAteliers();
        
        
            ASSnbO.setVisible(false);
            ASVnbOcc.setVisible(false);
            ASTlib.setText(null);
            ASVlib.setText("Libelle du service/atelier");
        // TODO add your handling code here:
    }//GEN-LAST:event_atelierRadioActionPerformed

    private void fonctionRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fonctionRadioActionPerformed

        remplirFonction();
       
        ASSnbO.setVisible(true);
            ASVnbOcc.setVisible(true);
            ASTlib.setText(null);
            ASVlib.setText("Libelle de la fonction");
            ASVnbOcc.setText("Nombre max d'occupant");
            
                   
        // TODO add your handling code here:
    }//GEN-LAST:event_fonctionRadioActionPerformed

    private void btnNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNouveauActionPerformed

        if(this.atelierRadio.isSelected()){
            ASSnbO.setVisible(false);
            ASVnbOcc.setVisible(false);
            ASTlib.setText(null);
            ASVlib.setText("Libelle du service/atelier");
         
        }else{
        
            jScrollPane10.setVisible(true);
            jScrollPane11.setVisible(true);
            ASSnbO.setVisible(true);
            ASVnbOcc.setVisible(true);
            ASTlib.setText(null);
            ASVlib.setText("Libelle de la fonction");
            ASVnbOcc.setText("Nombre max d'occupant");
            btnAjouter.setVisible(true);
            btnRetirer.setVisible(true);
            listdepart.setVisible(true);
            listefinal.setVisible(true);
            remplirListeDeDepart();
            }
           ASbuttonSave.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNouveauActionPerformed

    private void btnModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActionPerformed

        int ind=tableFonction.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la ligne à modifier");
            return;
        }
        if(ASTlib.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez Entrer un libellé svp");
            return;
        }
        if(this.atelierRadio.isSelected()){
            Atelier a=(Atelier) ateliers.get(ind);
            a.setlibelleAtelier(ASTlib.getText());
            a.UpdateAtelier();
            remplirAteliers();
        }
        else{
            try {
                ASSnbO.commitEdit();
                Fonction f=(Fonction) fonctions.get(ind);
                f.setLibelleFonction(ASTlib.getText());
                f.setNbPersonne((int)ASSnbO.getValue());
                f.updateFonction();
                remplirFonction();
            } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
            }
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModActionPerformed

    private void btnRafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRafActionPerformed

        if(this.atelierRadio.isSelected()){
            ASSnbO.setVisible(false);
            ASVnbOcc.setVisible(false);
            ASTlib.setText(null);
            ASVlib.setText("Libelle du service/atelier");
         
        }else{
        ASSnbO.setVisible(true);
            ASVnbOcc.setVisible(true);
            ASTlib.setText(null);
            ASVlib.setText("Libelle de la fonction");
            ASVnbOcc.setText("Nombre max d'occupant");
            btnAjouter.setVisible(false);
            btnRetirer.setVisible(false);
            listdepart.setVisible(false);
            listefinal.setVisible(false);
            remplirListeDeDepart();
            }
           ASbuttonSave.setEnabled(false);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRafActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
int ind=this.tableFonction.getSelectedRow();
        if(this.atelierRadio.isSelected() ){
            if(ind!=-1){
                if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de vouloir supprimer ce service? Tous les agents concernés seront automatiquement au chomage ")==0){
            
                Atelier a=(Atelier) ateliers.get(ind);
            a.deleteAtelier();
            remplirAteliers();
                }
            }
            else{
            JOptionPane.showMessageDialog(null, "Veuillez choisir un service svp!");
        }
            
        }
        else{
            if(ind!=-1){
                if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de vouloir supprimer cette fonction? Tous les agents concernés seront automatiquement au chomage ")==0){
            Fonction f=(Fonction) this.fonctions.get(ind);
            f.deleteFonction();
            remplirFonction();
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Veuillez choisir une fonction svp");
            }
        }
        
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed

        btnRPannuler.setEnabled(true);
        RPenregistrer.setEnabled(true);
        
        if(rubriqueRadio.isSelected()){
           RPVmontant.setEnabled(false);
           RPVnom.setEnabled(false);
           RPVprenom.setEnabled(false);
           RPVrubrique.setText(null);
           gain.setVisible(true);
           perte.setVisible(true);
           jTabbedPane2.setSelectedIndex(0);
        }else{
            btnRPAjouter.setVisible(true);
            btnRPretirer.setVisible(true);
            jTabbedPane2.setSelectedIndex(1);
        }
      
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed

        int ind=this.tableRubriques.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez selectionner une ligne de la table");
            return;
        }
        Rubrique r=(Rubrique) this.rubriques.get(ind);
       r.deleteRubrique();
       remplirRubrique();
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

        
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void serviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceActionPerformed

        this.yfonctions.clear();
        this.fonction.removeAllItems();
        int ind=this.service.getSelectedIndex();
        Atelier a= (Atelier) this.yateliers.get(ind);
      
        try {
            Statement s= sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.idFonction,f.libelleFonction,f.nbPersonne from fonction f join composer c where c.idFonction=f.idFonction and c.suppr=0 and f.suppr=0 and f.suppr=0 and c.idServiceAtelier="+a.getidAtelier());
            while(r.next()){
                Fonction f=new Fonction(r.getInt(1),r.getString(2),r.getInt(3));
                this.yfonctions.add(f);
            }
            for(int i=0;i<this.yfonctions.size();i++){
                Fonction f=(Fonction) this.yfonctions.get(i);
                this.fonction.addItem(f.getLibelleFonction());
            }
        }
        catch(Exception ex){
       JOptionPane.showMessageDialog(null, ex);     
        }
        
            // TODO add your handling code here:
    }//GEN-LAST:event_serviceActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2=new SimpleDateFormat("dd-MM-yyyy");
        Fonction f=(Fonction) this.yfonctions.get(this.fonction.getSelectedIndex());
        Atelier at=(Atelier) this.yateliers.get(this.service.getSelectedIndex());
        Agent a=new Agent(this.nomAgent.getText(),this.prenomAgent.getText(),this.sexeAgent.getSelectedItem().toString(),format2.format(this.dateEmbauche.getValue()), format2.format(this.ddnAgent.getValue()), this.telAgent.getText(), this.bpAgent.getText(), this.villeAgent.getText(), this.rueAgent.getText(),this.qualificationAgent.getSelectedItem().toString());
        try {
            if(!a.verificationExistence()){
                a.saveAgent();
                a.setIdAgent(a.rechercheIdAgent());
                //String d=this.dateEmbauche.getV.substring(6,10)+"-"+this.dateEmbauche.getText().substring(3,5)+"-"+this.dateEmbauche.getText().substring(0,2);
                String d=format.format(dateEmbauche.getValue());
                Occuper o=new Occuper(a, at, f,d);
                o.saveOccuper();
                TypeEvenement te=new TypeEvenement("MUTATION");
                te.setIdTypeEvenement(te.rechercheID());
                Evenement ev=new Evenement(a, te, format2.format(this.dateEmbauche.getValue()),format2.format(this.dateEmbauche.getValue()));
                ev.saveEvenement();
                Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                ResultSet r=s.executeQuery("Select idRubrique from rubrique where suppr=0");
                while(r.next()){
                    Rubrique rb=new Rubrique(r.getInt(1));
                    Agent_Rubrique ar=new Agent_Rubrique(a,rb);
                    ar.setMontantRubrique(0);
                    ar.saveAgentRubrique();
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Un agent identique existe déjà");
                return;
            }
            
            // TODO add your handling code here:
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,ex);
           }       
        remplirAgent();
        jButton1.setEnabled(false);
        remplirAgentRubrique();
        remplirEvenement();
        remplirListeDeDepart();
      
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableAgentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAgentsMouseClicked

        SimpleDateFormat forma=new SimpleDateFormat("yyyy-MM-dd");
        int ind=tableAgents.getSelectedRow();
        Agent ag=(Agent) agents.get(ind);
        nomAgent.setText(ag.getNomAgent());
        this.prenomAgent.setText(ag.getPrenomAgent());
        this.rueAgent.setText(ag.getRueAgent());
        this.villeAgent.setText(ag.getVilleAgent());
        this.telAgent.setText(ag.getTelAgent());
        service.setEnabled(false);
        fonction.setEnabled(false);
        this.bpAgent.setText(ag.getBpAgent());
        qualificationAgent.setSelectedItem(ag.getQualificationAgent());
        sexeAgent.setSelectedItem(ag.getSexe());
        try {
            this.ddnAgent.setValue(forma.parse(ag.getDateNaissAgent()));
            this.dateEmbauche.setValue(forma.parse(ag.getDateEmbaucheAgent()));
        } catch (ParseException ex) {
           
        }
        
        if(ag.getSexe().equalsIgnoreCase("MASCULIN")){
        this.sexeAgent.setSelectedIndex(1);
        }
        else{
            this.sexeAgent.setSelectedIndex(0);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableAgentsMouseClicked

    private void btnSuppr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuppr1ActionPerformed
  nomAgent.setText(null);
        this.prenomAgent.setText(null);
        this.rueAgent.setText(null);
        this.villeAgent.setText(null);
        this.telAgent.setText(null);
        service.setEnabled(true);
        fonction.setEnabled(true);
        this.bpAgent.setText(null);
        sexeAgent.setSelectedItem(null);
        dateEmbauche.setValue(new Date());
        ddnAgent.setValue(new Date());
      
jButton1.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuppr1ActionPerformed

    private void btnEvNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvNouveauActionPerformed
if(EvenementRadio.isSelected()){
tabAgents.setSelectedIndex(0);
suivant.setVisible(true);
precedent.setVisible(true);
btnEvEnregistrer.setEnabled(true);
cas.setEnabled(true);
cte.setEnabled(true);
cf.setEnabled(true);
tabAgentsConcernes.setVisible(true);    
}
else{btnEvEnregistrer.setEnabled(true);
    tabAgents.setSelectedIndex(2);
    TETlib.setText(null);
}

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEvNouveauActionPerformed

    private void btnEvSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvSupprimerActionPerformed

        
        int ind = this.tableEvenement.getSelectedRow();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une ligne du tableau");
            return;
        }

        if(EvenementRadio.isSelected()){
        Evenement e=(Evenement) evenements.get(ind);
        e.deleteEvenement();
        remplirEvenement();
    
        }else{
            TypeEvenement t=(TypeEvenement) typeEvenements.get(ind);
            t.deleteTypeEvenement();
            remplirTypeEvenement();
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEvSupprimerActionPerformed

    private void btnEvModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvModifierActionPerformed

        if(tableEvenement.getSelectedRow()==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir une ligne du tableau");
            return;
        }
        if(EvenementRadio.isSelected()){
        try {
            datedebut.commitEdit();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        int ind = this.tableEvenement.getSelectedRow();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une ligne du tableau");
            return;
        }

         SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
         SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");

        if(datefin.isVisible()){
            try {
                if(format1.parse(datefin.getValue().toString()).after(format1.parse(datedebut.getValue().toString()))){
              JOptionPane.showMessageDialog(null, "La date de debut est après la date de fin");
              return;
                }
            } catch (ParseException ex) {
              JOptionPane.showMessageDialog(null, ex);
              
            }
        }
        String datefinev=format1.format(datedebut.getValue());
         Evenement evene= (Evenement) evenements.get(ind);
       
        if(datefin.isVisible()){
           datefinev=format.format(datefin.getValue());
        }
             
       //JOptionPane.showMessageDialog(null, evene.getDateDebEvenement());
       //JOptionPane.showMessageDialog(null, motif.getText());
       
       evene.setMotif(motif.getText());
      evene.updateEvenement(format1.format(datedebut.getValue()),datefinev);
         remplirEvenement();

    
        }
        else{
            int ind=tableEvenement.getSelectedRow();
            
            TypeEvenement t=(TypeEvenement) typeEvenements.get(ind);
            if(TETlib.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Veuillez entrer le libelle du type d'evenement svp");
                return;
            }
            t.setLibelleTypeEvenement(TETlib.getText());
            t.updateTypeEvenement();
            remplirTypeEvenement();
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEvModifierActionPerformed

    private void btnEvEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvEnregistrerActionPerformed
if(EvenementRadio.isSelected()){
    String choix=this.cte.getSelectedItem().toString();
       SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if(this.evAgentselectionés.size()!=0){
        switch (choix) {
            case "MUTATION" :
                int inda=this.cas.getSelectedIndex();
                int indf=this.cf.getSelectedIndex();
                Atelier newatel= (Atelier) this.evService.get(inda);
                Fonction newfonc= (Fonction) this.evFonction.get(indf);
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag, newatel, newfonc,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                if(JOptionPane.showConfirmDialog(null, "Voulez vous mettre fin aux  anciennes occupations de "+ag.getNomAgent()+" "+ag.getPrenomAgent())==0){
                 occuper.mettrefinOccuper();
                    
                }
               
                occuper.saveOccuper();         
        }
        
                this.revalidate();
                break;
            case "LICENCIEMENT" :
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                occuper.mettrefinOccuper();
                        
        }
                this.revalidate();
                break;
            case "DEMISSION" :
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                occuper.mettrefinOccuper();
                }
                this.revalidate();
                break;
            case "DECES" :
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                occuper.mettrefinOccuper();
                }
                this.revalidate();
                break;
            case "RETRAITE" :
                
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                occuper.mettrefinOccuper();
                }
                this.revalidate();
                break;
            case "REVOCATION" :
                for(int o=0;o<this.evAgentselectionés.size();o++){
                Agent ag= (Agent) this.evAgentselectionés.get(o);
                Occuper occuper=new Occuper(ag,format.format(datedebut.getValue()),format.format(datedebut.getValue()));
                occuper.mettrefinOccuper();
                }
                this.revalidate();
                break;
            default :
                this.revalidate();
                break;
        }
        SimpleDateFormat forma1=new SimpleDateFormat("dd-MM-yyyy");
        for(int o=0;o<this.evAgentselectionés.size();o++){
        Agent ag= (Agent) this.evAgentselectionés.get(o);
        int j=this.cte.getSelectedIndex();
        TypeEvenement TE=(TypeEvenement) this.evTypeEvenement.get(j);
        String datedeb=forma1.format(datedebut.getValue());
        String datefin=datedeb;
        if(this.datefin.isVisible()){
            datefin=forma1.format(this.datefin.getValue());
        }
        double dur=0;
        if(this.cd.isVisible()){
            dur=    (double) this.cd.getSelectedItem();
        }
        Evenement e=new Evenement(ag, TE,datedeb,datefin,dur, this.motif.getText());
        e.saveEvenement();
        }
        
cas.setEnabled(false);
cte.setEnabled(false);
cf.setEnabled(false);
tabAgentsConcernes.setVisible(false);
btnEvEnregistrer.setEnabled(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Veuillez choisir au moin un agent");
        }
remplirEvenement();
suivant.setVisible(false);
precedent.setVisible(false);
}
else{
    if(TETlib.getText().isEmpty()){
        JOptionPane.showMessageDialog(null,"Veuillez entrer le libelle svp");
        return;
    }
    TypeEvenement t=new TypeEvenement(TETlib.getText());
            try {
                if(!t.verificationExistence()){
                t.saveTypeEvenement();    
                }
                else{
                    JOptionPane.showMessageDialog(null,"Ce type evenement existe déja");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex);
            }
    
    remplirTypeEvenement();
}
 
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEvEnregistrerActionPerformed

    private void tableEvenementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEvenementMouseClicked

        if(EvenementRadio.isSelected()){
       // SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
        int ind=tableEvenement.getSelectedRow();
        Evenement evene= (Evenement) evenements.get(ind);
        cte.setSelectedItem(evene.getTypeEvenement().getLibelleTypeEvenement());
        try {
            //JOptionPane.showMessageDialog(null,format1.parse(evene.getDateDebEvenement()));
            datedebut.setValue(format1.parse(evene.getDateDebEvenement()));
            datefin.setValue(format1.parse(evene.getDateFinEvenement()));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,"pas marché "+ex);
        }
        motif.setText(evene.getMotif());     
        }
        else{
        int ind=tableEvenement.getSelectedRow();
        TETlib.setText(libelleTE.get(ind).toString());    
        }
       
        // TODO add your handling code here:
    }//GEN-LAST:event_tableEvenementMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        int ind = this.tableDepart.getSelectedRow();
        if (!this.evAgentselectionés.contains(this.evAgents.get(ind)) && ind != -1) {
            this.evAgentselectionés.add(this.evAgents.get(ind));
            Agent k = (Agent) this.evAgents.get(ind);
            this.evPrenomD.add(k.getPrenomAgent());
            this.evNomD.add(k.getNomAgent());
            DefaultTableModel mod = new DefaultTableModel();
            mod.addColumn("Nom", evNomD.toArray());
            mod.addColumn("Prenom", this.evPrenomD.toArray());
            this.tableFinale.setModel(mod);
        }


        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

        int ind = this.tableFinale.getSelectedRow();
        if (ind != -1) {
            this.evAgentselectionés.remove(ind);
            this.evNomD.remove(ind);
            this.evPrenomD.remove(ind);
            DefaultTableModel mod = new DefaultTableModel();
            mod.addColumn("Nom", evNomD.toArray());
            mod.addColumn("Prenom", this.evPrenomD.toArray());
            this.tableFinale.setModel(mod);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void cteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cteActionPerformed

        String choix = this.cte.getSelectedItem().toString();
        motif.setText("Aucun");
        switch (choix) {
            case "MUTATION":
                lde.setText("Date de la Mutation");
                ldfe.setVisible(false);
                this.datefin.setVisible(false);
                ld.setVisible(false);
                this.cd.setVisible(false);
                lsa.setVisible(true);
                cas.setVisible(true);
                lf.setVisible(true);
                cf.setVisible(true);
                lm.setVisible(true);
                lm.setText("Raison de la Mutation");
                motif.setVisible(true);
                this.revalidate();
                break;
            case "LICENCIEMENT":
                lde.setText("Date du Licenciement");
                ldfe.setVisible(false);
                this.datefin.setVisible(false);
                ld.setVisible(false);
                this.cd.setVisible(false);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Cause du Licenciement");
                motif.setVisible(true);
                this.revalidate();
                break;
            case "DEMISSION":
                lde.setText("Date de la Démission");
                ldfe.setVisible(false);
                this.datefin.setVisible(false);
                ld.setVisible(false);
                this.cd.setVisible(false);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Cause de la démission");
                motif.setVisible(true);
                this.revalidate();
                break;
            case "DECES":
                lde.setText("Date du Décès");
                ldfe.setVisible(false);
                this.datefin.setVisible(false);
                ld.setVisible(false);
                this.cd.setVisible(false);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Cause du décès");
                motif.setVisible(true);
                this.revalidate();
                break;
            case "CONGES":
                lde.setText("Date des Congés");
                ldfe.setVisible(true);
                ldfe.setText("Date de fin des congés");
                this.datefin.setVisible(true);
                ld.setVisible(false);
                this.cd.setVisible(false);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Motif des congés");
                motif.setVisible(true);
                this.revalidate();
                break;
            case "ABSENCES":
                lde.setText("Date de l'Absence");
                ldfe.setVisible(false);
                ldfe.setText("Date de fin des congés");
                this.datefin.setVisible(false);
                ld.setVisible(true);
                ld.setText("Durée de l'absence");
                this.cd.setVisible(true);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Justificatif");
                motif.setVisible(true);
                this.revalidate();
                break;
            default:
                lde.setText("Date de l'Evenement");
                ldfe.setVisible(true);
                ldfe.setText("Date de fin de l'Evenement");
                this.datefin.setVisible(true);
                ld.setVisible(true);
                ld.setText("Durée de l'Evenement");
                this.cd.setVisible(true);
                lsa.setVisible(false);
                cas.setVisible(false);
                lf.setVisible(false);
                cf.setVisible(false);
                lm.setVisible(true);
                lm.setText("Justificatif");
                motif.setVisible(true);
                this.revalidate();
                break;
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cteActionPerformed

    private void casActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casActionPerformed

        if (cte.getSelectedItem().toString().equals("MUTATION")) {
            this.evFonction.clear();
            this.cf.removeAllItems();
            int ind = this.cas.getSelectedIndex();
            Atelier a = (Atelier) this.evService.get(ind);

            try {
                Statement s = sogemee.SOGEMEE.c.getConn().createStatement();
                ResultSet r = s.executeQuery("select f.idFonction,f.libelleFonction,f.nbPersonne from fonction f join composer c where c.idFonction=f.idFonction and c.suppr=0 and f.suppr=0 and c.idServiceAtelier=" + a.getidAtelier());
                while (r.next()) {
                    Fonction f = new Fonction(r.getInt(1), r.getString(2), r.getInt(3));
                    this.evFonction.add(f);
                }
                for (int i = 0; i < this.evFonction.size(); i++) {
                    Fonction f = (Fonction) this.evFonction.get(i);
                    this.cf.addItem(f.getLibelleFonction());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_casActionPerformed

    private void datedebutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_datedebutKeyReleased
       
    }//GEN-LAST:event_datedebutKeyReleased

    private void datedebutMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datedebutMouseMoved

       
        // TODO add your handling code here:
    }//GEN-LAST:event_datedebutMouseMoved

    private void datedebutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_datedebutFocusLost

        JOptionPane.showMessageDialog(null,datedebut.getValue());
        // TODO add your handling code here:
    }//GEN-LAST:event_datedebutFocusLost

    private void precedentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precedentActionPerformed
tabAgents.setSelectedIndex(0);        // TODO add your handling code here:
    }//GEN-LAST:event_precedentActionPerformed

    private void suivantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suivantActionPerformed
tabAgents.setSelectedIndex(1);        // TODO add your handling code here:
    }//GEN-LAST:event_suivantActionPerformed

    private void tableFonctionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFonctionMouseClicked

        if(atelierRadio.isSelected()){
            Atelier a=(Atelier) ateliers.get(tableFonction.getSelectedRow());
            remplirTableFonctionsDansAtelier();
        labelAS.setText("Liste des Fonctions disponible dans l'atelier");
        ASTlib.setText(a.getlibelleAtelier());
        ASVlib.setText("Libelle Atelier/Service");
        }
        else{
            Fonction f=(Fonction) fonctions.get(tableFonction.getSelectedRow());
            remplirTableAgentsDansFonction();
        labelAS.setText("Liste des Agents a ce poste");
        ASTlib.setText(f.getLibelleFonction());
        ASVlib.setText("Libelle Fonction");
        ASVnbOcc.setText("Nombre max d'Occupant");
        ASSnbO.setValue(f.getNbPersonne());
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tableFonctionMouseClicked

    private void ASbuttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ASbuttonSaveActionPerformed

        if(ASTlib.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez entrer le libellé svp");
            return;
        }
        if(atelierRadio.isSelected()){
            Atelier a=new Atelier(ASTlib.getText());
            a.saveAtelier();
            remplirAteliers();
        }
        else{
            try {
                ASSnbO.commitEdit();
                Fonction f= new Fonction(ASTlib.getText(),(int)ASSnbO.getValue());
                if(!f.verificationSaisie()){
           JOptionPane.showMessageDialog(null, "Veuillez reprendre la saisie svp !");
        }
        else {
            try {
                if(f.verificationExistence()){
                    JOptionPane.showMessageDialog(null,"Une fonction identique existe déjà");
                    return;
                }
                else {
                    if(this.listef.isEmpty()){
                        JOptionPane.showMessageDialog(null,"Veuillez choisir au moin un service de destination !");
                      return;
                    }
                    f.saveFonction();
                
                }
                f.setIdFonction(f.recupererIdFonction());
                    for(int y=0;y<this.listef.size();y++){
                        Atelier a= (Atelier) this.listef.get(y);
                        Composer c=new Composer(a,f);
                        c.saveComposer();
                        
                    }
                    this.listef.clear();
                        v.clear();
                        this.listefinal.setListData(v);
                        listdepart.setVisible(false);
                        listefinal.setVisible(false);
                        btnAjouter.setVisible(false);
                        btnRetirer.setVisible(false);
            
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null,"Consulter le developpeur du logiciel");
            }
        }
                remplirFonction();
            } catch (ParseException ex) {
               JOptionPane.showMessageDialog(null,ex);
            }
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_ASbuttonSaveActionPerformed

    private void EvenementRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EvenementRadioActionPerformed

        remplirEvenement();
        tabAgents.setSelectedIndex(0);
         ETElabel.setText("Liste des 50 derniers evenements");
        // TODO add your handling code here:
    }//GEN-LAST:event_EvenementRadioActionPerformed

    private void typeEvenementRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeEvenementRadioActionPerformed

        ETElabel.setText("Liste des Types Evenement");
        tabAgents.setSelectedIndex(2);
        remplirTypeEvenement();
        // TODO add your handling code here:
    }//GEN-LAST:event_typeEvenementRadioActionPerformed

    private void rubriqueRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rubriqueRadioActionPerformed

btnRPimprimer.setEnabled(true);        
remplirRubrique();
RPVmontant.setEnabled(true);
RPVnom.setEnabled(true);
RPVprenom.setEnabled(true);
RPVrubrique.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_rubriqueRadioActionPerformed

    private void RPVnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RPVnomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RPVnomActionPerformed

    private void tableRPAgentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRPAgentsMouseClicked

        int ind=tableRPAgents.getSelectedRow();
        RPagent=(Agent) agents.get(ind);
       
            RPVnom.setText(RPagent.getNomAgent());
            RPVprenom.setText(RPagent.getPrenomAgent());
    if(RPrubr.getIdRubrique()!=0){       
            for(int i=0;i<RPAgentRubrique.size();i++){
                Agent_Rubrique ar=(Agent_Rubrique) RPAgentRubrique.get(i);
                if(ar.getAgent().getIdAgent()==RPagent.getIdAgent() && ar.getRubrique().getIdRubrique()==RPrubr.getIdRubrique()){
                 RPVmontant.setValue(ar.getMontantRubrique());   
                }
            }
    }
        
      
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRPAgentsMouseClicked

    private void tableRubriquesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRubriquesMouseClicked
int ind=tableRubriques.getSelectedRow();
        RPrubr=(Rubrique) rubriques.get(ind);

            RPVrubrique.setText(RPrubr.getLibelleRubrique());
           
        if(RPagent.getIdAgent()!=0){
            for(int i=0;i<RPAgentRubrique.size();i++){
                Agent_Rubrique ar=(Agent_Rubrique) RPAgentRubrique.get(i);
                if(ar.getAgent().getIdAgent()==RPagent.getIdAgent() && ar.getRubrique().getIdRubrique()==RPrubr.getIdRubrique()){
                 RPVmontant.setValue(ar.getMontantRubrique());   
                }
            }
        }
                // TODO add your handling code here:
    }//GEN-LAST:event_tableRubriquesMouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

        if(RPrubr.getIdRubrique()==0 || RPagent.getIdAgent()==0){
            JOptionPane.showMessageDialog(null,"Veuillez choisir l'agent et la rubrique à modifier");
            return;
        }
        else{
                try{
                    RPVmontant.commitEdit();
                }catch(Exception ex){
                    
                }
             for(int i=0;i<RPAgentRubrique.size();i++){
                Agent_Rubrique ar=(Agent_Rubrique) RPAgentRubrique.get(i);
                if(ar.getAgent().getIdAgent()==RPagent.getIdAgent() && ar.getRubrique().getIdRubrique()==RPrubr.getIdRubrique()){
                    if(RPrubr.getEtat()==1)
                    ar.setMontantRubrique((double)RPVmontant.getValue());
                    else
                    ar.setMontantRubrique(-(double)RPVmontant.getValue());    
                    ar.updateAgentRubrique();
                    break;
                    
                }
            }
    
        remplirAgentRubrique();

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void RPenregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RPenregistrerActionPerformed

        
if(rubriqueRadio.isSelected()){
    Rubrique b=new Rubrique(RPVrubrique.getText());
        if (!b.Verifisaisie())
        { 
            JOptionPane.showMessageDialog(null, "Veuillez reprendre la saisie");
        }
        else {
            try {
                if (b.verificationExistence()){
                    JOptionPane.showMessageDialog(null, "Existe deja");
                }
                else{
                    if(gain.isSelected()){
                        b.setEtat(1);
                    }
                    else{
                        b.setEtat(0);
                    }
                    b.saveRubrique();
                    b.setIdRubrique(b.rechercheId());
                    Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                       ResultSet r=s.executeQuery("Select idAgent from agent where suppr=0");
                while(r.next()){
                    Agent a=new Agent();
                    a.setIdAgent(r.getInt(1));
                    Agent_Rubrique ar=new Agent_Rubrique(a,b);
                    ar.setMontantRubrique(0);
                    ar.saveAgentRubrique();
                }
                remplirAgentRubrique();
                remplirRubrique();
                    RPVmontant.setEnabled(true);
                    RPVnom.setEnabled(true);
                    RPVprenom.setEnabled(true);
                    RPVrubrique.setText(null);
                    gain.setVisible(false);
                    perte.setVisible(false);
                }
                    
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex);
                
            }
        }
        
}        
else{
   
    SimpleDateFormat forma=new SimpleDateFormat("yyyy-MM-dd");
            try {
                datePaie.commitEdit();
            } catch (ParseException ex) {
                Logger.getLogger(Fen_Menu_GesPersonnel.class.getName()).log(Level.SEVERE, null, ex);
            }
    //JOptionPane.showMessageDialog(null,forma.format(datePaie.getValue()));    
    periodepaie p=new periodepaie(forma.format(datePaie.getValue()));
    if(p.rechercheID()==0){
    p.saveperiodepaie();    
    }
    
    p.setId_periodepaie(p.rechercheID());
    int ind1=tableRPAgents.getSelectedRow();
    Agent agent=(Agent) agents.get(ind1);
    for(int i=0;i<rubrSelec.size();i++){
        Rubrique rub=(Rubrique) rubrSelec.get(i);
        for(int y=0;y<RPAgentRubrique.size();y++){
            Agent_Rubrique ar=(Agent_Rubrique) RPAgentRubrique.get(y);
            if(ar.getAgent().getIdAgent()==agent.getIdAgent() && ar.getRubrique().getIdRubrique()==rub.getIdRubrique()){
                Payer paye=new Payer(p, ar);
                paye.savePayer();

   
            }
        }
    }
                         try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/gesperson/bulletinPaye.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            Integer id=agent.getIdAgent();
            Map param = new HashMap();
            param.put("periode", forma.parse(p.getLibelle_periodepaie()));
            param.put("agent",id);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
btnRPAjouter.setVisible(false);
            btnRPretirer.setVisible(false);
}
        
        // TODO add your handling code here:
    }//GEN-LAST:event_RPenregistrerActionPerformed

    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        int ind = this.listdepart.getSelectedIndex();
        if (!this.listef.contains(this.listeDeDepart.get(ind))) {
            this.listef.add(this.listeDeDepart.get(ind));
            v.addElement(remp[ind]);
        }

        this.listefinal.setListData(v);



        // TODO add your handling code here:
    }//GEN-LAST:event_btnAjouterActionPerformed

    private void btnRetirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirerActionPerformed

        int index = this.listefinal.getSelectedIndex();
        this.listef.remove(index);
        v.remove(index);
        this.listefinal.setListData(v);



        // TODO add your handling code here:
    }//GEN-LAST:event_btnRetirerActionPerformed

    private void btnRPAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPAjouterActionPerformed

        int ind=tableRubriques.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la rubrique a ajouter");
            return;
        }
        if(!rubrSelec.contains(rubriques.get(ind))){
            rubrSelec.add(rubriques.get(ind));
            Rubrique r=(Rubrique) rubriques.get(ind);
            rubrSelecLib.add(r.getLibelleRubrique());
        }
        else{
            JOptionPane.showMessageDialog(null, "Cette rubrique a déjà été sélectionnée");
        }
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Libelle Rubrique",rubrSelecLib.toArray());
        tableRPrubrSelec.setModel(mod);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRPAjouterActionPerformed

    private void btnRPretirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPretirerActionPerformed

        int ind=tableRPrubrSelec.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la rubrique a retirer");
            return;
        }
        rubrSelec.remove(ind);
        rubrSelecLib.remove(ind);
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Libelle Rubrique",rubrSelecLib.toArray());
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRPretirerActionPerformed

    private void payeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payeRadioActionPerformed

btnRPimprimer.setEnabled(false);        
remplirRubrique();
RPVmontant.setEnabled(false);
RPVnom.setEnabled(false);
RPVprenom.setEnabled(false);
RPVrubrique.setEnabled(false);
// TODO add your handling code here:
    }//GEN-LAST:event_payeRadioActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
       
        try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/gesperson/lagent.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            
            Map param = new HashMap();
            //param.put("periode", forma.parse(p.getLibelle_periodepaie()));
            //param.put("agent",id);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);
            

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnRPannulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPannulerActionPerformed

        if(rubriqueRadio.isSelected()){
            gain.setVisible(false);
            perte.setVisible(false);
            RPenregistrer.setEnabled(false);
        }else{
            jTabbedPane2.setEnabledAt(1,false);
            btnRPAjouter.setVisible(false);
            btnRPretirer.setVisible(false);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRPannulerActionPerformed

    private void btnRPimprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRPimprimerActionPerformed
                         try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/gesperson/agentRubrique.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            
            Map param = new HashMap();
            //param.put("periode", forma.parse(p.getLibelle_periodepaie()));
            //param.put("agent",id);

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);
            

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRPimprimerActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
  nomAgent.setText(null);
        this.prenomAgent.setText(null);
        this.rueAgent.setText(null);
        this.villeAgent.setText(null);
        this.telAgent.setText(null);
        service.setEnabled(true);
        fonction.setEnabled(true);
        this.bpAgent.setText(null);
        sexeAgent.setSelectedItem(null);
        dateEmbauche.setValue(new Date());
        ddnAgent.setValue(new Date());
      
jButton1.setEnabled(false);
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
if(EvenementRadio.isSelected()){
tabAgents.setSelectedIndex(0);
suivant.setVisible(false);
precedent.setVisible(false);
btnEvEnregistrer.setEnabled(false);
cas.setEnabled(false);
cte.setEnabled(false);
cf.setEnabled(false);
tabAgentsConcernes.setVisible(true);    
}
else{btnEvEnregistrer.setEnabled(false);
    tabAgents.setSelectedIndex(2);
    TETlib.setText(null);
}        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fen_Menu_GesPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fen_Menu_GesPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fen_Menu_GesPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fen_Menu_GesPersonnel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                new Fen_Menu_GesPersonnel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner ASSnbO;
    private javax.swing.JTextField ASTlib;
    private javax.swing.JLabel ASVlib;
    private javax.swing.JLabel ASVnbOcc;
    private javax.swing.JButton ASbuttonSave;
    private javax.swing.JLabel ETElabel;
    private javax.swing.ButtonGroup EvbuttonGroup;
    private javax.swing.JRadioButton EvenementRadio;
    private javax.swing.JSpinner RPVmontant;
    private javax.swing.JTextField RPVnom;
    private javax.swing.JTextField RPVprenom;
    private javax.swing.JTextField RPVrubrique;
    private javax.swing.ButtonGroup RPbouttonGroup;
    private javax.swing.JButton RPenregistrer;
    private javax.swing.JButton RPtt;
    private javax.swing.JTextField TETlib;
    private javax.swing.JRadioButton atelierRadio;
    private javax.swing.JTextField bpAgent;
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEvEnregistrer;
    private javax.swing.JButton btnEvModifier;
    private javax.swing.JButton btnEvNouveau;
    private javax.swing.JButton btnEvSupprimer;
    private javax.swing.JButton btnMod;
    private javax.swing.JButton btnModif;
    private javax.swing.JButton btnNouveau;
    private javax.swing.JButton btnRPAjouter;
    private javax.swing.JButton btnRPannuler;
    private javax.swing.JButton btnRPimprimer;
    private javax.swing.JButton btnRPretirer;
    private javax.swing.JButton btnRaf;
    private javax.swing.JButton btnRetirer;
    private javax.swing.JButton btnSuppr;
    private javax.swing.JButton btnSuppr1;
    private javax.swing.ButtonGroup buttonGroupAteliers;
    private javax.swing.ButtonGroup buttonGroupEvenement;
    private javax.swing.ButtonGroup buttonGroupEvenementsRecherche;
    private javax.swing.JComboBox cas;
    private javax.swing.JComboBox cd;
    private javax.swing.JComboBox cf;
    private javax.swing.JComboBox cte;
    private javax.swing.JSpinner dateEmbauche;
    private javax.swing.JSpinner datePaie;
    private javax.swing.JSpinner datedebut;
    private javax.swing.JSpinner datefin;
    private javax.swing.JSpinner ddnAgent;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox fonction;
    private javax.swing.JRadioButton fonctionRadio;
    private javax.swing.JRadioButton gain;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelAS;
    private javax.swing.JLabel ld;
    private javax.swing.JLabel lde;
    private javax.swing.JLabel ldfe;
    private javax.swing.JLabel lf;
    private javax.swing.JList listdepart;
    private javax.swing.JList listefinal;
    private javax.swing.JLabel lm;
    private javax.swing.JLabel lsa;
    private javax.swing.JLabel lte;
    private javax.swing.JTextArea motif;
    private javax.swing.JTextField nomAgent;
    private javax.swing.JRadioButton payeRadio;
    private javax.swing.JRadioButton perte;
    private javax.swing.JButton precedent;
    private javax.swing.JTextField prenomAgent;
    private javax.swing.JComboBox qualificationAgent;
    private javax.swing.ButtonGroup rubriquePayes;
    private javax.swing.JRadioButton rubriqueRadio;
    private javax.swing.ButtonGroup rubriquepayesRecherche;
    private javax.swing.JTextField rueAgent;
    private javax.swing.JComboBox service;
    private javax.swing.JComboBox sexeAgent;
    private javax.swing.JButton suivant;
    private javax.swing.JPanel tabAgent;
    private javax.swing.JTabbedPane tabAgents;
    private javax.swing.JPanel tabAgentsConcernes;
    private javax.swing.JPanel tabAtelier;
    private javax.swing.JPanel tabEvenement;
    private javax.swing.JPanel tabPaye;
    private javax.swing.JTable tableAS;
    private static javax.swing.JTable tableAgents;
    private javax.swing.JTable tableDepart;
    private javax.swing.JTable tableEvenement;
    private javax.swing.JTable tableFinale;
    private javax.swing.JTable tableFonction;
    private javax.swing.JTable tableRPAgents;
    private javax.swing.JTable tableRPrubrSelec;
    private javax.swing.JTable tableRubriques;
    private javax.swing.JTextField telAgent;
    private javax.swing.JRadioButton typeEvenementRadio;
    private javax.swing.JTextField villeAgent;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the evTypeEvenement
     */
    public ArrayList getEvTypeEvenement() {
        return evTypeEvenement;
    }

    /**
     * @return the evService
     */
    public ArrayList getEvService() {
        return evService;
    }

    /**
     * @return the evFonction
     */
    public ArrayList getEvFonction() {
        return evFonction;
    }

    /**
     * @return the evAgents
     */
    public ArrayList getEvAgents() {
        return evAgents;
    }

    /**
     * @return the evFonctions
     */
    public ArrayList getEvFonctions() {
        return evFonctions;
    }

    /**
     * @return the evNomA
     */
    public ArrayList getEvNomA() {
        return evNomA;
    }

    /**
     * @return the evPrenomA
     */
    public ArrayList getEvPrenomA() {
        return evPrenomA;
    }

    /**
     * @return the evFonctionA
     */
    public ArrayList getEvFonctionA() {
        return evFonctionA;
    }

    /**
     * @return the evNomD
     */
    public ArrayList getEvNomD() {
        return evNomD;
    }

    /**
     * @return the evPrenomD
     */
    public ArrayList getEvPrenomD() {
        return evPrenomD;
    }

    /**
     * @return the evFonctionD
     */
    public ArrayList getEvFonctionD() {
        return evFonctionD;
    }

    /**
     * @return the evAgentselectionés
     */
    public ArrayList getEvAgentselectionés() {
        return evAgentselectionés;
    }
}
