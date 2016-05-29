/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package approvisionnement;

import fabrication.CategorieModele;
import fabrication.Meuble;
import fabrication.Modele;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.xpath.operations.Mod;
import vente.FactureClient;
import vente.Menu_Vente;


/**
 *
 * @author el-diablo
 */
public class Fen_Approvisionnement extends javax.swing.JFrame {

    private ArrayList PtypeBois=new ArrayList();
    private ArrayList Pforme=new ArrayList();
    private ArrayList Plongueur=new ArrayList();
    private ArrayList Pplanche=new ArrayList();
    private ArrayList PTtypeBois=new ArrayList();
    private ArrayList PTforme=new ArrayList();
    private ArrayList PTlongueur=new ArrayList();
    private ArrayList TFLtypeBois=new ArrayList();
    private ArrayList TFLforme=new ArrayList();
    private ArrayList TFLongueur=new ArrayList();
    private ArrayList TFLTtypeBois=new ArrayList();
    private ArrayList TFLTforme=new ArrayList();
    private ArrayList TFLTLongueur=new ArrayList();
    private ArrayList Ffournisseur=new ArrayList();
    private  ArrayList Ccomfour=new ArrayList();
    private ArrayList Ccommandes=new ArrayList();
    private ArrayList CcomPlanche=new ArrayList();
    private ArrayList CcomPlancheFinal=new ArrayList();
    private ArrayList Llivraisons=new ArrayList();
    private ArrayList LlivrPlanche=new ArrayList();
    private ArrayList LlivrPlancheFinal=new ArrayList();
    private ArrayList LlivrComnonLiv=new ArrayList();
    private ArrayList ProCommandeProFormat=new ArrayList();
    private ArrayList ProCommandeSansProFormat=new ArrayList();
    private ArrayList ProPlanches=new ArrayList();
    private ArrayList ProPlancheFinal=new ArrayList();
    private ArrayList InvPlanches=new ArrayList();
    private ArrayList InvQtePlanches=new ArrayList();    
    private ArrayList InvInventaires=new ArrayList();
    private ArrayList InvMeubles=new ArrayList();
    private ArrayList InvQteMeubles=new ArrayList();
    private ArrayList InvPlanchesDet=new ArrayList();
    private ArrayList InvPlancheFinal=new ArrayList();
    private ArrayList LCFactures=new ArrayList();
    
    
    
    
    /**
     * Creates new form Fen_Approvisionnement
     */
    public Fen_Approvisionnement() {
        initComponents();
        PremplirCombo();
        PremplirPlanche();
        TFLremplirType();
        FremplirFournisseur();
        CremplirFournisseur();
        CremplirCommandes();
        LremplirtableLivr();
        ProremplirProFormat();
        remplirInventaire();
        remplirFacturesNnLiv();
 
    }

    
    @SuppressWarnings("unchecked")

    
    public void remplirFacturesNnLiv(){
         LCFactures.clear();
      DefaultTableModel mod=new DefaultTableModel();
      mod.addColumn("Num Facture"); 
      mod.addColumn("Date Facture");
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.idFactureClient,f.dateFacture,f.montantFacture,sum(r.montantReglement)  from reglement r,factureClient f where f.idFactureClient=r.idFactureClient and r.suppr=0 and f.suppr=0 and dejaLivrer=0 group by f.idFactureClient having sum(r.montantReglement)=f.montantFacture");
            while(r.next()){
           FactureClient fc=new FactureClient(r.getInt(1), r.getString(2),0);
           
            Object []row={fc.getIdFactureClient(),fc.getDateFacture()};
              mod.addRow(row);
           LCFactures.add(fc);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    LCtable.setModel(mod); 
    }
        public void remplirPlanchesDeter(){
       InvPlanchesDet.clear();
      DefaultTableModel mod=new DefaultTableModel();
      mod.addColumn("Type Bois"); 
      mod.addColumn("Forme");
      mod.addColumn("Longueur");
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur from planche p,typeBois t,formePlanche f,longeurPlanche l where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.idPlanche in(select idPlanche from livraison_planche where suppr=0) and p.suppr=0");
            while(r.next()){
           Type_Bois tb=new Type_Bois(r.getString(2));
           FormePlanche fp=new FormePlanche(r.getString(3));
           LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
           Planche p=new Planche(r.getInt(1), fp, tb, lp);
            Object []row={tb.getLibelleTypeBois(),fp.getLibeFormPlanche(),lp.getValeurLongueur()};
              mod.addRow(row);
           InvPlanchesDet.add(p);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    InvTablePlanchesDet.setModel(mod);    
    }
    
    
    
    
    public void remplirInventaire(){
       InvInventaires.clear();
      DefaultTableModel mod=new DefaultTableModel();
      mod.addColumn("Inventaire N*"); 
      mod.addColumn("Date Inventaire");
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from inventaire where suppr=0");
            while(r.next()){
             Inventaire inv=new Inventaire(r.getInt(1),r.getString(2));
            Object []row={inv.getIdInventaire(),inv.getDateInventaire()};
              mod.addRow(row);
           InvInventaires.add(inv);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    InvTableInv.setModel(mod);    
    }
    
    
    
     public void ProremplirSansProFormat(){
         ProCommandeSansProFormat.clear();
      DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Date Commande");
       mod.addColumn("Commande N*");
       mod.addColumn("Fournisseur");
        
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select c.idCommande,c.dateCommande,f.nomFournisseur from commande c,fournisseur f where c.idFournisseur=f.idFournisseur and c.suppr=0 and c.dateProFormat='1960-01-01'");
            while(r.next()){
             Commande com=new Commande(r.getInt(1),r.getString(2));
             Fournisseur f=new Fournisseur();
             f.setNomFournisseur(r.getString(3));
            Object []row={com.getDateCommande(),com.getIdCommande(),f.getNomFournisseur()};
              mod.addRow(row);
           ProCommandeSansProFormat.add(com);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
       ProTableCom.setModel(mod);
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }
    
    
    
 public void ProremplirProFormat(){
         ProCommandeProFormat.clear();
         ProTva.removeAllItems();
      DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Date ProFormat");
       mod.addColumn("Commande N*");
       mod.addColumn("Fournisseur");
        
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select c.idCommande,c.dateProFormat,t.valeurTva,f.nomFournisseur from commande c,fournisseur f,tva t where c.idFournisseur=f.idFournisseur and c.idTva=t.idTva and c.suppr=0 and c.dateProFormat<>'1960-01-01'");
            while(r.next()){
             Commande com=new Commande(r.getString(2));
             com.setIdCommande(r.getInt(1));
            Tva t=new Tva(r.getFloat(3));
            com.setTva(t);
             Fournisseur f=new Fournisseur();
             f.setNomFournisseur(r.getString(4));
           ProTva.addItem(t.getValeurTva());
             Object []row={com.getDateproformat(),com.getIdCommande(),f.getNomFournisseur()};
              mod.addRow(row);
           ProCommandeProFormat.add(com);
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
       ProTableProFormat.setModel(mod);
    }   
    
    public void remplirLCom(){
        LlivrComnonLiv.clear();
      DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Date Livraison");
       mod.addColumn("Commande N*");
       mod.addColumn("Fournisseur");
        
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select eric.*,sum(yes) from eric group by idCommande");
            while(r.next()){
             Commande com=new Commande(r.getInt(1),r.getString(2));
            Fournisseur f=new Fournisseur(r.getInt(3),r.getString(4));
            if(r.getDouble(7)>0){
             Object []row={com.getDateCommande(),com.getIdCommande(),f.getNomFournisseur()};
              mod.addRow(row);
            LlivrComnonLiv.add(com);
            }   
            
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        tableLCom.setModel(mod);
    }
    
    
    public void LremplirtableLivr(){
      DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Date Livraison");
       mod.addColumn("Commande N*");
       mod.addColumn("Fournisseur");
       //mod.addColumn("Quantité");
       Llivraisons.clear();
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select l.idLivraison,l.dateLivraison,l.idCommande,f.nomFournisseur,c.idCommande from livraison l,commande c,fournisseur f  where l.idCommande=c.idCommande and c.idFournisseur=f.idFournisseur and l.suppr=0");
           while(r.next()){
               Fournisseur f=new Fournisseur();
               f.setNomFournisseur(r.getString(4));
             Commande cm=new Commande(r.getInt(5),r.getString(2),f);
             Livraison liv=new Livraison(r.getInt(1),r.getString(2), cm);
             Object []row={cm.getDateCommande(),cm.getIdCommande(),f.getNomFournisseur()};
              mod.addRow(row);
              Llivraisons.add(liv);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       tableLivraison.setModel(mod);
    
    }
    
    
    public void CremplirtableNew(JTable t,ArrayList l){
      DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Type bois");
       mod.addColumn("Forme");
       mod.addColumn("Longueur");
       //mod.addColumn("Quantité");
      CcomPlanche.clear();
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,p.idPlanche from formePlanche f,typeBois t,longeurPlanche l,planche p  where  p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.suppr=0");
           while(r.next()){
             Type_Bois tb=new Type_Bois(r.getString(1));
             FormePlanche fp=new FormePlanche(r.getString(2));
             LongueurPlanche lp=new LongueurPlanche(r.getInt(3));
             Planche p=new Planche(r.getInt(4), fp, tb, lp, 0,0);
             l.add(p);
             Object []row={tb.getLibelleTypeBois(),fp.getLibeFormPlanche(),lp.getValeurLongueur()};
              mod.addRow(row);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       t.setModel(mod);
    
    }
    
    
      public void CremplirFournisseur(){
        try {
            Ccomfour.clear();
            cfournisseur.removeAllItems();
            Statement scombo= sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet rcombo;
            rcombo = scombo.executeQuery("select idFournisseur, nomFournisseur from fournisseur where suppr=0");
            
            while(rcombo.next()){
                Fournisseur nom=new Fournisseur(rcombo.getInt(1),rcombo.getString(2));
                Ccomfour.add(nom);
            }
            
            for (int i=0; i<Ccomfour.size();i++){
                Fournisseur f= (Fournisseur) Ccomfour.get(i);
                this.cfournisseur.addItem(f.getNomFournisseur());
            }
            }
            catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
            
        }
        
    }
    
    
    
    public void CremplirCommandes(){
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Date de la commande");
       mod.addColumn("Fournisseur");
       mod.addColumn("Type Commande");
      
       Ccommandes.clear();
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select c.idCommande,c.dateCommande,f.nomFournisseur,c.typeCommande from commande c,fournisseur f where c.idFournisseur=f.idFournisseur and c.suppr=0 ");
           while(r.next()){
              Fournisseur f=new Fournisseur();
              f.setNomFournisseur(r.getString(3));
              Commande c=new Commande(r.getInt(1),r.getString(2), f);
              c.setTypeCommande(r.getString(4));
              Ccommandes.add(c);
              Object []row={c.getDateCommande(),f.getNomFournisseur(),c.getTypeCommande()};
              mod.addRow(row);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       tableCommande.setModel(mod);
    }
    
    
    
    public void FremplirFournisseur(){
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Fournisseur");
       mod.addColumn("Ville");
       mod.addColumn("Téléphone");
       mod.addColumn("Rue");
       Ffournisseur.clear();
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select idFournisseur,nomFournisseur,telFournisseur,bpFournisseur,villeFournisseur,rueFournisseur from fournisseur where suppr=0");
           while(r.next()){
              Fournisseur f=new Fournisseur(r.getInt(1),r.getString(2),r.getString(4),r.getString(3),r.getString(5),r.getString(6));
              Ffournisseur.add(f);
              Object []row={f.getNomFournisseur(),f.getVilleFournisseur(),f.getTelFournisseur(),f.getRueFournisseur()};
              mod.addRow(row);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       tableFournisseur.setModel(mod);
    }
    
    public void TFLremplirType(){
        TFLTtypeBois.clear();
        TFLtypeBois.clear();
        TFLlabel.setText("Libelle du type bois");
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from typeBois where suppr=0");
            while( r.next()){
                Type_Bois tb=new Type_Bois(r.getInt(1),r.getString(2));
                TFLtypeBois.add(tb);
                TFLTtypeBois.add(tb.getLibelleTypeBois());
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Libelle du type bois", TFLTtypeBois.toArray());
        tableTFL.setModel(mod);
    }
    
    
    public void TFLremplirForme(){
        TFLTforme.clear();
        TFLforme.clear();
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from formePlanche where suppr=0");
            while( r.next()){
                FormePlanche fp=new FormePlanche(r.getInt(1),r.getString(2));
                TFLforme.add(fp);
                TFLTforme.add(fp.getLibeFormPlanche());
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        TFLlabel.setText("Libelle de la forme");
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Libelle de la forme de planche", TFLTforme.toArray());
        tableTFL.setModel(mod);
    }
    
    
    public void TFLremplirLongueur(){
        TFLTLongueur.clear();
        TFLongueur.clear();
        
        try {
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select * from longeurPlanche where suppr=0");
            while( r.next()){
                LongueurPlanche lp=new LongueurPlanche(r.getInt(1),r.getInt(2));
                TFLongueur.add(lp);
                TFLTLongueur.add(lp.getValeurLongueur());
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane, ex);
        }
        TFLlabel.setText("Valeur de la longueur");
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Valeur de la longueur", TFLTLongueur.toArray());
        tableTFL.setModel(mod);
    }
    
      public void PremplirCombo(){
       try{
           
           Pforme.clear();
           Plongueur.clear();
           PtypeBois.clear();
           Pcomboforme.removeAllItems();
           Pcombolongueur.removeAllItems();
           Pcombotypebois.removeAllItems();
         
        Statement su=sogemee.SOGEMEE.c.getConn().createStatement();
        Statement sa=sogemee.SOGEMEE.c.getConn().createStatement();
        Statement sz=sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet rtypebois =su.executeQuery("select idTypeBois, libelleTypeBois from typeBois");
        ResultSet rforme =sa.executeQuery("select idFormePlanche, libelleFormePlanche from formePlanche");
        ResultSet rlongueur =sz.executeQuery("select idLongeurPlanche, valeurLongeur from longeurPlanche");
        while (rtypebois.next()){     
        Type_Bois t=new Type_Bois(rtypebois.getInt(1),rtypebois.getString(2));
       
         //ajout de la lecture dans le tableau
         PtypeBois.add(t);
       
     }  
     
    while(rforme.next()){
         FormePlanche form=new FormePlanche(rforme.getInt(1),rforme.getString(2));
         Pforme.add(form);
        
     }
     
     while(rlongueur.next()){
         LongueurPlanche form=new LongueurPlanche(rlongueur.getInt(1),rlongueur.getInt(2));
         Plongueur.add(form);
        
     
     }
    
     }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
     }
     
     
     for(int i=0; i< PtypeBois.size(); i++){
        Type_Bois t=(Type_Bois) PtypeBois.get(i);
         Pcombotypebois.addItem(t.getLibelleTypeBois());
      
     }
     
     for(int i=0; i< Pforme.size(); i++){
         FormePlanche fo= (FormePlanche) Pforme.get(i);
         Pcomboforme.addItem(fo.getLibeFormPlanche());
     }
     
     for(int i=0; i< Plongueur.size(); i++){
         LongueurPlanche lon = (LongueurPlanche) Plongueur.get(i);
         Pcombolongueur.addItem(lon.getValeurLongueur());
     }   
    }
    
      public void PremplirPlanche(){
          PTlongueur.clear();
          PTforme.clear();
          PTtypeBois.clear();
          Pplanche.clear();
          try{
              Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
              ResultSet r=s.executeQuery("select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,p.coutLissage,p.coutTraitement,p.prixVentePlanche from planche p,typeBois t,formePlanche f,longeurPlanche l where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.suppr=0 and l.suppr=0 and t.suppr=0 and f.suppr=0");
              while(r.next()){
              FormePlanche fp=new FormePlanche(r.getString(3));
              PTforme.add(fp.getLibeFormPlanche());
              Type_Bois tb=new Type_Bois(r.getString(2));
              PTtypeBois.add(tb.getLibelleTypeBois());
              LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
              PTlongueur.add(lp.getValeurLongueur());
              Planche p=new Planche(r.getInt(1), fp, tb, lp);
              p.setCoutLissage(r.getInt(5));
              p.setCoutTraitement(r.getInt(6));
              p.setPrixVentePlanche(r.getDouble(7));
              Pplanche.add(p);
              }
          }catch(Exception ex){
              JOptionPane.showMessageDialog(null, ex);
          }
          DefaultTableModel mod=new DefaultTableModel();
          mod.addColumn("Type Bois",PTtypeBois.toArray());
          mod.addColumn("Forme",PTforme.toArray());
          mod.addColumn("Longueur",PTlongueur.toArray());
          tablePlanche.setModel(mod);
      }
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TFLbuttonGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PlanPanel = new javax.swing.JPanel();
        Pcombotypebois = new javax.swing.JComboBox();
        Pcomboforme = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Pcombolongueur = new javax.swing.JComboBox();
        PcoutTraitement = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        PcoutLissage = new javax.swing.JSpinner();
        PprixVente = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlanche = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        PBtnSave = new javax.swing.JButton();
        PBtnNew = new javax.swing.JButton();
        PBtnModif = new javax.swing.JButton();
        PBtnSuppr = new javax.swing.JButton();
        PBtnAnnuler = new javax.swing.JButton();
        TFLPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableTFL = new javax.swing.JTable();
        TFLlibelle = new javax.swing.JTextField();
        TFLlabel = new javax.swing.JLabel();
        TFLTypeBoisRadio = new javax.swing.JRadioButton();
        TFLformeRadio = new javax.swing.JRadioButton();
        TFLLongueurRadio = new javax.swing.JRadioButton();
        TFLBtnNew = new javax.swing.JButton();
        TFLBtnSave = new javax.swing.JButton();
        TFLBtnModif = new javax.swing.JButton();
        TFLBtnSuppr = new javax.swing.JButton();
        TFLBtnAnnuler = new javax.swing.JButton();
        FourPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableFournisseur = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        Fnomfour = new javax.swing.JTextField();
        Ftelfour = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        FBP = new javax.swing.JTextField();
        FVille = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        FRue = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        FBtnNew = new javax.swing.JButton();
        FBtnSave = new javax.swing.JButton();
        FBtnModif = new javax.swing.JButton();
        FBtnSuppr = new javax.swing.JButton();
        FBtnAnnuler = new javax.swing.JButton();
        ComPanel = new javax.swing.JPanel();
        cfournisseur = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCplanche = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableCfinal = new javax.swing.JTable();
        CqteCommande = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        CBtnAdd = new javax.swing.JButton();
        CBtnRemove = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableCommande = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        CBtnNew = new javax.swing.JButton();
        CBtnSave = new javax.swing.JButton();
        CBtnSuppr = new javax.swing.JButton();
        CctypeCommande = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        LivrPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableLivraison = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        LBtnNew = new javax.swing.JButton();
        LBtnSave = new javax.swing.JButton();
        LBtnSuppr = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        LBtnAdd = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        LtablePlancheLivr = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        LtablePlanchefinal = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        LQteLivr = new javax.swing.JSpinner();
        LBtnRemove = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableLCom = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        LdateLiv = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        ProPanel = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        ProTableProFormat = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        ProTablePlanches = new javax.swing.JTable();
        jScrollPane14 = new javax.swing.JScrollPane();
        ProTablePlancheFinal = new javax.swing.JTable();
        ProBtnAdd = new javax.swing.JButton();
        ProBtnRetirer = new javax.swing.JButton();
        ProPU = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        ProTableCom = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        ProDatePro = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        ProTva = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        InvTableInv = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        InvBtnNew = new javax.swing.JButton();
        InvBtnSuppr = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        InvTablePlanche = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        InvTableMeuble = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        InvTablePlanchesDet = new javax.swing.JTable();
        jScrollPane19 = new javax.swing.JScrollPane();
        InvTableFinal = new javax.swing.JTable();
        InvQteDeterior = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        InvBtnStart = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        LCtable = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion Approvisionnement");
        setBackground(new java.awt.Color(228, 255, 201));
        setResizable(false);

        PlanPanel.setBackground(new java.awt.Color(228, 255, 201));

        jLabel2.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.black);
        jLabel2.setText("Forme");

        jLabel1.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.black);
        jLabel1.setText("Type bois");

        jLabel3.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.black);
        jLabel3.setText("Longueur");

        PcoutTraitement.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(5)));

        jLabel4.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.black);
        jLabel4.setText("Coût de traitement");

        jLabel5.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.black);
        jLabel5.setText("Coût de lissage");

        PcoutLissage.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(5)));

        PprixVente.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(250.0d), Double.valueOf(250.0d), null, Double.valueOf(25.0d)));

        jLabel6.setFont(new java.awt.Font("Cooper Black", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.black);
        jLabel6.setText("Prix de vente");

        tablePlanche.setModel(new javax.swing.table.DefaultTableModel(
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
        tablePlanche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePlancheMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePlanche);

        jLabel7.setFont(new java.awt.Font("DialogInput", 1, 15)); // NOI18N
        jLabel7.setText("Liste des planches");

        PBtnSave.setText("Enregistrer");
        PBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBtnSaveActionPerformed(evt);
            }
        });

        PBtnNew.setText("Nouveau");
        PBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBtnNewActionPerformed(evt);
            }
        });

        PBtnModif.setText("Modifier");
        PBtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBtnModifActionPerformed(evt);
            }
        });

        PBtnSuppr.setText("Supprimer");
        PBtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBtnSupprActionPerformed(evt);
            }
        });

        PBtnAnnuler.setText("Annuler");
        PBtnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBtnAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PlanPanelLayout = new javax.swing.GroupLayout(PlanPanel);
        PlanPanel.setLayout(PlanPanelLayout);
        PlanPanelLayout.setHorizontalGroup(
            PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PBtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PBtnNew)
                    .addComponent(PBtnSuppr)
                    .addComponent(PBtnAnnuler)
                    .addComponent(PBtnModif))
                .addGap(18, 18, 18)
                .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PlanPanelLayout.createSequentialGroup()
                            .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addGap(1, 1, 1)))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Pcombotypebois, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Pcomboforme, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Pcombolongueur, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(PprixVente, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PcoutLissage, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(PcoutTraitement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(137, 137, 137))
        );

        PlanPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {PBtnAnnuler, PBtnModif, PBtnNew, PBtnSave, PBtnSuppr});

        PlanPanelLayout.setVerticalGroup(
            PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PlanPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(PlanPanelLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PlanPanelLayout.createSequentialGroup()
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(Pcombotypebois, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Pcomboforme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Pcombolongueur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PcoutTraitement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PcoutLissage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(PlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PprixVente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(PlanPanelLayout.createSequentialGroup()
                        .addComponent(PBtnNew)
                        .addGap(18, 18, 18)
                        .addComponent(PBtnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PBtnModif)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PBtnSuppr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PBtnAnnuler)))
                .addContainerGap(250, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Planche", PlanPanel);

        TFLPanel.setBackground(new java.awt.Color(228, 255, 201));

        tableTFL.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTFL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTFLMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableTFL);

        TFLlabel.setText("jLabel8");

        TFLbuttonGroup.add(TFLTypeBoisRadio);
        TFLTypeBoisRadio.setSelected(true);
        TFLTypeBoisRadio.setText("Type de Bois");
        TFLTypeBoisRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLTypeBoisRadioActionPerformed(evt);
            }
        });

        TFLbuttonGroup.add(TFLformeRadio);
        TFLformeRadio.setText("Forme");
        TFLformeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLformeRadioActionPerformed(evt);
            }
        });

        TFLbuttonGroup.add(TFLLongueurRadio);
        TFLLongueurRadio.setText("Longueur");
        TFLLongueurRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLLongueurRadioActionPerformed(evt);
            }
        });

        TFLBtnNew.setText("Nouveau");
        TFLBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLBtnNewActionPerformed(evt);
            }
        });

        TFLBtnSave.setText("Enregistrer");
        TFLBtnSave.setEnabled(false);
        TFLBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLBtnSaveActionPerformed(evt);
            }
        });

        TFLBtnModif.setText("Modifier");
        TFLBtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLBtnModifActionPerformed(evt);
            }
        });

        TFLBtnSuppr.setText("Supprimer");
        TFLBtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLBtnSupprActionPerformed(evt);
            }
        });

        TFLBtnAnnuler.setText("Annuler");
        TFLBtnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFLBtnAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout TFLPanelLayout = new javax.swing.GroupLayout(TFLPanel);
        TFLPanel.setLayout(TFLPanelLayout);
        TFLPanelLayout.setHorizontalGroup(
            TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TFLPanelLayout.createSequentialGroup()
                .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(TFLPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TFLTypeBoisRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TFLformeRadio))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TFLPanelLayout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(TFLlabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                        .addComponent(TFLlibelle, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136))
                    .addGroup(TFLPanelLayout.createSequentialGroup()
                        .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TFLPanelLayout.createSequentialGroup()
                                .addGap(208, 208, 208)
                                .addComponent(TFLLongueurRadio))
                            .addGroup(TFLPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TFLBtnSave)
                                    .addComponent(TFLBtnNew)
                                    .addComponent(TFLBtnModif)
                                    .addComponent(TFLBtnSuppr)
                                    .addComponent(TFLBtnAnnuler))))
                        .addContainerGap())))
        );

        TFLPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {TFLBtnAnnuler, TFLBtnModif, TFLBtnNew, TFLBtnSave, TFLBtnSuppr});

        TFLPanelLayout.setVerticalGroup(
            TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TFLPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TFLTypeBoisRadio)
                    .addComponent(TFLformeRadio)
                    .addComponent(TFLLongueurRadio))
                .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TFLPanelLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(TFLPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TFLlibelle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TFLlabel))
                        .addGap(18, 18, 18)
                        .addComponent(TFLBtnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFLBtnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFLBtnModif)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFLBtnSuppr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TFLBtnAnnuler))
                    .addGroup(TFLPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(155, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Type Bois||Forme||Longueur", TFLPanel);

        FourPanel.setBackground(new java.awt.Color(228, 255, 201));

        tableFournisseur.setModel(new javax.swing.table.DefaultTableModel(
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
        tableFournisseur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFournisseurMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableFournisseur);

        jLabel8.setText("Liste des fournisseurs");

        jLabel9.setText("Téléphone");

        jLabel10.setText("Nom du Fournisseur");

        jLabel11.setText("B.P");

        jLabel12.setText("Ville");

        jLabel13.setText("Rue");

        FBtnNew.setText("Nouveau");
        FBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBtnNewActionPerformed(evt);
            }
        });

        FBtnSave.setText("Enregistrer");
        FBtnSave.setEnabled(false);
        FBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBtnSaveActionPerformed(evt);
            }
        });

        FBtnModif.setText("Modifier");
        FBtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBtnModifActionPerformed(evt);
            }
        });

        FBtnSuppr.setText("Supprimer");
        FBtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBtnSupprActionPerformed(evt);
            }
        });

        FBtnAnnuler.setText("Annuler");
        FBtnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FBtnAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FourPanelLayout = new javax.swing.GroupLayout(FourPanel);
        FourPanel.setLayout(FourPanelLayout);
        FourPanelLayout.setHorizontalGroup(
            FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FourPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FourPanelLayout.createSequentialGroup()
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FBtnNew)
                            .addComponent(FBtnSave)
                            .addComponent(FBtnModif)
                            .addComponent(FBtnSuppr))
                        .addGap(84, 84, 84)
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FourPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                                .addComponent(Fnomfour, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FourPanelLayout.createSequentialGroup()
                                .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Ftelfour)
                                    .addComponent(FBP)
                                    .addComponent(FVille)
                                    .addComponent(FRue, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(54, 54, 54))
                    .addGroup(FourPanelLayout.createSequentialGroup()
                        .addComponent(FBtnAnnuler)
                        .addContainerGap())))
        );

        FourPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {FBtnAnnuler, FBtnModif, FBtnNew, FBtnSave, FBtnSuppr});

        FourPanelLayout.setVerticalGroup(
            FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FourPanelLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(FourPanelLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(FourPanelLayout.createSequentialGroup()
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(Fnomfour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(Ftelfour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FBP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FVille, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(FourPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FRue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(FourPanelLayout.createSequentialGroup()
                        .addComponent(FBtnNew)
                        .addGap(18, 18, 18)
                        .addComponent(FBtnSave)
                        .addGap(18, 18, 18)
                        .addComponent(FBtnModif)
                        .addGap(18, 18, 18)
                        .addComponent(FBtnSuppr)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FBtnAnnuler)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Fournisseur", FourPanel);

        ComPanel.setBackground(new java.awt.Color(228, 255, 201));

        jLabel14.setText("Adressée à");

        tableCplanche.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCplanche.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tableCplanche);

        tableCfinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type Bois", "Forme", "Longueur", "Quantité"
            }
        ));
        tableCfinal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tableCfinal);
        tableCfinal.setVisible(false);
        jScrollPane5.setVisible(false);
        this.revalidate();

        CqteCommande.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel15.setText("Quantité commandée");

        CBtnAdd.setText("Ajouter");
        CBtnAdd.setVisible(false);
        CBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtnAddActionPerformed(evt);
            }
        });

        CBtnRemove.setText("Retirer");
        CBtnRemove.setVisible(false);
        CBtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtnRemoveActionPerformed(evt);
            }
        });

        tableCommande.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCommande.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableCommande.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCommandeMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableCommande);

        jLabel16.setText("Liste des commandes");

        CBtnNew.setText("Nouveau");
        CBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtnNewActionPerformed(evt);
            }
        });

        CBtnSave.setText("Enregistrer");
        CBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBtnSaveActionPerformed(evt);
            }
        });

        CBtnSuppr.setText("Supprimer");

        CctypeCommande.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NORMALE", "EXPRESSE" }));

        jLabel17.setText("Type Commande");

        javax.swing.GroupLayout ComPanelLayout = new javax.swing.GroupLayout(ComPanel);
        ComPanel.setLayout(ComPanelLayout);
        ComPanelLayout.setHorizontalGroup(
            ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ComPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBtnNew)
                            .addComponent(CBtnSave)
                            .addComponent(CBtnSuppr)))
                    .addComponent(jLabel16))
                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ComPanelLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CqteCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ComPanelLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(CBtnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CBtnRemove)
                        .addGap(113, 113, 113))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ComPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ComPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel17)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(CctypeCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ComPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cfournisseur, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        ComPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CBtnNew, CBtnSave, CBtnSuppr});

        ComPanelLayout.setVerticalGroup(
            ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ComPanelLayout.createSequentialGroup()
                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ComPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cfournisseur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ComPanelLayout.createSequentialGroup()
                                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CctypeCommande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(CqteCommande, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(ComPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CBtnAdd)
                                    .addComponent(CBtnRemove))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ComPanelLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(CBtnNew)
                        .addGap(18, 18, 18)
                        .addComponent(CBtnSave)
                        .addGap(18, 18, 18)
                        .addComponent(CBtnSuppr)))
                .addGap(0, 105, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Commande", ComPanel);

        LivrPanel.setBackground(new java.awt.Color(228, 255, 201));

        tableLivraison.setModel(new javax.swing.table.DefaultTableModel(
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
        tableLivraison.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLivraisonMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tableLivraison);

        jLabel18.setText("Liste des Livraisons");

        LBtnNew.setText("Nouveau");
        LBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LBtnNewActionPerformed(evt);
            }
        });

        LBtnSave.setText("Enregistrer");
        LBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LBtnSaveActionPerformed(evt);
            }
        });

        LBtnSuppr.setText("Supprimer");
        LBtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LBtnSupprActionPerformed(evt);
            }
        });

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LBtnAdd.setText("Ajouter");
        LBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LBtnAddActionPerformed(evt);
            }
        });
        LBtnAdd.setVisible(false);

        LtablePlancheLivr.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane9.setViewportView(LtablePlancheLivr);

        LtablePlanchefinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type Bois", "Forme", "Longueur", "Quantité"
            }
        ));
        jScrollPane8.setViewportView(LtablePlanchefinal);
        jScrollPane8.setVisible(false);
        LtablePlanchefinal.setVisible(false);

        jLabel19.setText("Planches Livrées");

        jLabel20.setText("Quantité Livrée");

        LQteLivr.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(0), null, Integer.valueOf(1)));

        LBtnRemove.setText("Retirer");
        LBtnRemove.setVisible(false);
        LBtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LBtnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(208, 208, 208)
                                    .addComponent(LQteLivr, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(LBtnAdd)
                        .addGap(179, 179, 179)
                        .addComponent(LBtnRemove))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel19)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LQteLivr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LBtnAdd)
                    .addComponent(LBtnRemove))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Planches livrées", jPanel1);

        jScrollPane10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane10MouseClicked(evt);
            }
        });

        tableLCom.setModel(new javax.swing.table.DefaultTableModel(
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
        tableLCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLComMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tableLCom);

        jButton1.setText("Suivant");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        LdateLiv.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        jLabel21.setText("Date de la livraison");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(400, 400, 400)
                            .addComponent(jButton1))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LdateLiv, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LdateLiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(23, 23, 23))
        );

        jTabbedPane2.addTab("Choisir commande", jPanel3);
        jTabbedPane2.setEnabledAt(1,false);

        javax.swing.GroupLayout LivrPanelLayout = new javax.swing.GroupLayout(LivrPanel);
        LivrPanel.setLayout(LivrPanelLayout);
        LivrPanelLayout.setHorizontalGroup(
            LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LivrPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LivrPanelLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addContainerGap())
                    .addGroup(LivrPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LBtnSuppr)
                            .addComponent(LBtnNew)
                            .addComponent(LBtnSave))
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane2))))
        );

        LivrPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {LBtnNew, LBtnSave, LBtnSuppr});

        LivrPanelLayout.setVerticalGroup(
            LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LivrPanelLayout.createSequentialGroup()
                .addGroup(LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LivrPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(LivrPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7)
                            .addComponent(jTabbedPane2)))
                    .addGroup(LivrPanelLayout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(LBtnNew)
                        .addGap(18, 18, 18)
                        .addComponent(LBtnSave)
                        .addGap(18, 18, 18)
                        .addComponent(LBtnSuppr)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Livraison", LivrPanel);

        ProPanel.setBackground(new java.awt.Color(228, 255, 201));

        ProTableProFormat.setModel(new javax.swing.table.DefaultTableModel(
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
        ProTableProFormat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProTableProFormatMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(ProTableProFormat);

        jLabel22.setText("Liste des commandes à proFormat");

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        ProTablePlanches.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane13.setViewportView(ProTablePlanches);

        ProTablePlancheFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane14.setViewportView(ProTablePlancheFinal);

        ProBtnAdd.setText("Ajouter");
        ProBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProBtnAddActionPerformed(evt);
            }
        });

        ProBtnRetirer.setText("Retirer");
        ProBtnRetirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProBtnRetirerActionPerformed(evt);
            }
        });

        ProPU.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(25.0d), Double.valueOf(25.0d), null, Double.valueOf(25.0d)));

        jLabel23.setText("Prix Unitaire");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(99, 99, 99)
                                .addComponent(ProPU, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)))))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(ProBtnAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ProBtnRetirer)
                .addGap(81, 81, 81))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ProBtnAdd)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(ProBtnRetirer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Liste des planches et des prix", jPanel5);

        ProTableCom.setModel(new javax.swing.table.DefaultTableModel(
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
        ProTableCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProTableComMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(ProTableCom);

        jButton6.setText("Suivant");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        ProDatePro.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        jLabel25.setText("Date de la ProFormat");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(18, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ProDatePro, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProDatePro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Liste des commandes sans proFormat", jPanel6);
        jTabbedPane3.setEnabledAt(1,false);

        jButton2.setText("Nouveau");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Enregistrer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Supprimer");

        jButton5.setText("Annuler");

        ProTva.setBackground(new java.awt.Color(210, 73, 73));
        ProTva.setEditable(true);
        ProTva.setForeground(new java.awt.Color(237, 46, 46));

        jLabel24.setText("Tva");

        javax.swing.GroupLayout ProPanelLayout = new javax.swing.GroupLayout(ProPanel);
        ProPanel.setLayout(ProPanelLayout);
        ProPanelLayout.setHorizontalGroup(
            ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(85, 85, 85)
                        .addComponent(jTabbedPane3))
                    .addGroup(ProPanelLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addGap(57, 57, 57)
                        .addComponent(ProTva, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        ProPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4, jButton5});

        ProPanelLayout.setVerticalGroup(
            ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProPanelLayout.createSequentialGroup()
                .addGroup(ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProPanelLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(ProTva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(ProPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane11)
                            .addComponent(jTabbedPane3)))
                    .addGroup(ProPanelLayout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pro Format", ProPanel);

        jPanel2.setBackground(new java.awt.Color(228, 255, 201));

        InvTableInv.setModel(new javax.swing.table.DefaultTableModel(
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
        InvTableInv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InvTableInvMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(InvTableInv);

        jLabel26.setText("Liste des inventaires");

        InvBtnNew.setText("Nouveau");
        InvBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InvBtnNewActionPerformed(evt);
            }
        });

        InvBtnSuppr.setText("Supprimer");

        InvTablePlanche.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane16.setViewportView(InvTablePlanche);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Planches ", jPanel4);

        InvTableMeuble.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane17.setViewportView(InvTableMeuble);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Meubles ", jPanel7);

        InvTablePlanchesDet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane18.setViewportView(InvTablePlanchesDet);

        InvTableFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type Bois", "Forme", "Longueur", "Quantité"
            }
        ));
        jScrollPane19.setViewportView(InvTableFinal);

        InvQteDeterior.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel27.setText("Quantité détériorée");

        jButton7.setText("Suivant");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Ajouter");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Retirer");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addComponent(jButton8)
                            .addGap(128, 128, 128)
                            .addComponent(jButton9))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel27)
                            .addGap(142, 142, 142)
                            .addComponent(InvQteDeterior, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InvQteDeterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane4.addTab("Planches détériorées", jPanel8);
        jTabbedPane4.setEnabledAt(2,false);

        InvBtnStart.setText("Démarrer l'inventaire");
        InvBtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InvBtnStartActionPerformed(evt);
            }
        });
        InvBtnStart.setVisible(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InvBtnNew)
                            .addComponent(InvBtnSuppr))
                        .addGap(99, 99, 99)
                        .addComponent(jTabbedPane4)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(InvBtnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(195, 195, 195))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {InvBtnNew, InvBtnSuppr});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel26))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(InvBtnStart)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(InvBtnNew)
                        .addGap(18, 18, 18)
                        .addComponent(InvBtnSuppr)))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventaire", jPanel2);

        LCtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane20.setViewportView(LCtable);

        jButton10.setText("Actualiser");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Livrer");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel29.setText("Factures non livrées mais totalement payées");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10)
                            .addComponent(jButton11)))
                    .addComponent(jLabel29))
                .addContainerGap(640, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton11});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jButton10)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11)))
                .addContainerGap(200, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Livraison Client", jPanel9);

        jLabel28.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel28.setText("APPROVISIONNEMENT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(jLabel28)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(22, 22, 22)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBtnSaveActionPerformed
        int indTB = Pcombotypebois.getSelectedIndex();
        int coutT = (int) PcoutTraitement.getValue();
        int coutL = (int) PcoutLissage.getValue();
        if (coutT <= 0 || coutL <= 0) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer un prix valide svp");
            return;
        }
        Type_Bois tb = (Type_Bois) PtypeBois.get(indTB);
        int indTf = Pcomboforme.getSelectedIndex();
        FormePlanche tf = (FormePlanche) Pforme.get(indTf);
        int indTl = Pcombolongueur.getSelectedIndex();
        LongueurPlanche tl = (LongueurPlanche) Plongueur.get(indTl);

        Planche p = new Planche(tf, tb, tl, coutT, coutL);
        if (p.verifexistance()) {
            JOptionPane.showMessageDialog(null, "Cette planche a déjà été enregistrée");
            return;
        }
        p.setPrixVentePlanche((double) PprixVente.getValue());
        p.savePlanche();
        PBtnSave.setEnabled(false);
        PBtnAnnuler.setEnabled(false);
        PBtnModif.setEnabled(true);
        PBtnNew.setEnabled(true);
        PBtnSuppr.setEnabled(true);
        
        PremplirCombo();
        PremplirPlanche();
    }//GEN-LAST:event_PBtnSaveActionPerformed

    private void PBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBtnNewActionPerformed

        PBtnModif.setEnabled(false);
        PBtnNew.setEnabled(false);
        PBtnSuppr.setEnabled(false);
        PBtnSave.setEnabled(true);
        PBtnAnnuler.setEnabled(true);
        PremplirCombo();        // TODO add your handling code here:
    }//GEN-LAST:event_PBtnNewActionPerformed

    private void tablePlancheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlancheMouseClicked
int ind=tablePlanche.getSelectedRow();
Planche p=(Planche) Pplanche.get(ind);
Pcombotypebois.setSelectedItem(p.getTypebois().getLibelleTypeBois());
Pcomboforme.setSelectedItem(p.getForme().getLibeFormPlanche());
Pcombolongueur.setSelectedItem(p.getLongueur().getValeurLongueur());
PprixVente.setValue(p.getPrixVentePlanche());
PcoutLissage.setValue(p.getCoutLissage());
PcoutTraitement.setValue(p.getCoutTraitement());
// TODO add your handling code here:
    }//GEN-LAST:event_tablePlancheMouseClicked

    private void PBtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBtnModifActionPerformed
int ind=tablePlanche.getSelectedRow();
if(ind==-1){
JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à modifier svp");
return;
}
try{
    PcoutTraitement.commitEdit();
    PcoutLissage.commitEdit();
    PprixVente.commitEdit();
}catch(Exception ex){
    JOptionPane.showMessageDialog(rootPane, ex);
}
Planche p=(Planche) Pplanche.get(ind);
int indT=Pcombotypebois.getSelectedIndex();
int indL=Pcombolongueur.getSelectedIndex();
int indF=Pcomboforme.getSelectedIndex();

Type_Bois tb=(Type_Bois) PtypeBois.get(indT);
FormePlanche fp=(FormePlanche) Pforme.get(indF);
LongueurPlanche lp=(LongueurPlanche) Plongueur.get(indL);

p.setPrixVentePlanche((double)PprixVente.getValue());
p.setCoutLissage((int) PcoutLissage.getValue());
p.setCoutTraitement((int)PcoutTraitement.getValue());

if(JOptionPane.showConfirmDialog(null, "Êtes Vous sûres des modifications apportées apportées à "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur())==0){
p.updatePlanche(fp, tb, lp);
PremplirPlanche();
}

// TODO add your handling code here:
    }//GEN-LAST:event_PBtnModifActionPerformed

    private void PBtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBtnSupprActionPerformed

        int ind=tablePlanche.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à supprimer SVP");
            return;
        }
        Planche p=(Planche) Pplanche.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes Vous sûres de vouloir supprimer "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur())==0){
            p.deletePlanche();
            PremplirPlanche();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_PBtnSupprActionPerformed

    private void PBtnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBtnAnnulerActionPerformed

        PBtnSave.setEnabled(false);
        PBtnAnnuler.setEnabled(false);
        PBtnModif.setEnabled(true);
        PBtnNew.setEnabled(true);
        PBtnSuppr.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_PBtnAnnulerActionPerformed

    private void TFLTypeBoisRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLTypeBoisRadioActionPerformed
TFLremplirType();        // TODO add your handling code here:
    }//GEN-LAST:event_TFLTypeBoisRadioActionPerformed

    private void TFLformeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLformeRadioActionPerformed
TFLremplirForme();        // TODO add your handling code here:
    }//GEN-LAST:event_TFLformeRadioActionPerformed

    private void TFLLongueurRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLLongueurRadioActionPerformed
TFLremplirLongueur();        // TODO add your handling code here:
    }//GEN-LAST:event_TFLLongueurRadioActionPerformed

    private void tableTFLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTFLMouseClicked

        int ind=tableTFL.getSelectedRow();
        if(TFLTypeBoisRadio.isSelected()){
            TFLlibelle.setText(TFLTtypeBois.get(ind).toString());
            return;
        }
        if(TFLformeRadio.isSelected()){
            TFLlibelle.setText(TFLTforme.get(ind).toString());
            return;
        }
        if(TFLLongueurRadio.isSelected()){
            TFLlibelle.setText(TFLTLongueur.get(ind).toString());
            return;
        }
      // TODO add your handling code here:
    }//GEN-LAST:event_tableTFLMouseClicked

    private void TFLBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLBtnNewActionPerformed

        TFLlibelle.setText(null);
        TFLBtnNew.setEnabled(false);
        TFLBtnModif.setEnabled(false);
        TFLBtnSuppr.setEnabled(false);
        TFLBtnSave.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_TFLBtnNewActionPerformed

    private void TFLBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLBtnSaveActionPerformed

        int ind=tableTFL.getSelectedRow();
        if(TFLTypeBoisRadio.isSelected()){
              Type_Bois typ =new Type_Bois(TFLlibelle.getText());
       if (typ.verifsaisi()){
            try {
                if (!typ.verifexit()){
                typ.saveType();
                TFLremplirType();
                }
                else{
                    JOptionPane.showMessageDialog(rootPane,"Cet type de bois existe deja!!!");
                }
                    
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
       }
       else
           JOptionPane.showMessageDialog(null,"Entrer un type de bois");

            return;
        }
        
        
        if(TFLformeRadio.isSelected()){
                   FormePlanche form = new FormePlanche(TFLlibelle.getText());
       if (form.verificationSaisi()){
            try {
                if(!form.verifexist()){
                 form.saveForme();
                    TFLremplirForme();  
                }
            else{
           JOptionPane.showMessageDialog(rootPane,"La forme existance deje salaut!!");
       }
                    
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, ex);
            }
           }
       else{
           JOptionPane.showMessageDialog(rootPane,"Entrer une forme de planche");
       return;
       }
           
 }
        
        
        
        if(TFLLongueurRadio.isSelected()){
                   
        try{
            Integer.parseInt(TFLlibelle.getText());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Veuillez entrer une longueur valide");
            return;
        }
        LongueurPlanche lon =new LongueurPlanche(Integer.parseInt(TFLlibelle.getText()));
        if (lon.verifSaisi()){
            try {
                if(!lon.verifexit()) {
               lon.saveLongueur();
               TFLremplirLongueur();
                } 
             
               else{
                    JOptionPane.showMessageDialog(null,"Cette longueur existe deja");
        }
            
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,ex);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Veuillez entrer une valeur correcte");
        return;
        }
            

            
        }
        
        PremplirCombo();
        
        TFLBtnNew.setEnabled(true);
        TFLBtnModif.setEnabled(true);
        TFLBtnSuppr.setEnabled(true);
        TFLBtnSave.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_TFLBtnSaveActionPerformed

    private void TFLBtnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLBtnAnnulerActionPerformed
 TFLBtnNew.setEnabled(true);
        TFLBtnModif.setEnabled(true);
        TFLBtnSuppr.setEnabled(true);
        TFLBtnSave.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_TFLBtnAnnulerActionPerformed

    private void TFLBtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLBtnModifActionPerformed

        int ind=tableTFL.getSelectedRow();
        if(TFLTypeBoisRadio.isSelected()){
            TFLlibelle.setText(TFLTtypeBois.get(ind).toString());
            return;
        }
        if(TFLformeRadio.isSelected()){
            TFLlibelle.setText(TFLTforme.get(ind).toString());
            return;
        }
        if(TFLLongueurRadio.isSelected()){
            TFLlibelle.setText(TFLTLongueur.get(ind).toString());
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TFLBtnModifActionPerformed

    private void TFLBtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFLBtnSupprActionPerformed

        int ind=tableTFL.getSelectedRow();
        if(TFLTypeBoisRadio.isSelected()){
            TFLlibelle.setText(TFLTtypeBois.get(ind).toString());
            return;
        }
        if(TFLformeRadio.isSelected()){
            TFLlibelle.setText(TFLTforme.get(ind).toString());
            return;
        }
        if(TFLLongueurRadio.isSelected()){
            TFLlibelle.setText(TFLTLongueur.get(ind).toString());
            return;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TFLBtnSupprActionPerformed

    private void tableFournisseurMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFournisseurMouseClicked

        int ind=tableFournisseur.getSelectedRow();
        Fournisseur f=(Fournisseur) Ffournisseur.get(ind);
        Fnomfour.setText(f.getNomFournisseur());
        Ftelfour.setText(f.getTelFournisseur());
        FVille.setText(f.getVilleFournisseur());
        FRue.setText(f.getRueFournisseur());
        FBP.setText(f.getBpFournisseur());
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableFournisseurMouseClicked

    private void FBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBtnNewActionPerformed

        Fnomfour.setText(null);
        Ftelfour.setText(null);
        FVille.setText(null);
        FBP.setText(null);
        FRue.setText(null);
        FBtnNew.setEnabled(false);
        FBtnModif.setEnabled(false);
        FBtnSuppr.setEnabled(false);
        FBtnSave.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_FBtnNewActionPerformed

    private void FBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBtnSaveActionPerformed

        if(Fnomfour.getText().isEmpty() || Ftelfour.getText().isEmpty() || FVille.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez remplir le nom, le téléphone et la ville du fournisseur SVP");
            return;
        }
        
        Fournisseur f=new Fournisseur(Fnomfour.getText(),FBP.getText(),Ftelfour.getText(),FVille.getText(), FRue.getText());
        try {
            if(f.verifexistance()){
                JOptionPane.showMessageDialog(null, "Ce fournisseur est déjà enregistré");
                return;
            }
            else{
                f.saveFournisseur();
                FremplirFournisseur();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
        FBtnNew.setEnabled(true);
        FBtnModif.setEnabled(true);
        FBtnSuppr.setEnabled(true);
        FBtnSave.setEnabled(false);// TODO add your handling code here:
    }//GEN-LAST:event_FBtnSaveActionPerformed

    private void FBtnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBtnAnnulerActionPerformed
        Fnomfour.setText(null);
        Ftelfour.setText(null);
        FVille.setText(null);
        FBP.setText(null);
        FRue.setText(null);
        
        FBtnNew.setEnabled(true);
        FBtnModif.setEnabled(true);
        FBtnSuppr.setEnabled(true);
        FBtnSave.setEnabled(false);        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_FBtnAnnulerActionPerformed

    private void FBtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBtnModifActionPerformed

        int ind=tableFournisseur.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le fournisseur à modifier");
            return;
        }
        if(Fnomfour.getText().isEmpty() || Ftelfour.getText().isEmpty() || FVille.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez remplir le nom, le téléphone et la ville du fournisseur SVP");
            return;
        }
        Fournisseur f=(Fournisseur) Ffournisseur.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres des modifications apportées à "+f.getNomFournisseur())==0){
         f.setBpFournisseur(FBP.getText());
        f.setNomFournisseur(Fnomfour.getText());
        f.setRueFournisseur(FRue.getText());
        f.setTelFournisseur(Ftelfour.getText());
        f.setVilleFournisseur(FVille.getText());
        f.updateFournisseur();
        FremplirFournisseur();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_FBtnModifActionPerformed

    private void FBtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FBtnSupprActionPerformed

        int ind=tableFournisseur.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le fournisseur à supprimer");
            return;
        }
        
        Fournisseur f=(Fournisseur) Ffournisseur.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer "+f.getNomFournisseur())==0){
        
        f.deleteFournisseur();
        FremplirFournisseur();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FBtnSupprActionPerformed

    private void tableCommandeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCommandeMouseClicked

    int ind=tableCommande.getSelectedRow();
    Commande comm=(Commande) Ccommandes.get(ind);
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Type bois");
       mod.addColumn("Forme");
       mod.addColumn("Longueur");
       mod.addColumn("Quantité");
      
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,i.qteCommande from commande c,formePlanche f,typeBois t,longeurPlanche l,inclure i,planche p  where c.idCommande=i.idCommande and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.idPlanche=i.idPlanche and c.idCommande="+comm.getIdCommande());
           while(r.next()){
             Type_Bois tb=new Type_Bois(r.getString(1));
             FormePlanche fp=new FormePlanche(r.getString(2));
             LongueurPlanche lp=new LongueurPlanche(r.getInt(3));
               Object []row={tb.getLibelleTypeBois(),fp.getLibeFormPlanche(),lp.getValeurLongueur(),r.getInt(4)};
              mod.addRow(row);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       tableCplanche.setModel(mod);
    
            // TODO add your handling code here:
    }//GEN-LAST:event_tableCommandeMouseClicked

    private void CBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtnNewActionPerformed

        
        CremplirtableNew(tableCplanche,CcomPlanche);
        CBtnAdd.setVisible(true);
        CBtnRemove.setVisible(true);
        tableCfinal.setVisible(true);
        jScrollPane5.setVisible(true);
        this.revalidate();
        // TODO add your handling code here:
    }//GEN-LAST:event_CBtnNewActionPerformed

    private void CBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtnAddActionPerformed

        int ind=tableCplanche.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à ajouter svp");
            return;
        }
        Planche p=(Planche) CcomPlanche.get(ind);
        if(CcomPlancheFinal.contains(p)){
            JOptionPane.showMessageDialog(null,"Cette planche a déjà été sélectionée");
            return;
        }
        try {
            CqteCommande.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Fen_Approvisionnement.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel mod=(DefaultTableModel) tableCfinal.getModel();
        
       Object [] row={p.getTypebois().getLibelleTypeBois(),p.getForme().getLibeFormPlanche(),p.getLongueur().getValeurLongueur(),CqteCommande.getValue()};
       mod.addRow(row);
       CcomPlancheFinal.add(p);
        // TODO add your handling code here:
    }//GEN-LAST:event_CBtnAddActionPerformed

    private void CBtnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtnRemoveActionPerformed

        int ind=tableCfinal.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à retirer svp");
            return;
        }
        Planche p=(Planche) CcomPlancheFinal.get(ind);
        DefaultTableModel mod=(DefaultTableModel) tableCfinal.getModel();
        
       mod.removeRow(ind);
       CcomPlancheFinal.remove(ind);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_CBtnRemoveActionPerformed

    private void CBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBtnSaveActionPerformed
if(this.CcomPlancheFinal.size()!=0){
        Fournisseur fournisseur=(Fournisseur) this.Ccomfour.get(this.cfournisseur.getSelectedIndex());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        
        Commande commande =new Commande(format.format(date), fournisseur);
        commande.setTypeCommande(CctypeCommande.getSelectedItem().toString());
        commande.saveCommande();
        commande.setIdCommande(commande.rechercheID());
        
        DefaultTableModel mod=(DefaultTableModel) tableCfinal.getModel();
        for (int i=0; i<CcomPlancheFinal.size();i++){
                Planche p= (Planche) CcomPlancheFinal.get(i);
                int quantite =(int) mod.getValueAt(i, 3);
                int pu=0;
                Inclure inclure =new Inclure(p, commande,quantite,pu);
                inclure.saveInclure();
        }
       JOptionPane.showMessageDialog(null,"Enregistrement de la commande reussie");
        CremplirCommandes();
        
        } else{
            JOptionPane.showMessageDialog(null,"Veuillez remplir la table des planches commandées svp!");
        }
                // TODO add your handling code here:
    }//GEN-LAST:event_CBtnSaveActionPerformed

    private void tableLivraisonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLivraisonMouseClicked

    int ind=tableLivraison.getSelectedRow();
    Livraison liv=(Livraison) Llivraisons.get(ind);
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Type bois");
       mod.addColumn("Forme");
       mod.addColumn("Longueur");
       mod.addColumn("Quantité");
      
       try{
           Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,i.qteLivraison from livraison c,formePlanche f,typeBois t,longeurPlanche l,livraison_planche i,planche p  where c.idLivraison=i.idLivraison and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.idPlanche=i.idPlanche and c.idLivraison="+liv.getIdLivraison());
           while(r.next()){
             Type_Bois tb=new Type_Bois(r.getString(1));
             FormePlanche fp=new FormePlanche(r.getString(2));
             LongueurPlanche lp=new LongueurPlanche(r.getInt(3));
               Object []row={tb.getLibelleTypeBois(),fp.getLibeFormPlanche(),lp.getValeurLongueur(),r.getInt(4)};
              mod.addRow(row);
           }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
       }
       
       LtablePlancheLivr.setModel(mod);
            // TODO add your handling code here:
    }//GEN-LAST:event_tableLivraisonMouseClicked

    private void LBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LBtnNewActionPerformed

  

        jTabbedPane2.setEnabledAt(1,true);
        jTabbedPane2.setSelectedIndex(1);
        remplirLCom();
        LBtnAdd.setVisible(true);
        LBtnRemove.setVisible(true);
        LtablePlanchefinal.setVisible(true);
        jScrollPane8.setVisible(true);
        this.revalidate();        // TODO add your handling code here:
    }//GEN-LAST:event_LBtnNewActionPerformed

    private void LBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LBtnAddActionPerformed

        int ind = LtablePlancheLivr.getSelectedRow();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir la planche à ajouter svp");
            return;
        }
        Planche p = (Planche) LlivrPlanche.get(ind);
        if (LlivrPlancheFinal.contains(p)) {
            JOptionPane.showMessageDialog(null, "Cette planche a déjà été sélectionée");
            return;
        }
        try {
            LQteLivr.commitEdit();
            int LQte=(int) LQteLivr.getValue();
            int qteRe=(int) LtablePlancheLivr.getValueAt(ind, 3);
            if(LQte>qteRe){
                JOptionPane.showMessageDialog(null,"La quantité restante est inférieur à la quantité entrée");
                return;
            }
        } catch (ParseException ex) {
            Logger.getLogger(Fen_Approvisionnement.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel mod = (DefaultTableModel) LtablePlanchefinal.getModel();

        Object[] row = {p.getTypebois().getLibelleTypeBois(), p.getForme().getLibeFormPlanche(), p.getLongueur().getValeurLongueur(), LQteLivr.getValue()};
        mod.addRow(row);
        LlivrPlancheFinal.add(p);


        // TODO add your handling code here:
    }//GEN-LAST:event_LBtnAddActionPerformed

    private void tableLComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLComMouseClicked


        int ind=tableLCom.getSelectedRow();
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Type Bois");
        mod.addColumn("Forme");
        mod.addColumn("Longueur");
        mod.addColumn("Quantité restante");
        Commande com=(Commande) LlivrComnonLiv.get(ind);
        try{
            LlivrPlanche.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,i.qteCommande from planche p,typeBois t,longeurPlanche l,formePlanche f,inclure i where i.idPlanche=p.idPlanche and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and i.idCommande="+com.getIdCommande());
            while(r.next()){
                Type_Bois tb=new Type_Bois(r.getString(2));
                FormePlanche fp=new FormePlanche(r.getString(3));
                LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
                Planche p=new Planche(r.getInt(1), fp, tb, lp);
                int qteCom=r.getInt(5);
                Statement e=sogemee.SOGEMEE.c.getConn().createStatement();
                ResultSet q=e.executeQuery("select sum(q.qteLivraison) from livraison l,livraison_planche q where q.idLivraison=l.idLivraison and l.idCommande="+com.getIdCommande()+" and q.idPlanche="+p.getIdPlanche());
                if(q.next()){
                    int qteLivr=q.getInt(1);
                    qteCom=qteCom-qteLivr;
                }
               if(qteCom>0){
                  Object [] tab={p.getTypebois().getLibelleTypeBois(),p.getForme().getLibeFormPlanche(),p.getLongueur().getValeurLongueur(),qteCom}; 
                 mod.addRow(tab);
                 LlivrPlanche.add(p);
               } 
            
            }  
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        LtablePlancheLivr.setModel(mod);
        // TODO add your handling code here:
    }//GEN-LAST:event_tableLComMouseClicked

    private void jScrollPane10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane10MouseClicked

    private void LBtnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LBtnRemoveActionPerformed

        int ind=LtablePlanchefinal.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à retirer svp");
            return;
        }
        Planche p=(Planche) LlivrPlancheFinal.get(ind);
        DefaultTableModel mod=(DefaultTableModel) LtablePlanchefinal.getModel();
        
       mod.removeRow(ind);
       LlivrPlancheFinal.remove(ind);
                // TODO add your handling code here:
    }//GEN-LAST:event_LBtnRemoveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
jTabbedPane2.setSelectedIndex(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void LBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LBtnSaveActionPerformed

        int indC=tableLCom.getSelectedRow();
        Commande com=(Commande) LlivrComnonLiv.get(indC);
        try {
            LdateLiv.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Fen_Approvisionnement.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Livraison liv=new Livraison(format.format(LdateLiv.getValue()), com);
        liv.saveLivraison();
        liv.setIdLivraison(liv.rechercheID());
        for(int i=0;i<LlivrPlancheFinal.size();i++){
            Planche p=(Planche) LlivrPlancheFinal.get(i);
            Livraison_Planche lp=new Livraison_Planche(p, liv, (int)LtablePlanchefinal.getValueAt(i, 3));
            lp.saveLivraisonPlanche();
        }
        
       LtablePlancheLivr.removeAll();
       LtablePlanchefinal.removeAll();
       LlivrComnonLiv.clear();
       LlivrPlancheFinal.clear();
       LtablePlanchefinal.removeAll();
        LtablePlanchefinal.setVisible(false);
        jTabbedPane2.setEnabledAt(1,false);
        jTabbedPane2.setSelectedIndex(0);
        LremplirtableLivr();
        // TODO add your handling code here:
    }//GEN-LAST:event_LBtnSaveActionPerformed

    private void LBtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LBtnSupprActionPerformed

        int ind=tableLivraison.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la livraison à supprimer");
            return;
        }
        Livraison liv=(Livraison) Llivraisons.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer cette livraison ?")==0){
            liv.deleteLivraison();
            LremplirtableLivr();
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_LBtnSupprActionPerformed

    private void ProTableProFormatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProTableProFormatMouseClicked

       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Type bois");
       mod.addColumn("Forme");
       mod.addColumn("Longueur");
       mod.addColumn("Quantité");
       mod.addColumn("PU");
      
        int ind=ProTableProFormat.getSelectedRow();
        Commande com=(Commande) ProCommandeProFormat.get(ind);
        ProTva.setSelectedItem(com.getTva().getValeurTva());
        try{
          Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
           ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,i.qteCommande,i.pu from commande c,formePlanche f,typeBois t,longeurPlanche l,inclure i,planche p  where c.idCommande=i.idCommande and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.idPlanche=i.idPlanche and c.idCommande="+com.getIdCommande());
           while(r.next()){
             Type_Bois tb=new Type_Bois(r.getString(1));
             FormePlanche fp=new FormePlanche(r.getString(2));
             LongueurPlanche lp=new LongueurPlanche(r.getInt(3));
               Object []row={tb.getLibelleTypeBois(),fp.getLibeFormPlanche(),lp.getValeurLongueur(),r.getInt(4),r.getDouble(5)};
              mod.addRow(row);
           }
      
        }
        catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex); 
        }
       ProTablePlanches.setModel(mod); 
        // TODO add your handling code here:
    }//GEN-LAST:event_ProTableProFormatMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        ProremplirSansProFormat();
        jTabbedPane3.setEnabledAt(1,true);
        jTabbedPane3.setSelectedIndex(1);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ProBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProBtnAddActionPerformed

        
        int ind = ProTablePlanches.getSelectedRow();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir la planche à ajouter svp");
            return;
        }
        Planche p = (Planche) ProPlanches.get(ind);
        
        try {
            ProPU.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Fen_Approvisionnement.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel mod = (DefaultTableModel) ProTablePlancheFinal.getModel();
        DefaultTableModel mod1 = (DefaultTableModel) ProTablePlanches.getModel();
        mod1.removeRow(ind);

        Object[] row = {p.getTypebois().getLibelleTypeBois(), p.getForme().getLibeFormPlanche(), p.getLongueur().getValeurLongueur(), ProPU.getValue()};
        mod.addRow(row);
        ProPlancheFinal.add(p);
        ProPlanches.remove(p);


        // TODO add your handling code here:
    }//GEN-LAST:event_ProBtnAddActionPerformed

    private void ProBtnRetirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProBtnRetirerActionPerformed

        
        int ind=ProTablePlancheFinal.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la planche à retirer svp");
            return;
        }
        Planche p=(Planche) ProPlancheFinal.get(ind);
        DefaultTableModel mod=(DefaultTableModel) ProTablePlancheFinal.getModel();
        DefaultTableModel mod1=(DefaultTableModel) ProTablePlanches.getModel();
        mod.removeRow(ind);
        ProPlancheFinal.remove(ind);
        ProPlanches.add(p);
        Object [] rr={p.getTypebois().getLibelleTypeBois(),p.getForme().getLibeFormPlanche(),p.getLongueur().getValeurLongueur()};
        mod1.addRow(rr);
        // TODO add your handling code here:
    }//GEN-LAST:event_ProBtnRetirerActionPerformed

    private void ProTableComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProTableComMouseClicked

        
        int ind=ProTableCom.getSelectedRow();
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Type Bois");
        mod.addColumn("Forme");
        mod.addColumn("Longueur");
        
        Commande com=(Commande) ProCommandeSansProFormat.get(ind);
        try{
            ProPlanches.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur from planche p,typeBois t,longeurPlanche l,formePlanche f,inclure i where i.idPlanche=p.idPlanche and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and i.idCommande="+com.getIdCommande());
            while(r.next()){
                Type_Bois tb=new Type_Bois(r.getString(2));
                FormePlanche fp=new FormePlanche(r.getString(3));
                LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
                Planche p=new Planche(r.getInt(1), fp, tb, lp);
                  Object [] tab={p.getTypebois().getLibelleTypeBois(),p.getForme().getLibeFormPlanche(),p.getLongueur().getValeurLongueur()}; 
                 mod.addRow(tab);
                 ProPlanches.add(p);
            }  
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        ProTablePlanches.setModel(mod);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_ProTableComMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        jTabbedPane3.setSelectedIndex(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if(ProPlancheFinal.isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez renseigner les prix SVP");
            return;
        }
        int indC=ProTableCom.getSelectedRow();
        Commande com=(Commande) ProCommandeSansProFormat.get(indC);
        Tva tva=new Tva(Float.parseFloat(ProTva.getSelectedItem().toString()));
        if(tva.rechercheID()==0){
            tva.saveTva();
        }
        tva.setIdTva(tva.rechercheID());
        com.setTva(tva);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        com.setDateproformat(format.format(ProDatePro.getValue()));
        com.updateCommande();
        for(int i=0;i<ProPlancheFinal.size();i++){
            Planche p=(Planche) ProPlancheFinal.get(i);
            Double pu=(Double) ProTablePlancheFinal.getValueAt(i,3);
            Inclure inc=new Inclure(p, com, indC, pu);
            inc.updateInclure(p, com, i, pu);
        }
        ProremplirProFormat();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void InvTableInvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InvTableInvMouseClicked

        int ind=InvTableInv.getSelectedRow();
        Inventaire inv=(Inventaire) InvInventaires.get(ind);
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Type Bois");
        model.addColumn("Forme");
        model.addColumn("Longeur");
        model.addColumn("Qte Trouvée");
        model.addColumn("Qte non Trouvée");
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,q.qteInventaire,x.qteSortie from typeBois t,formePlanche f,longeurPlanche l,planche p,inventaire i,inventaire_planche q,sortie s,sortie_planche x where x.idSortie=s.idSortie and x.idPlanche=p.idPlanche and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche  and i.idInventaire=q.idInventaire and p.idPlanche=q.idPlanche and i.dateInventaire='"+inv.getDateInventaire()+"' and s.dateSortie='"+inv.getDateInventaire()+"' and x.motifSortie='Vole'");
            while(r.next()){
                Object [] ee={r.getString(1),r.getString(2),r.getInt(3),r.getInt(4),r.getInt(5)};
                model.addRow(ee);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        InvTablePlanche.setModel(model);
        
        
        DefaultTableModel model1=new DefaultTableModel();
        model1.addColumn("Categorie");
        model1.addColumn("Modele");
        model1.addColumn("Qte Trouvée");
        model1.addColumn("Qte non Trouvée");
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select c.libelleCategorieModele,m.libelleModele,q.qteInventaire from modele m,categorieModele c,inventaire i,inventaire_modele q where c.idCategorieModele=m.idCategorieModele and i.idInventaire=q.idInventaire and q.idModele=m.idModele and i.dateInventaire='"+inv.getDateInventaire()+"'");
            while(r.next()){
                Object [] ee={r.getString(1),r.getString(2),r.getInt(3),0};
                model1.addRow(ee);
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        InvTableMeuble.setModel(model1);
        // TODO add your handling code here:
    }//GEN-LAST:event_InvTableInvMouseClicked

    private void InvBtnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvBtnStartActionPerformed

           Date datejr=new Date();
        ArrayList listeT=new ArrayList();
        ArrayList listeM=new ArrayList();
        
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Inventaire inv=new Inventaire(format.format(datejr));
        if(inv.rechercheID()!=0){
            JOptionPane.showMessageDialog(null,"Un inventaire a déjà été réalisé ");
            return;
        }
        JOptionPane.showMessageDialog(null,"Partie planches");
        InvPlanches.clear();
        InvQtePlanches.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("Select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur from planche p,typeBois t,formePlanche f,longeurPlanche l where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.idPlanche in(select idPlanche from livraison_planche ) and p.suppr=0");
        while(r.next()){
            Type_Bois tb=new Type_Bois(r.getString(2));
            FormePlanche fp=new FormePlanche(r.getString(3));
            LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
            Planche p=new Planche(r.getInt(1), fp, tb, lp);
            InvPlanches.add(p);
        }
        
        for(int i=0;i<InvPlanches.size();i++){
            Planche p=(Planche) InvPlanches.get(i);
            String rep;
            rep=JOptionPane.showInputDialog(null,"Quantité de "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+"m trouvée");
            if(rep==null){
                if(JOptionPane.showConfirmDialog(null,"Voulez vous mettre fin à l'inventaire?")==0){
                return;    
                }
            }
            try{
                int qte=Integer.parseInt(rep);
                InvQtePlanches.add(qte);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Veuillez entrer une quantité valide SVP");
                i--;
            }
        }
        JOptionPane.showMessageDialog(null,"Fin partie planches");
        JOptionPane.showMessageDialog(null,"Partie meuble");
        try{
            InvMeubles.clear();
            InvQteMeubles.clear();
            Statement sm=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet rm=sm.executeQuery("Select c.libelleCategorieModele,m.idModele,m.libelleModele from categorieModele c,modele m where c.idCategorieModele=m.idCategorieModele and m.suppr=0 and c.suppr=0 and m.idModele in(select idModele from meuble where suppr=0)");
            while(rm.next()){
                CategorieModele c=new CategorieModele(rm.getString(1));
                Modele m=new Modele(rm.getInt(2),rm.getString(3), c);
                InvMeubles.add(m);
            }
            
            for(int i=0;i<InvMeubles.size();i++){
                Modele m=(Modele) InvMeubles.get(i);
             String rep=JOptionPane.showInputDialog(null,"Quantité de "+m.getCategorieModele().getLibelleCategorieModele()+" "+m.getLibelleModele()+" trouvée");
            if(rep==null){
                if(JOptionPane.showConfirmDialog(null,"Voulez vous mettre fin à l'inventaire?")==0){
                return;    
                }
            }
            try{
                int qte=Integer.parseInt(rep);
                InvQteMeubles.add(qte);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Veuillez entrer une quantité valide SVP");
                i--;
            }
       
            }
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
     
        inv.saveInventaire();
        inv.setIdInventaire(inv.rechercheID());
        for(int i=0;i<InvPlanches.size();i++){
            Planche p=(Planche) InvPlanches.get(i);
            int qte=(int) InvQtePlanches.get(i);
            Inventaire_planche ip=new Inventaire_planche(inv, p, qte);
            ip.saveInventairePlanche();
        }
        for(int i=0;i<InvMeubles.size();i++){
            Modele m=(Modele) InvMeubles.get(i);
            int qte=(int) InvQteMeubles.get(i);
            Inventaire_Meuble im=new Inventaire_Meuble(inv,m, qte);
            im.saveInventaireModele();
        }
        
           Statement sl=sogemee.SOGEMEE.c.getConn().createStatement();
            sl.executeUpdate("drop view etatStock");
            sl.close();
            Statement ses=sogemee.SOGEMEE.c.getConn().createStatement();
            
            Date date=new Date();
            String requete="create view etatStock as";
            requete+=" select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,-sum(i.qteSortie) as qte from planche p,typeBois t,formePlanche f,longeurPlanche l,bonSortie_planche i,bonSortie b where p.suppr=0 and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and b.idBonSortie=i.idBonSortie and b.suppr=0 and p.idLongeurPlanche=l.idLongeurPlanche and i.idPlanche=p.idPlanche and dateBonSortie <='"+format.format(date) +"'  group by i.idPlanche";
            requete+=" union";
            requete+=" select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,-sum(i.qteSortie) from planche p,typeBois t,formePlanche f,longeurPlanche l,sortie_planche i,sortie b where p.suppr=0 and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and b.idSortie=i.idSortie and p.idLongeurPlanche=l.idLongeurPlanche and i.idPlanche=p.idPlanche and dateSortie <='"+format.format(date) +"'  group by i.idPlanche";
            requete+=" union ";
            requete+=" select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,sum(i.qteLivraison) from planche p,typeBois t,formePlanche f,longeurPlanche l,livraison_planche i,livraison k where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and k.idLivraison=i.idLivraison and i.idPlanche=p.idPlanche and dateLivraison<='"+format.format(date)+"' and k.suppr=0 group by i.idPlanche ";
            requete+=" union"; 
            requete+=" select"; 
            requete+=" p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,-sum(c.qteFacture) from planche p,typeBois t,formePlanche f,longeurPlanche l,factureClient i,concerner c where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and c.idFactureClient=i.idFactureClient and c.idPlanche=p.idPlanche and dateFacture<='"+format.format(date)+"'  and i.suppr=0 and c.typeAction='Vente' group by c.idPlanche";
            ses.executeUpdate(requete);
            Statement seses=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet res=seses.executeQuery("select  idPlanche,libelleTypeBois,libelleFormePlanche,valeurLongeur,sum(qte) from etatStock group by idPlanche");
            while(res.next()){
                Type_Bois tb=new Type_Bois(res.getString(2));
                FormePlanche fp=new FormePlanche(res.getString(3));
                LongueurPlanche lp=new LongueurPlanche(res.getInt(4));
                Planche p=new Planche(res.getInt(1), fp, tb, lp);
                Sortie sortie=new Sortie(format.format(date));
               
                sortie.setIdSortie(sortie.rechercheID());
                for(int i=0;i<InvPlanches.size();i++){
                  Planche pp=(Planche) InvPlanches.get(i);
                    if(p.getIdPlanche()==pp.getIdPlanche()){
                        
                        int qte=(int) InvQtePlanches.get(i);
                        if(res.getInt(5)>qte){
                           
                           Sortie_planche sp=new Sortie_planche(sortie, pp, (res.getInt(5)-qte), "Vole");
                          listeT.add(qte);
                          listeM.add((res.getInt(5)-qte));
                           sp.saveSortiePlanche();
                            JOptionPane.showMessageDialog(null,"il manque "+(res.getInt(5)-qte)+" "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+"m");
                        }
                        else{
                        if(res.getInt(5)<qte){
                           
                           //Sortie_planche sp=new Sortie_planche(sortie, pp, (res.getInt(5)-qte), "Vole");
                           //sp.saveSortiePlanche();
                            JOptionPane.showMessageDialog(null,"il y a un surplus de "+(qte-res.getInt(5))+" "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+"m");
                        }    
                        }
                    }
                }
            }
            
            Statement vol=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet resV=vol.executeQuery("select idModele,count(idMeuble) from meuble where idFactureClient=0 and idSortie=0 group by idModele ");
            Sortie sortie=new Sortie(format.format(date));
                          sortie.setIdSortie(sortie.rechercheID());
                          JOptionPane.showMessageDialog(null,sortie.getIdSortie());
            while(resV.next()){
                for(int i=0;i<InvMeubles.size();i++){
                   Modele m=(Modele) InvMeubles.get(i);
                   if(m.getIdModele()==resV.getInt(1)){
                       int qte=resV.getInt(2);
                       int qteTrouv=(int)InvQteMeubles.get(i);
                       if(qteTrouv<qte){
                           JOptionPane.showMessageDialog(null,"Il manque "+(qte-qteTrouv)+" de "+m.getCategorieModele().getLibelleCategorieModele()+" "+m.getLibelleModele());
                           Statement meublV=sogemee.SOGEMEE.c.getConn().createStatement();
                           ResultSet meublR=meublV.executeQuery("select idMeuble from meuble where idFactureClient=0 and idSortie=0 and idModele="+m.getIdModele()+" limit 0,"+(qte-qteTrouv));
                           while(meublR.next()){
                               Meuble meu=new Meuble();
                               meu.setIdMeuble(meublR.getInt(1));
                               meu.VoleMeuble(sortie);
                           }
                       }
                   }
                }
                
            }
             
            JOptionPane.showMessageDialog(null,"Fin de l'inventaire");
            JasperDesign jasperdesign = JRXmlLoader.load("src/approvisionnement/Inventaire.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            
            Map param = new HashMap();
            param.put("listeT", listeT);
            param.put("listeM",listeM);
            param.put("date",format.format(date));

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);

            JasperDesign jasperdesign1 = JRXmlLoader.load("src/approvisionnement/Inventaire2.jrxml");

            JasperReport jr1 = JasperCompileManager.compileReport(jasperdesign1);

            
            Map param1 = new HashMap();
            param1.put("date",format.format(date));

            JasperPrint jp1 = JasperFillManager.fillReport(jr1, param1, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp1,false);

            InvBtnStart.setVisible(false);
            remplirInventaire();
        }
        catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_InvBtnStartActionPerformed

    private void InvBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvBtnNewActionPerformed

        JOptionPane.showMessageDialog(null,"Veuillez entrer les planches détériorées");
        jTabbedPane4.setSelectedIndex(2);
        jTabbedPane4.setEnabledAt(2,true);
        remplirPlanchesDeter();        // TODO add your handling code here:
    }//GEN-LAST:event_InvBtnNewActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        int ind=InvTablePlanchesDet.getSelectedRow();
        if(ind==-1){
            return;
        }
        Planche p=(Planche) InvPlanchesDet.get(ind);
        if(InvPlancheFinal.contains(p)){
            JOptionPane.showMessageDialog(null,"Cette Planche est déjà sélectionnée");
            return;
        }
        try {
            InvQteDeterior.commitEdit();
        } catch (ParseException ex) {
            Logger.getLogger(Fen_Approvisionnement.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultTableModel model=(DefaultTableModel) InvTableFinal.getModel();
        Object[]ee={p.getTypebois().getLibelleTypeBois(),p.getForme().getLibeFormPlanche(),p.getLongueur().getValeurLongueur(),InvQteDeterior.getValue()};
        model.addRow(ee);
        InvPlancheFinal.add(p);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
 int ind=InvTableFinal.getSelectedRow();
        if(ind==-1){
            return;
        }
        Planche p=(Planche) InvPlancheFinal.get(ind);
        DefaultTableModel model=(DefaultTableModel) InvTableFinal.getModel();
        model.removeRow(ind);
        InvPlancheFinal.remove(ind);
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

JOptionPane.showMessageDialog(null,"Appuyez sur demarrer l'inventaire quand vous serai prêt");        
SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
Date d=new Date();
Sortie s=new Sortie(format.format(d));
s.saveSortie();
s.setIdSortie(s.rechercheID());
for(int i=0;i<InvPlancheFinal.size();i++){
    Planche p=(Planche) InvPlancheFinal.get(i);
    Sortie_planche sp=new Sortie_planche(s, p, (int)InvTableFinal.getValueAt(i,3),"Détérioration");
    sp.saveSortiePlanche();
}        
InvBtnStart.setVisible(true);        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
remplirFacturesNnLiv();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

        int ind=LCtable.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la facture à livrer");
            return;
        }
        FactureClient fc=(FactureClient) LCFactures.get(ind);
        if(JOptionPane.showConfirmDialog(null, "Êtes vous sûres de livrer cette facture ?")==0){
            fc.LivreFactureClient();
            remplirFacturesNnLiv();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

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
            java.util.logging.Logger.getLogger(Fen_Approvisionnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fen_Approvisionnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fen_Approvisionnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fen_Approvisionnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Fen_Approvisionnement().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CBtnAdd;
    private javax.swing.JButton CBtnNew;
    private javax.swing.JButton CBtnRemove;
    private javax.swing.JButton CBtnSave;
    private javax.swing.JButton CBtnSuppr;
    private javax.swing.JComboBox CctypeCommande;
    private javax.swing.JPanel ComPanel;
    private javax.swing.JSpinner CqteCommande;
    private javax.swing.JTextField FBP;
    private javax.swing.JButton FBtnAnnuler;
    private javax.swing.JButton FBtnModif;
    private javax.swing.JButton FBtnNew;
    private javax.swing.JButton FBtnSave;
    private javax.swing.JButton FBtnSuppr;
    private javax.swing.JTextField FRue;
    private javax.swing.JTextField FVille;
    private javax.swing.JTextField Fnomfour;
    private javax.swing.JPanel FourPanel;
    private javax.swing.JTextField Ftelfour;
    private javax.swing.JButton InvBtnNew;
    private javax.swing.JButton InvBtnStart;
    private javax.swing.JButton InvBtnSuppr;
    private javax.swing.JSpinner InvQteDeterior;
    private javax.swing.JTable InvTableFinal;
    private javax.swing.JTable InvTableInv;
    private javax.swing.JTable InvTableMeuble;
    private javax.swing.JTable InvTablePlanche;
    private javax.swing.JTable InvTablePlanchesDet;
    private javax.swing.JButton LBtnAdd;
    private javax.swing.JButton LBtnNew;
    private javax.swing.JButton LBtnRemove;
    private javax.swing.JButton LBtnSave;
    private javax.swing.JButton LBtnSuppr;
    private javax.swing.JTable LCtable;
    private javax.swing.JSpinner LQteLivr;
    private javax.swing.JSpinner LdateLiv;
    private javax.swing.JPanel LivrPanel;
    private javax.swing.JTable LtablePlancheLivr;
    private javax.swing.JTable LtablePlanchefinal;
    private javax.swing.JButton PBtnAnnuler;
    private javax.swing.JButton PBtnModif;
    private javax.swing.JButton PBtnNew;
    private javax.swing.JButton PBtnSave;
    private javax.swing.JButton PBtnSuppr;
    private javax.swing.JComboBox Pcomboforme;
    private javax.swing.JComboBox Pcombolongueur;
    private javax.swing.JComboBox Pcombotypebois;
    private javax.swing.JSpinner PcoutLissage;
    private javax.swing.JSpinner PcoutTraitement;
    private javax.swing.JPanel PlanPanel;
    private javax.swing.JSpinner PprixVente;
    private javax.swing.JButton ProBtnAdd;
    private javax.swing.JButton ProBtnRetirer;
    private javax.swing.JSpinner ProDatePro;
    private javax.swing.JSpinner ProPU;
    private javax.swing.JPanel ProPanel;
    private javax.swing.JTable ProTableCom;
    private javax.swing.JTable ProTablePlancheFinal;
    private javax.swing.JTable ProTablePlanches;
    private javax.swing.JTable ProTableProFormat;
    private javax.swing.JComboBox ProTva;
    private javax.swing.JButton TFLBtnAnnuler;
    private javax.swing.JButton TFLBtnModif;
    private javax.swing.JButton TFLBtnNew;
    private javax.swing.JButton TFLBtnSave;
    private javax.swing.JButton TFLBtnSuppr;
    private javax.swing.JRadioButton TFLLongueurRadio;
    private javax.swing.JPanel TFLPanel;
    private javax.swing.JRadioButton TFLTypeBoisRadio;
    private javax.swing.ButtonGroup TFLbuttonGroup;
    private javax.swing.JRadioButton TFLformeRadio;
    private javax.swing.JLabel TFLlabel;
    private javax.swing.JTextField TFLlibelle;
    private javax.swing.JComboBox cfournisseur;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tableCfinal;
    private javax.swing.JTable tableCommande;
    private javax.swing.JTable tableCplanche;
    private javax.swing.JTable tableFournisseur;
    private javax.swing.JTable tableLCom;
    private javax.swing.JTable tableLivraison;
    private javax.swing.JTable tablePlanche;
    private javax.swing.JTable tableTFL;
    // End of variables declaration//GEN-END:variables
}
