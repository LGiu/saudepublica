package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.LoginDto;
import br.com.saudepublica.saudepublica.domain.dto.EstablishmentDto;
import br.com.saudepublica.saudepublica.domain.repository.EstablishmentRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstablishmentService {

    private final EstablishmentRepository establishmentRepository;
    private final ModelMapper modelMapper;

    public EstablishmentService(EstablishmentRepository establishmentRepository, ModelMapper modelMapper) {
        this.establishmentRepository = establishmentRepository;
        this.modelMapper = modelMapper;
    }

    public List<EstablishmentDto> getAll() {
        return establishmentRepository.getAll().stream().map(establishment ->
                modelMapper.map(establishment, EstablishmentDto.class)).collect(Collectors.toList());
    }

    public EstablishmentDto getById(Long id) throws Exception {
        return modelMapper.map(establishmentRepository.getById(id), EstablishmentDto.class);
    }

    public void deleteById(Long id) {
        establishmentRepository.deleteById(id);
    }

    public EstablishmentDto save(EstablishmentDto establishmentDto) {
        if(establishmentDto.getId() == null){
            if(establishmentRepository.existsByCnpj(establishmentDto.getCnpj())){
                establishmentDto.setErrors(Collections.singletonList("O CNPJ já está cadastrado!"));
                return establishmentDto;
            }
        } else {
            if(establishmentRepository.existsByCnpjAndDifferentId(establishmentDto.getCnpj(), establishmentDto.getId())){
                establishmentDto.setErrors(Collections.singletonList("O CNPJ já está cadastrado!"));
                return establishmentDto;
            }
        }

        return modelMapper.map(
                establishmentRepository.save(
                        modelMapper.map(establishmentDto, Establishment.class)),
                EstablishmentDto.class);
    }
    
}

