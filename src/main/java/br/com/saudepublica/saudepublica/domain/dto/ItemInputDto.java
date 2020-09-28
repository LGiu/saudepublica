package br.com.saudepublica.saudepublica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInputDto extends Dto {

    private Long id;

    private StockInputDto stockInput;

    @NotNull(message = "O medicamento deve ser imformado!")
    private MedicationDto medication;

    @NotNull(message = "A quantidade não pode ser vazio!")
    @Range(min = 1)
    private Integer amount;

    @NotNull(message = "O valor não pode ser vazio!")
    private BigDecimal value;
}
