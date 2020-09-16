package br.com.saudepublica.saudepublica.application.util;

import br.com.saudepublica.saudepublica.domain.dto.UserDto;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;

public class Session {

    public static UserDto getUser(){
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setPassword(null);
            return new ModelMapper().map(user, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

}
