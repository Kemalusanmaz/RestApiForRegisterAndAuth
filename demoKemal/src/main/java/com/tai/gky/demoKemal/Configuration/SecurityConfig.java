package com.tai.gky.demoKemal.Configuration;

import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
@Configuration //Konfigürsayon sınıfı anotasyonu
@EnableWebSecurity //Web günveliğinin aktif edildiği anotasyon
public class SecurityConfig {



//########################################### Basic User Authentication ################################################

//    @Bean
//    public UserDetailsService userDetailsService(){ //Kullanıcı detatlarının sağlandığı sınıf
//
//        Collection<UserDetails> users = new ArrayList<>(); //Kullanıcı detayları UserDetail tipinde user isimli collection olarak saklanır.
//        UserDetails userDetails = User.builder() //Kullanıcı ayrıntıları oluşturulur. oluşturulur.
//                .username("user")
//                .password(passwordEncoder().encode("user"))
//                .roles("USER")
//                .build();
//        users.add(userDetails); //user collectiona eklenir.
//        UserDetails userDetails1 = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER")
//                .build();
//        users.add(userDetails1);
//
//        return new InMemoryUserDetailsManager(users); //Kullanıcı bilgilerini bellekte saklar
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() { //Şifreler bu yöntemle şifrelenir.
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http)throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable); //Register post işlemi için autharization'ı disable eder.


        return http.build();
    }

}