/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import approvisionnement.Livraison;
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
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;


import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author el-diablo
 */
@SuppressWarnings("serial")
public class Menu_Vente extends javax.swing.JFrame {

    private ArrayList rfLivraisons=new ArrayList();
    private ArrayList rfDateLivraison=new ArrayList();
    private ArrayList rfFournisseur=new ArrayList();
    private ArrayList rfMontantRestant=new ArrayList();
    private ArrayList rfFournisseurRegle=new ArrayList();
    private ArrayList rfdateLivraisonRegle=new ArrayList();
    private ArrayList rfdateReglement=new ArrayList();
    private ArrayList rfmontantRegle=new ArrayList();
    private ArrayList rfReglementFournisseur=new ArrayList();
    private ArrayList periode=new ArrayList();
    private ArrayList montantPeriode=new ArrayList();
    private ArrayList sort=new ArrayList();
    
    
    private ArrayList RCfactureClient=new ArrayList();
    private ArrayList RCidfactureClient=new ArrayList();
    private ArrayList RCclient=new ArrayList();
    private ArrayList RCnomClient=new ArrayList();
    private ArrayList RCprenomClient=new ArrayList();
    private ArrayList RCtelClient=new ArrayList();
    private ArrayList RCmontantFacture=new ArrayList();
    
    private ArrayList RCreglement=new ArrayList();
    private ArrayList RCdateReglement=new ArrayList();
    private ArrayList RCnomReglement=new ArrayList();
    private ArrayList RCprenomReglement=new ArrayList();
    private ArrayList RCnumFactureReglement=new ArrayList();
    private ArrayList RCmontantTReglement=new ArrayList();
    
    
    private ArrayList FCfactures=new ArrayList();
    private ArrayList FCnoms=new ArrayList();
    private ArrayList FCprenomns=new ArrayList();
    private ArrayList FCdateFacture=new ArrayList();
    private ArrayList FCnumFacture=new ArrayList();
    private ArrayList FCmontantTFacture=new ArrayList();
    private ArrayList FCProduit=new ArrayList();
    private ArrayList FCForme=new ArrayList();
    private ArrayList FCLongueur=new ArrayList();
    private ArrayList FCmeuble=new ArrayList();
    private ArrayList FCqteProduit=new ArrayList();
    private ArrayList FCqteMeuble=new ArrayList();
    private ArrayList FCtypeAction=new ArrayList();
    private ArrayList FCtypeTraitement=new ArrayList();
    private ArrayList client=new ArrayList();
    private ArrayList nomClient=new ArrayList();
    private ArrayList prenomClient=new ArrayList();
    private ArrayList telClient=new ArrayList();
    
    
    
    /**
     * Creates new form Menu_Vente
     */
    public Menu_Vente() {
        initComponents();
        
        
        remplirRecapitulatifMois();
        remplirLivraison();
        remplirReglement();
        remplirFacture();   
        remplirTableReglements();
        remplirTableFactures();
        journal();
        try {
            remplirClients();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    
    
    public void journal(){
        DefaultTableModel model=new DefaultTableModel();
            model.addColumn("Compte");
            model.addColumn("Montant opération");
            model.addColumn("Sens Opération");
            model.addColumn("Libelle Operation");
        try {
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Statement s= sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select libelleCompte,montantOperation, sensOperation,libelleOperation from compte c,operation o where c.idCompte=o.idCompte and dateOperation='"+format.format(date) +"'");
            while(r.next())
            {
                Object [] ee={r.getString(1),r.getDouble(2),r.getString(3),r.getString(4)};
                model.addRow(ee);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        tableJournal.setModel(model);
    }
    
    public void remplirClients() throws SQLException{
        client.clear();
        nomClient.clear();
        prenomClient.clear();
        telClient.clear();
       DefaultTableModel model=new DefaultTableModel();
      
        Statement sCli= sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet rsCli=sCli.executeQuery("select idClient, nomClient, prenomClient, telClient from client where suppr=0");
        while(rsCli.next())
        {
            Client clit=new Client(rsCli.getInt(1),rsCli.getString(2),rsCli.getString(3),rsCli.getString(4));
            client.add(clit);
            nomClient.add(clit.getNomClient());
            prenomClient.add(clit.getPrenomClient());
            telClient.add(clit.getTelClient());
                
        }
        
      model.addColumn("Nom",nomClient.toArray());
      model.addColumn("Prénoms",prenomClient.toArray() );
      model.addColumn("Téléphone",telClient.toArray());
      
        tableClient.setModel(model);
    }
    
    
    public void remplirRechFacture(String date,double mont){
        
        String rechNom="%";
        String rechPrenom="%";
        String rechDate=date;
        
        double rechMontant=mont;
        rechNom+=FCRechNom.getText()+"%";
        rechPrenom+=FCRechPrenom.getText()+"%";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        //rechDate=format.format(FCRechDate.getValue());
        
        try{
                 FCnoms.clear();
             FCprenomns.clear();
            FCfactures.clear();
            FCdateFacture.clear();
            FCmontantTFacture.clear();
            FCnumFacture.clear();
           
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.idFactureClient,f.dateFacture,f.montantFacture,c.nomClient,c.prenomClient from factureClient f,client c where f.idClient=c.idClient and f.suppr=0 and c.nomClient like '"+rechNom+"' and  c.prenomClient like '"+rechPrenom+"' and f.dateFacture>='"+rechDate+"' and f.montantFacture>="+rechMontant);
            while(r.next()){
            
                FCnoms.add(r.getString(4));
             FCprenomns.add(r.getString(5));
            FactureClient f=new FactureClient(r.getInt(1),0);
            f.setDateFacture(r.getString(2));
            f.setMontantFacture(r.getDouble(3));
            FCfactures.add(f);
            FCdateFacture.add(f.getDateFacture());
            FCmontantTFacture.add(f.getMontantFacture());
            FCnumFacture.add(f.getIdFactureClient());
           
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Num Facture",FCnumFacture.toArray());
        mod.addColumn("Date Facture",FCdateFacture.toArray());
        mod.addColumn("Montant Facture",FCmontantTFacture.toArray());
        mod.addColumn("Nom Client",FCnoms.toArray());
        mod.addColumn("Prenom Client", FCprenomns.toArray());
        tableFCfactures.setModel(mod);
        
    }
    
    
    public void remplirTableFactures(){
        try{
                 FCnoms.clear();
             FCprenomns.clear();
            FCfactures.clear();
            FCdateFacture.clear();
            FCmontantTFacture.clear();
            FCnumFacture.clear();
           
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.idFactureClient,f.dateFacture,f.montantFacture,c.nomClient,c.prenomClient from factureClient f,client c where f.idClient=c.idClient and f.suppr=0");
            while(r.next()){
            
                FCnoms.add(r.getString(4));
             FCprenomns.add(r.getString(5));
            FactureClient f=new FactureClient(r.getInt(1),0);
            f.setDateFacture(r.getString(2));
            f.setMontantFacture(r.getDouble(3));
            FCfactures.add(f);
            FCdateFacture.add(f.getDateFacture());
            FCmontantTFacture.add(f.getMontantFacture());
            FCnumFacture.add(f.getIdFactureClient());
           
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Num Facture",FCnumFacture.toArray());
        mod.addColumn("Date Facture",FCdateFacture.toArray());
        mod.addColumn("Montant Facture",FCmontantTFacture.toArray());
        mod.addColumn("Nom Client",FCnoms.toArray());
        mod.addColumn("Prenom Client", FCprenomns.toArray());
        tableFCfactures.setModel(mod);
    
    }
    
    
    public void remplirTableReglements(){
        try{
            RCreglement.clear();
            RCnumFactureReglement.clear();
            RCnomReglement.clear();
            RCprenomReglement.clear();
            RCmontantTReglement.clear();
            RCdateReglement.clear();
            Statement z=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=z.executeQuery("select r.idReglement,r.montantReglement,c.nomClient,c.prenomClient,r.idFactureClient,r.dateReglement from reglement r,client c,factureClient f where r.suppr=0 and c.suppr=0 and f.suppr=0 and r.idFactureClient=f.idFactureClient and f.idClient=c.idClient");
            while(r.next()){
            RCnumFactureReglement.add(r.getInt(5));
            RCnomReglement.add(r.getString(3));
            RCprenomReglement.add(r.getString(4));
            RCmontantTReglement.add(r.getDouble(2));
            RCdateReglement.add(r.getString(6));
            Reglement reg=new Reglement(r.getInt(1), r.getDouble(2), null, null);
            RCreglement.add(reg);
            }
        
        
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Date du Reglement",RCdateReglement.toArray());
        mod.addColumn("Nom Client",RCnomReglement.toArray());
        mod.addColumn("Prenom",RCprenomReglement.toArray());
        mod.addColumn("Num Fact",RCnumFactureReglement.toArray());
        mod.addColumn("Montant",RCmontantTReglement.toArray());
        RCTableReglements.setModel(mod);
    }
    
     public void remplirFacture(){
        try{
           RCfactureClient.clear();
                RCidfactureClient.clear();
                RCnomClient.clear();
                RCprenomClient.clear();
                RCtelClient.clear();
                RCmontantFacture.clear();

            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select f.idFactureClient,c.nomClient,c.prenomClient,c.telClient,f.montantFacture,f.suppr from factureClient f ,client c where  f.idClient=c.idClient and f.idFactureClient not in(select idFactureClient from reglement) union (select r.idFactureClient,c.nomClient,c.prenomClient,c.telClient,(f.montantFacture-sum(r.montantReglement)),f.montantFacture from reglement r,factureClient f,client c where r.idFactureClient=f.idFactureClient and f.idClient=c.idClient and  r.suppr=0  group by r.idFactureClient having sum(r.montantReglement)<f.montantFacture)");
            while(r.next()){
                FactureClient facture=new FactureClient(r.getInt(1),0);
                RCfactureClient.add(facture);
                RCidfactureClient.add(facture.getIdFactureClient());
                RCnomClient.add(r.getString(2));
                RCprenomClient.add(r.getString(3));
                RCtelClient.add(r.getString(4));
                RCmontantFacture.add(r.getDouble(5));
            }
            DefaultTableModel mod=new DefaultTableModel();
            mod.addColumn("Numéro ",RCidfactureClient.toArray());
            mod.addColumn("Nom client",RCnomClient.toArray());
            mod.addColumn("Prenom client",RCprenomClient.toArray());
            mod.addColumn("Reste à payer", RCmontantFacture.toArray());
            RCtableReglement.setModel(mod);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
    }

    
    public void remplirReglement(){
        try{
            rfReglementFournisseur.clear();
            rfFournisseurRegle.clear();
                rfdateLivraisonRegle.clear();
                rfdateReglement.clear();
                rfmontantRegle.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select l.idLivraison,l.dateLivraison,f.nomFournisseur,r.idReglementFournisseur,r.montantReglementFournisseur,r.dateReglementFournisseur from fournisseur f, livraison l,reglementFournisseur r,commande c  where c.idFournisseur=f.idFournisseur and l.idCommande=c.idCommande and r.idLivraison=l.idLivraison and l.suppr=0 and c.suppr=0 and r.suppr=0  order by r.dateReglementFournisseur ");
            while(r.next()){
                Livraison liv=new Livraison(r.getInt(1),r.getString(2), null);
                rfdateLivraisonRegle.add(liv.getDateLiivraison());
                rfdateReglement.add(r.getString(6));
                rfFournisseurRegle.add(r.getString(3));
                rfmontantRegle.add(r.getDouble(5));
                ReglementFournisseur r1=new ReglementFournisseur(r.getInt(4),r.getString(6),r.getDouble(5), liv);
                rfReglementFournisseur.add(r1);
            }
            
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        DefaultTableModel mod= new DefaultTableModel();
        mod.addColumn("Date Reglement",rfdateReglement.toArray());
        mod.addColumn("Montant Réglé",rfmontantRegle.toArray());
        mod.addColumn("Fournisseur",rfFournisseurRegle.toArray());
        mod.addColumn("Date Livraison",rfdateLivraisonRegle.toArray());
        
        tableRF.setModel(mod);
    }
    
    private void remplirLivraison(){
        try{
            rfLivraisons.clear();
                rfDateLivraison.clear();
                rfFournisseur.clear();
                rfMontantRestant.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select l.idLivraison,p.idPlanche,c.idCommande,i.pu,q.qteLivraison, sum(i.pu*q.qteLivraison),f.nomFournisseur,l.dateLivraison from fournisseur f, livraison l,planche p,commande c,inclure i,livraison_planche q where c.idFournisseur=f.idFournisseur and l.idCommande=c.idCommande and q.idLivraison=l.idLivraison and q.idPlanche=p.idPlanche and c.idCommande=i.idCommande and p.idPlanche=i.idPlanche and l.idLivraison not in (select idLivraison from reglementFournisseur) and l.suppr=0 and c.suppr=0 group by l.idLivraison,c.idCommande ");
            while(r.next()){
                Livraison liv=new Livraison(r.getInt(1),r.getString(8), null);
                rfLivraisons.add(liv);
                rfDateLivraison.add(liv.getDateLiivraison());
                rfFournisseur.add(r.getString(7));
                rfMontantRestant.add(r.getDouble(6));
            }
            ResultSet rsp=s.executeQuery("select l.idLivraison,p.idPlanche,c.idCommande,i.pu,q.qteLivraison, sum(i.pu*q.qteLivraison),f.nomFournisseur,l.dateLivraison from fournisseur f, livraison l,planche p,commande c,inclure i,livraison_planche q where c.idFournisseur=f.idFournisseur and l.idCommande=c.idCommande and q.idLivraison=l.idLivraison and q.idPlanche=p.idPlanche and c.idCommande=i.idCommande and p.idPlanche=i.idPlanche and l.idLivraison in (select idLivraison from reglementFournisseur) and l.suppr=0 and c.suppr=0 group by l.idLivraison,c.idCommande ");
            while(rsp.next()){
                Livraison liv=new Livraison(rsp.getInt(1),rsp.getString(8), null);
                Statement ssss=sogemee.SOGEMEE.c.getConn().createStatement();
                ResultSet rmp=ssss.executeQuery("Select sum(montantReglementFournisseur) from reglementFournisseur where suppr=0 and idLivraison="+liv.getIdLivraison());
                rmp.next();
                double montantPaye=rmp.getDouble(1);
                if(montantPaye<rsp.getDouble(6)){
                rfMontantRestant.add(rsp.getDouble(6)-montantPaye);    
                rfLivraisons.add(liv);
                rfDateLivraison.add(liv.getDateLiivraison());
                rfFournisseur.add(rsp.getString(7));
                }
                
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        DefaultTableModel mod= new DefaultTableModel();
        mod.addColumn("Fournisseur",rfFournisseur.toArray());
        mod.addColumn("Date Livraison",rfDateLivraison.toArray());
        mod.addColumn("Montant restant",rfMontantRestant.toArray());
        tableLivraison.setModel(mod);
    }
    
    
    public void remplirRecapitulatifMois(){
       double mont=0;
        try{
            periode.clear();
            montantPeriode.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery(" select sum(montantFacture),MONTHNAME(dateFacture) from factureClient group by EXTRACT(MONTH from dateFacture) order by EXTRACT(MONTH from dateFacture)" );
            while(r.next()){
                periode.add(r.getString(2));
                montantPeriode.add(r.getDouble(1));
            }
            
        }catch(Exception ex){
            
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Mois",periode.toArray());
        mod.addColumn("Montant", montantPeriode.toArray());
        tableRecapitulatif.setModel(mod);
        
        for(int i=0;i<montantPeriode.size();i++){
            mont+=(double)montantPeriode.get(i);
        }
        Object o=mont;
        montant.setText(o.toString());
    }
    
    public void remplirRecapitulatifAnnee(){
       double mont=0;
        try{
            periode.clear();
            montantPeriode.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery(" select sum(montantFacture),EXTRACT(YEAR from dateFacture) from factureClient group by EXTRACT(YEAR from dateFacture) order by EXTRACT(YEAR from dateFacture) " );
            while(r.next()){
                periode.add(r.getString(2));
                montantPeriode.add(r.getDouble(1));
            }
            
        }catch(Exception ex){
            
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Année",periode.toArray());
        mod.addColumn("Montant", montantPeriode.toArray());
        tableRecapitulatif.setModel(mod);
        
        for(int i=0;i<montantPeriode.size();i++){
            mont+=(double)montantPeriode.get(i);
        }
        Object o=mont;
        montant.setText(o.toString());
    }
     
   
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recapitulatif = new javax.swing.ButtonGroup();
        RCButtonGroup = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRecapitulatif = new javax.swing.JTable();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        montant = new javax.swing.JLabel();
        canvas1 = new java.awt.Canvas();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        RCjLabel12 = new javax.swing.JLabel();
        RCvnomVisu = new javax.swing.JTextField();
        RCvprenomvisu = new javax.swing.JTextField();
        RCvidFacturevisu = new javax.swing.JTextField();
        RCjLabel14 = new javax.swing.JLabel();
        RCvmontantvisu = new javax.swing.JTextField();
        RCjLabel15 = new javax.swing.JLabel();
        RCjLabel17 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        RCjLabel1 = new javax.swing.JLabel();
        RCbtnRafraichir = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        RCtableReglement = new javax.swing.JTable();
        RCjLabel9 = new javax.swing.JLabel();
        RCdatedujour = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        RCjLabel10 = new javax.swing.JLabel();
        RCjLabel7 = new javax.swing.JLabel();
        RCmontantReglement = new javax.swing.JFormattedTextField();
        RCjLabel11 = new javax.swing.JLabel();
        RCespeceRadioButton = new javax.swing.JRadioButton();
        RCchequeRadioButton = new javax.swing.JRadioButton();
        RCjLabel13 = new javax.swing.JLabel();
        RCmontantRecu = new javax.swing.JFormattedTextField();
        RCjLabel18 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        RCTableReglements = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        RCjLabel5 = new javax.swing.JLabel();
        RCjLabel6 = new javax.swing.JLabel();
        RCjLabel4 = new javax.swing.JLabel();
        RCjLabel8 = new javax.swing.JLabel();
        RCjLabel3 = new javax.swing.JLabel();
        RCjLabel2 = new javax.swing.JLabel();
        RCvnom = new javax.swing.JTextField();
        RCvprenom = new javax.swing.JTextField();
        RCvtel = new javax.swing.JTextField();
        RCvidFacture = new javax.swing.JTextField();
        RCvmontant = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        RCSuppr = new javax.swing.JButton();
        RCNouveau = new javax.swing.JButton();
        RCEnre = new javax.swing.JButton();
        RCModif = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rfTNomFour = new javax.swing.JTextField();
        rfTDate = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rfSDateR = new javax.swing.JSpinner();
        rfSMont = new javax.swing.JSpinner();
        rfJPanel1 = new javax.swing.JPanel();
        rfJLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableLivraison = new javax.swing.JTable();
        rfJButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        montantRegle = new javax.swing.JSpinner();
        label = new javax.swing.JLabel();
        dateReglement = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRF = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        rfJPanel3 = new javax.swing.JPanel();
        rfbtnEnr = new javax.swing.JButton();
        rfbtnEnr.setEnabled(false);
        rfbtnAnnuler = new javax.swing.JButton();
        rfbtnSuppr = new javax.swing.JButton();
        rfbtnNew = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableFCfactures = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        FCRechDate = new javax.swing.JSpinner();
        FCRechNom = new javax.swing.JTextField();
        FCRechPrenom = new javax.swing.JTextField();
        FCRechMontant = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableFCProduits = new javax.swing.JTable();
        FCVnom = new javax.swing.JTextField();
        FCVprenom = new javax.swing.JTextField();
        FCVdate = new javax.swing.JTextField();
        FCVmontant = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        btnRafraichir = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableClient = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        Btn_Annuler = new javax.swing.JButton();
        Btn_Enregistrer = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        TelClient = new javax.swing.JTextField();
        NomClient = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        PrenomClient = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableJournal = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        libelleOperation = new javax.swing.JTextField();
        sensOperation = new javax.swing.JTextField();
        montantOperation = new javax.swing.JTextField();
        compte = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GesVente");

        jPanel1.setBackground(new java.awt.Color(228, 255, 201));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });

        tableRecapitulatif.setModel(new javax.swing.table.DefaultTableModel(
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
        tableRecapitulatif.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tableRecapitulatif);

        recapitulatif.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Mois");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        recapitulatif.add(jRadioButton2);
        jRadioButton2.setText("Années");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel1.setText("Somme des ventes par");

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        jLabel2.setText("Total");

        montant.setFont(new java.awt.Font("Cantarell", 1, 18)); // NOI18N
        montant.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jLabel2)
                        .addGap(96, 96, 96)
                        .addComponent(montant)))
                .addGap(723, 723, 723)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(montant))))
                .addContainerGap(199, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Récapitulatif des Ventes", jPanel1);

        jPanel3.setBackground(new java.awt.Color(228, 255, 201));

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        RCjLabel12.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        RCjLabel12.setText("Nom du client");

        RCvnomVisu.setEditable(false);
        RCvnomVisu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCvnomVisuActionPerformed(evt);
            }
        });

        RCvprenomvisu.setEditable(false);

        RCvidFacturevisu.setEditable(false);

        RCjLabel14.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        RCjLabel14.setText("Montant Réglé");

        RCvmontantvisu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RCvmontantvisuKeyPressed(evt);
            }
        });

        RCjLabel15.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        RCjLabel15.setText("Facture N*");

        RCjLabel17.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        RCjLabel17.setText("Prenom du client");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(RCjLabel12)
                        .addGap(64, 64, 64)
                        .addComponent(RCvnomVisu, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RCjLabel17)
                            .addComponent(RCjLabel15)
                            .addComponent(RCjLabel14))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RCvidFacturevisu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RCvprenomvisu, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RCvmontantvisu, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel12)
                    .addComponent(RCvnomVisu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel17)
                    .addComponent(RCvprenomvisu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel15)
                    .addComponent(RCvidFacturevisu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvmontantvisu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel14))
                .addContainerGap(262, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Visualisation", jPanel11);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RCjLabel1.setText("Liste des factures impayées");

        RCbtnRafraichir.setText("Rafraîchir");
        RCbtnRafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCbtnRafraichirActionPerformed(evt);
            }
        });

        RCtableReglement.setModel(new javax.swing.table.DefaultTableModel(
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
        RCtableReglement.setEnabled(false);
        RCtableReglement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RCtableReglementMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(RCtableReglement);

        RCjLabel9.setText("Date du reglement");

        RCdatedujour.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), new java.util.Date(), java.util.Calendar.AM_PM));

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RCjLabel10.setText("REGLEMENT");

        RCjLabel7.setText("Montant Réglé");

        RCmontantReglement.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RCmontantReglementKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RCmontantReglementKeyReleased(evt);
            }
        });

        RCjLabel11.setText("Mode de Règlement");

        RCButtonGroup.add(RCespeceRadioButton);
        RCespeceRadioButton.setSelected(true);
        RCespeceRadioButton.setText("Espèce");

        RCButtonGroup.add(RCchequeRadioButton);
        RCchequeRadioButton.setText("Chèque");

        RCjLabel13.setText("Montant reçu");

        RCmontantRecu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RCmontantRecuKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                RCmontantRecuKeyReleased(evt);
            }
        });

        RCjLabel18.setText("jLabel14");
        RCjLabel18.setVisible(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(212, 212, 212)
                                .addComponent(RCjLabel10))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(RCjLabel7)
                                .addGap(80, 80, 80)
                                .addComponent(RCmontantReglement, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RCjLabel13)
                            .addComponent(RCjLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(RCjLabel18)
                            .addComponent(RCmontantRecu, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RCespeceRadioButton)
                    .addComponent(RCchequeRadioButton))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RCjLabel10)
                .addGap(40, 40, 40)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel7)
                    .addComponent(RCmontantReglement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel13)
                    .addComponent(RCmontantRecu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCjLabel11)
                    .addComponent(RCjLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RCespeceRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RCchequeRadioButton)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(RCbtnRafraichir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(328, 328, 328))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(RCjLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(RCjLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(RCdatedujour, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCdatedujour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel9))
                .addGap(28, 28, 28)
                .addComponent(RCjLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RCbtnRafraichir, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Nouveau Reglement", jPanel8);

        RCTableReglements.setModel(new javax.swing.table.DefaultTableModel(
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
        RCTableReglements.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RCTableReglementsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(RCTableReglements);

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jPanel10.setVisible(false);

        RCjLabel5.setText("Visualisation des données entrées");

        RCjLabel6.setText("Facture N*");

        RCjLabel4.setText("Téléphone du client");

        RCjLabel8.setText("Montant Réglé");

        RCjLabel3.setText("Prenom du client");

        RCjLabel2.setText("Nom du client");

        RCvnom.setEditable(false);
        RCvnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCvnomActionPerformed(evt);
            }
        });

        RCvprenom.setEditable(false);

        RCvtel.setEditable(false);

        RCvidFacture.setEditable(false);

        RCvmontant.setEditable(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(RCjLabel5))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RCjLabel8)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RCjLabel2)
                                    .addComponent(RCjLabel3)
                                    .addComponent(RCjLabel4)
                                    .addComponent(RCjLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RCvmontant, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(RCvidFacture, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(RCvprenom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                        .addComponent(RCvtel, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(RCvnom)))))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(RCjLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvprenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvtel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvidFacture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel6))
                .addGap(8, 8, 8)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RCvmontant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RCjLabel8))
                .addGap(38, 38, 38))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        RCSuppr.setText("Supprimer");
        buttonGroup1.add(RCSuppr);
        RCSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCSupprActionPerformed(evt);
            }
        });

        RCNouveau.setText("Nouveau");
        buttonGroup1.add(RCNouveau);
        RCNouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCNouveauActionPerformed(evt);
            }
        });

        RCEnre.setText("Enregistrer");
        buttonGroup1.add(RCEnre);
        RCEnre.setEnabled(false);
        RCEnre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCEnreActionPerformed(evt);
            }
        });

        RCModif.setText("Modifier");
        buttonGroup1.add(RCModif);
        RCModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RCModifActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(RCEnre, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(RCModif, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(RCNouveau))
                    .addComponent(RCSuppr, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {RCEnre, RCModif, RCNouveau, RCSuppr});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RCNouveau, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RCEnre, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RCModif, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(RCSuppr, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Reglements Clients", jPanel3);

        jPanel4.setBackground(new java.awt.Color(228, 255, 201));

        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel6.setText("Fournisseur");

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel7.setText("Livraison du ");

        jLabel8.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel8.setText("Montant Réglé");

        rfTNomFour.setEditable(false);

        rfTDate.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel10.setText("Reglement du");

        rfSDateR.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        rfSMont.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(2000.0d), Double.valueOf(0.0d), null, Double.valueOf(25.0d)));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rfTNomFour)
                    .addComponent(rfTDate)
                    .addComponent(rfSDateR, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(rfSMont))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rfTNomFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rfTDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rfSDateR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(rfSMont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(274, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Visualisation des données", jPanel6);

        rfJPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rfJLabel1.setText("Liste des livraisons impayées");

        tableLivraison.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
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
        tableLivraison.setEnabled(false);
        tableLivraison.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tableLivraison);

        rfJButton3.setText("Rafraîchir");
        rfJButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfJButton3ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Montant Réglé");

        montantRegle.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(200.0d), Double.valueOf(200.0d), null, Double.valueOf(25.0d)));

        label.setText("Reste à payer");
        label.setVisible(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(82, 82, 82)
                        .addComponent(montantRegle, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(montantRegle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addComponent(label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dateReglement.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));

        jLabel4.setText("Reglement du ");

        javax.swing.GroupLayout rfJPanel1Layout = new javax.swing.GroupLayout(rfJPanel1);
        rfJPanel1.setLayout(rfJPanel1Layout);
        rfJPanel1Layout.setHorizontalGroup(
            rfJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rfJPanel1Layout.createSequentialGroup()
                .addGroup(rfJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addGroup(rfJPanel1Layout.createSequentialGroup()
                        .addGroup(rfJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rfJButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rfJLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rfJPanel1Layout.createSequentialGroup()
                        .addGap(0, 80, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(80, 80, 80)
                        .addComponent(dateReglement, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        rfJPanel1Layout.setVerticalGroup(
            rfJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rfJPanel1Layout.createSequentialGroup()
                .addGroup(rfJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dateReglement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(rfJLabel1)
                .addGap(18, 18, 18)
                .addComponent(rfJButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Nouveau", rfJPanel1);

        tableRF.setModel(new javax.swing.table.DefaultTableModel(
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
        tableRF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableRFMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableRF);

        jLabel5.setText("Liste des reglements Fournisseurs");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        rfJPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rfbtnEnr.setText("Enregistrer");
        rfbtnEnr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfbtnEnrActionPerformed(evt);
            }
        });

        rfbtnAnnuler.setText("Modifier");
        rfbtnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfbtnAnnulerActionPerformed(evt);
            }
        });

        rfbtnSuppr.setText("Supprimer");
        rfbtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfbtnSupprActionPerformed(evt);
            }
        });

        rfbtnNew.setText("Nouveau");
        rfbtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rfbtnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rfJPanel3Layout = new javax.swing.GroupLayout(rfJPanel3);
        rfJPanel3.setLayout(rfJPanel3Layout);
        rfJPanel3Layout.setHorizontalGroup(
            rfJPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rfJPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rfJPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rfbtnAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rfbtnEnr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rfbtnSuppr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rfbtnNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rfJPanel3Layout.setVerticalGroup(
            rfJPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rfJPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rfbtnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfbtnEnr, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfbtnSuppr, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rfbtnAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rfJPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel5)
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(rfJPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reglement Fournisseur", jPanel4);

        jPanel2.setBackground(new java.awt.Color(228, 255, 201));

        jLabel9.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel9.setText("Liste des Factures ");

        tableFCfactures.setModel(new javax.swing.table.DefaultTableModel(
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
        tableFCfactures.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableFCfactures.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableFCfacturesMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableFCfactures);

        jLabel11.setText("Recherche des factures");

        FCRechDate.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(631211280000L), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        FCRechDate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                FCRechDateStateChanged(evt);
            }
        });
        FCRechDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FCRechDatePropertyChange(evt);
            }
        });
        FCRechDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FCRechDateKeyReleased(evt);
            }
        });

        FCRechNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FCRechNomKeyReleased(evt);
            }
        });

        FCRechPrenom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FCRechPrenomKeyReleased(evt);
            }
        });

        FCRechMontant.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(25.0d)));
        FCRechMontant.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                FCRechMontantStateChanged(evt);
            }
        });
        FCRechMontant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FCRechMontantKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel12.setText("Par date");

        jLabel13.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel13.setText("Par nom Client");

        jLabel14.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel14.setText("Par prenom Client");

        jLabel15.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel15.setText("Montant");

        jButton1.setText("Nouveau");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Supprimer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel16.setText("Nom Client");

        jLabel17.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel17.setText("Prenom Client");

        jLabel18.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel18.setText("Montant Facture");

        jLabel19.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel19.setText("Produits Concernés");

        tableFCProduits.setModel(new javax.swing.table.DefaultTableModel(
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
        tableFCProduits.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tableFCProduits);

        FCVnom.setEditable(false);
        FCVnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FCVnomActionPerformed(evt);
            }
        });

        FCVprenom.setEditable(false);

        FCVdate.setEditable(false);

        jLabel20.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel20.setText("Date Facture");

        btnRafraichir.setText("Rafraîchir");
        btnRafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRafraichirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(FCVmontant, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                    .addComponent(FCVdate, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FCVnom, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FCVprenom, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(266, 266, 266)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)
                                .addComponent(btnRafraichir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(72, 72, 72)))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FCRechMontant, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FCRechDate, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(FCRechNom, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(FCRechPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel16)
                        .addGap(9, 9, 9)
                        .addComponent(FCVnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FCVprenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FCVdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FCVmontant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FCRechDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FCRechNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FCRechPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FCRechMontant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(54, 54, 54)
                        .addComponent(jLabel9)
                        .addGap(320, 320, 320))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(btnRafraichir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))))
        );

        jTabbedPane1.addTab("Factures Clients", jPanel2);

        jPanel7.setBackground(new java.awt.Color(228, 255, 201));

        tableClient.setModel(new javax.swing.table.DefaultTableModel(
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
        tableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableClient);

        jLabel21.setText("Liste des Clients");

        Btn_Annuler.setText("Annuler");
        Btn_Annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AnnulerActionPerformed(evt);
            }
        });

        Btn_Enregistrer.setText("Enregistrer");
        Btn_Enregistrer.setEnabled(false);
        Btn_Enregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_EnregistrerActionPerformed(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel22.setText("Nom");

        jLabel24.setText("Téléphone");

        jLabel23.setText("Prénom");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(NomClient, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PrenomClient, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TelClient, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(NomClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(PrenomClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(TelClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton3.setText("Nouveau");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Supprimer");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Imprimer");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Btn_Annuler)
                                    .addComponent(jButton3)
                                    .addComponent(Btn_Enregistrer)
                                    .addComponent(jButton4))
                                .addGap(70, 70, 70)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Btn_Annuler, Btn_Enregistrer, jButton3, jButton4, jButton5});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jButton3)
                        .addGap(27, 27, 27)
                        .addComponent(Btn_Enregistrer)
                        .addGap(30, 30, 30)
                        .addComponent(Btn_Annuler)
                        .addGap(33, 33, 33)
                        .addComponent(jButton4)
                        .addGap(28, 28, 28)
                        .addComponent(jButton5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("     Client      ", jPanel7);

        tableJournal.setModel(new javax.swing.table.DefaultTableModel(
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
        tableJournal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableJournalMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tableJournal);

        jLabel25.setText("Libelle Opération");

        jLabel26.setText("Sens Opération");

        jLabel27.setText("Montant");

        jLabel28.setText("Compte");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addGap(252, 252, 252)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(libelleOperation)
                            .addComponent(sensOperation)
                            .addComponent(montantOperation)
                            .addComponent(compte, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(libelleOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(sensOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(montantOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(compte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
        );

        jTabbedPane1.addTab("Journal", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
remplirRecapitulatifMois();        // TODO add your handling code here:
repaint();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
remplirRecapitulatifAnnee();
repaint();// TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained

      
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1FocusGained

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
      // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseExited

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
     // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentShown

    private void rfJButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfJButton3ActionPerformed

        remplirLivraison();
        // TODO add your handling code here:
    }//GEN-LAST:event_rfJButton3ActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    private void rfbtnEnrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfbtnEnrActionPerformed

        int ind = tableLivraison.getSelectedRow();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une livraison");
            return;
        }
        Livraison liv = (Livraison) rfLivraisons.get(ind);
        double mont = (double) montantRegle.getValue();
        if (mont > (double) rfMontantRestant.get(ind)) {
            JOptionPane.showMessageDialog(null, "Le montant entré est supérieur au montant restant");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(dateReglement.getValue());
        ReglementFournisseur rf = new ReglementFournisseur(date, mont, liv);
        rf.saveReglementFour();
        Compte c=new Compte(2,"Fournisseur");
            Operation o=new Operation("Reglement Fournisseur", date,mont, "Credit", c);
            o.saveOperation();
        Compte c1=new Compte(5,"Caisse");
            Operation o1=new Operation("Reglement Fournisseur", date,mont, "Debit", c1);
            o1.saveOperation();
        Compte c2=new Compte(3,"Achat");
            Operation o2=new Operation("Reglement Fournisseur", date,mont, "Credit", c2);
            o2.saveOperation();
           journal();              

        label.setText("Reste à payer " + ((double) rfMontantRestant.get(ind) - mont));
        label.setVisible(true);
        tableLivraison.setEnabled(false);
        rfbtnEnr.setEnabled(false);
        remplirLivraison();
        remplirReglement();
        // TODO add your handling code here:
    }//GEN-LAST:event_rfbtnEnrActionPerformed

    private void rfbtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfbtnSupprActionPerformed

        int ind=tableRF.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le reglement à supprimer");
            return;
        }
        ReglementFournisseur reg= (ReglementFournisseur) rfReglementFournisseur.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer cet enregistrement")==0){
            reg.deleteRegelementFournisseur();
            remplirLivraison();
            remplirReglement();
        }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_rfbtnSupprActionPerformed

    private void rfbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfbtnNewActionPerformed

        jTabbedPane2.setSelectedIndex(1);
        rfbtnEnr.setEnabled(true);
        tableLivraison.setEnabled(true);
       
        // TODO add your handling code here:
    }//GEN-LAST:event_rfbtnNewActionPerformed

    private void tableRFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRFMouseClicked

        
        int ind=tableRF.getSelectedRow();
        ReglementFournisseur reg= (ReglementFournisseur) rfReglementFournisseur.get(ind);
        rfTNomFour.setText(rfFournisseurRegle.get(ind).toString());
        rfTDate.setText(reg.getLivraison().getDateLiivraison());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            rfSDateR.setValue(format.parse(reg.getDateReglement()));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        rfSMont.setValue(reg.getMontantReglementFour());
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableRFMouseClicked

    private void rfbtnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rfbtnAnnulerActionPerformed

        int ind=tableRF.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir une ligne du tableau");
            return;
        }
        ReglementFournisseur reg= (ReglementFournisseur) rfReglementFournisseur.get(ind);
        try {
            rfSDateR.commitEdit();
            rfSMont.commitEdit();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            reg.setDateReglement(format.format(rfSDateR.getValue()));
            reg.setMontantReglementFour((double)rfSMont.getValue());
              JOptionPane.showMessageDialog(null, reg.getMontantReglementFour());
            reg.updateRegelementFournisseur();
            remplirReglement();
            remplirLivraison();
        // TODO add your handling code here:
    }//GEN-LAST:event_rfbtnAnnulerActionPerformed

    private void RCbtnRafraichirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCbtnRafraichirActionPerformed

        remplirFacture();
        // TODO add your handling code here:
    }//GEN-LAST:event_RCbtnRafraichirActionPerformed

    private void RCtableReglementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RCtableReglementMouseClicked

        int ind = RCtableReglement.getSelectedRow();
        if(ind==-1){
            return;
            
        }
        RCvnom.setText(RCnomClient.get(ind).toString());
        RCvprenom.setText(RCprenomClient.get(ind).toString());
        RCvtel.setText(RCtelClient.get(ind).toString());
        RCvidFacture.setText(RCidfactureClient.get(ind).toString());

        // TODO add your handling code here:
    }//GEN-LAST:event_RCtableReglementMouseClicked

    private void RCmontantReglementKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RCmontantReglementKeyPressed




        // TODO add your handling code here:
}//GEN-LAST:event_RCmontantReglementKeyPressed

    private void RCmontantReglementKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RCmontantReglementKeyReleased

        this.RCvmontant.setText(this.RCmontantReglement.getText());
        // TODO add your handling code here:
    }//GEN-LAST:event_RCmontantReglementKeyReleased

    private void RCmontantRecuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RCmontantRecuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RCmontantRecuKeyPressed

    private void RCmontantRecuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RCmontantRecuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_RCmontantRecuKeyReleased

    private void RCvnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCvnomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RCvnomActionPerformed

    private void RCvnomVisuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCvnomVisuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RCvnomVisuActionPerformed

    private void RCEnreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCEnreActionPerformed
        try {
            SimpleDateFormat from=new SimpleDateFormat("yyyy-MM-dd");
            String d=from.format( this.RCdatedujour.getValue());
            if(RCvidFacture.getText().isEmpty() ){
                JOptionPane.showMessageDialog(null,"Veuillez choisir une facture");
                return;
            }
           try{
               if(Double.parseDouble(RCmontantReglement.getText())<=0){
                JOptionPane.showMessageDialog(null,"Veuillez entrer un montant valide");
                return;
            }
               
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(null, "Veuillez entrer un montant valide");
               return;
           }
           try{
               if(Double.parseDouble(RCmontantRecu.getText())<=0){
                JOptionPane.showMessageDialog(null,"Veuillez entrer un montant reçu valide");
                return;
            }
               
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(null, "Veuillez entrer un montant reçu valide");
               return;
           }
            if(Double.parseDouble(RCmontantRecu.getText())<Double.parseDouble(RCmontantReglement.getText())){
                JOptionPane.showMessageDialog(null,"Le montant reçu est inférieur au montant réglé");
                return;
            }
           double restePayer=(double) RCmontantFacture.get(RCtableReglement.getSelectedRow());
           if(Double.parseDouble(RCmontantReglement.getText())>restePayer){
               JOptionPane.showMessageDialog(null,"Le montant entrer dépasse le montant a payer");
               return;
           }
           RCjLabel18.setText("Relicat  :"+(Double.parseDouble(RCmontantRecu.getText())-Double.parseDouble(RCmontantReglement.getText())));
           RCjLabel18.setVisible(true); 
           ModeReglement modeR;
            if(RCespeceRadioButton.isSelected()){
            modeR=new ModeReglement(1,"Espèce");    
            }
            else{
                modeR=new ModeReglement(2,"Chèque");
            }
            
            FactureClient facture=(FactureClient) RCfactureClient.get(RCtableReglement.getSelectedRow());
            Reglement reglement=new Reglement(Double.parseDouble(RCmontantReglement.getText()), facture, modeR);
            reglement.setDateReglement(d);
            reglement.saveReglement();
            Compte c=new Compte(1,"Client");
            Operation o=new Operation("Reglement Client", d,Double.parseDouble(RCmontantReglement.getText()), "Credit", c);
            o.saveOperation();
              Compte c1=new Compte(5,"Caisse");
            Operation o1=new Operation("Reglement Client", d,Double.parseDouble(RCmontantReglement.getText()), "Credit", c);
            o1.saveOperation();         
            journal();
            
        try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/vente/Recu.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            Map param = new HashMap();
            

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

            // TODO add your handling code here:
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(null, ex);
        }
        RCtableReglement.setEnabled(false);
        RCEnre.setEnabled(false);
        jPanel10.setVisible(false);
        remplirFacture();
        
        remplirTableReglements();
        // TODO add your handling code here:
    }//GEN-LAST:event_RCEnreActionPerformed

    private void RCNouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCNouveauActionPerformed

        jTabbedPane3.setSelectedIndex(1);
        RCtableReglement.setEnabled(true);
        RCEnre.setEnabled(true);
        jPanel10.setVisible(true);
       
        // TODO add your handling code here:
    }//GEN-LAST:event_RCNouveauActionPerformed

    private void RCTableReglementsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RCTableReglementsMouseClicked

        int ind=RCTableReglements.getSelectedRow();
        RCvnomVisu.setText(RCnomReglement.get(ind).toString());
        RCvprenomvisu.setText(RCprenomReglement.get(ind).toString());
        RCvidFacturevisu.setText(RCnumFactureReglement.get(ind).toString());
        RCvmontantvisu.setText(RCmontantTReglement.get(ind).toString());
        
        // TODO add your handling code here:
    }//GEN-LAST:event_RCTableReglementsMouseClicked

    private void RCvmontantvisuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RCvmontantvisuKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_RCvmontantvisuKeyPressed

    private void RCModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCModifActionPerformed

        int ind=RCTableReglements.getSelectedRow();
        try{
            Double.parseDouble(RCvmontantvisu.getText());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Veuillez entrer un montant valide svp");
        }
        Reglement reg=(Reglement) RCreglement.get(ind);
        reg.setMontantReglement(Double.parseDouble(RCvmontantvisu.getText()));
        reg.updateReglement();
        remplirFacture();
        remplirTableReglements();
        // TODO add your handling code here:
    }//GEN-LAST:event_RCModifActionPerformed

    private void RCSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RCSupprActionPerformed

        
        int ind=RCTableReglements.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le reglement a supprimer svp!");
            return;
        }
        Reglement reg=(Reglement) RCreglement.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de vouloir supprimer ce reglement")==0){
            reg.deleteReglement();
            remplirFacture();
            remplirTableReglements();
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_RCSupprActionPerformed

    private void FCVnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FCVnomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FCVnomActionPerformed

    private void tableFCfacturesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFCfacturesMouseClicked

        FCProduit.clear();
        FCqteProduit.clear();
        FCtypeAction.clear();
        FCtypeTraitement.clear();
        int ind=tableFCfactures.getSelectedRow();
        FCVnom.setText(FCnoms.get(tableFCfactures.getSelectedRow()).toString());
        FCVprenom.setText(FCprenomns.get(ind).toString());
        FCVdate.setText(FCdateFacture.get(ind).toString());
        FCVmontant.setText(FCmontantTFacture.get(ind).toString());
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,c.qteFacture,c.typeAction,c.typeTraitement from concerner c,planche p,formePlanche f,longeurPlanche l,typeBois t where p.idTypeBois=t.idTypeBois and c.idPlanche =p.idPlanche and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and c.idFactureClient="+FCnumFacture.get(ind));
            while(r.next()){
                FCProduit.add(r.getString(1)+" "+r.getString(2)+" "+r.getInt(3));
                
                FCqteProduit.add(r.getInt(4));
                FCtypeAction.add(r.getString(5));
                FCtypeTraitement.add(r.getString(6));
            }
            ResultSet rsm=s.executeQuery("select libelleModele,count(idMeuble) from modele m,meuble b where m.idModele=b.idModele and b.idFactureClient="+FCnumFacture.get(ind)+" group by m.idModele");
            while(rsm.next()){
                FCProduit.add(rsm.getString(1));
                
                FCqteProduit.add(rsm.getInt(2));
                FCtypeAction.add("Vente");
                FCtypeTraitement.add("Aucun");
            }
            
        }catch(Exception ex){
            
        }
         DefaultTableModel mod=new DefaultTableModel();
         mod.addColumn("Produit",FCProduit.toArray());
         mod.addColumn("Qte", FCqteProduit.toArray());
         mod.addColumn("Type Traitement",FCtypeTraitement.toArray());
         mod.addColumn("Action",FCtypeAction.toArray() );
         tableFCProduits.setModel(mod);
         // TODO add your handling code here:
    }//GEN-LAST:event_tableFCfacturesMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        Fen_FactureClient fc=new Fen_FactureClient();
        fc.setVisible(true);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int ind=tableFCfactures.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir la facture a supprimer");
            return;
        }
        else{
            if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer cette facture ?")==0){
            FactureClient f=(FactureClient) FCfactures.get(ind);
            f.deleteFactureClient();
            JOptionPane.showMessageDialog(null,"Facture supprimée avec succès");
                
            }
            remplirTableFactures();
           tableFCProduits.removeAll();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnRafraichirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRafraichirActionPerformed
remplirFacture();        // TODO add your handling code here:
    }//GEN-LAST:event_btnRafraichirActionPerformed

    private void Btn_AnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AnnulerActionPerformed
        NomClient.setText(null);
        PrenomClient.setText(null);
        TelClient.setText(null);
        Btn_Enregistrer.setEnabled(false);
    }//GEN-LAST:event_Btn_AnnulerActionPerformed

    private void Btn_EnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_EnregistrerActionPerformed

        Client client = new Client(NomClient.getText(), PrenomClient.getText(), TelClient.getText());
        if (!client.verifSaisie()) {
            JOptionPane.showMessageDialog(rootPane, "Veuillez remplir tous les champs obligatoires svp.");


        } else {
            try {
                if (client.verifExistence()) {
                    JOptionPane.showMessageDialog(rootPane, "Ce client existe déjà,veuillez en saisir un autre");
                } else {
                    client.saveClient();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }
           Btn_Enregistrer.setEnabled(false);
    }//GEN-LAST:event_Btn_EnregistrerActionPerformed

    private void tableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientMouseClicked

        int ind=tableClient.getSelectedRow();
        Client cli= (Client) client.get(ind);
        NomClient.setText(cli.getNomClient());
        PrenomClient.setText(cli.getPrenomClient());
        TelClient.setText(cli.getTelClient());
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableClientMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        NomClient.setText(null);
        PrenomClient.setText(null);
        TelClient.setText(null);
        Btn_Enregistrer.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int ind=tableClient.getSelectedRow();
        Client c=(Client) client.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer ce client ?")==0){
            c.deleteClient();
            try {
                remplirClients();
            } catch (SQLException ex) {
                Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void FCRechDateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FCRechDateKeyReleased
        try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_FCRechDateKeyReleased

    private void FCRechNomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FCRechNomKeyReleased

        try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_FCRechNomKeyReleased

    private void FCRechPrenomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FCRechPrenomKeyReleased
try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FCRechPrenomKeyReleased

    private void FCRechMontantKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FCRechMontantKeyReleased
try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FCRechMontantKeyReleased

    private void FCRechMontantStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FCRechMontantStateChanged
try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FCRechMontantStateChanged

    private void FCRechDateStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FCRechDateStateChanged
try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_FCRechDateStateChanged

    private void FCRechDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FCRechDatePropertyChange
try {
            FCRechDate.commitEdit();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            FCRechMontant.commitEdit();
            remplirRechFacture(format.format(FCRechDate.getValue()), (double)FCRechMontant.getValue());
            // TODO add your handling code here:
        } catch (ParseException ex) {
            Logger.getLogger(Menu_Vente.class.getName()).log(Level.SEVERE, null, ex);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_FCRechDatePropertyChange

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
         
        try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/vente/listeClients.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            Map param = new HashMap();
            

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);

            //JasperExportManager.exportReportToHtmlFile(jp,"/home/eeeee");
            // JasperExportManager.exportReportToPdfFile(jp,"/home/el-diablo/NetBeansProjects/SOGEMEE/src/vente/etat4.jrxml");
            // TODO add your handling code here:
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tableJournalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableJournalMouseClicked

        int ind=tableJournal.getSelectedRow();
        libelleOperation.setText(tableJournal.getValueAt(ind,3).toString());
        compte.setText(tableJournal.getValueAt(ind,0).toString());
        sensOperation.setText(tableJournal.getValueAt(ind,2).toString());
        montantOperation.setText(tableJournal.getValueAt(ind, 1).toString());
        // TODO add your handling code here:
    }//GEN-LAST:event_tableJournalMouseClicked

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
            java.util.logging.Logger.getLogger(Menu_Vente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Vente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Vente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Vente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Menu_Vente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Annuler;
    private javax.swing.JButton Btn_Enregistrer;
    private javax.swing.JSpinner FCRechDate;
    private javax.swing.JSpinner FCRechMontant;
    private javax.swing.JTextField FCRechNom;
    private javax.swing.JTextField FCRechPrenom;
    private javax.swing.JTextField FCVdate;
    private javax.swing.JTextField FCVmontant;
    private javax.swing.JTextField FCVnom;
    private javax.swing.JTextField FCVprenom;
    private javax.swing.JTextField NomClient;
    private javax.swing.JTextField PrenomClient;
    private javax.swing.ButtonGroup RCButtonGroup;
    private javax.swing.JButton RCEnre;
    private javax.swing.JButton RCModif;
    private javax.swing.JButton RCNouveau;
    private javax.swing.JButton RCSuppr;
    private javax.swing.JTable RCTableReglements;
    private javax.swing.JButton RCbtnRafraichir;
    private javax.swing.JRadioButton RCchequeRadioButton;
    private javax.swing.JSpinner RCdatedujour;
    private javax.swing.JRadioButton RCespeceRadioButton;
    private javax.swing.JLabel RCjLabel1;
    private javax.swing.JLabel RCjLabel10;
    private javax.swing.JLabel RCjLabel11;
    private javax.swing.JLabel RCjLabel12;
    private javax.swing.JLabel RCjLabel13;
    private javax.swing.JLabel RCjLabel14;
    private javax.swing.JLabel RCjLabel15;
    private javax.swing.JLabel RCjLabel17;
    private javax.swing.JLabel RCjLabel18;
    private javax.swing.JLabel RCjLabel2;
    private javax.swing.JLabel RCjLabel3;
    private javax.swing.JLabel RCjLabel4;
    private javax.swing.JLabel RCjLabel5;
    private javax.swing.JLabel RCjLabel6;
    private javax.swing.JLabel RCjLabel7;
    private javax.swing.JLabel RCjLabel8;
    private javax.swing.JLabel RCjLabel9;
    private javax.swing.JFormattedTextField RCmontantRecu;
    private javax.swing.JFormattedTextField RCmontantReglement;
    private javax.swing.JTable RCtableReglement;
    private javax.swing.JTextField RCvidFacture;
    private javax.swing.JTextField RCvidFacturevisu;
    private javax.swing.JTextField RCvmontant;
    private javax.swing.JTextField RCvmontantvisu;
    private javax.swing.JTextField RCvnom;
    private javax.swing.JTextField RCvnomVisu;
    private javax.swing.JTextField RCvprenom;
    private javax.swing.JTextField RCvprenomvisu;
    private javax.swing.JTextField RCvtel;
    private javax.swing.JTextField TelClient;
    private javax.swing.JButton btnRafraichir;
    private javax.swing.ButtonGroup buttonGroup1;
    private java.awt.Canvas canvas1;
    private javax.swing.JTextField compte;
    private javax.swing.JSpinner dateReglement;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel label;
    private javax.swing.JTextField libelleOperation;
    private javax.swing.JLabel montant;
    private javax.swing.JTextField montantOperation;
    private javax.swing.JSpinner montantRegle;
    private javax.swing.ButtonGroup recapitulatif;
    private javax.swing.JButton rfJButton3;
    private javax.swing.JLabel rfJLabel1;
    private javax.swing.JPanel rfJPanel1;
    private javax.swing.JPanel rfJPanel3;
    private javax.swing.JSpinner rfSDateR;
    private javax.swing.JSpinner rfSMont;
    private javax.swing.JTextField rfTDate;
    private javax.swing.JTextField rfTNomFour;
    private javax.swing.JButton rfbtnAnnuler;
    private javax.swing.JButton rfbtnEnr;
    private javax.swing.JButton rfbtnNew;
    private javax.swing.JButton rfbtnSuppr;
    private javax.swing.JTextField sensOperation;
    private javax.swing.JTable tableClient;
    private javax.swing.JTable tableFCProduits;
    private javax.swing.JTable tableFCfactures;
    private javax.swing.JTable tableJournal;
    private javax.swing.JTable tableLivraison;
    private javax.swing.JTable tableRF;
    private javax.swing.JTable tableRecapitulatif;
    // End of variables declaration//GEN-END:variables
}
