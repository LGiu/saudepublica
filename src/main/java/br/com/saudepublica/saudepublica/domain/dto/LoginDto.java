package br.com.saudepublica.saudepublica.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto extends Dto {

    @NotEmpty(message = "O e-mail não pode ser vazio!")
    @Email(message = "O e-mail é inválido!")
    private String email;

    @NotEmpty(message = "O e-mail não pode ser vazio!")
    @Size(min = 7, max = 20, message = "A senha deve ter entre 7 e 20 caracteres!")
    private String password;
}
