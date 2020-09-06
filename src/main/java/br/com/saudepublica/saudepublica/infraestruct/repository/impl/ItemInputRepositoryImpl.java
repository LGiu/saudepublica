package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.ItemInputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.ItemInputDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ItemInputRepositoryImpl implements ItemInputRepository {

    private final ItemInputDataRepository itemInputDataRepository;

    public ItemInputRepositoryImpl(ItemInputDataRepository itemInputDataRepository) {
        this.itemInputDataRepository = itemInputDataRepository;
    }

    @Override
    public ItemInput save(ItemInput itemInput) {
        try {
            return itemInputDataRepository.save(itemInput);
        } catch (Exception e) {
            itemInput.setErrors(Collections.singletonList(e.getMessage()));
            return itemInput;
        }
    }

    @Override
    public void deleteById(Long id) {
        itemInputDataRepository.deleteById(id);
    }

    @Override
    public ItemInput getById(Long id) throws Exception {
        return itemInputDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<ItemInput> getAll() {
        return itemInputDataRepository.findAll();
    }

    @Override
    public List<ItemInput> getByStockInputId(Long stockInputId) {
        return itemInputDataRepository.findByStockInputId(stockInputId);
    }

    @Override
    public boolean existsByStockInputIdAndMedicationIdAndDifferentId(Long id, Long stockInputId, Long medicationId) {
        return itemInputDataRepository.existsByStockInputIdAndMedicationIdAndDifferentId(stockInputId, medicationId, id);
    }

    @Override
    public boolean existsByStockInputIdAndMedicationId(Long stockInputId, Long medicationId) {
        return itemInputDataRepository.existsByStockInputIdAndMedicationId(stockInputId, medicationId);
    }

    @Override
    public boolean existsByStockInputId(Long stockInputId) {
        return itemInputDataRepository.existsByStockInputId(stockInputId);
    }

}

