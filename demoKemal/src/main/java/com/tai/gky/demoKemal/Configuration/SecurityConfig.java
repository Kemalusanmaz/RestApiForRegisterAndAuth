package com.tai.gky.demoKemal.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration //Konfigürsayon sınıfı anotasyonu
@EnableWebSecurity //Web günveliğinin aktif edildiği anotasyon
public class SecurityConfig {



//########################################### Basic User Regiser ################################################

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



//########################################### Basic User Authentication ################################################

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http)throws Exception {

        http
                //.csrf(AbstractHttpConfigurer::disable) //Register post işlemi için autharization'ı disable eder. CSRF korumasını devre dışı bırakır.
                //.authorizeRequests().anyRequest().permitAll(); //authenticate isteklerinin hepsine izin verir.
                .authorizeRequests().anyRequest().authenticated() // Tüm isteklerin kimlik doğrulama (authentication) gerektirdiğini belirtir. spring tüm endpointleri korur
                //Bu ayar ile spring tarafından üretilen password ve user kullanıcı adı ile giriş yapılır.
                .and()
                .httpBasic(); // HTTP temel kimlik doğrulama kullanılacak şekilde yapılandırılır. Spring, kullanıcı adı ve şifre ile doğrulama sağlar.

        return http.build(); // Yapılandırmayı uygula ve SecurityFilterChain bean'ini döndür.
    }


}