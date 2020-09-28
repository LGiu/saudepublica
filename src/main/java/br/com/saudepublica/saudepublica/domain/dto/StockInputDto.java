package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockInputDto extends Dto {

    private Long id;

    @NotEmpty(message = "O número da nota fiscal não pode ser vazia!")
    private String invoiceNumber;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize()
    private LocalDateTime date;

    private String provider;

    @NotNull(message = "O estabelcimento deve ser informado!")
    private EstablishmentDto establishment;

    @Size(min = 1, message = "É necessário pelo menos um item!")
    private List<ItemInputDto> itemInputList;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    private BigDecimal totalValue;
}
