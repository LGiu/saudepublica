package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemOutputRepository {

    ItemOutput save(ItemOutput stockOutput);

    void deleteById(Long id);

    ItemOutput getById(Long id) throws Exception;

    List<ItemOutput> getAll();

    List<ItemOutput> getByStockOutputId(Long stockOutputId);

    boolean existsByStockOutputIdAndMedicationIdAndDifferentId(Long id, Long stockOutputId, Long medicationId);

    boolean existsByStockOutputIdAndMedicationId(Long stockOutputId, Long medicationId);

    boolean existsByStockOutputId(Long stockOutputId);
}

