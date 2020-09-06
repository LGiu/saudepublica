package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends Dto {

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotEmpty(message = "O CPF não pode ser vazio!")
    @CPF(message = "O CPF é inválido!")
    private String cpf;

    @NotEmpty(message = "O e-mail não pode ser vazio!")
    @Email(message = "O e-mail é inválido!")
    private String email;

    @Size(min = 7, max = 20, message = "A senha deve ter entre 7 e 20 caracteres!")
    private String password;

    @Enumerated(EnumType.STRING)
    private TypeUser type;

}
