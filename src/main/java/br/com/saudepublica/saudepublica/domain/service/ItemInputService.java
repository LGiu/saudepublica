package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.ItemInputDto;
import br.com.saudepublica.saudepublica.domain.repository.ItemInputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemInputService {

    private final ItemInputRepository itemInputRepository;
    private final ModelMapper modelMapper;

    public ItemInputService(ItemInputRepository itemInputRepository, ModelMapper modelMapper) {
        this.itemInputRepository = itemInputRepository;
        this.modelMapper = modelMapper;
    }

    public ItemInputDto getById(Long id) throws Exception {
        return modelMapper.map(itemInputRepository.getById(id), ItemInputDto.class);
    }

    public void deleteById(Long id) {
        itemInputRepository.deleteById(id);
    }

    public ItemInputDto save(ItemInputDto itemInputDto) {
        if (itemInputDto.getId() == null) {
            if (itemInputRepository.existsByStockInputIdAndMedicationId(itemInputDto.getStockInput().getId(), itemInputDto.getMedication().getId())) {
                itemInputDto.setErrors(Collections.singletonList("O item já existe na entrada de estoque!"));
                return itemInputDto;
            }
        } else {
            if (itemInputRepository.existsByStockInputIdAndMedicationIdAndDifferentId(
                    itemInputDto.getStockInput().getId(),
                    itemInputDto.getMedication().getId(),
                    itemInputDto.getId())) {
                itemInputDto.setErrors(Collections.singletonList("O item já existe na entrada de estoque!"));
                return itemInputDto;
            }
        }

        return modelMapper.map(
                itemInputRepository.save(
                        modelMapper.map(itemInputDto, ItemInput.class)),
                ItemInputDto.class);
    }

    public List<ItemInputDto> getByStockInputId(Long stockInputId) {
        return itemInputRepository.getByStockInputId(stockInputId).stream().map(itemInput ->
                modelMapper.map(itemInput, ItemInputDto.class)).collect(Collectors.toList());
    }

}

