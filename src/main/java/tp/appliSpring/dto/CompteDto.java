package tp.appliSpring.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;

public class CompteDto {
	
    private Long numero;
    
    @Length(min=2, max=30, message = "Nom trop long ou trop court")
	private String label;
    
    @Min(-999)
	private Double solde;

	
	@Override
	public String toString() {
		return "CompteDto [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}
	
	public CompteDto() {
		super();
	}
	public CompteDto(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getSolde() {
		return solde;
	}
	public void setSolde(Double solde) {
		this.solde = solde;
	}

	

}
