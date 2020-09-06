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
public class Stock extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Establishment establishment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    @Column
    private Integer amount;
}
