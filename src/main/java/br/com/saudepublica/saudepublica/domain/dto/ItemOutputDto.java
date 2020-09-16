package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOutputDto extends Dto {

    private Long id;

    private StockOutputDto stockOutput;

    @NotNull(message = "O medicamento deve ser imformado!")
    private MedicationDto medication;

    @NotNull(message = "A quantidade não pode ser vazio!")
    @Range(min = 1)
    private Integer amount;
}
