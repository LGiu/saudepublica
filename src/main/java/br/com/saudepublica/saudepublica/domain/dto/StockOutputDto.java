package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeStockOutput;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOutputDto extends Dto {

    private Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize()
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo da saída deve ser informado!")
    private TypeStockOutput type;

    @NotNull(message = "O estabelcimento deve ser informado!")
    private EstablishmentDto establishment;

    @Size(min = 1, message = "É necessário pelo menos um item!")
    private List<ItemOutputDto> itemOutputList;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    private String name;

    private String cpf;

    private EstablishmentDto transferEstablishment;

}
