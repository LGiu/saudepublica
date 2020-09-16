package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.ItemInputDto;
import br.com.saudepublica.saudepublica.domain.dto.StockInputDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.domain.repository.ItemInputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockInputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockInputService {

    private final StockInputRepository stockInputRepository;
    private final ItemInputRepository itemInputRepository;
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;

    public StockInputService(StockInputRepository stockInputRepository, ItemInputRepository itemInputRepository, StockRepository stockRepository, ModelMapper modelMapper) {
        this.stockInputRepository = stockInputRepository;
        this.itemInputRepository = itemInputRepository;
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    public List<StockInputDto> getAll() {
        return stockInputRepository.getAll().stream().map(stockInput ->
                modelMapper.map(stockInput, StockInputDto.class)).collect(Collectors.toList());
    }

    public StockInputDto getById(Long id) throws Exception {
        return modelMapper.map(stockInputRepository.getById(id), StockInputDto.class);
    }

    public void deleteById(Long id) {
        stockInputRepository.deleteById(id);
    }

    public StockInputDto save(StockInputDto stockInputDto) {
        if (stockInputDto.getId() == null) {
            stockInputDto.setStatus(StockStatus.SAVED);
        } else {
            stockInputDto.setStatus(StockStatus.FINALIZED);
        }

        stockInputDto.setItemInputList(
                itemInputRepository.getByStockInputId(stockInputDto.getId()).stream().map(itemInput ->
                        modelMapper.map(itemInput, ItemInputDto.class)).collect(Collectors.toList()));
        if (stockInputDto.getStatus().equals(StockStatus.FINALIZED) &&
                (stockInputDto.getItemInputList() == null || stockInputDto.getItemInputList().isEmpty())) {
            stockInputDto.setErrors(Collections.singletonList("É necessário pelo menos um item para FINALIZAR a entrada de estoque!"));
            return stockInputDto;
        }

        if (stockInputDto.getStatus().equals(StockStatus.FINALIZED)) {
            stockInputDto.getItemInputList().forEach(itemInputDto -> {
                Optional<Stock> stockOptional = stockRepository.getByEstablishmentIdAndMedicationId(stockInputDto.getEstablishment().getId(), itemInputDto.getMedication().getId());
                Stock stock;
                if (stockOptional.isPresent()) {
                    stock = stockOptional.get();
                    stock.setAmount(stock.getAmount() + itemInputDto.getAmount());
                } else {
                    stock = new Stock();
                    stock.setEstablishment(modelMapper.map(stockInputDto.getEstablishment(), Establishment.class));
                    stock.setMedication(modelMapper.map(itemInputDto.getMedication(), Medication.class));
                    stock.setAmount(itemInputDto.getAmount());
                }
                stockRepository.save(stock);
            });
        }

        stockInputDto.setDate(LocalDateTime.now());


        return modelMapper.map(
                stockInputRepository.save(
                        modelMapper.map(stockInputDto, StockInput.class)),
                StockInputDto.class);
    }

}

