package sogemee;
import java.sql.*;
import javax.swing.JOptionPane;


public class Connexion {
    protected Connection conn = null;
    protected Statement stat= null;
    protected ResultSet  resultat=null;
    
    public Connexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sogemee", "root","");
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex);
            
        }
    }
    
    public Connection getConn(){
        return conn ;
    }


    private void vertables(String nom_table) throws SQLException {
         DatabaseMetaData infosurbd=conn.getMetaData();
            ResultSet rr=infosurbd.getTables(conn.getCatalog(), null, nom_table, null);
            boolean a=rr.next();
            if(!a) {
                if(nom_table.equals("typeEvenement"))
                {stat=conn.createStatement();
                 stat.executeUpdate("CREATE TABLE `sogemee`.`typeEvenement`( `idTypeEvenement` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `libelleTypeEvenement` VARCHAR(95) NOT NULL,suppr int ,  PRIMARY KEY(`idTypeEvenement`) )");
                }
                
                 if(nom_table.equals("fonction"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`fonction`(  `idFonction` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`libelleFonction` VARCHAR(45) NOT NULL,nbPersonne int,suppr int ,  PRIMARY KEY(`idFonction`))");
                }
                
                
                if(nom_table.equals("typeBois"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table typeBois( `idTypeBois` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `libelleTypeBois` VARCHAR(45) NOT NULL,suppr int , PRIMARY KEY(`idTypeBois`))");
                }
                  if(nom_table.equals("fournisseur"))
                {stat=conn.createStatement();
                 stat.executeUpdate("CREATE TABLE `sogemee`.`fournisseur`( `idFournisseur` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `nomFournisseur` VARCHAR(45) NOT NULL,`telFournisseur` VARCHAR(50) NOT NULL, `addFournisseur` VARCHAR(150) ,suppr int ,PRIMARY KEY(`idFournisseur`))");
                }

                      if(nom_table.equals("agent"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`agent` ( `idAgent` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`nomAgent` VARCHAR(50) NOT NULL,  `prenomAgent` VARCHAR(150) NOT NULL,`sexe` VARCHAR(15) NOT NULL, `dateEmbaucheAgent` DATE NOT NULL, `dateNaissAgent` DATE NOT NULL,`telAgent` VARCHAR(45),  `domicileAgent` TEXT,suppr int ,  PRIMARY KEY(`idAgent`))");
                }
                       if(nom_table.equals("client"))
                {stat=conn.createStatement();
                 stat.executeUpdate("CREATE TABLE `sogemee`.`client`( `idClient` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`nomClient` VARCHAR(45) NOT NULL,`prenomClient` VARCHAR(50) NOT NULL,`telClient` VARCHAR(45),suppr int , PRIMARY KEY(`idClient`))");
                }
                            if(nom_table.equals("formePlanche"))
                {stat=conn.createStatement();
                 stat.executeUpdate("CREATE TABLE `sogemee`.`formePlanche`(`idFormePlanche` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `libelleFormePlanche` VARCHAR(45) NOT NULL,suppr int ,PRIMARY KEY(`idFormePlanche`))");
                }
               
                          if(nom_table.equals("evenement"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table evenement( `idAgent` INTEGER UNSIGNED NOT NULL ,`idTypeEvenement` INTEGER UNSIGNED NOT NULL,dateDebEvenement date not null,dateFinEvenement date,dureEvenement real,motif text,suppr int ,foreign key(idAgent) references agent(idAgent),foreign key(idTypeEvenement) references typeEvenement(idTypeEvenement),primary key(idAgent,idtypeEvenement,datedebEvenement))");
                }
                          
                     
                    if(nom_table.equals("serviceAtelier"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table serviceAtelier(`idServiceAtelier` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `libelleServiceAtelier` VARCHAR(55) NOT NULL,suppr int , PRIMARY KEY(`idServiceAtelier`))");
                }
                    
                  if(nom_table.equals("bonSortie"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`bonSortie`( `idBonSortie` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `dateBonSortie` DATE NOT NULL,`idServiceAtelier` INTEGER UNSIGNED NOT NULL,suppr int , PRIMARY KEY(`idBonSortie`), CONSTRAINT `idServiceAtelier` FOREIGN KEY `idServiceAtelier` (`idServiceAtelier`) REFERENCES `serviceatelier` (`idServiceAtelier`))");
                }
                  
                   if(nom_table.equals("tva"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`tva`( `idTva` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`valeurTva` FLOAT NOT NULL,suppr int ,PRIMARY KEY(`idTva`))");
                }
                   
                      if(nom_table.equals("commande"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`commande`( `idCommande` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `dateCommande` DATE NOT NULL,`dateProFormat` DATE ,`idFournisseur` INTEGER UNSIGNED NOT NULL,`idTva` INTEGER UNSIGNED ,suppr int ,PRIMARY KEY(`idCommande`), CONSTRAINT `idFournisseur` FOREIGN KEY `idFournisseur` (`idFournisseur`)  REFERENCES `fourniseur` (`idFournisseur`),CONSTRAINT `idTva` FOREIGN KEY `idTva` (`idTva`) REFERENCES `tva` (`idTva`))");
                }
                      
                         if(nom_table.equals("livraison"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`livraison` (  `idLivraison` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `dateLivraison` DATE NOT NULL,`idCommande` INTEGER UNSIGNED NOT NULL,PRIMARY KEY(`idLivraison`),suppr int ,CONSTRAINT `idCommande` FOREIGN KEY `idCommande` (`idCommande`)  REFERENCES `commande` (`idCommande`))");
                }
                         
                  if(nom_table.equals("modeReglement"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`modeReglement`(  `idModeReglement` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `libelleModeReglement` VARCHAR(95) NOT NULL,suppr int ,PRIMARY KEY(`idModeReglement`))");
                }
                  
                if(nom_table.equals("periodePaie"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`periodePaie`( `idPeriodePaie` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`libellePeriodePaie` DATE NOT NULL,suppr int ,PRIMARY KEY(`idPeriodePaie`) )");
                }
                
                if(nom_table.equals("factureClient"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`factureClient`(  `idFactureClient` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `dateFacture` DATE NOT NULL,  `dejaLivrer` INTEGER UNSIGNED NOT NULL, `idAgent` INTEGER UNSIGNED NOT NULL,`idClient` INTEGER UNSIGNED NOT NULL, suppr int , PRIMARY KEY(`idFactureClient`), CONSTRAINT `idAgent` FOREIGN KEY `idAgent` (`idAgent`) REFERENCES `agent` (`idAgent`),CONSTRAINT `idClient` FOREIGN KEY `idClient` (`idClient`)  REFERENCES `client` (`idClient`))");
                }
                
                if(nom_table.equals("trimestre"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`trimestre`( `idTrimestre` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, `libelletrimestre` VARCHAR(45) NOT NULL, `anneeTrimestre` TEXT NOT NULL,suppr int ,PRIMARY KEY(`idTrimestre`))");
                }
                
             if(nom_table.equals("rubrique"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`rubrique`(   `idRubrique` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `libelleRubrique` VARCHAR(95) NOT NULL, suppr int , PRIMARY KEY(`idRubrique`))");
                }
             
              if(nom_table.equals("reglement"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`reglement`(  `idReglement` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`montantReglement` FLOAT NOT NULL,  `idFactureClient` INTEGER UNSIGNED NOT NULL,`idModeReglement` INTEGER UNSIGNED NOT NULL,suppr int ,PRIMARY KEY(`idReglement`),CONSTRAINT `idFactureClient` FOREIGN KEY `idFactureClient` (`idFactureClient`) REFERENCES `factureclient` (`idFactureClient`),CONSTRAINT `idModeReglemen` FOREIGN KEY `idModeReglemen` (`idModeReglement`)    REFERENCES `modereglement` (`idModeReglement`))");
                }
              
               if(nom_table.equals("longeurPlanche"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`longeurPlanche`(  `idLongeurPlanche` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`valeurLongeur` FLOAT NOT NULL,suppr int ,PRIMARY KEY(`idLongeurPlanche`))");
                }
               
                if(nom_table.equals("categorieModele"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`categorieModele`(  `idCategorieModele` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,  `libelleCategorieModele` varchar(50) NOT NULL,suppr int ,  PRIMARY KEY(`idCategorieModele`))");
                }
                
                  if(nom_table.equals("planche"))
                {stat=conn.createStatement();
                stat.executeUpdate("CREATE TABLE `sogemee`.`planche`( `idPlanche` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,`idLongeurPlanche` INTEGER UNSIGNED NOT NULL,`idTypeBois` INTEGER UNSIGNED NOT NULL,`idFormePlanche` INTEGER UNSIGNED NOT NULL,suppr int , PRIMARY KEY(`idPlanche`), CONSTRAINT `idLongeurPlanche` FOREIGN KEY `idLongeurPlanche` (`idLongeurPlanche`)   REFERENCES `longeurplanche` (`idLongeurPlanche`),CONSTRAINT `idTypeBois` FOREIGN KEY `idTypeBois` (`idTypeBois`) REFERENCES `typebois` (`idTypeBois`),CONSTRAINT `idFormePlanche` FOREIGN KEY `idFormePlanche` (`idFormePlanche`) REFERENCES `formeplanche` (`idFormePlanche`))");
                }
                  
                 if(nom_table.equals("concerner"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table concerner( `idPlanche` INTEGER UNSIGNED NOT NULL ,`idFactureClient` INTEGER UNSIGNED NOT NULL,qteFacture int ,typeTraitement varchar(50),suppr int ,foreign key(idPlanche) references planche(idPlanche),foreign key(idFactureClient) references factureClient(idFactureClient),primary key(idFactureClient,idPlanche))");
                }
                  if(nom_table.equals("modele"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table modele(`idModele` INTEGER  NOT NULL AUTO_INCREMENT,  `libelleModele` VARCHAR(100)  NOT NULL,`idCategorieModele` INTEGER UNSIGNED NOT NULL,suppr int ,PRIMARY KEY (`idModele`),foreign key(idCategorieModele) references categorieModele(idCategorieModele))");
                }
                  
                    if(nom_table.equals("meuble"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table meuble(`idMeuble` INTEGER  NOT NULL AUTO_INCREMENT,  `idModele` INTEGER  NOT NULL,`idFactureClient` INTEGER UNSIGNED NOT NULL,suppr int ,PRIMARY KEY (`idMeuble`),foreign key(idModele) references modele(idModele),foreign key(idFactureClient) references factureClient(idFactureClient))");
                }
                  
                                 if(nom_table.equals("occuper"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table occuper( `idAgent` INTEGER UNSIGNED NOT NULL ,`idServiceAtelier` INTEGER UNSIGNED NOT NULL,`idFonction` INTEGER UNSIGNED NOT NULL,datedebOccuper date not null,datefinOccuper date ,suppr int ,foreign key(idAgent) references agent(idAgent),foreign key(idServiceAtelier) references serviceAtelier(idServiceAtelier),foreign key(idFonction) references fonction(idFonction),primary key(idAgent,idFonction,idServiceAtelier,datedebOccuper))");
                }
                    
                           if(nom_table.equals("composer"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table composer(`idServiceAtelier` INTEGER UNSIGNED NOT NULL,`idFonction` INTEGER UNSIGNED,suppr int ,foreign key(idServiceAtelier) references serviceAtelier(idServiceAtelier),foreign key(idFonction) references fonction(idFonction),primary key(idServiceAtelier,idFonction))");
                }
                  
                     if(nom_table.equals("alouer"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table alouer( `idFormePlanche` INTEGER UNSIGNED NOT NULL,`idServiceAtelier` INTEGER UNSIGNED NOT NULL,`idTrimestre` INTEGER UNSIGNED NOT NULL,suppr int ,foreign key(idFormePlanche) references formePlanche(idFormePlanche),foreign key(idServiceAtelier) references serviceAtelier(idServiceAtelier),foreign key(idTrimestre) references trimestre(idTrimestre),primary key(idTrimestre,idServiceAtelier,idFormePlanche))");
                }
             if(nom_table.equals("payer"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table payer( `idAgent` INTEGER UNSIGNED NOT NULL ,`idRubrique` INTEGER UNSIGNED NOT NULL,`idPeriodePaie` INTEGER UNSIGNED NOT NULL,montantPayer real ,suppr int ,foreign key(idAgent) references agent(idAgent),foreign key(idRubrique) references rubrique(idRubrique),foreign key(idPeriodePaie) references periodePaie(idPeriodePaie),primary key(idAgent,idPeriodePaie,idRubrique))");
                }
             
     if(nom_table.equals("inclure"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table inclure(`idPlanche` INTEGER UNSIGNED NOT NULL ,`idCommande` INTEGER UNSIGNED NOT NULL,suppr int ,foreign key(idPlanche) references planche(idPlanche),foreign key(idCommande) references commande(idCommande),primary key(idCommande,idPlanche))");
                }
     
          if(nom_table.equals("livraison_planche"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table livraison_planche(`idPlanche` INTEGER UNSIGNED NOT NULL ,`idLivraison` INTEGER UNSIGNED NOT NULL,qteLivraison int,suppr int ,foreign key(idPlanche) references planche(idPlanche),foreign key(idLivraison) references livraison(idLivraison),primary key(idLivraison,idPlanche))");
                }
          if(nom_table.equals("agent_rubrique"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table agent_rubrique( `idAgent` INTEGER UNSIGNED NOT NULL ,`idRubrique` INTEGER UNSIGNED NOT NULL,montantRubrique real ,suppr int ,foreign key(idAgent) references agent(idAgent),foreign key(idRubrique) references rubrique(idRubrique),primary key(idAgent,idRubrique))");
                }
          
          if(nom_table.equals("bonSortie_planche"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table bonSortie_planche(`idPlanche` INTEGER UNSIGNED NOT NULL ,`idBonSortie` INTEGER UNSIGNED NOT NULL,qteSortie int,suppr int ,foreign key(idPlanche) references planche(idPlanche),foreign key(idBonSortie) references bonSortie(idBonSortie),primary key(idBonSortie,idPlanche))");
                }
          if(nom_table.equals("utiliser"))
                {stat=conn.createStatement();
                 stat.executeUpdate("create table utiliser(`idPlanche` INTEGER UNSIGNED NOT NULL ,`idModele` INTEGER UNSIGNED NOT NULL,qte int,suppr int ,foreign key(idPlanche) references planche(idPlanche),foreign key(idModele) references modele(idModele),primary key(idModele,idPlanche))");
                }
            }
    }


}
