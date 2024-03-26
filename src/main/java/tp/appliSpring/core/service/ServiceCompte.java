package tp.appliSpring.core.service;

import java.util.List;

import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.exception.BankException;
import tp.appliSpring.core.exception.NotFoundException;

//par defaut , les méthodes peuvent renvoyer RuntimeException
public interface ServiceCompte {
	public void transferer(double montant,long numCptDeb,long numCptCred)throws BankException;
	public Compte rechercherCompte(long numCpt)throws NotFoundException;
	public List<Compte> rechercherTousLesComptes(); //retourne liste vide si rien trouver
	public List<Compte> rechercherComptesAvecSoldeMini(double soldeMini); //retourne liste vide si rien trouver
	public Compte sauvegarderCompte(Compte cpt);
	public Compte updateCompte(Compte cpt)throws NotFoundException;
	public void deleteCompte(Long numCpt)throws NotFoundException;
	//...
}
