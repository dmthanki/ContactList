package com.contacts.Security;

import com.contacts.dao.ConttactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private ConttactDao conttactDao;

    @Autowired
    public SecurityConfig(ConttactDao conttactDao){
        this.conttactDao = conttactDao;
    }


    @Bean
    public static PasswordEncoder passEncode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((a)->a.requestMatchers("contactlist/*").authenticated()
                ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.builder()
                .username("admin")
                .password(passEncode().encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user")
                .password(passEncode().encode("user"))
                .roles("USER")
                .build();

        List<UserDetails> details = new ArrayList<>();

        for (int i = 0 ; i < conttactDao.eMails().size(); i++){
            UserDetails user = User.builder()
                    .username(conttactDao.eMails().get(i))
                    .password(passEncode().encode(conttactDao.eMails().get(i)))
                    .roles("USER")
                    .build();
            details.add(user);
        }

        details.add(user1);
        details.add(user2);

        return new InMemoryUserDetailsManager(details);
    }

}
