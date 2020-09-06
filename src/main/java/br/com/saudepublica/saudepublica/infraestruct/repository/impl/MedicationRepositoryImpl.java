package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.ItemOutputRepository;
import br.com.saudepublica.saudepublica.domain.repository.MedicationRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.MedicationDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MedicationRepositoryImpl implements MedicationRepository {

    private final MedicationDataRepository medicationDataRepository;

    public MedicationRepositoryImpl(MedicationDataRepository medicationDataRepository) {
        this.medicationDataRepository = medicationDataRepository;
    }

    @Override
    public Medication save(Medication medication) {
        try {
            return medicationDataRepository.save(medication);
        } catch (Exception e) {
            medication.setErrors(Collections.singletonList(e.getMessage()));
            return medication;
        }
    }

    @Override
    public void deleteById(Long id) {
        medicationDataRepository.deleteById(id);
    }

    @Override
    public Medication getById(Long id) throws Exception {
        return medicationDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Medication> getAll() {
        return medicationDataRepository.findAll();
    }

}

