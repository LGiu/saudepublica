package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.ItemOutputRepository;
import br.com.saudepublica.saudepublica.domain.repository.ItemOutputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.ItemOutputDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ItemOutputRepositoryImpl implements ItemOutputRepository {
    
    private final ItemOutputDataRepository itemInputDataRepository;

    public ItemOutputRepositoryImpl(ItemOutputDataRepository itemInputDataRepository) {
        this.itemInputDataRepository = itemInputDataRepository;
    }

    @Override
    public ItemOutput save(ItemOutput itemInput) {
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
    public ItemOutput getById(Long id) throws Exception {
        return itemInputDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<ItemOutput> getAll() {
        return itemInputDataRepository.findAll();
    }

    @Override
    public List<ItemOutput> getByStockOutputId(Long stockInputId) {
        return itemInputDataRepository.findByStockOutputId(stockInputId);
    }

    @Override
    public boolean existsByStockOutputIdAndMedicationIdAndDifferentId(Long id, Long stockInputId, Long medicationId) {
        return itemInputDataRepository.existsByStockOutputIdAndMedicationIdAndDifferentId(stockInputId, medicationId, id);
    }

    @Override
    public boolean existsByStockOutputIdAndMedicationId(Long stockInputId, Long medicationId) {
        return itemInputDataRepository.existsByStockOutputIdAndMedicationId(stockInputId, medicationId);
    }

    @Override
    public boolean existsByStockOutputId(Long stockInputId) {
        return itemInputDataRepository.existsByStockOutputId(stockInputId);
    }
}

