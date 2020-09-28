package br.com.saudepublica.saudepublica.infraestruct.model.entity;

import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class StockInput extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String invoiceNumber;

    @Column
    private LocalDateTime date;

    @Column
    private String provider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    @OneToMany(mappedBy = "stockInput", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ItemInput> itemInputList;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    @Column
    private BigDecimal totalValue;
}
