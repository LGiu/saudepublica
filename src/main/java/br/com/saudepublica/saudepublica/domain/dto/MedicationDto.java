package br.com.saudepublica.saudepublica.domain.dto;

import br.com.saudepublica.saudepublica.domain.util.LocalDateDeserializer;
import br.com.saudepublica.saudepublica.domain.util.LocalDateSerializer;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto extends Dto {

    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio!")
    private String name;

    @NotNull(message = "A data de vencimento não pode ser vazio!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private String barcode;

    private List<Stock> stockList;

}
