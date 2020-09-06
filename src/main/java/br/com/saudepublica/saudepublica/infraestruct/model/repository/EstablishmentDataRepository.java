package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentDataRepository extends JpaRepository<Establishment, Long> {

    @Query(value = "select case when (count(establishment.id) > 0) then true else false end from Establishment establishment " +
            "where establishment.cnpj = :cnpj and establishment.id <> :id")
    boolean existsByCnpjAndDifferentId(@Param("cnpj") String cnpj, @Param("id") Long id);

    boolean existsByCnpj(String cnpj);

}

