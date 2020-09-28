package br.com.saudepublica.saudepublica.infraestruct.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ItemInput extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockInput stockInput;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    @Column
    private Integer amount;

    @Column
    private BigDecimal value;
}
