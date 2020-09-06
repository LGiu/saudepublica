package br.com.saudepublica.saudepublica.infraestruct.model.entity;

import br.com.saudepublica.saudepublica.domain.util.LocalDateDeserializer;
import br.com.saudepublica.saudepublica.domain.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Medication extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate dueDate;

    @Column
    private String barcode;

    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Stock> stockList;
}
