package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.StockInputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockInputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.StockInputDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StockInputRepositoryImpl implements StockInputRepository {

    private final StockInputDataRepository stockInputDataRepository;

    public StockInputRepositoryImpl(StockInputDataRepository stockInputDataRepository) {
        this.stockInputDataRepository = stockInputDataRepository;
    }

    @Override
    public StockInput save(StockInput stockInput) {
        try {
            return stockInputDataRepository.save(stockInput);
        } catch (Exception e) {
            stockInput.setErrors(Collections.singletonList(e.getMessage()));
            return stockInput;
        }
    }

    @Override
    public void deleteById(Long id) {
        stockInputDataRepository.deleteById(id);
    }

    @Override
    public StockInput getById(Long id) throws Exception {
        return stockInputDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<StockInput> getAll() {
        return stockInputDataRepository.findAllByOrderByDateDesc();
    }
}

