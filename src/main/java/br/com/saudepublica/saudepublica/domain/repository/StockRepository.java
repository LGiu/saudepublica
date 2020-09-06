package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    
    Stock save(Stock stock);

    void deleteById(Long id);

    Stock getById(Long id) throws Exception;

    List<Stock> getAll();

    Optional<Stock> getByEstablishmentIdAndMedicationId(Long stockInputId, Long medicationId);

}

