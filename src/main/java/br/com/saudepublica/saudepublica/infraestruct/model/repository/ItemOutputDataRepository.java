package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOutputDataRepository extends JpaRepository<ItemOutput, Long> {

    @Query(value = "select itemOutput from ItemOutput itemOutput where itemOutput.stockOutput.id = :stockOutputId")
    List<ItemOutput> findByStockOutputId(@Param("stockOutputId") Long stockOutputId);

    @Query(value = "select case when (count(itemOutput.id) > 0) then true else false end from ItemOutput itemOutput " +
            "where itemOutput.stockOutput.id = :stockOutputId and itemOutput.medication.id  = :medicationId and itemOutput.id <> :id")
    boolean existsByStockOutputIdAndMedicationIdAndDifferentId(@Param("stockOutputId") Long stockOutputId, @Param("medicationId") Long medicationId, @Param("id") Long id);

    @Query(value = "select case when (count(itemOutput.id) > 0) then true else false end from ItemOutput itemOutput " +
            "where itemOutput.stockOutput.id = :stockOutputId and itemOutput.medication.id  = :medicationId")
    boolean existsByStockOutputIdAndMedicationId(@Param("stockOutputId") Long stockOutputId, @Param("medicationId") Long medicationId);

    @Query(value = "select case when (count(itemOutput.id) > 0) then true else false end from ItemOutput itemOutput " +
            "where itemOutput.stockOutput.id = :stockOutputId")
    boolean existsByStockOutputId(@Param("stockOutputId") Long stockOutputId);
    
}

