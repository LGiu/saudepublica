package br.com.saudepublica.saudepublica.domain.util;

import br.com.saudepublica.saudepublica.domain.repository.UserRepository;
import br.com.saudepublica.saudepublica.infraestruct.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionProvider {

    private final UserRepository userRepository;

    public SessionProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        return userRepository.loadUserByUsername(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername()
        );
    }
}
