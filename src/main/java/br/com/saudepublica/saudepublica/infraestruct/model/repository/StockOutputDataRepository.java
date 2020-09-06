package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOutputDataRepository extends JpaRepository<StockOutput, Long> {

    List<StockOutput> findAllByOrderByDateDesc();

}

