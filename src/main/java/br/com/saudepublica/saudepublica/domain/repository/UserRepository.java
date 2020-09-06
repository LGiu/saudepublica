package br.com.saudepublica.saudepublica.domain.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository  {

    User loadUserByUsername(String email);

    User save(User user);

    void deleteById(Long id);

    User getById(Long id) throws Exception;

    List<User> getAll();

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpfAndDifferentId(String cpf, Long id);

    boolean existsByEmailAndDifferentId(String email, Long id);
}

