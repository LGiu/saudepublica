package br.com.saudepublica.saudepublica.infraestruct.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ItemOutput extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockOutput stockOutput;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    @Column
    private Integer amount;
}
