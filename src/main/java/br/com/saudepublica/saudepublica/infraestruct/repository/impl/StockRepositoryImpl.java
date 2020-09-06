package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.StockOutputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.MedicationDataRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.StockDataRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StockRepositoryImpl implements StockRepository {

    private final StockDataRepository stockDataRepository;

    public StockRepositoryImpl(StockDataRepository stockDataRepository) {
        this.stockDataRepository = stockDataRepository;
    }

    @Override
    public Stock save(Stock stock) {
        return stockDataRepository.save(stock);
    }

    @Override
    public void deleteById(Long id) {
        stockDataRepository.deleteById(id);
    }

    @Override
    public Stock getById(Long id) throws Exception {
        return stockDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Stock> getAll() {
        return stockDataRepository.findAll();
    }

    @Override
    public Optional<Stock> getByEstablishmentIdAndMedicationId(Long stockInputId, Long medicationId) {
        return stockDataRepository.findByEstablishmentIdAndMedicationId(stockInputId, medicationId);
    }
}

