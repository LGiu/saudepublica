package br.com.saudepublica.saudepublica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentDto extends Dto {

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotEmpty(message = "O CNPJ não pode ser vazio!")
    @CNPJ(message = "O CNPJ é inválido!")
    private String cnpj;
}
