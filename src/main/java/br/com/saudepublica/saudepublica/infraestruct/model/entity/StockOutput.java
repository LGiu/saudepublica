package br.com.saudepublica.saudepublica.infraestruct.model.entity;

import br.com.saudepublica.saudepublica.domain.dto.EstablishmentDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class StockOutput extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonSerialize()
    private LocalDateTime date;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment transferEstablishment;

    @OneToMany(mappedBy = "stockOutput", fetch = FetchType.LAZY)
    private List<ItemOutput> itemOutputList;

    @Enumerated(EnumType.STRING)
    private StockStatus status;
}
