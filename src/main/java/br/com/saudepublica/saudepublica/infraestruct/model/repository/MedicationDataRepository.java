package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationDataRepository extends JpaRepository<Medication, Long> {

}

