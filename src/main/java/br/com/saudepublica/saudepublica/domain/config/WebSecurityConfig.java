package br.com.saudepublica.saudepublica.domain.config;


import br.com.saudepublica.saudepublica.domain.repository.UserRepository;
import br.com.saudepublica.saudepublica.domain.service.UserService;
import br.com.saudepublica.saudepublica.infraestruct.repository.impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private EmployeeAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/cadastrarEvento").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                    .loginPage("/login")
                    .successHandler(successHandler)
                .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userRepositoryImpl)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/materialize/**", "/style/**", "/css/**", "/js/**");
    }

}
