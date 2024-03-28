package tp.appliSpring.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.appliSpring.converter.GenericMapper;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.CompteDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController //@Component de type controller d'api rest
@RequestMapping(value = "/rest/api-bank/compte", headers = "Accept=application/json")
public class CompteRestCtrl {

    @Autowired
    private ServiceCompte serviceCompte;

	/*
	//V1 sans DTO
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@GetMapping("/{id}")
	public Compte getCompteById(@PathVariable("id") long numeroCompte) {
		System.out.println("getCompteById() appelee avec numeroCompte="+numeroCompte);
		Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
		System.out.println("getCompteById() retournant compteEntity="+compteEntity);
		return compteEntity;
		//NB: l'objet retourné sera automatiquement converti au format json
	}

	 */


    //V2 avec DTO et V4 (avec automatisme ExceptionHandler)
    //declencher en mode GET avec
    //http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2
    @GetMapping("/{id}")
    public CompteDto getCompteById(@PathVariable("id") long numeroCompte) {

        Compte compteEntity = serviceCompte.rechercherCompte(numeroCompte);
        return GenericMapper.MAPPER.map(compteEntity, CompteDto.class);
        //NB: l'objet retourné sera automatiquement converti au format json
    }




    /*
    //V3 avec ResponseEntity<?> mais sans ExceptionHandler
    //declencher en mode GET avec
    //http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2
    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
        try {
            Compte compteEntity = serviceCompte.rechercherCompte(numeroCompte);
            return new ResponseEntity<CompteDto>(GenericMapper.MAPPER.map(compteEntity, CompteDto.class), HttpStatus.OK);
        } catch (NotFoundException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    */

    //En GET
    //http://localhost:8181/appliSpring/rest/api-bank/compte
    //http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50
    //http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50&critere2=val2&critere3=val3
    @GetMapping("")
    public List<CompteDto> getComptesByCriteria(@RequestParam(value = "soldeMini", required = false) Double soldeMini,
                                                @RequestParam(value = "firstChar", required = false) String firstChar) {

        List<Compte> compteList;
        if (soldeMini == null) {
            compteList = serviceCompte.rechercherTousLesComptes();
        } else {
            compteList = serviceCompte.rechercherComptesAvecSoldeMini(soldeMini);
        }
        if (firstChar != null) {
            compteList = compteList.stream().filter(c -> c.getLabel().startsWith(firstChar)).collect(Collectors.toList());
        }
        return GenericMapper.MAPPER.map(compteList, CompteDto.class);

        //NB: l'objet retourné sera automatiquement converti au format json
    }


    //appelé en mode POST
    //avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
    //avec dans la partie "body" de la requête
    // { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
    @PostMapping
    CompteDto postCompte(@RequestBody CompteDto compteDto) {
        System.out.println("compte to update:" + compteDto);
        Compte compte = GenericMapper.MAPPER.map(compteDto, Compte.class);
        serviceCompte.sauvegarderCompte(compte);

        return GenericMapper.MAPPER.map(compte, CompteDto.class);
    }


    //appelé en mode PUT
    //avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
    //ou bien avec url = http://localhost:8181/appliSpring/rest/api-bank/compte/1
    //avec dans la partie "body" de la requête
    // { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
    @PutMapping("")
    CompteDto putCompte(@RequestBody CompteDto compteDto) {
        System.out.println("devise to update:" + compteDto);
        Compte compte = GenericMapper.MAPPER.map(compteDto, Compte.class);
        serviceCompte.updateCompte(compte);

        return GenericMapper.MAPPER.map(compte, CompteDto.class);
    }

    // appel en mode DELETE
    //http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompte(@PathVariable("id") long numeroCompte) {
        serviceCompte.deleteCompte(numeroCompte);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public CompteRestCtrl() {
    }

}
