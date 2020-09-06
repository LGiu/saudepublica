package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
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
public class StockDto extends Dto {

    private Long id;

    @NotNull(message = "O estabelecimento deve ser informado!")
    private Establishment establishment;

    @NotNull(message = "O estabelecimento deve ser informado!")
    private Medication medication;

    @NotNull(message = "A quantidade não pode ser vazio!")
    private Integer amount;
}
