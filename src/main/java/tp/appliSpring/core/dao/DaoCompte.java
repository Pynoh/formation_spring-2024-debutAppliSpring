package tp.appliSpring.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.appliSpring.core.entity.Compte;

/*
 NB: avec l'extension spring-data , une classe d'implémentation de cette interface
     est générée automatiquement et c'est injectable avec @Autowired
 */

public interface DaoCompte extends JpaRepository<Compte,Long>{
    /*
     par héritage , on a :
	.save()
	.findById()
	.findAll()
	.deleteById()
	....
	*/
	
	//code de la requete dans @NamedQuery("Compte.findWithOperations")
	Compte findWithOperations(long numCompte);
	
	//le code/la requete de cette méthode va être généré automatiquement (sans @NamedQuery)
	//car on a respecter la convention de nom de methode
	//findBy+Solde+GreaterThanEqual avec Compte.solde qui existe
	List<Compte> findBySoldeGreaterThanEqual(double soldeMini);
}
