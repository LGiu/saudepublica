package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.EstablishmentRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.EstablishmentDataRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class EstablishmentRepositoryImpl implements EstablishmentRepository {
    
    private final EstablishmentDataRepository establishmentDataRepository;

    public EstablishmentRepositoryImpl(EstablishmentDataRepository establishmentDataRepository) {
        this.establishmentDataRepository = establishmentDataRepository;
    }

    @Override
    public Establishment save(Establishment establishment) {
        try {
            return establishmentDataRepository.save(establishment);
        } catch (Exception e) {
            establishment.setErrors(Collections.singletonList(e.getMessage()));
            return establishment;
        }
    }

    @Override
    public void deleteById(Long id) {
        establishmentDataRepository.deleteById(id);
    }

    @Override
    public Establishment getById(Long id) throws Exception {
        return establishmentDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<Establishment> getAll() {
        return establishmentDataRepository.findAll();
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return establishmentDataRepository.existsByCnpj(cnpj);
    }

    @Override
    public boolean existsByCnpjAndDifferentId(String cnpj, Long id) {
        return establishmentDataRepository.existsByCnpjAndDifferentId(cnpj, id);
    }
}

