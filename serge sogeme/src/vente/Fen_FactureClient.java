/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vente;

import fabrication.Meuble;
import fabrication.Modele;
import approvisionnement.FormePlanche;
import approvisionnement.LongueurPlanche;
import approvisionnement.Planche;
import approvisionnement.Type_Bois;
import gesperson.Agent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author el-diablo
 */
@SuppressWarnings("serial")
public class Fen_FactureClient extends javax.swing.JFrame {

    /**
     * Creates new form Fen_FactureClient
     */
    
    
    private ArrayList client=new ArrayList();
    private ArrayList nomClient=new ArrayList();
    private ArrayList prenomClient=new ArrayList();
    private ArrayList telClient=new ArrayList();
    
    private ArrayList type=new ArrayList();
    private ArrayList lType=new ArrayList();
    private ArrayList forme=new ArrayList();
    private ArrayList lForme=new ArrayList();
    private ArrayList longueur=new ArrayList();
    private ArrayList lLongueur=new ArrayList();
    private ArrayList planche=new ArrayList();
    private ArrayList qtePlancheFinal=new ArrayList();
    private ArrayList qtePlanche= new ArrayList();
    private ArrayList plancheFinal= new ArrayList();
    private ArrayList plancheFinalTraitement= new ArrayList();
    private ArrayList typeAction= new ArrayList();
    
    private ArrayList modele=new ArrayList();
    private ArrayList lModele=new ArrayList();
    private ArrayList meuble=new ArrayList();
    private ArrayList refmeuble=new ArrayList();
    private ArrayList categorie=new ArrayList();
    private ArrayList meubleFinal= new ArrayList();
    private ArrayList qteMeuble=new ArrayList();
    private ArrayList qteMeubleFinal=new ArrayList();
   
    private ArrayList produit=new ArrayList();
    private ArrayList qteproduit=new ArrayList();
    private ArrayList prixProduit=new ArrayList();
    private ArrayList prixTotalProduit=new ArrayList();
    
    private ArrayList Traitementproduit=new ArrayList();
    private ArrayList servicePlanche=new ArrayList();
    private ArrayList typeServicePlanche=new ArrayList();
    private ArrayList qteServicePlanche=new ArrayList();

    public Fen_FactureClient() {
        
        initComponents();
        Date dat=new Date();
        SimpleDateFormat forma=new SimpleDateFormat("dd-MM-yyyy");
        datedujour.setText(forma.format(dat));
     try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idFactureClient+1 from factureClient order by idFactureClient desc limit 0,1");
            r.next();
            String a=r.getObject(1).toString();
            idFact.setText(a);
        }catch(Exception ex){
            
        }
        try {
            remplirClients();
        } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null,ex);
        }
        remplirPlanches();
    
    }

    @SuppressWarnings("unchecked")
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
      
        tableClients.setModel(model);
      
      
    }
    
    
    @SuppressWarnings("unchecked")
    public void ajouterVente(){
        DefaultTableModel mod=new DefaultTableModel();
        int ind=tablePlanche.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir un produit de la liste");
        return;
        }
       int qte=(int) quantite.getValue();
        if(quantite.getValue()==0){
            JOptionPane.showMessageDialog(null, "Veuillez entrer une quantité valide");
        return;
        }
        if(venteRadioButton.isSelected()){
            if(radPlanches.isSelected()){
                String plan="Planche ";
                String choix = new String();
                Planche p=(Planche) planche.get(ind);
                if(traitementRadioButton.isSelected()){
                    choix=traitementRadioButton.getText();
                            }
                    if(lissageRadioButton.isSelected()){
                     choix="Lissage";}
                    if(traitementLissageRadioButton.isSelected()){
                     choix="Traitement et Lissage";
                    }
                    if(aucunRadioButton.isSelected()){
                     choix=aucunRadioButton.getText();}
                plan=plan+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+" mètres";
                    for(int i=0;i<produit.size();i++){
                        if(produit.get(i).toString().equals(plan) && Traitementproduit.get(i).toString().equals(choix)){
                          JOptionPane.showMessageDialog(null,"Ce produit a déjà été sélectionné!");
                          return;  
                        }
                    }
              
                    plancheFinal.add(p);
                    if(traitementRadioButton.isSelected()){
                      plancheFinalTraitement.add("Traitement");
                      Traitementproduit.add("Traitement");
                    }
                    
                    if(lissageRadioButton.isSelected()){
                       plancheFinalTraitement.add("Lissage");
                       Traitementproduit.add("Lissage");
                    }
                    if(traitementLissageRadioButton.isSelected()){
                        plancheFinalTraitement.add("Traitement et Lissage");
                        Traitementproduit.add("Traitement et Lissage");
                    }
                    if(aucunRadioButton.isSelected()){
                        plancheFinalTraitement.add("Aucun");
                        Traitementproduit.add("Aucun");
                        
                    }
                    prixProduit.add(prixU.getValue());
                    produit.add(plan);
                    qteproduit.add(qte);
                    prixTotalProduit.add(((double)prixU.getValue())*qte);
                    qtePlancheFinal.add(qte);
                
             }
            else{
                
                String meu="Meuble ";
                Modele m= (Modele) modele.get(ind);
                if(meubleFinal.contains(m)){
                    JOptionPane.showMessageDialog(null,"Ce produit a déjà été sélectionné");
                    return;
                }
                int qtemax=(int) tablePlanche.getValueAt(ind, 2);
                if(qte>qtemax){
                    JOptionPane.showMessageDialog(null,"La quantité saisie n'est pas disponible en stock");
                    return;
                }
                meubleFinal.add(m);
                meu=meu+categorie.get(ind).toString()+" "+m.getLibelleModele();
                produit.add(meu);
                qteMeubleFinal.add(quantite.getValue());
                qteproduit.add(qte);
                Traitementproduit.add("Aucun");
                prixProduit.add(prixU.getValue());
                prixTotalProduit.add(((double)prixU.getValue())*((int)quantite.getValue()));
            }
            typeAction.add("Vente");
        }
        mod.addColumn("Action", typeAction.toArray());
        mod.addColumn("Désignation",produit.toArray());
        mod.addColumn("Type de Traitement",Traitementproduit.toArray());
        mod.addColumn("Quantité",qteproduit.toArray());
        mod.addColumn("PU",prixProduit.toArray());
        mod.addColumn("PT",prixTotalProduit.toArray());
        tableChange2.setModel(mod);
        
    }
    
    
    @SuppressWarnings("unchecked")
    public void ajouterService(){
         int ind=tablePlanche.getSelectedRow();
            if(ind==-1){
                JOptionPane.showMessageDialog(null,"Veuillez choisir une ligne de la table");
            }
            Planche p=(Planche) planche.get(ind);
            
            String plan="Planche "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur();
            
            String trait=new String();
            if(traitementRadioButton.isSelected()){
                trait="Traitement";
            }
             if(lissageRadioButton.isSelected()){
                trait="Lissage";
            }
             if(traitementLissageRadioButton.isSelected()){
                trait="Traitement et Lissage";
            }
            
             for(int i=0;i<produit.size();i++){
                 if(produit.get(i).toString().equals(plan) && Traitementproduit.get(i).toString().equals(trait) && typeAction.get(i).equals("Service")){
                     JOptionPane.showMessageDialog(null,"Ce produit a déjà été sélectioné");
                     return;
                 }
             }
             servicePlanche.add(p);
             produit.add(plan);
             qteServicePlanche.add(quantite.getValue());
             typeAction.add("Service");
             Traitementproduit.add(trait);
             typeServicePlanche.add(trait);
             qteproduit.add(quantite.getValue());
               prixProduit.add(prixU.getValue());
               Object pu=prixU.getValue();
                prixTotalProduit.add(Double.parseDouble(pu.toString())*(int)quantite.getValue());
             DefaultTableModel mod=new DefaultTableModel();
              mod.addColumn("Action", typeAction.toArray());
             mod.addColumn("Désignation",produit.toArray());
             mod.addColumn("Type de traitement", Traitementproduit.toArray());
             mod.addColumn("Quantité", qteproduit.toArray());
             mod.addColumn("PU",prixProduit.toArray());
             mod.addColumn("PT",prixTotalProduit.toArray());
        
             tableChange2.setModel(mod);
        
    }
    
    @SuppressWarnings("unchecked")
    public void remplirPlancheRecherche(String typebois,String libeforme,int vallongueur){
                type.clear();
                lType.clear();
                forme.clear();
                lForme.clear();
                longueur.clear();
                lLongueur.clear();
                planche.clear();
                
                
                if(typebois.isEmpty()){
                    typebois="%";
                }
                else{
                typebois="%"+typebois+"%";    
                }
                if(libeforme.isEmpty()){
                    libeforme="%";
                }else{
                 libeforme="%"+libeforme+"%";    
                }
                
        
        try {
            Statement sp=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet rsp=sp.executeQuery("select t.idTypeBois, t.libelleTypeBois, f.idFormePlanche, f.libelleFormePlanche, l.idLongeurPlanche, l.valeurLongeur, p.idPlanche,p.coutTraitement,p.coutLissage,p.prixVentePlanche from typeBois t, longeurPlanche l, formePlanche f, planche p where t.idTypeBois=p.idTypeBois and f.idFormePlanche=p.idFormePlanche and l.idLongeurPlanche=p.idLongeurPlanche and p.suppr=0 and t.libelleTypeBois like '"+typebois+"' and f.libelleFormePlanche like '"+libeforme+"' and l.valeurLongeur>="+vallongueur);
            
            while(rsp.next())
            {
                Type_Bois tb=new Type_Bois(rsp.getInt(1),rsp.getString(2));
                type.add(tb);
                lType.add(tb.getLibelleTypeBois());
                
                FormePlanche fp=new FormePlanche(rsp.getInt(3), rsp.getString(4));
                forme.add(fp);
                lForme.add(fp.getLibeFormPlanche());
                
                LongueurPlanche lp=new LongueurPlanche(rsp.getInt(5),rsp.getInt(6));
                longueur.add(lp);
                lLongueur.add(lp.getValeurLongueur());
                
                Planche p=new Planche(rsp.getInt(7), fp, tb, lp,rsp.getInt(8),rsp.getInt(9));
                 p.setPrixVentePlanche(rsp.getFloat(10));
                planche.add(p);
            }
            
            DefaultTableModel model1=new DefaultTableModel();
            model1.addColumn("Type de bois",lType.toArray());
            model1.addColumn("Forme",lForme.toArray());
            model1.addColumn("Longueur",lLongueur.toArray());
            tablePlanche.setModel(model1);
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    
    public void retirer(){
        
        int ind=tableChange2.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le produit a retirer de la commande");
            return;
        }
     
        String prod=(String) produit.get(ind);
        if(typeAction.get(ind).toString().equals("Vente")){
            
         String choix = new String();
         choix=(String) tableChange2.getValueAt(ind,2);
        
        if(!prod.startsWith("Meuble")){
            JOptionPane.showMessageDialog(null,prod);
            for(int i=0;i<plancheFinal.size();i++){
            Planche p=(Planche) plancheFinal.get(i);
                String plan="Planche "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+" mètres";
                
                
                if(plan.contains(prod) && plancheFinalTraitement.get(i).toString().equals(choix)){
                   // JOptionPane.showMessageDialog(null,p.getTypebois().getLibelleTypeBois()+"   "+plancheFinalTraitement.get(i).toString());
                    plancheFinal.remove(i);
                    plancheFinalTraitement.remove(i);
                   qtePlancheFinal.remove(i);
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(null,prod);
           for(int i=0;i<meubleFinal.size();i++){
            Modele m= (Modele) meubleFinal.get(i);
           String meu=m.getLibelleModele();
                
            if(produit.get(ind).toString().contains(meu)){
                
               JOptionPane.showMessageDialog(null, m.getLibelleModele());
                meubleFinal.remove(i);
                qteMeubleFinal.remove(i);
               
           }
            }
           
        }
        }//fin de retrait de vente
        else{
            for(int i=0;i<servicePlanche.size();i++){
                
                Planche p=(Planche) servicePlanche.get(i);
                String plan="Planche "+p.getTypebois().getLibelleTypeBois()+" "+p.getForme().getLibeFormPlanche()+" "+p.getLongueur().getValeurLongueur()+" mètres";
                String choix = new String();
         choix=(String) tableChange2.getValueAt(ind,2);
                
                if(plan.contains(prod) && typeServicePlanche.get(i).toString().equals(choix)){
                    JOptionPane.showMessageDialog(null,p.getTypebois().getLibelleTypeBois()+"   "+typeServicePlanche.get(i).toString());
                    servicePlanche.remove(i);
                    typeServicePlanche.remove(i);
                    qteServicePlanche.remove(i);
                }
            }
        }
        typeAction.remove(ind);
        produit.remove(ind);
        qteproduit.remove(ind);
        Traitementproduit.remove(ind);
          prixProduit.remove(ind);
                prixTotalProduit.remove(ind);
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Type d'Action",typeAction.toArray());
        mod.addColumn("Désignation",produit.toArray());
        mod.addColumn("Type de Traitement",Traitementproduit.toArray());
        mod.addColumn("Quantité commandée", qteproduit.toArray());
        tableChange2.setModel(mod);
        
    }
    public void enregistrerFact(){
        
        if(produit.isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez choisir un produit");
            return;
        }
        double montantTotal=0;
        for(int i=0;i<prixTotalProduit.size();i++){
            montantTotal+=(double) prixTotalProduit.get(i);
        }
           Client clien=new Client();
        if(clienExistenceCheckBox.isSelected()){
                Client client=new Client(nom.getText(),prenom.getText(),telephone.getText());
      if(!client.verifSaisie())
        {
            JOptionPane.showMessageDialog(rootPane, "Veuillez remplir tous les champs concernant le client SVP.");
        return;
        }   
      else{
            try {
            if(client.verifExistence())
                {
                 JOptionPane.showMessageDialog(rootPane,"Ce client existe déjà,veuillez le choisir dans la liste SVP");   
                return;
                }
            else {
                client.saveClient();
                clien=client;
                clien.setIdClient(clien.rechercheID());
            }
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex);
        }
            
      }
    }
        else{
              int indC=tableClients.getSelectedRow();
            
         clien=(Client) client.get(indC);   
        }
         
        Agent a=new Agent();
        a.setIdAgent(11);
        Date dat=new Date();
        SimpleDateFormat forma=new SimpleDateFormat("yyyy-MM-dd");
        FactureClient facture=new FactureClient(forma.format(dat), 0, a, clien,montantTotal);
        facture.saveFactureClient();
         Compte c=new Compte(4,"Vente");
            Operation o=new Operation("Vente",forma.format(dat),montantTotal, "Crédit", c);
            o.saveOperation();
          
        facture.setIdFactureClient(facture.rechercheID());
        for(int i=0;i<plancheFinal.size();i++){
        Planche p=(Planche) plancheFinal.get(i);
        String typeT=(String) plancheFinalTraitement.get(i);
           
           Concerner concer=new Concerner(p, facture, (int)qtePlancheFinal.get(i),typeT,"Vente" );
           concer.saveConcerner();
           
       }
        
        for(int i=0;i<servicePlanche.size();i++){
        Planche p=(Planche) servicePlanche.get(i);
        String typeT=(String) typeServicePlanche.get(i);
        Concerner concer=new Concerner(p, facture,(int)qteServicePlanche.get(i),typeT,"Service" );
        concer.saveConcerner();
      }
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            for(int i=0;i<meubleFinal.size();i++){
             Modele m=(Modele) meubleFinal.get(i);
            int nb=(int) qteMeubleFinal.get(i);
//            JOptionPane.showMessageDialog(null,nb);
            for(int y=0;y<nb;y++){
            //JOptionPane.showMessageDialog(null,m.getIdModele());
            ResultSet r=s.executeQuery("Select idMeuble from meuble where idFactureClient=0 and idModele="+m.getIdModele());
            r.next();
            Meuble meuble=new Meuble(r.getInt(1),m, facture);
            meuble.UpdateMeuble(); 
            }
            }
            
        }catch(Exception ex){
            
        }
        
    }
    
     public void annuler(){
        prixProduit.clear();
        qteServicePlanche.clear();
        type.clear();
        lType.clear();
        forme.clear();
        lForme.clear();
        longueur.clear();
        lLongueur.clear();
        planche.clear();
        qtePlancheFinal.clear();
        qtePlanche.clear();
        plancheFinal.clear();
        plancheFinalTraitement.clear();
        typeAction.clear();
        modele.clear();
        meuble.clear();
        refmeuble.clear();
        categorie.clear();
        meubleFinal.clear();
        qteMeuble.clear();
        typeServicePlanche.clear();
        servicePlanche.clear();
        Traitementproduit.clear();
        qteproduit.clear();
        produit.clear();
        qteMeubleFinal.clear();
        try {
            remplirClients();
        } catch (SQLException ex) {
            //Logger.getLogger(Fen_PrefactureClient.class.getName()).log(Level.SEVERE, null, ex);
        }
       DefaultTableModel mod=new DefaultTableModel();
       mod.addColumn("Produit");
       tablePlanche.setModel(mod);
       tableChange2.setModel(mod);
        
    }
    public void retirerPlanches(){
           
        int hi = this.tableChange2.getSelectedRow();
        servicePlanche.remove(this.tableChange2.getValueAt(this.tableChange2.getSelectedRow(), 0));
        typeServicePlanche.remove(this.tableChange2.getValueAt(this.tableChange2.getSelectedRow(), 1));
        qteServicePlanche.remove(this.tableChange2.getValueAt(this.tableChange2.getSelectedRow(), 2));
        qtePlanche.remove(hi);
        qtePlancheFinal.remove(hi);
       DefaultTableModel dac = new DefaultTableModel();
       dac.addColumn("Type bois",servicePlanche.toArray());
       dac.addColumn("Forme",typeServicePlanche.toArray());
       dac.addColumn("Longueur",qteServicePlanche.toArray());
       dac.addColumn("Quantité",qtePlanche.toArray());
     this.tableChange2.setModel(dac);
    }
    
    @SuppressWarnings("unchecked")
    public void remplirPlanches() {
         type.clear();
                lType.clear();
                
               
                forme.clear();
                lForme.clear();
                
                
                longueur.clear();
                lLongueur.clear();
                
                
                planche.clear();
        
        try {
            Statement sp=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet rsp=sp.executeQuery("select t.idTypeBois, t.libelleTypeBois, f.idFormePlanche, f.libelleFormePlanche, l.idLongeurPlanche, l.valeurLongeur, p.idPlanche,p.coutTraitement,p.coutLissage,p.prixVentePlanche from typeBois t, longeurPlanche l, formePlanche f, planche p where t.idTypeBois=p.idTypeBois and f.idFormePlanche=p.idFormePlanche and l.idLongeurPlanche=p.idLongeurPlanche and p.suppr=0");
            
            while(rsp.next())
            {
                Type_Bois tb=new Type_Bois(rsp.getInt(1),rsp.getString(2));
                type.add(tb);
                lType.add(tb.getLibelleTypeBois());
                
                FormePlanche fp=new FormePlanche(rsp.getInt(3), rsp.getString(4));
                forme.add(fp);
                lForme.add(fp.getLibeFormPlanche());
                
                LongueurPlanche lp=new LongueurPlanche(rsp.getInt(5),rsp.getInt(6));
                longueur.add(lp);
                lLongueur.add(lp.getValeurLongueur());
                
                Planche p=new Planche(rsp.getInt(7), fp, tb, lp,rsp.getInt(8),rsp.getInt(9));
                p.setPrixVentePlanche(rsp.getFloat(10));
                planche.add(p);
            }
            
            DefaultTableModel model1=new DefaultTableModel();
            model1.addColumn("Type de bois",lType.toArray());
            model1.addColumn("Forme",lForme.toArray());
            model1.addColumn("Longueur",lLongueur.toArray());
            tablePlanche.setModel(model1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    @SuppressWarnings({"unchecked", "unchecked", "unchecked"})
    public void remplirMeubles() throws SQLException{
            meuble.clear();
            modele.clear();
            refmeuble.clear();
            categorie.clear();
            qteMeuble.clear();
            lModele.clear();
        Statement sm=(Statement) sogemee.SOGEMEE.c.getConn().createStatement();
        ResultSet rsm=sm.executeQuery("select d.idModele,d.libelleModele,c.libelleCategorieModele,count(m.idMeuble),d.prixVente from meuble m, modele d,categorieModele c where m.idModele=d.idModele and c.idCategorieModele=d.idCategorieModele and m.idFactureClient=0 group by d.idModele");
        
        while(rsm.next())
        {
            Modele mod=new Modele(rsm.getInt(1),rsm.getString(2));
            mod.setPrixVente(rsm.getDouble(5));
            //Meuble m=new Meuble(rsm.getInt(1),mod);
            modele.add(mod);
            lModele.add(mod.getLibelleModele());
            //refmeuble.add(m.getIdMeuble());
            categorie.add(rsm.getString(3));
            qteMeuble.add(rsm.getInt(4));
        }
        DefaultTableModel model3=new DefaultTableModel();
        //model3.addColumn("Référence du meuble",refmeuble.toArray());
        model3.addColumn("Categorie du Modèle", categorie.toArray());
        model3.addColumn("Modèle du Meuble",lModele.toArray());
        model3.addColumn("Quantite de meuble disponible", qteMeuble.toArray());
        //model3.addColumn("Qte");
        tablePlanche.setModel(model3);

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        action = new javax.swing.ButtonGroup();
        typeProduit = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        traitLiss = new javax.swing.ButtonGroup();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePlanche = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        BtnRetirer = new javax.swing.JButton();
        prixU = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        traitementLissageRadioButton = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        traitementRadioButton = new javax.swing.JRadioButton();
        aucunRadioButton = new javax.swing.JRadioButton();
        lissageRadioButton = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableChange2 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        datedujour = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        radMeubles = new javax.swing.JRadioButton();
        radPlanches = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        btnValider = new javax.swing.JButton();
        btnAnnuler = new javax.swing.JButton();
        idFact = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        prenom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nom = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        clienExistenceCheckBox = new javax.swing.JCheckBox();
        telephone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClients = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        BtnAjouter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        serviceRadioButton = new javax.swing.JRadioButton();
        venteRadioButton = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rechF = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        rechTB = new javax.swing.JTextField();
        rechL = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        quantite = new javax.swing.JSpinner();

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GesVente");
        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jPanel7.setAutoscrolls(true);
        jPanel7.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel1.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel1.setText("Facture num :");

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
        jScrollPane2.setViewportView(tablePlanche);

        jLabel2.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel2.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel2.setText("Date du jour");

        BtnRetirer.setBackground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        BtnRetirer.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        BtnRetirer.setText("Retirer");
        BtnRetirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRetirerActionPerformed(evt);
            }
        });

        prixU.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(200.0d), Double.valueOf(200.0d), null, Double.valueOf(5.0d)));

        jLabel11.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel11.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel11.setText("Quantité");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        traitLiss.add(traitementLissageRadioButton);
        traitementLissageRadioButton.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        traitementLissageRadioButton.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        traitementLissageRadioButton.setText("<html>Traitement<br> et Lissage</html>");
        traitementLissageRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traitementLissageRadioButtonActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel16.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel16.setText("Type de traitement");

        traitLiss.add(traitementRadioButton);
        traitementRadioButton.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        traitementRadioButton.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        traitementRadioButton.setText("Traitement");
        traitementRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traitementRadioButtonActionPerformed(evt);
            }
        });

        traitLiss.add(aucunRadioButton);
        aucunRadioButton.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        aucunRadioButton.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        aucunRadioButton.setSelected(true);
        aucunRadioButton.setText("Aucun");
        aucunRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aucunRadioButtonActionPerformed(evt);
            }
        });

        traitLiss.add(lissageRadioButton);
        lissageRadioButton.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        lissageRadioButton.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        lissageRadioButton.setText("Lissage");
        lissageRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lissageRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(aucunRadioButton)
                    .addComponent(traitementRadioButton)
                    .addComponent(lissageRadioButton)
                    .addComponent(traitementLissageRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(17, 17, 17)
                .addComponent(aucunRadioButton)
                .addGap(17, 17, 17)
                .addComponent(traitementRadioButton)
                .addGap(17, 17, 17)
                .addComponent(lissageRadioButton)
                .addGap(17, 17, 17)
                .addComponent(traitementLissageRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tableChange2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableChange2);

        jLabel13.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel13.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel13.setText("Liste des Produits retenus ainsi que leur quantités");

        datedujour.setEditable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        typeProduit.add(radMeubles);
        radMeubles.setFont(new java.awt.Font("Cantarell", 3, 15)); // NOI18N
        radMeubles.setText("Meuble");
        radMeubles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radMeublesActionPerformed(evt);
            }
        });

        typeProduit.add(radPlanches);
        radPlanches.setFont(new java.awt.Font("Cantarell", 3, 15)); // NOI18N
        radPlanches.setSelected(true);
        radPlanches.setText("Planche");
        radPlanches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPlanchesActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel8.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel8.setText("Type de produit");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(40, 40, 40)
                .addComponent(radPlanches)
                .addGap(18, 18, 18)
                .addComponent(radMeubles)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(radPlanches)
                    .addComponent(radMeubles))
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel18.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel18.setText("Validation");

        btnValider.setText("Valider");
        btnValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValiderActionPerformed(evt);
            }
        });

        btnAnnuler.setText("Annuler");
        btnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel18))
                    .addComponent(btnAnnuler)
                    .addComponent(btnValider, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAnnuler, btnValider});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(btnValider)
                .addGap(18, 18, 18)
                .addComponent(btnAnnuler)
                .addContainerGap())
        );

        idFact.setEditable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        prenom.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel6.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel6.setText("Téléphone");

        nom.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel4.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel4.setText("Nom du client");

        jLabel3.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel3.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel3.setText("Liste des Clients");

        clienExistenceCheckBox.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        clienExistenceCheckBox.setText("Cochez si le client est nouveau");
        clienExistenceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienExistenceCheckBoxActionPerformed(evt);
            }
        });

        telephone.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel5.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel5.setText("Prenom du client");

        tableClients.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        tableClients.setForeground(javax.swing.UIManager.getDefaults().getColor("TabRenderer.selectedActivatedBackground"));
        tableClients.setModel(new javax.swing.table.DefaultTableModel(
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
        tableClients.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableClients);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clienExistenceCheckBox)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nom)
                                .addComponent(telephone)
                                .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clienExistenceCheckBox)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telephone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel9.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel9.setText("Liste des Produits disponibles");

        BtnAjouter.setBackground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        BtnAjouter.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        BtnAjouter.setText("Ajouter");
        BtnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAjouterActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel7.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel7.setText("Type d'Action");

        action.add(serviceRadioButton);
        serviceRadioButton.setFont(new java.awt.Font("Cantarell", 3, 15)); // NOI18N
        serviceRadioButton.setText("Service");
        serviceRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceRadioButtonActionPerformed(evt);
            }
        });

        action.add(venteRadioButton);
        venteRadioButton.setFont(new java.awt.Font("Cantarell", 3, 15)); // NOI18N
        venteRadioButton.setSelected(true);
        venteRadioButton.setText("Vente");
        venteRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venteRadioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(39, 39, 39)
                .addComponent(venteRadioButton)
                .addGap(32, 32, 32)
                .addComponent(serviceRadioButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(venteRadioButton)
                    .addComponent(serviceRadioButton))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel17.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel17.setText("Longueur");

        jLabel10.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel10.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel10.setText("Recherche rapide");

        rechF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechFActionPerformed(evt);
            }
        });
        rechF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rechFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechFKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel14.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel14.setText("Forme");

        jLabel15.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel15.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel15.setText("Type bois");

        rechTB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rechTBKeyReleased(evt);
            }
        });

        rechL.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rechLStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(43, 43, 43)
                        .addComponent(rechF))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(rechTB))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(46, 46, 46))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rechL, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(rechTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rechF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rechL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(219, 219, 219))
        );

        jLabel12.setFont(new java.awt.Font("Cantarell", 1, 15)); // NOI18N
        jLabel12.setForeground(javax.swing.UIManager.getDefaults().getColor("nb.warningForeground"));
        jLabel12.setText("P.U");

        quantite.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 875, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(BtnAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BtnRetirer, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(jLabel9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(prixU, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(26, 26, 26)
                        .addComponent(idFact, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(45, 45, 45)
                        .addComponent(datedujour, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(idFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(datedujour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(prixU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)))
                                    .addComponent(jLabel11))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BtnRetirer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(29, 29, 29)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientsMouseClicked

        int ind=this.tableClients.getSelectedRow();
        Client c=(Client) this.client.get(ind);
        this.nom.setText(c.getNomClient());
        prenom.setText(c.getPrenomClient());
        telephone.setText(c.getTelClient());
                // TODO add your handling code here:
    }//GEN-LAST:event_tableClientsMouseClicked

    private void clienExistenceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienExistenceCheckBoxActionPerformed

        if(clienExistenceCheckBox.isSelected()){
            nom.setEditable(true);
            nom.setText(null);
            prenom.setEditable(true);
            prenom.setText(null);
            telephone.setEditable(true);
            telephone.setText(null);
        }else{
            nom.setEditable(false);
            prenom.setEditable(false);
            telephone.setEditable(false);
            
        }
                // TODO add your handling code here:
    }//GEN-LAST:event_clienExistenceCheckBoxActionPerformed

    private void venteRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venteRadioButtonActionPerformed

        
        radMeubles.setVisible(true);
        radPlanches.setVisible(true);
        radPlanches.setSelected(true);
        aucunRadioButton.setVisible(true);
        aucunRadioButton.setSelected(true);
        
                // TODO add your handling code here:
    }//GEN-LAST:event_venteRadioButtonActionPerformed

    private void serviceRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceRadioButtonActionPerformed

        
        radMeubles.setVisible(false);
        radPlanches.setVisible(false);
        
 this.lissageRadioButton.setVisible(true);
            this.traitementLissageRadioButton.setVisible(true);
            this.traitementRadioButton.setVisible(true);
            traitementRadioButton.setSelected(true);
            this.aucunRadioButton.setVisible(false);

        remplirPlanches();        // TODO add your handling code here:
    }//GEN-LAST:event_serviceRadioButtonActionPerformed

    private void radPlanchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPlanchesActionPerformed
remplirPlanches();

            this.lissageRadioButton.setVisible(true);
            this.traitementLissageRadioButton.setVisible(true);
            this.traitementRadioButton.setVisible(true);
            this.aucunRadioButton.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_radPlanchesActionPerformed

    private void radMeublesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radMeublesActionPerformed
        try {
            remplirMeubles();
            
            this.lissageRadioButton.setVisible(false);
            this.traitementLissageRadioButton.setVisible(false);
            this.traitementRadioButton.setVisible(false);
            this.aucunRadioButton.setVisible(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_radMeublesActionPerformed

    private void tablePlancheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlancheMouseClicked

        int ind=tablePlanche.getSelectedRow();
        if(serviceRadioButton.isSelected()){
            Planche p=(Planche) planche.get(ind);
            if(traitementRadioButton.isSelected()){
               prixU.setValue(p.getCoutTraitement());
            }
            else{
                if(lissageRadioButton.isSelected()){
                    prixU.setValue(p.getCoutLissage());
                }
                else{
                    prixU.setValue(p.getCoutTraitement()+p.getCoutLissage());
                }
            }
           
        }
        else{
            if(radPlanches.isSelected()){
                Planche p=(Planche) planche.get(ind);
                if(traitementRadioButton.isSelected()){
               prixU.setValue(p.getCoutTraitement()+p.getPrixVentePlanche());
            }
            else{
                if(lissageRadioButton.isSelected()){
                    prixU.setValue(p.getCoutLissage()+p.getPrixVentePlanche());
                }
                else{
                    if(traitementLissageRadioButton.isSelected()){
                          prixU.setValue(p.getCoutTraitement()+p.getCoutLissage()+p.getPrixVentePlanche());
                  
                    }
                    else{
                          prixU.setValue(p.getPrixVentePlanche());
                    }
                }
            }
          
            
            }
            else{
                Modele mod=(Modele) modele.get(ind);
                prixU.setValue(mod.getPrixVente());
            }
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePlancheMouseClicked

    private void BtnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAjouterActionPerformed

        if(venteRadioButton.isSelected()){
        ajouterVente();     
        }
        else{
        ajouterService();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAjouterActionPerformed

    private void BtnRetirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRetirerActionPerformed
        
        retirer();
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRetirerActionPerformed

    private void btnValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValiderActionPerformed
    
        enregistrerFact();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnValiderActionPerformed

    private void btnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnulerActionPerformed
annuler();

// TODO add your handling code here:
    }//GEN-LAST:event_btnAnnulerActionPerformed

    private void traitementRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_traitementRadioButtonActionPerformed

        int ind=tablePlanche.getSelectedRow();
        if(serviceRadioButton.isSelected()){
            Planche p=(Planche) planche.get(ind);
            prixU.setValue(p.getCoutTraitement());
        }
        else{
             if(radPlanches.isSelected()){
                Planche p=(Planche) planche.get(ind);
                if(traitementRadioButton.isSelected()){
               prixU.setValue(p.getCoutTraitement()+p.getPrixVentePlanche());
            }
        }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_traitementRadioButtonActionPerformed

    private void lissageRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lissageRadioButtonActionPerformed

        int ind=tablePlanche.getSelectedRow();
        if(serviceRadioButton.isSelected()){
            Planche p=(Planche) planche.get(ind);
            prixU.setValue(p.getCoutLissage());
        }
        else{
                      if(radPlanches.isSelected()){
                Planche p=(Planche) planche.get(ind);
                if(lissageRadioButton.isSelected()){
               prixU.setValue(p.getCoutLissage()+p.getPrixVentePlanche());
                }
        }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_lissageRadioButtonActionPerformed

    private void traitementLissageRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_traitementLissageRadioButtonActionPerformed

        int ind=tablePlanche.getSelectedRow();
        if(serviceRadioButton.isSelected()){
            Planche p=(Planche) planche.get(ind);
            prixU.setValue(p.getCoutTraitement()+p.getCoutLissage());
        }else{
        
                     if(radPlanches.isSelected()){
                Planche p=(Planche) planche.get(ind);
                if(traitementLissageRadioButton.isSelected()){
               prixU.setValue(p.getCoutLissage()+p.getCoutTraitement()+p.getPrixVentePlanche());
                }
        }
        
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_traitementLissageRadioButtonActionPerformed

    private void rechTBKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechTBKeyReleased
        remplirPlancheRecherche(rechTB.getText(), rechF.getText(),(int)rechL.getValue());
                // TODO add your handling code here:
    }//GEN-LAST:event_rechTBKeyReleased

    private void rechFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechFKeyReleased
 remplirPlancheRecherche(rechTB.getText(), rechF.getText(),(int)rechL.getValue());
        // TODO add your handling code here:
    }//GEN-LAST:event_rechFKeyReleased

    private void rechLStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rechLStateChanged
 
         remplirPlancheRecherche(rechTB.getText(), rechF.getText(),(int)rechL.getValue());
        // TODO add your handling code here:
    }//GEN-LAST:event_rechLStateChanged

    private void aucunRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aucunRadioButtonActionPerformed
        int ind=tablePlanche.getSelectedRow(); 
        if(radPlanches.isSelected()){
                Planche p=(Planche) planche.get(ind);
                if(aucunRadioButton.isSelected()){
                prixU.setValue(p.getPrixVentePlanche());
                }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_aucunRadioButtonActionPerformed

    private void rechFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rechFKeyPressed
//         remplirPlancheRecherche(rechTB.getText(), rechF.getText(),(int)rechL.getValue());
        // TODO add your handling code here:
    }//GEN-LAST:event_rechFKeyPressed

    private void rechFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechFActionPerformed
//         remplirPlancheRecherche(rechTB.getText(), rechF.getText(),(int)rechL.getValue());
        // TODO add your handling code here:
    }//GEN-LAST:event_rechFActionPerformed

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
            java.util.logging.Logger.getLogger(Fen_FactureClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fen_FactureClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fen_FactureClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fen_FactureClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Fen_FactureClient().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAjouter;
    private javax.swing.JButton BtnRetirer;
    private javax.swing.ButtonGroup action;
    private javax.swing.JRadioButton aucunRadioButton;
    private javax.swing.JButton btnAnnuler;
    private javax.swing.JButton btnValider;
    private javax.swing.JCheckBox clienExistenceCheckBox;
    private javax.swing.JTextField datedujour;
    private javax.swing.JTextField idFact;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable3;
    private javax.swing.JRadioButton lissageRadioButton;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField prenom;
    private javax.swing.JSpinner prixU;
    private javax.swing.JSpinner quantite;
    private javax.swing.JRadioButton radMeubles;
    private javax.swing.JRadioButton radPlanches;
    private javax.swing.JTextField rechF;
    private javax.swing.JSpinner rechL;
    private javax.swing.JTextField rechTB;
    private javax.swing.JRadioButton serviceRadioButton;
    private javax.swing.JTable tableChange2;
    private javax.swing.JTable tableClients;
    private javax.swing.JTable tablePlanche;
    private javax.swing.JTextField telephone;
    private javax.swing.ButtonGroup traitLiss;
    private javax.swing.JRadioButton traitementLissageRadioButton;
    private javax.swing.JRadioButton traitementRadioButton;
    private javax.swing.ButtonGroup typeProduit;
    private javax.swing.JRadioButton venteRadioButton;
    // End of variables declaration//GEN-END:variables
}
