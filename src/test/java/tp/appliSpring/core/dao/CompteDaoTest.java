package tp.appliSpring.core.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpring.AppliSpringApplication;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@RunWith(SpringRunner.class)  //si junit4
//@ExtendWith(SpringExtension.class) //si junit5/jupiter // pas nécessaire en spring-boot
@SpringBootTest(classes = {AppliSpringApplication.class})//reprendre la configuration de la classe principale
@ActiveProfiles({"dev"})
public class CompteDaoTest {

    private static Logger logger = LoggerFactory.getLogger(CompteDaoTest.class);

    @Autowired
    private DaoCompte daoCompte;

    @Autowired
    private DaoOperation daoOperation;

//
//    @BeforeEach
//    public void before() {
//        this.daoCompte.deleteAll();
//    }


    @Test
    public void testAjoutEtRelectureEtSuppression() {
        //hypothese : base avec tables vides au lancement du test
        Compte compte = new Compte(null, "compteA", 100.0);
        Compte compteSauvegarde = this.daoCompte.save(compte); //INSERT INTO
        logger.debug("compteSauvegarde=" + compteSauvegarde);

        Compte compteRelu = this.daoCompte.findById(compteSauvegarde.getNumero()).orElseThrow(); //SELECT
        Assertions.assertEquals("compteA", compteRelu.getLabel());
        Assertions.assertEquals(100.0, compteRelu.getSolde());
        logger.debug("compteRelu apres insertion=" + compteRelu);

        compte.setSolde(150.0);
        compte.setLabel("compte_a");
        Compte compteMisAjour = this.daoCompte.save(compte); //UPDATE
        logger.debug("compteMisAjour=" + compteMisAjour);

        compteRelu = this.daoCompte.findById(compteSauvegarde.getNumero()).get(); //SELECT
        Assertions.assertEquals("compte_a", compteRelu.getLabel());
        Assertions.assertEquals(150.0, compteRelu.getSolde());
        logger.debug("compteRelu apres miseAjour=" + compteRelu);
		/*
		//+supprimer :
		this.daoCompte.deleteById(compteSauvegarde.getNumero());
		
		//verifier bien supprimé (en tentant une relecture qui renvoi null)
		Compte compteReluApresSuppression = this.daoCompte.findById(compteSauvegarde.getNumero()).orElse(null);
		Assertions.assertTrue(compteReluApresSuppression == null);
		*/
    }

    @Test
    public void testFindBySoldeGreaterThanEqual() {

        Compte compte1 = new Compte("compte1", 100.0);
        Compte compte2 = new Compte("compte2", -100.0);
        Compte compte3 = new Compte("compte3", 50.0);
        Compte compte4 = new Compte("compte3", 49.9);
        List<Compte> compteArrayList = new ArrayList<>();
        compteArrayList.add(compte1);
        compteArrayList.add(compte2);
        compteArrayList.add(compte3);
        compteArrayList.add(compte4);

        daoCompte.saveAll(compteArrayList);

        List<Compte> comptesGreaterThan50 = daoCompte.findBySoldeGreaterThanEqual(50);
        Assertions.assertEquals( 2, comptesGreaterThan50.size(), "Le nombre de comptes dans la liste devrait être de 2");
        logger.debug("2 comptes avec solde>=50 : " + comptesGreaterThan50);

        List<Compte> comptesGreaterThanMinus100 = daoCompte.findBySoldeGreaterThanEqual(-100);
        Assertions.assertEquals( 4, comptesGreaterThanMinus100.size(), "Le nombre de comptes dans la liste devrait être de 4");
        logger.debug("4 comptes avec solde>=-100 : " + comptesGreaterThanMinus100);
    }



    @Test
    public void testCompteAvecOperations() {
        // Phase 1 : créer des comptes et des opérations attachées et tout enregistrer en base
        Compte compte1 = new Compte("compte1", 1700.0);
        Compte compte2 = new Compte("compte2", -100.0);
        Compte compte3 = new Compte("compte3", 50.0);
        Compte compte4 = new Compte("compte4", 49.9);

        List<Operation> operationsCompte1 = new ArrayList<>();
        operationsCompte1.add(new Operation("courses", -30.0, new Date()));
        operationsCompte1.add(new Operation("salaire", 1500.0, new Date()));
        //compte1.setOperations(operationsCompte1); // Ne crée pas les opérations en BDD

        List<Compte> compteArrayList = new ArrayList<>();
        compteArrayList.add(compte1);
        compteArrayList.add(compte2);
        compteArrayList.add(compte3);
        compteArrayList.add(compte4);
        daoCompte.saveAll(compteArrayList);

        Operation operation1 = new Operation("courses", -30.0, new Date());
        operation1.setCompte(compte1);
        daoOperation.save(operation1);
        Operation operation2 = new Operation("salaire", 1600.0, new Date());
        operation2.setCompte(compte1);
        daoOperation.save(operation2);

        // Phase 2 : relire les informations
        Compte compte1Relu = this.daoCompte.findWithOperations(compte1.getNumero());
        logger.debug("compte1Relu="+compte1Relu);
        Assertions.assertNotNull(compte1Relu);
        for (Operation op : compte1Relu.getOperations()) {
            logger.debug("\t operation="+op);
        }
    }
}
