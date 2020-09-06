package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EstablishmentRepository {

    Establishment save(Establishment establishment);

    void deleteById(Long id);

    Establishment getById(Long id) throws Exception;

    List<Establishment> getAll();

    boolean existsByCnpj(String cnpj);

    boolean existsByCnpjAndDifferentId(String cnpj,  Long id);

}

