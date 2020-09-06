package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockDataRepository extends JpaRepository<Stock, Long> {


    @Query(value = "select stock from Stock stock where stock.establishment.id = :establishmentId and stock.medication.id = :medicationId")
    Optional<Stock> findByEstablishmentIdAndMedicationId(@Param("establishmentId") Long establishmentId, @Param("medicationId") Long medicationId);

}

