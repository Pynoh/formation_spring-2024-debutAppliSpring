package tp.appliSpring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CompteDto {

    private Long numero;

    @Length(min = 2, max = 30, message = "Nom trop long ou trop court")
    private String label;

    @Min(-999)
    @Schema(description = "solde du compte", defaultValue = "100")
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


}
