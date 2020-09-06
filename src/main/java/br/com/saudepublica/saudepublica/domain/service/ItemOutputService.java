package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.ItemOutputDto;
import br.com.saudepublica.saudepublica.domain.repository.ItemOutputRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemOutput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemOutputService {

    private final ItemOutputRepository itemOutputRepository;
    private final ModelMapper modelMapper;

    public ItemOutputService(ItemOutputRepository itemOutputRepository, ModelMapper modelMapper) {
        this.itemOutputRepository = itemOutputRepository;
        this.modelMapper = modelMapper;
    }

    public ItemOutputDto getById(Long id) throws Exception {
        return modelMapper.map(itemOutputRepository.getById(id), ItemOutputDto.class);
    }

    public void deleteById(Long id) {
        itemOutputRepository.deleteById(id);
    }

    public ItemOutputDto save(ItemOutputDto itemOutputDto) {
        if (itemOutputDto.getId() == null) {
            if (itemOutputRepository.existsByStockOutputIdAndMedicationId(itemOutputDto.getStockOutput().getId(), itemOutputDto.getMedication().getId())) {
                itemOutputDto.setErrors(Collections.singletonList("O item já existe na saída de estoque!"));
                return itemOutputDto;
            }
        } else {
            if (itemOutputRepository.existsByStockOutputIdAndMedicationIdAndDifferentId(
                    itemOutputDto.getStockOutput().getId(),
                    itemOutputDto.getMedication().getId(),
                    itemOutputDto.getId())) {
                itemOutputDto.setErrors(Collections.singletonList("O item já existe na saída de estoque!"));
                return itemOutputDto;
            }
        }

        return modelMapper.map(
                itemOutputRepository.save(
                        modelMapper.map(itemOutputDto, ItemOutput.class)),
                ItemOutputDto.class);
    }

    public List<ItemOutputDto> getByStockOutputId(Long stockInputId) {
        return itemOutputRepository.getByStockOutputId(stockInputId).stream().map(itemOutput ->
                modelMapper.map(itemOutput, ItemOutputDto.class)).collect(Collectors.toList());
    }

}

