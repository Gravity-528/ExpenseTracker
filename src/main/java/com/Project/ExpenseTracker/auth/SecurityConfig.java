package com.Project.ExpenseTracker.auth;

import com.Project.ExpenseTracker.repository.UserRepository;
import com.Project.ExpenseTracker.service.UserDetailServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
@Data
public class SecurityConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserDetailServiceImpl userDetailService;

    @Bean
    @Autowired
    public UserDetailsService userDetailsService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        return new UserDetailServiceImpl(userRepository,passwordEncoder);
    }
}
