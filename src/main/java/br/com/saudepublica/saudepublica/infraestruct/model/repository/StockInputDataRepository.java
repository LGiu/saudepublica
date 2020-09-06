package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.ItemInput;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.StockInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface StockInputDataRepository extends JpaRepository<StockInput, Long> {

    List<StockInput> findAllByOrderByDateDesc();

}

