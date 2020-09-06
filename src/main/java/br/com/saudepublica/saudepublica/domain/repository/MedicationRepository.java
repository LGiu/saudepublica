package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MedicationRepository {

    Medication save(Medication medication);

    void deleteById(Long id);

    Medication getById(Long id) throws Exception;

    List<Medication> getAll();

}

