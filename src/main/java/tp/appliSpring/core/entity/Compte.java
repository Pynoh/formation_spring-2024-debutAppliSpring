package tp.appliSpring.core.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@NamedQuery(name = "Compte.findWithOperations",
        query = "SELECT cpt FROM Compte cpt LEFT JOIN FETCH cpt.operations WHERE cpt.numero = ?1")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="numero")
    private Long numero;

    @Column(name = "label", length = 64)
    private String label;

    private Double solde;

    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    //@JsonIgnore
    private List<Operation> operations = new ArrayList<>(); //+get/set

    //+get/set , constructeur , toString()

    @Override
    public String toString() {
        return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
    }


    public Compte(Long numero, String label, Double solde) {
        this.numero = numero;
        this.label = label;
        this.solde = solde;
    }

    public Compte(String label, Double solde) {
        this.label = label;
        this.solde = solde;
    }


    public Compte() {
        super();
    }


}