package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInputDataRepository extends JpaRepository<ItemInput, Long> {

    @Query(value = "select itemInput from ItemInput itemInput where itemInput.stockInput.id = :stockInputId")
    List<ItemInput> findByStockInputId(@Param("stockInputId") Long stockInputId);

    @Query(value = "select case when (count(itemInput.id) > 0) then true else false end from ItemInput itemInput " +
            "where itemInput.stockInput.id = :stockInputId and itemInput.medication.id  = :medicationId and itemInput.id <> :id")
    boolean existsByStockInputIdAndMedicationIdAndDifferentId(@Param("stockInputId") Long stockInputId, @Param("medicationId") Long medicationId, @Param("id") Long id);

    @Query(value = "select case when (count(itemInput.id) > 0) then true else false end from ItemInput itemInput " +
            "where itemInput.stockInput.id = :stockInputId and itemInput.medication.id  = :medicationId")
    boolean existsByStockInputIdAndMedicationId(@Param("stockInputId") Long stockInputId, @Param("medicationId") Long medicationId);

    @Query(value = "select case when (count(itemInput.id) > 0) then true else false end from ItemInput itemInput " +
            "where itemInput.stockInput.id = :stockInputId")
    boolean existsByStockInputId(@Param("stockInputId") Long stockInputId);
}

