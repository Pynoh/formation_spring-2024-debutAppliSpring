package tp.appliSpring.core.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpring.AppliSpringApplication;
import tp.appliSpring.core.dao.DaoOperation;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;
import tp.appliSpring.core.exception.BankException;

import java.util.Date;

@SpringBootTest(classes = {AppliSpringApplication.class})//reprendre la configuration de la classe principale
@ActiveProfiles({"dev"})
class ServiceCompteTest {

    private static Logger logger = LoggerFactory.getLogger(ServiceCompteTest.class);

    @Autowired
    private ServiceCompte serviceCompte;

    @Autowired
    private DaoOperation daoOperation;

    @Test
    void transfererOK() {

        Compte compteASauvegarde = this.serviceCompte.sauvegarderCompte(
                new Compte(null, "compteA", 300.0));
        Compte compteBSauvegarde = this.serviceCompte.sauvegarderCompte(
                new Compte(null, "compteB", 100.0));
        long numCptA = compteASauvegarde.getNumero();
        long numCptB = compteBSauvegarde.getNumero();

        double soldeA_avant = compteASauvegarde.getSolde();
        double soldeB_avant = compteBSauvegarde.getSolde();
        logger.debug("avant bon virement, soldeA_avant=" + soldeA_avant +
                " et soldeB_avant=" + soldeB_avant);

        //On effectue un virement de 50 euros d'un compte A vers vers compte B
        this.serviceCompte.transferer(50.0, numCptA, numCptB);

        //remonter en memoire les nouveaux soldes des compte A et B apres virement
        // (+affichage console ou logger)
        Compte compteAReluApresVirement =
                this.serviceCompte.rechercherCompte(numCptA);
        Compte compteBReluApresVirement =
                this.serviceCompte.rechercherCompte(numCptB);
        double soldeA_apres = compteAReluApresVirement.getSolde();
        double soldeB_apres = compteBReluApresVirement.getSolde();
        logger.debug("apres bon virement, soldeA_apres=" + soldeA_apres
                + " et soldeB_apres=" + soldeB_apres);
        //verifier -50 et +50 sur les différences de soldes sur A et B :
        Assertions.assertEquals(soldeA_avant - 50, soldeA_apres, 0.000001);
        Assertions.assertEquals(soldeB_avant + 50, soldeB_apres, 0.000001);

    }

    @Test
    void transfererKO() {
        Compte compteASauvegarde = this.serviceCompte.sauvegarderCompte(
                new Compte(null, "compteA", 300.0));
        Compte compteBSauvegarde = this.serviceCompte.sauvegarderCompte(
                new Compte(null, "compteB", 100.0));
        long numCptA = compteASauvegarde.getNumero();
        long numCptB = compteBSauvegarde.getNumero();

        double soldeA_avant = compteASauvegarde.getSolde();
        double soldeB_avant = compteBSauvegarde.getSolde();
        logger.debug("avant mauvais virement, soldeA_avant=" + soldeA_avant + " et soldeB_avant=" + soldeB_avant);

        //On effectue un virement de 50 euros d'un compte A vers un compte -B inexistant
        Executable executable = () -> this.serviceCompte.transferer(50.0, numCptA, -numCptB); //erreur volontaire

        Assertions.assertThrows(BankException.class, executable, "On attend une BankException");

        //remonter en memoire les nouveaux soldes des compte A et B apres virement
        // (+affichage console ou logger)
        Compte compteAReluApresVirement = this.serviceCompte.rechercherCompte(numCptA);
        Compte compteBReluApresVirement = this.serviceCompte.rechercherCompte(numCptB);
        double soldeA_apres = compteAReluApresVirement.getSolde();
        double soldeB_apres = compteBReluApresVirement.getSolde();
        logger.debug("apres mauvais virement, soldeA_apres=" + soldeA_apres + " et soldeB_apres=" + soldeB_apres);
        //verifier -50 et +50 sur les différences de soldes sur A et B :
        Assertions.assertEquals(soldeA_avant, soldeA_apres, 0.000001);
        Assertions.assertEquals(soldeB_avant, soldeB_apres, 0.000001);
    }

    @Test
    void rechercherCompte() {
        // Phase 1 : créer des comptes et des opérations attachées et tout enregistrer en base
        Compte compte1 = new Compte("compte1", 1700.0);
        serviceCompte.sauvegarderCompte(compte1);

        Operation operation1 = new Operation("courses", -30.0, new Date());
        operation1.setCompte(compte1);
        daoOperation.save(operation1);
        Operation operation2 = new Operation("salaire", 1600.0, new Date());
        operation2.setCompte(compte1);
        daoOperation.save(operation2);

        // Phase 2 : relire les informations
        Compte compte1Relu = this.serviceCompte.rechercherCompte(compte1.getNumero());
        logger.debug("compte1Relu="+compte1Relu);
        Assertions.assertNotNull(compte1Relu);
        for (Operation op : compte1Relu.getOperations()) {
            logger.debug("\t operation="+op);
        }
    }

    @Test
    void rechercherTousLesComptes() {
    }

    @Test
    void rechercherComptesAvecSoldeMini() {
    }

    @Test
    void sauvegarderCompte() {
    }

    @Test
    void updateCompte() {
    }

    @Test
    void deleteCompte() {
    }
}