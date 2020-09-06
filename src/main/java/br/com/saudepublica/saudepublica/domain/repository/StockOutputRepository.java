package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StockOutputRepository {

    StockOutput save(StockOutput stockOutput);

    void deleteById(Long id);

    StockOutput getById(Long id) throws Exception;

    List<StockOutput> getAll();
    
}

