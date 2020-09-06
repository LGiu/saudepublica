package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StockInputRepository {

    StockInput save(StockInput stockInput);

    void deleteById(Long id);

    StockInput getById(Long id) throws Exception;

    List<StockInput> getAll();

}

