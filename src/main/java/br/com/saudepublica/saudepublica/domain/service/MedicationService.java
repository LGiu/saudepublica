package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.MedicationDto;
import br.com.saudepublica.saudepublica.domain.repository.MedicationRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final ModelMapper modelMapper;

    public MedicationService(MedicationRepository medicationRepository, ModelMapper modelMapper) {
        this.medicationRepository = medicationRepository;
        this.modelMapper = modelMapper;
    }

    public List<MedicationDto> getAll() {
        return medicationRepository.getAll().stream().map(medication ->
                modelMapper.map(medication, MedicationDto.class)).collect(Collectors.toList());
    }

    public MedicationDto getById(Long id) throws Exception {
        return modelMapper.map(medicationRepository.getById(id), MedicationDto.class);
    }

    public void deleteById(Long id) {
        medicationRepository.deleteById(id);
    }

    public MedicationDto save(MedicationDto medicationDto) {
        if (medicationDto.getDueDate().isBefore(LocalDate.now())) {
            medicationDto.setErrors(Collections.singletonList("A data de vencimento não pode ser menor que hoje!"));
            return medicationDto;
        }

        return modelMapper.map(
                medicationRepository.save(
                        modelMapper.map(medicationDto, Medication.class)),
                MedicationDto.class);
    }

}

