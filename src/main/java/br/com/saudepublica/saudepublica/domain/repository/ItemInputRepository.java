package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemInputRepository {
    
    ItemInput save(ItemInput stockInput);

    void deleteById(Long id);

    ItemInput getById(Long id) throws Exception;

    List<ItemInput> getAll();

    List<ItemInput> getByStockInputId(Long stockInputId);

    boolean existsByStockInputIdAndMedicationIdAndDifferentId(Long id, Long stockInputId, Long medicationId);

    boolean existsByStockInputIdAndMedicationId(Long stockInputId, Long medicationId);

    boolean existsByStockInputId(Long stockInputId);

}

