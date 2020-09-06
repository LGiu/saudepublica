package br.com.saudepublica.saudepublica.infraestruct.repository.impl;

import br.com.saudepublica.saudepublica.domain.repository.UserRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import br.com.saudepublica.saudepublica.infraestruct.model.repository.UserDataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

@Component
public class UserRepositoryImpl implements UserRepository, UserDetailsService {

    private final UserDataRepository userDataRepository;

    public UserRepositoryImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDataRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return user;
    }

    @Override
    public User save(User user) {
        try {
           return userDataRepository.save(user);
        } catch (Exception e) {
            user.setErrors(Collections.singletonList(e.getMessage()));
            return user;
        }
    }

    @Override
    public void deleteById(Long id) {
        userDataRepository.deleteById(id);
    }

    @Override
    public User getById(Long id) throws Exception {
        return userDataRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public List<User> getAll() {
        return userDataRepository.findAll();
    }

    @Override
    public boolean existsByCpfAndDifferentId(String cpf, Long id) {
        return userDataRepository.existsByCpfAndDifferentId(cpf, id);
    }

    @Override
    public boolean existsByEmailAndDifferentId(String email, Long id) {
        return userDataRepository.existsByEmailAndDifferentId(email, id);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return userDataRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDataRepository.existsByEmail(email);
    }


}

