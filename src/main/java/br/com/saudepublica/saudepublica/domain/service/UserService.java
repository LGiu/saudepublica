package br.com.saudepublica.saudepublica.domain.service;

import br.com.saudepublica.saudepublica.domain.dto.LoginDto;
import br.com.saudepublica.saudepublica.domain.dto.UserDto;
import br.com.saudepublica.saudepublica.domain.repository.UserRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User login(LoginDto loginDto) {
        return userRepository.loadUserByUsername(loginDto.getEmail());
    }

    public List<UserDto> getAll() {
        return userRepository.getAll().stream().map(user ->
                modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    public UserDto getById(Long id) throws Exception {
        return modelMapper.map(userRepository.getById(id), UserDto.class);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto save(UserDto userDto) {
        if(userDto.getId() == null){
            if (userRepository.existsByCpf(userDto.getCpf())) {
                userDto.setErrors(Collections.singletonList("O CPF já está cadastrado!"));
                return userDto;
            }

            if (userRepository.existsByCpf(userDto.getEmail())) {
                userDto.setErrors(Collections.singletonList("O email já está cadastrado!"));
                return userDto;
            }
        } else {
            if (userRepository.existsByCpfAndDifferentId(userDto.getCpf(), userDto.getId())) {
                userDto.setErrors(Collections.singletonList("O CPF já está cadastrado!"));
                return userDto;
            }

            if (userRepository.existsByEmailAndDifferentId(userDto.getEmail(), userDto.getId())) {
                userDto.setErrors(Collections.singletonList("O email já está cadastrado!"));
                return userDto;
            }
        }


        return modelMapper.map(
                userRepository.save(
                        modelMapper.map(userDto, User.class)),
                UserDto.class);
    }
}

