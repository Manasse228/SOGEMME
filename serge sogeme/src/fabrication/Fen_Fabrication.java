/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fabrication;

import approvisionnement.FormePlanche;
import approvisionnement.LongueurPlanche;
import approvisionnement.Planche;
import approvisionnement.Type_Bois;
import com.lowagie.text.Anchor;
import gesperson.Atelier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Fen_Fabrication extends javax.swing.JFrame {

    private ArrayList Modcategorie=new ArrayList();
    
    private ArrayList ModlibCateg=new ArrayList();
    private ArrayList Modtype=new ArrayList();
    private ArrayList ModlType=new ArrayList();
    private ArrayList Modforme=new ArrayList();
    private ArrayList ModlForme=new ArrayList();
    private ArrayList Modlongueur=new ArrayList();
    private ArrayList ModlLongueur=new ArrayList();
    private ArrayList Modplanche=new ArrayList();
    private ArrayList ModqtePlancheFinal=new ArrayList();
    private ArrayList ModqtePlanche= new ArrayList();
    private ArrayList ModplancheFinal= new ArrayList();
    private ArrayList ModModeles=new ArrayList();
    private ArrayList ModlibModeles=new ArrayList();
    private ArrayList Modutiliser=new ArrayList();
    
    
    private ArrayList Meumodeles=new ArrayList();
    private ArrayList MeuLibModeles=new ArrayList();
    private ArrayList MeuQteDispo=new ArrayList();
    private ArrayList MeucategDispo=new ArrayList();
    
    private ArrayList BSbons=new ArrayList();
    private ArrayList BSdatesBonsprece=new ArrayList();
    private ArrayList BSAtelierBonprece=new ArrayList();
    private ArrayList BSAteliers=new ArrayList();
    
    
    private ArrayList BSplanches=new ArrayList();
    private ArrayList BSlibTypeBois=new ArrayList();
    private ArrayList BSlibForme=new ArrayList();
    private ArrayList BSlibLongueur=new ArrayList();
    private ArrayList BSplanchesFinal=new ArrayList();
    private ArrayList BSqteplanchesFinal=new ArrayList();
    private ArrayList BSlibTypeBoisFinal=new ArrayList();
    private ArrayList BSlibFormeFinal=new ArrayList();
    private ArrayList BSlibLongueurFinal=new ArrayList();
    private ArrayList BSqteStock=new ArrayList();
    private ArrayList BSheureBon=new ArrayList();
    
    private ArrayList CMcategories=new ArrayList();
    private ArrayList CMlibCateg=new ArrayList();
    
    
    
    
    /**
     * Creates new form Fen_Fabrication
     */
    public Fen_Fabrication() {
        initComponents();
        ModremplirCombo();
        ModremplirPlanches();
        ModRemplirModeles();
        MeuremplirCombo();
        MeuremplirTable();
        BSremplirCombo();
        BSremplirPrecedent();
        CMremplirCM();
       
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    
    
    
    public void CMremplirCM(){
        CMcategories.clear();
        CMlibCateg.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("Select * from categorieModele where suppr=0");
            while(r.next()){
                CMlibCateg.add(r.getString(2));
                CategorieModele cm=new CategorieModele(r.getInt(1),r.getString(2));
                CMcategories.add(cm);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Libelle de la catégorie",CMlibCateg.toArray());
        tableCM.setModel(mod);
    }
    
    public void BSremplirBois(){
        BSplanches.clear();
        BSlibForme.clear();
        BSlibLongueur.clear();
        BSlibTypeBois.clear();
        BSqteStock.clear();
        BSqteplanchesFinal.clear();
        BSlibFormeFinal.clear();
        BSlibLongueurFinal.clear();
        BSlibTypeBoisFinal.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("Select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,sum(v.qteLivraison) from planche p,formePlanche f,longeurPlanche l,typeBois t,livraison_planche v where p.idPlanche =v.idPlanche  and p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche and p.suppr=0 and f.suppr=0 and l.suppr=0 and t.suppr=0 and v.suppr=0 group by v.idPlanche");
            while(r.next()){
               int qteStock=r.getInt(5);
                //JOptionPane.showMessageDialog(null,qteStock);
                Type_Bois tb=new Type_Bois(r.getString(2));
                BSlibTypeBois.add(tb.getLibelleTypeBois());
                FormePlanche fp=new FormePlanche(r.getString(3));
                BSlibForme.add(fp.getLibeFormPlanche());
                LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
                BSlibLongueur.add(lp.getValeurLongueur());
                Planche p=new Planche(r.getInt(1), fp, tb, lp);
                BSplanches.add(p);
               
                Statement q=sogemee.SOGEMEE.c.getConn().createStatement();
                
                ResultSet rsm=q.executeQuery("select sum(c.qteFacture) from concerner c,planche p where c.suppr=0 and p.suppr=0 and p.idPlanche=c.idPlanche  and p.idPlanche="+ p.getIdPlanche()+" union select sum(qteSortie) from bonSortie c,bonSortie_planche b,planche p where b.suppr=0 and c.suppr=0 and c.idBonSortie=b.idBonSortie and p.suppr=0 and p.idPlanche=b.idPlanche  and p.idPlanche="+p.getIdPlanche());
                while(rsm.next()){
                    
                    int qteSortie=rsm.getInt(1);
                  //   JOptionPane.showMessageDialog(null,qteSortie);
                    qteStock=qteStock-qteSortie;
                }
                rsm.close();
                 BSqteStock.add(qteStock);
             }
        
        }catch(Exception ex ){
            
        }
        
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Type Bois",BSlibTypeBois.toArray());
        model.addColumn("Forme ",BSlibForme.toArray());
        model.addColumn("Longueur", BSlibLongueur.toArray());
        model.addColumn("Quantité Stock", BSqteStock.toArray());
        tableBSbois.setModel(model);
    }
    public void BSremplirPrecedent(){
        BSbons.clear();
        BSAtelierBonprece.clear();
        BSdatesBonsprece.clear();
        BSheureBon.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idBonSortie,dateBonSortie,libelleServiceAtelier,heure,s.idServiceAtelier from bonSortie b,serviceAtelier s where b.idServiceAtelier=s.idServiceAtelier and b.suppr=0  order by dateBonSortie,heure desc");
            while(r.next()){
            Atelier ate=new Atelier(r.getInt(5),r.getString(3));
            BonSortie bs=new BonSortie(r.getInt(1), ate,r.getString(2));
            bs.setHeure(r.getString(4));
            BSbons.add(bs);
            BSheureBon.add(r.getString(4));
            BSAtelierBonprece.add(ate.getlibelleAtelier());
            BSdatesBonsprece.add(bs.getDateBonSortie());
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Date Bon ",BSdatesBonsprece.toArray());
        model.addColumn("Heure Bon", BSheureBon.toArray());
        model.addColumn("Auteur",BSAtelierBonprece.toArray());
        tableBS.setModel(model);
        
    }
    
    
    public void BSremplirCombo(){
        BSAteliers.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("Select idServiceAtelier,libelleServiceAtelier from serviceAtelier where suppr=0");
            while(r.next()){
                Atelier a=new Atelier(r.getInt(1), r.getString(2));
                BSAteliers.add(a);
                BScomboAtelier.addItem(a.getlibelleAtelier());
            }
        }catch(Exception ex){
            
        }
    }
    
    public  void MeuremplirTable(){
        try{
            MeuLibModeles.clear();
            MeuQteDispo.clear();
            MeucategDispo.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select libelleModele,libelleCategorieModele,count(idMeuble) from modele m,categorieModele c,meuble b where c.idCategorieModele=m.idCategorieModele and b.idModele=m.idModele and b.idFactureClient=0 and m.suppr=0 and b.suppr=0");
            while(r.next()){
               
                MeuLibModeles.add(r.getString(1));
                MeucategDispo.add(r.getString(2));
                MeuQteDispo.add(r.getInt(3));
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Categorie",MeucategDispo.toArray());
        mod.addColumn("Modele",MeuLibModeles.toArray());
        mod.addColumn("Quantité",MeuQteDispo.toArray());
        tableMeuble.setModel(mod);
    }
    
    public void MeuremplirCombo(){
        try{
            Meumodeles.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idModele,libelleModele,idCategorieModele from modele where suppr=0");
            while(r.next()){
                Modele m=new Modele(r.getInt(1),r.getString(2));
                Meumodeles.add(m);
            }
            for(int i=0;i<Meumodeles.size();i++){
                Modele mod=(Modele) Meumodeles.get(i);
                this.Meucmodele.addItem(mod.getLibelleModele());
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void ModRemplirModeles(){
        ModlibModeles.clear();
        ModModeles.clear();
        ModlibCateg.clear();
        try{
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select m.idModele,m.libelleModele,c.libelleCategorieModele,m.prixVente from modele m,categorieModele c where m.idCategorieModele=c.idCategorieModele and m.suppr=0 and c.suppr=0");
            while(r.next()){
                CategorieModele cat=new CategorieModele(r.getString(3));
                Modele mod=new Modele(r.getInt(1),r.getString(2), cat);
                mod.setPrixVente(r.getDouble(4));
                ModModeles.add(mod);
                ModlibModeles.add(mod.getLibelleModele());
                ModlibCateg.add(mod.getCategorieModele().getLibelleCategorieModele());
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        DefaultTableModel mo=new DefaultTableModel();
        mo.addColumn("Catégorie du modèle", ModlibCateg.toArray());
        mo.addColumn("Libelle du Modele",ModlibModeles.toArray());
        tableMod.setModel(mo);
    }
    
    
    public void ModremplirPlanches() {
                Modtype.clear();
                ModlType.clear();
                Modforme.clear();
                ModlForme.clear();
                Modlongueur.clear();
                ModlLongueur.clear();
                Modplanche.clear();
        
        try {
            Statement sp=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet rsp=sp.executeQuery("select t.idTypeBois, t.libelleTypeBois, f.idFormePlanche, f.libelleFormePlanche, l.idLongeurPlanche, l.valeurLongeur, p.idPlanche,p.coutTraitement,p.coutLissage from typeBois t, longeurPlanche l, formePlanche f, planche p where t.idTypeBois=p.idTypeBois and f.idFormePlanche=p.idFormePlanche and l.idLongeurPlanche=p.idLongeurPlanche and p.suppr=0");
            
            while(rsp.next())
            {
                Type_Bois tb=new Type_Bois(rsp.getInt(1),rsp.getString(2));
                
                ModlType.add(tb.getLibelleTypeBois());
                
                FormePlanche fp=new FormePlanche(rsp.getInt(3), rsp.getString(4));
                
                ModlForme.add(fp.getLibeFormPlanche());
                
                LongueurPlanche lp=new LongueurPlanche(rsp.getInt(5),rsp.getInt(6));
                
                ModlLongueur.add(lp.getValeurLongueur());
                
                Planche p=new Planche(rsp.getInt(7), fp, tb, lp,rsp.getInt(8),rsp.getInt(9));
                Modplanche.add(p);
            }
            
            DefaultTableModel model1=new DefaultTableModel();
            model1.addColumn("Type de bois",ModlType.toArray());
            model1.addColumn("Forme",ModlForme.toArray());
            model1.addColumn("Longueur",ModlLongueur.toArray());
            ModtablePlanche.setModel(model1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
    
    @SuppressWarnings("unchecked")
     public void ModremplirCombo(){
        try{
            ModcategorieModele.removeAllItems();
            Modcategorie.clear();
            
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select idCategorieModele,libelleCategorieModele from categorieModele where suppr=0");
            while(r.next()){
                CategorieModele m=new CategorieModele(r.getInt(1),r.getString(2));
                Modcategorie.add(m);
            }
            for(int i=0;i<Modcategorie.size();i++){
                CategorieModele mod=(CategorieModele) Modcategorie.get(i);
                this.ModcategorieModele.addItem(mod.getLibelleCategorieModele());
            }
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    
    
    @SuppressWarnings("unchecked")
    public void Modajouter(){
        int ind=ModtablePlanche.getSelectedRow();
        Planche p=(Planche) Modplanche.get(ind);
        if(ModplancheFinal.contains(p)){
            JOptionPane.showMessageDialog(null,"Vous avez déjà sélectionné cette planche");
            return;
        }
        
        try{
            Modqte.commitEdit();
        }catch(Exception ex){
            
        }
        
        ModqtePlancheFinal.add(Modqte.getValue());
        ModplancheFinal.add(p);
        Modtype.add(p.getTypebois().getLibelleTypeBois());
        Modforme.add(p.getForme().getLibeFormPlanche());
        Modlongueur.add(p.getLongueur().getValeurLongueur());
            
            DefaultTableModel model1=new DefaultTableModel();
            model1.addColumn("Type de bois",Modtype.toArray());
            model1.addColumn("Forme",Modforme.toArray());
            model1.addColumn("Longueur",Modlongueur.toArray());
            model1.addColumn("Quantité ",ModqtePlancheFinal.toArray());
            Modtable2.setModel(model1);
    }
    
    public void Modretirer(){
        int ind=Modtable2.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir une planche");
            return;
        }
        ModplancheFinal.remove(ind);
        Modtype.remove(ind);
        Modforme.remove(ind);
        Modlongueur.remove(ind);
        ModqtePlancheFinal.remove(ind);
        DefaultTableModel model1=new DefaultTableModel();
            model1.addColumn("Type de bois",Modtype.toArray());
            model1.addColumn("Forme",Modforme.toArray());
            model1.addColumn("Longueur",Modlongueur.toArray());
            model1.addColumn("Quantité ",ModqtePlancheFinal.toArray());
            Modtable2.setModel(model1);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableBS = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableBSbois = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableBSfinal = new javax.swing.JTable();
        BSqteNec = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        BSbtnAjouter = new javax.swing.JButton();
        BSbtnRetirer = new javax.swing.JButton();
        BSbtnNew = new javax.swing.JButton();
        BSbtnSave = new javax.swing.JButton();
        BSbtnSuppr = new javax.swing.JButton();
        BSbtnAnnuler = new javax.swing.JButton();
        BScomboAtelier = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableMeuble = new javax.swing.JTable();
        MeuAnnuler = new javax.swing.JButton();
        MeuEnregistrer = new javax.swing.JButton();
        MeuqteMeuble = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Meucmodele = new javax.swing.JComboBox();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Modqte = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        ModtablePlanche = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        ModcategorieModele = new javax.swing.JComboBox();
        ModbtnRetirer = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ModlibModele = new javax.swing.JTextField();
        ModbtnAjouter = new javax.swing.JButton();
        ModprixVente = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        Modtable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMod = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        CMLibelleCategorieModele = new javax.swing.JTextField();
        CMBtnEnregistrer = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableCM = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        CMBtnNew = new javax.swing.JButton();
        CMBtnModif = new javax.swing.JButton();
        CMBtnSuppr = new javax.swing.JButton();
        CMBtnCancel = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(228, 255, 201));

        jPanel1.setBackground(new java.awt.Color(228, 255, 201));

        jLabel10.setText("Liste des Précédents Bons de sorties");

        tableBS.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBSMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableBS);

        tableBSbois.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Type Bois", "Forme", "Longueur", "Quantité"
            }
        ));
        tableBSbois.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(tableBSbois);
        tableBSbois.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tableBSfinal.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(tableBSfinal);
        tableBSfinal.setVisible(false);
        jScrollPane7.setVisible(false);

        BSqteNec.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel11.setText("Quantité");

        BSbtnAjouter.setText("Ajouter");
        BSbtnAjouter.setVisible(false);
        BSbtnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnAjouterActionPerformed(evt);
            }
        });

        BSbtnRetirer.setText("Retirer");
        BSbtnRetirer.setVisible(false);
        BSbtnRetirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnRetirerActionPerformed(evt);
            }
        });

        BSbtnNew.setText("Nouveau");
        BSbtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnNewActionPerformed(evt);
            }
        });

        BSbtnSave.setText("Enregistrer");
        BSbtnSave.setEnabled(false);
        BSbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnSaveActionPerformed(evt);
            }
        });

        BSbtnSuppr.setText("Supprimer");
        BSbtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnSupprActionPerformed(evt);
            }
        });

        BSbtnAnnuler.setText("Annuler");
        BSbtnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSbtnAnnulerActionPerformed(evt);
            }
        });

        jLabel12.setText("Etablie Par");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                        .addComponent(BScomboAtelier, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BSbtnAjouter)
                                .addGap(92, 92, 92)
                                .addComponent(BSbtnRetirer)
                                .addGap(89, 89, 89))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BSbtnNew, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BSbtnSave, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BSbtnSuppr, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BSbtnAnnuler, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(110, 110, 110)
                                        .addComponent(BSqteNec, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(15, 15, 15))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BSbtnAnnuler, BSbtnNew, BSbtnSave, BSbtnSuppr});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel10)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BScomboAtelier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BSqteNec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BSbtnRetirer, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BSbtnAjouter, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5))
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(BSbtnNew)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BSbtnSave)
                .addGap(18, 18, 18)
                .addComponent(BSbtnSuppr)
                .addGap(18, 18, 18)
                .addComponent(BSbtnAnnuler)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bon de Sortie", jPanel1);

        jPanel3.setBackground(new java.awt.Color(228, 255, 201));

        jLabel7.setText("Liste des meubles disponibles en stock");

        tableMeuble.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMeuble.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMeubleMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableMeuble);

        MeuAnnuler.setText("Annuler");
        MeuAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeuAnnulerActionPerformed(evt);
            }
        });

        MeuEnregistrer.setText("Enregistrer");
        MeuEnregistrer.setEnabled(false);
        MeuEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MeuEnregistrerActionPerformed(evt);
            }
        });

        MeuqteMeuble.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        MeuqteMeuble.setEnabled(false);

        jLabel8.setText("Quantité de meuble ");

        jLabel9.setText("Modèle du meuble");

        Meucmodele.setEnabled(false);

        jButton6.setText("Nouveau");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MeuEnregistrer)
                            .addComponent(MeuAnnuler)
                            .addComponent(jButton6))))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(Meucmodele, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MeuqteMeuble, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {MeuAnnuler, MeuEnregistrer, jButton6});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel7)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(Meucmodele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(MeuqteMeuble, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(MeuEnregistrer)
                        .addGap(18, 18, 18)
                        .addComponent(MeuAnnuler)))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Meuble", jPanel3);

        jPanel2.setBackground(new java.awt.Color(228, 255, 201));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Choisir et ajouter les planches nécessaires ");

        jLabel4.setText("Quantité");

        Modqte.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        ModtablePlanche.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(ModtablePlanche);

        jLabel5.setText("Prix du modèle");

        ModbtnRetirer.setText("Retirer");
        ModbtnRetirer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModbtnRetirerActionPerformed(evt);
            }
        });

        jLabel2.setText("Catégorie du Modèle");

        ModbtnAjouter.setText("Ajouter");
        ModbtnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModbtnAjouterActionPerformed(evt);
            }
        });

        ModprixVente.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(250.0f), Float.valueOf(250.0f), null, Float.valueOf(25.0f)));

        Modtable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Type Bois", "Forme", "Longueur", "Quantité"
            }
        ));
        jScrollPane2.setViewportView(Modtable2);

        jLabel1.setText("Libelle du Modèle");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(40, 40, 40)
                            .addComponent(ModlibModele, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ModprixVente, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(ModcategorieModele, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(26, 26, 26))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(63, 63, 63)
                            .addComponent(Modqte, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(ModbtnAjouter)
                            .addGap(97, 97, 97)
                            .addComponent(ModbtnRetirer)
                            .addGap(141, 141, 141))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ModlibModele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ModcategorieModele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ModprixVente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ModbtnAjouter)
                            .addComponent(ModbtnRetirer)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Modqte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        tableMod.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableModMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMod);

        jLabel6.setText("Liste des Modèle");

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

        jButton3.setText("Modifier");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Annuler");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Enregistrer");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jButton5)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton5});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3, jButton4, jButton5});

        jTabbedPane1.addTab("Modèles", jPanel2);

        jPanel5.setBackground(new java.awt.Color(228, 255, 201));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        CMBtnEnregistrer.setText("Enregistrer");
        CMBtnEnregistrer.setEnabled(false);
        CMBtnEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMBtnEnregistrerActionPerformed(evt);
            }
        });

        tableCM.setModel(new javax.swing.table.DefaultTableModel(
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
        tableCM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCMMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tableCM);

        jLabel13.setFont(new java.awt.Font("Cantarell", 1, 14)); // NOI18N
        jLabel13.setText("Liste des catégories modèles");

        CMBtnNew.setText("Nouveau");
        CMBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMBtnNewActionPerformed(evt);
            }
        });

        CMBtnModif.setText("Modifier");
        CMBtnModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMBtnModifActionPerformed(evt);
            }
        });

        CMBtnSuppr.setText("Supprimer");
        CMBtnSuppr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMBtnSupprActionPerformed(evt);
            }
        });

        CMBtnCancel.setText("Annuler");
        CMBtnCancel.setEnabled(false);
        CMBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CMBtnCancelActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Cantarell", 1, 14)); // NOI18N
        jLabel14.setText("Libellé de la catégorie modèle");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CMBtnModif)
                                    .addComponent(CMBtnSuppr)
                                    .addComponent(CMBtnCancel))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CMBtnNew)
                                    .addComponent(CMBtnEnregistrer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(CMLibelleCategorieModele, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(65, 65, 65))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CMBtnCancel, CMBtnEnregistrer, CMBtnModif, CMBtnNew, CMBtnSuppr});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(CMBtnNew)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CMBtnEnregistrer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CMBtnModif)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CMBtnSuppr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CMBtnCancel))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(CMLibelleCategorieModele, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(290, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Catégorie Modèle", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ModbtnRetirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModbtnRetirerActionPerformed
        Modretirer();
// TODO add your handling code here:
    }//GEN-LAST:event_ModbtnRetirerActionPerformed

    private void ModbtnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModbtnAjouterActionPerformed
        Modajouter();
// TODO add your handling code here:
    }//GEN-LAST:event_ModbtnAjouterActionPerformed

    private void tableModMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableModMouseClicked

        int ind=tableMod.getSelectedRow();
        Modele modele=(Modele) ModModeles.get(ind);
        ModlibModele.setText(modele.getLibelleModele());
        ModcategorieModele.setSelectedItem(modele.getCategorieModele().getLibelleCategorieModele());
        ModprixVente.setValue(modele.getPrixVente());
        try{
            Modforme.clear();
            Modlongueur.clear();
            Modtype.clear();
            ModplancheFinal.clear();
            ModqtePlancheFinal.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select p.idPlanche,t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,u.qte from planche p,formePlanche f,typeBois t,longeurPlanche l,utiliser u,modele m where p.idTypeBois=t.idTypeBois and p.idFormePlanche=f.idFormePlanche and l.idLongeurPlanche=p.idLongeurPlanche and u.idPlanche=p.idPlanche and u.idModele=m.idModele and u.suppr=0 and m.suppr=0 and u.idModele="+modele.getIdModele());
            while(r.next()){
                Type_Bois tb=new Type_Bois(r.getString(2));
                Modtype.add(tb.getLibelleTypeBois());
                FormePlanche fp=new FormePlanche(r.getString(3));
                Modforme.add(fp.getLibeFormPlanche());
                LongueurPlanche lp=new LongueurPlanche(r.getInt(4));
                Modlongueur.add(lp.getValeurLongueur());
                Planche p=new Planche(r.getInt(1), fp, tb, lp);
                ModplancheFinal.add(p);
                ModqtePlancheFinal.add(r.getInt(5));
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
        }
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Type Bois", Modtype.toArray());
        mod.addColumn("Forme ", Modforme.toArray());
        mod.addColumn("Longueur",Modlongueur.toArray());
        mod.addColumn("Quantité ",ModqtePlancheFinal.toArray());
        Modtable2.setModel(mod);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableModMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (this.ModlibModele.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le libelle du modele");
            this.ModlibModele.requestFocus();
            return;
        }
        int ind = this.ModcategorieModele.getSelectedIndex();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une catégorie de modèle");
            return;
        }
        if (ModplancheFinal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir les planches nécessaires svp");
            return;
        }
        CategorieModele catemodele = (CategorieModele) Modcategorie.get(ind);
        Modele modele = new Modele(this.ModlibModele.getText(), catemodele, (float) ModprixVente.getValue());
        if(modele.rechercheID()!=0){
            JOptionPane.showMessageDialog(null,"Ce modele existe déjà");
            return;
        }
        modele.savemodele();
        modele.setIdModele(modele.rechercheID());
        for (int i = 0; i < ModplancheFinal.size(); i++) {
            Planche p = (Planche) ModplancheFinal.get(i);
            int q = (int) ModqtePlancheFinal.get(i);
            Utiliser u = new Utiliser(p, modele, q);
            u.saveUtiliser();
        }
        MeuremplirCombo();
        ModRemplirModeles();
         jButton5.setEnabled(false);// TODO add your handling code here:
          jButton4.setEnabled(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (this.ModlibModele.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir le libelle du modele");
            this.ModlibModele.requestFocus();
            return;
        }
        int ind = this.ModcategorieModele.getSelectedIndex();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir une catégorie de modèle");
            return;
        }
        if (ModplancheFinal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir les planches nécessaires svp");
            return;
        }
        int indm=tableMod.getSelectedRow();
        if(indm==-1){
            JOptionPane.showMessageDialog(null,"Veuillez choisir le modele a modifier svp");
            return;
        }
        try{
            ModprixVente.commitEdit();
        }catch(Exception ex){
            
        }
        CategorieModele catemodele = (CategorieModele) Modcategorie.get(ind);
        Modele modele = (Modele) ModModeles.get(indm);
        modele.setLibelleModele(ModlibModele.getText());
        modele.setCategorieModele(catemodele);
        modele.setPrixVente((float)ModprixVente.getValue());
        modele.UpdateModele();
        try {
                Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
                s.executeUpdate("delete from utiliser where idModele="+modele.getIdModele());
                
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null,ex);
            }
            
        for (int i = 0; i < ModplancheFinal.size(); i++) {
            Planche p = (Planche) ModplancheFinal.get(i);
            int q = (int) ModqtePlancheFinal.get(i);
            Utiliser u = new Utiliser(p, modele, q);
            u.saveUtiliser();
        }
        ModRemplirModeles();// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jButton5.setEnabled(true);
        jButton4.setEnabled(true);        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

         jButton5.setEnabled(false);
          jButton4.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int ind=tableMod.getSelectedRow();
        Modele modele=(Modele) ModModeles.get(ind);
        if(JOptionPane.showConfirmDialog(null,"Êtes vous sûres de supprimer ce modèle?")==0){
        modele.deleteModele();    
        }
        
        ModRemplirModeles();
                
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void MeuAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeuAnnulerActionPerformed
 Meucmodele.setEnabled(false);
        MeuqteMeuble.setEnabled(false);        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_MeuAnnulerActionPerformed

    private void MeuEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MeuEnregistrerActionPerformed
        
        
        int nb = (int) this.MeuqteMeuble.getValue();
        int ind = this.Meucmodele.getSelectedIndex();
        if (ind == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez choisir un modèle");
            return;
        }
        Modele modele = (Modele) Meumodeles.get(ind);
        for (int i = 0; i < nb; i++) {
            Meuble meuble = new Meuble(modele);
            meuble.saveMeuble();
        }
        Meucmodele.setEnabled(false);
        MeuqteMeuble.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_MeuEnregistrerActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        Meucmodele.setEnabled(true);
        MeuqteMeuble.setEnabled(true);
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tableMeubleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMeubleMouseClicked

        int ind=tableMeuble.getSelectedRow();
        Meucmodele.setSelectedItem(MeuLibModeles.get(ind).toString());
        MeuqteMeuble.setValue(MeuQteDispo.get(ind));
        // TODO add your handling code here:
    }//GEN-LAST:event_tableMeubleMouseClicked

    private void BSbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnNewActionPerformed
BSbtnNew.setEnabled(false);
BSremplirBois();
BSbtnSave.setEnabled(true);
BSbtnSuppr.setEnabled(false);
jScrollPane7.setVisible(true);
BSbtnAjouter.setVisible(true);
BSbtnRetirer.setVisible(true);
tableBSfinal.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_BSbtnNewActionPerformed

    private void tableBSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBSMouseClicked

        int ind=tableBS.getSelectedRow();
        BonSortie bs=(BonSortie) BSbons.get(ind);
        try{
           
            BSlibForme.clear();
            BSlibLongueur.clear();
            BSlibTypeBois.clear();
            BSqteStock.clear();
            Statement s=sogemee.SOGEMEE.c.getConn().createStatement();
            ResultSet r=s.executeQuery("select t.libelleTypeBois,f.libelleFormePlanche,l.valeurLongeur,qteSortie from planche p,typeBois t,formePlanche f,longeurPlanche l,bonSortie b,bonSortie_planche s where b.suppr=0 and s.suppr=0 and p.idTypeBois=t.idTypeBois and f.idFormePlanche=p.idFormePlanche and p.idLongeurPlanche=l.idLongeurPlanche  and b.idBonSortie =s.idBonSortie and s.idPlanche=p.idPlanche and b.idBonSortie="+bs.getIdBonSortie());
            while(r.next()){
                BSlibForme.add(r.getString(2));
                BSlibLongueur.add(r.getInt(3));
                BSlibTypeBois.add(r.getString(1));
                BSqteStock.add(r.getInt(4));
            }
            DefaultTableModel mod=new DefaultTableModel();
            mod.addColumn("Type Bois",BSlibTypeBois.toArray());
            mod.addColumn("Forme",BSlibForme.toArray());
            mod.addColumn("Longueur", BSlibLongueur.toArray());
            mod.addColumn("Quantité",BSqteStock.toArray());
            tableBSbois.setModel(mod);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableBSMouseClicked

    private void BSbtnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnAnnulerActionPerformed
BSbtnNew.setEnabled(true);
BSbtnSave.setEnabled(false);
        BSbtnAjouter.setVisible(false);
        BSbtnSuppr.setEnabled(true);
BSbtnRetirer.setVisible(false); 
jScrollPane7.setVisible(false);
tableBSfinal.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_BSbtnAnnulerActionPerformed

    private void BSbtnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnAjouterActionPerformed

        int ind=tableBSbois.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir la planche a ajouter svp");
            return;
        }
       Planche p=(Planche) BSplanches.get(ind);
       if(BSplanchesFinal.contains(p)){
           JOptionPane.showMessageDialog(null,"Cette planche a déjà été sélectionnée");
           return;
       }
       else{
           try{
              BSqteNec.commitEdit(); 
           }catch(Exception ex){
               JOptionPane.showMessageDialog(null, ex);
           }
           int qteStock=(int) BSqteStock.get(ind);
           int qte=(int) BSqteNec.getValue();
           if(qte>qteStock){
               JOptionPane.showMessageDialog(null,"La quantité entrée n'est pas disponible en stock");
               return;
           }
           BSplanchesFinal.add(p);
           BSqteplanchesFinal.add(BSqteNec.getValue());
           BSlibFormeFinal.add(p.getForme().getLibeFormPlanche());
           BSlibTypeBoisFinal.add(p.getTypebois().getLibelleTypeBois());
           BSlibLongueurFinal.add(p.getLongueur().getValeurLongueur());
           DefaultTableModel mod=new DefaultTableModel();
           mod.addColumn("Type Bois", BSlibTypeBoisFinal.toArray());
           mod.addColumn("Forme", BSlibFormeFinal.toArray());
           mod.addColumn("Longueur", BSlibLongueurFinal.toArray());
           mod.addColumn("Quantité",BSqteplanchesFinal.toArray());
           tableBSfinal.setModel(mod);
       }
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_BSbtnAjouterActionPerformed

    private void BSbtnRetirerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnRetirerActionPerformed

        int ind=tableBSfinal.getSelectedRow();
        if(ind==-1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir la planche à retirer du bon");
            return;
        }
        BSplanchesFinal.remove(ind);
        BSqteplanchesFinal.remove(ind);
        BSlibFormeFinal.remove(ind);
        BSlibLongueurFinal.remove(ind);
        BSlibTypeBoisFinal.remove(ind);
        DefaultTableModel mod=new DefaultTableModel();
        mod.addColumn("Type Bois", BSlibTypeBoisFinal.toArray());
        mod.addColumn("Forme", BSlibFormeFinal.toArray());
        mod.addColumn("Longueur", BSlibLongueurFinal.toArray());
        mod.addColumn("Quantité",BSqteplanchesFinal.toArray());
        tableBSfinal.setModel(mod);
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_BSbtnRetirerActionPerformed

    private void BSbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnSaveActionPerformed

        int ind=BScomboAtelier.getSelectedIndex();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
       // JOptionPane.showMessageDialog(null, format.format(new Date()));
        Atelier a=(Atelier) BSAteliers.get(ind);
        String date=format.format(new Date());
        BonSortie bs=new BonSortie(a,date);
        bs.saveBonSortie();
        bs.setIdBonSortie(bs.rechercheID());
        for(int i=0;i<BSplanchesFinal.size();i++){
            Planche p=(Planche) BSplanchesFinal.get(i);
            BonSortie_Planche bsp=new BonSortie_Planche(bs, p, (int)BSqteplanchesFinal.get(i));
            bsp.save();
        }    
         try {//JOptionPane.showMessageDialog(null,"wep");
            JasperDesign jasperdesign = JRXmlLoader.load("src/fabrication/BonSortie.jrxml");

            JasperReport jr = JasperCompileManager.compileReport(jasperdesign);

            
            Map param = new HashMap();
            param.put("nomEntreprise",BScomboAtelier.getSelectedItem() );
            param.put("idBon",bs.getIdBonSortie());

            JasperPrint jp = JasperFillManager.fillReport(jr, param, sogemee.SOGEMEE.c.getConn());
            JasperViewer.viewReport(jp,false);
            

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        BSremplirPrecedent();
        BSremplirBois();
        tableBSfinal.removeAll();
        BSbtnAnnulerActionPerformed(evt);
        BSbtnNew.setEnabled(false);
        BSbtnSuppr.setEnabled(true);
        //JOptionPane.showMessageDialog(null,"Bon de Sortie enregistré avec succès");
        BSbtnNew.setEnabled(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_BSbtnSaveActionPerformed

    private void BSbtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSbtnSupprActionPerformed

        
        int ind=tableBS.getSelectedRow();
        BonSortie bs=(BonSortie) BSbons.get(ind);
        if(JOptionPane.showConfirmDialog(null, "Êtes vous sûres de vouloir supprimer ce bon?")==0){
            bs.deleteBon();
            BSremplirPrecedent();
        
        }        
        // TODO add your handling code here:
    }//GEN-LAST:event_BSbtnSupprActionPerformed

    private void CMBtnEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMBtnEnregistrerActionPerformed
        CategorieModele mode = new CategorieModele(CMLibelleCategorieModele.getText());
        if (!mode.verifSaisie()) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer un mode de règlement svp.");

        } else {
            try {
                if (mode.verifExistence()) {
                    JOptionPane.showMessageDialog(null, "Ce mode existe déjà,veuillez en saisir un autre");
                } else {
                    mode.saveCategorieModele();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        
        CMBtnEnregistrer.setEnabled(false);
        CMBtnCancel.setEnabled(false);
        CMBtnNew.setEnabled(true);
        CMBtnModif.setEnabled(true);
        CMBtnSuppr.setEnabled(true);
                CMremplirCM();
        ModremplirCombo();
        ModRemplirModeles();

      
    }//GEN-LAST:event_CMBtnEnregistrerActionPerformed

    private void CMBtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMBtnNewActionPerformed

        CMBtnEnregistrer.setEnabled(true);
        CMBtnCancel.setEnabled(true);
        CMBtnNew.setEnabled(false);
        CMBtnModif.setEnabled(false);
        CMLibelleCategorieModele.setText(null);
        CMLibelleCategorieModele.setEditable(true);
        CMBtnSuppr.setEnabled(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_CMBtnNewActionPerformed

    private void CMBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMBtnCancelActionPerformed
CMBtnEnregistrer.setEnabled(false);
        CMBtnCancel.setEnabled(false);
        CMBtnNew.setEnabled(true);
        CMBtnModif.setEnabled(true);
        CMLibelleCategorieModele.setText(null);
  //      CMLibelleCategorieModele.setEditable(false);
        CMBtnSuppr.setEnabled(true);// TODO add your handling code here:
    }//GEN-LAST:event_CMBtnCancelActionPerformed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5MouseClicked

    private void tableCMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCMMouseClicked

        int ind=tableCM.getSelectedRow();
        CategorieModele cm=(CategorieModele) CMcategories.get(ind);
        CMLibelleCategorieModele.setText(cm.getLibelleCategorieModele());
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tableCMMouseClicked

    private void CMBtnModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMBtnModifActionPerformed
 int ind=tableCM.getSelectedRow();
if(ind==-1){
    JOptionPane.showMessageDialog(null,"Veuillez choisir une catégorie");
    return;
}
CategorieModele cm=(CategorieModele) CMcategories.get(ind);
        if(CMLibelleCategorieModele.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez entrer le libellé SVP");
            return;
        }
        cm.setLibelleCategorieModele(CMLibelleCategorieModele.getText());
        cm.updateCategorieModele();
        CMremplirCM();
        ModremplirCombo();
        ModRemplirModeles();
    }//GEN-LAST:event_CMBtnModifActionPerformed

    private void CMBtnSupprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CMBtnSupprActionPerformed
int ind=tableCM.getSelectedRow();
if(ind==-1){
    JOptionPane.showMessageDialog(null,"Veuillez choisir une catégorie");
    return;
}
        CategorieModele cm=(CategorieModele) CMcategories.get(ind);
        if(CMLibelleCategorieModele.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Veuillez entrer le libellé SVP");
            return;
        }
        cm.setLibelleCategorieModele(CMLibelleCategorieModele.getText());
        cm.deleteCategorieModele();
        CMremplirCM();
        ModremplirCombo();
        ModRemplirModeles();        // TODO add your handling code here:
    }//GEN-LAST:event_CMBtnSupprActionPerformed

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
            java.util.logging.Logger.getLogger(Fen_Fabrication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fen_Fabrication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fen_Fabrication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fen_Fabrication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Fen_Fabrication().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BSbtnAjouter;
    private javax.swing.JButton BSbtnAnnuler;
    private javax.swing.JButton BSbtnNew;
    private javax.swing.JButton BSbtnRetirer;
    private javax.swing.JButton BSbtnSave;
    private javax.swing.JButton BSbtnSuppr;
    private javax.swing.JComboBox BScomboAtelier;
    private javax.swing.JSpinner BSqteNec;
    private javax.swing.JButton CMBtnCancel;
    private javax.swing.JButton CMBtnEnregistrer;
    private javax.swing.JButton CMBtnModif;
    private javax.swing.JButton CMBtnNew;
    private javax.swing.JButton CMBtnSuppr;
    private javax.swing.JTextField CMLibelleCategorieModele;
    private javax.swing.JButton MeuAnnuler;
    private javax.swing.JButton MeuEnregistrer;
    private javax.swing.JComboBox Meucmodele;
    private javax.swing.JSpinner MeuqteMeuble;
    private javax.swing.JButton ModbtnAjouter;
    private javax.swing.JButton ModbtnRetirer;
    private javax.swing.JComboBox ModcategorieModele;
    private javax.swing.JTextField ModlibModele;
    private javax.swing.JSpinner ModprixVente;
    private javax.swing.JSpinner Modqte;
    private javax.swing.JTable Modtable2;
    private javax.swing.JTable ModtablePlanche;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableBS;
    private javax.swing.JTable tableBSbois;
    private javax.swing.JTable tableBSfinal;
    private javax.swing.JTable tableCM;
    private javax.swing.JTable tableMeuble;
    private javax.swing.JTable tableMod;
    // End of variables declaration//GEN-END:variables
}
