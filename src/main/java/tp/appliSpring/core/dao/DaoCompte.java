package tp.appliSpring.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp.appliSpring.core.entity.Compte;

import java.util.List;

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
	@Query("select distinct c from Compte c inner join fetch c.operations where c.numero = ?1")
	Compte findWithOperations(long numCompte);
	
	//le code/la requete de cette méthode va être généré automatiquement (sans @NamedQuery)
	//car on a respecter la convention de nom de methode
	//findBy+Solde+GreaterThanEqual avec Compte.solde qui existe
	List<Compte> findBySoldeGreaterThanEqual(double soldeMini);
}
