package br.com.saudepublica.saudepublica.infraestruct.model.repository;

import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "select case when (count(user.id) > 0) then true else false end from User user where user.cpf = :cpf and user.id <> :id")
    boolean existsByCpfAndDifferentId(@Param("cpf") String cpf, @Param("id") Long id);

    boolean existsByCpf(String cpf);

    @Query(value = "select case when (count(user.id) > 0) then true else false end from User user where user.email = :email and user.id <> :id")
    boolean existsByEmailAndDifferentId(@Param("email") String email, @Param("id") Long id);

    boolean existsByEmail(String email);

}

