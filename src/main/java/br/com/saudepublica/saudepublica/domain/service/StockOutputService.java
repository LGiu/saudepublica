package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.ItemInputDto;
import br.com.saudepublica.saudepublica.domain.dto.ItemOutputDto;
import br.com.saudepublica.saudepublica.domain.dto.StockInputDto;
import br.com.saudepublica.saudepublica.domain.dto.StockOutputDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeStockOutput;
import br.com.saudepublica.saudepublica.domain.repository.ItemOutputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockInputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockOutputRepository;
import br.com.saudepublica.saudepublica.domain.repository.StockRepository;
import br.com.saudepublica.saudepublica.domain.util.ValidaCPF;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Stock;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockOutputService {

    private final StockOutputRepository stockOutputRepository;
    private final ItemOutputRepository itemOutputRepository;
    private final StockRepository stockRepository;
    private final StockInputService stockInputService;
    private final ItemInputService itemInputService;
    private final EstablishmentService establishmentService;
    private final ModelMapper modelMapper;

    public StockOutputService(StockOutputRepository stockOutputRepository, ItemOutputRepository itemOutputRepository, StockRepository stockRepository, StockInputRepository stockInputRepository, StockInputService stockInputService, ItemInputService itemInputService, EstablishmentService establishmentService, ModelMapper modelMapper) {
        this.stockOutputRepository = stockOutputRepository;
        this.itemOutputRepository = itemOutputRepository;
        this.stockRepository = stockRepository;
        this.stockInputService = stockInputService;
        this.itemInputService = itemInputService;
        this.establishmentService = establishmentService;
        this.modelMapper = modelMapper;
    }

    public List<StockOutputDto> getAll() {
        return stockOutputRepository.getAll().stream().map(stockOutput ->
                modelMapper.map(stockOutput, StockOutputDto.class)).collect(Collectors.toList());
    }

    public StockOutputDto getById(Long id) throws Exception {
        return modelMapper.map(stockOutputRepository.getById(id), StockOutputDto.class);
    }

    public void deleteById(Long id) {
        stockOutputRepository.deleteById(id);
    }

    public StockOutputDto save(StockOutputDto stockOutputDto) throws Exception {
        if (stockOutputDto.getId() == null) {
            stockOutputDto.setStatus(StockStatus.SAVED);
        } else {
            stockOutputDto.setStatus(StockStatus.FINALIZED);
        }

        if (stockOutputDto.getType() != null && stockOutputDto.getType().equals(TypeStockOutput.DISPEN)) {
            if (StringUtils.isEmpty(stockOutputDto.getCpf()) || StringUtils.isEmpty(stockOutputDto.getName())) {
                stockOutputDto.setErrors(Collections.singletonList("É necessário informar nome e CPF do paciente!"));
                return stockOutputDto;
            }
            if (!ValidaCPF.isCPF(stockOutputDto.getCpf())) {
                stockOutputDto.setErrors(Collections.singletonList("CPF inválido!"));
                return stockOutputDto;
            }
            stockOutputDto.setTransferEstablishment(null);
        } else if (stockOutputDto.getType() != null && stockOutputDto.getType().equals(TypeStockOutput.TRANS)) {
            if (stockOutputDto.getTransferEstablishment() == null) {
                stockOutputDto.setErrors(Collections.singletonList("É necessário informar o estabelecimento a ser transferido!"));
                return stockOutputDto;
            }
            if (stockOutputDto.getEstablishment().getId().equals(stockOutputDto.getTransferEstablishment().getId())) {
                stockOutputDto.setErrors(Collections.singletonList("Não é possível transferir para o mesmo estabelecimento informado!"));
                return stockOutputDto;
            }
            stockOutputDto.setName(null);
            stockOutputDto.setCpf(null);
        }

        stockOutputDto.setItemOutputList(
                itemOutputRepository.getByStockOutputId(stockOutputDto.getId()).stream().map(itemOutput ->
                        modelMapper.map(itemOutput, ItemOutputDto.class)).collect(Collectors.toList()));
        if (stockOutputDto.getStatus().equals(StockStatus.FINALIZED) &&
                (stockOutputDto.getItemOutputList() == null || stockOutputDto.getItemOutputList().isEmpty())) {
            stockOutputDto.setErrors(Collections.singletonList("É necessário pelo menos um item para FINALIZAR a saída de estoque!"));
            return stockOutputDto;
        }

        if (stockOutputDto.getStatus().equals(StockStatus.FINALIZED)) {
            stockOutputDto.setDate(LocalDateTime.now());

            for (ItemOutputDto itemOutputDto : stockOutputDto.getItemOutputList()) {
                Optional<Stock> stockOptional = stockRepository.getByEstablishmentIdAndMedicationId(stockOutputDto.getEstablishment().getId(), itemOutputDto.getMedication().getId());
                Stock stock;
                if (stockOptional.isPresent()) {
                    stock = stockOptional.get();
                    if (stock.getAmount() >= itemOutputDto.getAmount()) {
                        stock.setAmount(stock.getAmount() - itemOutputDto.getAmount());
                    } else {
                        stockOutputDto.setErrors(Collections.singletonList("O item '" + itemOutputDto.getMedication().getName() + "' não possuí estoque suficiente!"));
                        return stockOutputDto;
                    }
                } else {
                    stockOutputDto.setErrors(Collections.singletonList("O item '" + itemOutputDto.getMedication().getName() + "' não possuí estoque suficiente!"));
                    return stockOutputDto;
                }
                stockRepository.save(stock);
            }

            if (stockOutputDto.getType().equals(TypeStockOutput.TRANS)) {
                StockInputDto stockInput = new StockInputDto();
                stockInput.setEstablishment(stockOutputDto.getTransferEstablishment());
                stockInput.setDate(stockOutputDto.getDate());
                stockInput.setProvider("Transferência do estabelcimento " + establishmentService.getById(stockOutputDto.getEstablishment().getId()).getName());
                stockInput = stockInputService.save(stockInput);
                stockInput.setItemInputList(new ArrayList<>());
                for (ItemOutputDto itemOutputDto : stockOutputDto.getItemOutputList()) {
                    ItemInputDto itemInputDto = new ItemInputDto();
                    itemInputDto.setStockInput(stockInput);
                    itemInputDto.setMedication(itemOutputDto.getMedication());
                    itemInputDto.setAmount(itemOutputDto.getAmount());
                    stockInput.getItemInputList().add(itemInputService.save(itemInputDto));
                }
                stockInputService.save(stockInput);
            }
        }

        return modelMapper.map(
                stockOutputRepository.save(
                        modelMapper.map(stockOutputDto, StockOutput.class)),
                StockOutputDto.class);
    }

}

