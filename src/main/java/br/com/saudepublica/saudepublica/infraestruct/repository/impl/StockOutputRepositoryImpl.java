package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.StockInputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockOutputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.StockOutputDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StockOutputRepositoryImpl implements StockOutputRepository {
    
    private final StockOutputDataRepository stockOutputDataRepository;

    public StockOutputRepositoryImpl(StockOutputDataRepository stockOutputDataRepository) {
        this.stockOutputDataRepository = stockOutputDataRepository;
    }

    @Override
    public StockOutput save(StockOutput stockOutput) {
        try {
            return stockOutputDataRepository.save(stockOutput);
        } catch (Exception e) {
            stockOutput.setErrors(Collections.singletonList(e.getMessage()));
            return stockOutput;
        }
    }

    @Override
    public void deleteById(Long id) {
        stockOutputDataRepository.deleteById(id);
    }

    @Override
    public StockOutput getById(Long id) throws Exception {
        return stockOutputDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<StockOutput> getAll() {
        return stockOutputDataRepository.findAllByOrderByDateDesc();
    }
}

