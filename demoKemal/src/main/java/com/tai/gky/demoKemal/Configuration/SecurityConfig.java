package com.tai.gky.demoKemal.Configuration;


import com.tai.gky.demoKemal.Repository.IUserRepository;

import com.tai.gky.demoKemal.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.tai.gky.demoKemal.Enums.Role;



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

//    @Bean
//    public SecurityFilterChain SecurityFilterChain(HttpSecurity http)throws Exception {
//
//        http
//                //.csrf(AbstractHttpConfigurer::disable) //Register post işlemi için autharization'ı disable eder. CSRF korumasını devre dışı bırakır.
//                //.authorizeRequests().anyRequest().permitAll(); //authenticate isteklerinin hepsine izin verir.
//                .authorizeRequests().anyRequest().authenticated() // Tüm isteklerin kimlik doğrulama (authentication) gerektirdiğini belirtir. spring tüm endpointleri korur
//                //Bu ayar ile spring tarafından üretilen password ve user kullanıcı adı ile giriş yapılır.
//                .and()
//                .httpBasic(); // HTTP temel kimlik doğrulama kullanılacak şekilde yapılandırılır. Spring, kullanıcı adı ve şifre ile doğrulama sağlar. Tercih edilmeyen bir kullanıştır.
//
//        return http.build(); // Yapılandırmayı uygula ve SecurityFilterChain bean'ini döndür.
//    }


    //########################################### Session-Based User Authentication ################################################

    //Varsayılan filter chain'dir.
    //Request ilk geldiğinde filtreden geçer.
    // 1. TÜm yollarda kimlik doğrulmasını (authentication) HTTP Basic veya formLogin ile yap.
    // 2. No authoraziton: Tüm kimlik doğrulması yapılan kullanıcılar tüm yollara erişebilir.

//    @Bean
//    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()//csrf filtresi kapatılır.
//                //.authorizeHttpRequests().requestMatchers("/**").authenticated() //Tüm yollar için kimlik doğrulaması istenir.
//                .authorizeHttpRequests()
//                .requestMatchers("/").permitAll() //root'a herkes erişebilir
//                .requestMatchers("/register").permitAll()
//                //.requestMatchers("/user").hasAnyRole("USER","ADMIN") //user sayfasına ADMIN ve USER yetkisi olanlar erişebilir. Credential bilgileri InMemory den çrkildiğinde hasAnyRole kullanılır
//                //.requestMatchers("/admin").hasAnyRole("ADMIN") //admin sayfasına ADMIN  yetkisi olanlar erişebilir.
//                .requestMatchers("/user").hasAnyAuthority("USER", "ADMIN") //user sayfasına ADMIN ve USER yetkisi olanlar erişebilir. User credential bilgileri databaseden çekildiğinde hasAnyAuthority kullanılır.
//                .requestMatchers("/admin").hasAnyAuthority("ADMIN") //admin sayfasına ADMIN  yetkisi olanlar erişebilir.
//                .and()
//                .httpBasic()//HTTP Basic ile kimlik doğrulması yapılır
//                .and()
//                .formLogin()//Session-based kimlik doğrulaması username-password tabanlı kimlik doğrulama yapılır. (UsernamePasswordAuthenticationFilter nesnesini insert eder.)
//                // Username ve password bilgileri extract edilerek authentication objesi oluşturulur.
//                // Bu obje AuthenticationManager nesnesine gönderilir.
//                .and()
//                .logout().deleteCookies("JSESSIONID"); //çıkış yapılırken log in'de oluşturulan JSESSIONID'yi SecurityContextHolder (Session Table)'dan temizler ve expired date döndürülür.
//
//        return http.build();
//    }


//    @Bean
//    public AuthenticationProvider authenticationProvider(){ //Kimlik Doğrulama Sağlayıcısı seçebilmek için Spring'ten gelen AuthenticationProvider nesne referansı alınır.
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(); //Session-based kimlik ooğrlama yapılacağı için DaoAuthenticationProvider nesnesinin referansı alınır.
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    //////////////////////  InMemoryUserDetailManager'dan user credentials çekildiğinde    ////////////////////////
//    @Bean
//    public UserDetailsService userDetailsService() { //Kullanıcı detaylarının çekileceği servis
//        PasswordEncoder encoder = passwordEncoder();
//
//        UserDetails user = User.withUsername("user")
//                .password(encoder.encode("user"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user); //Userları memory'de tutan bir manager. loadByUserName metodu implemente eder.
//    }


    //////////////////////////////  Database'den User Crendential Çekildiğinde    ////////////////////////////////

//    private final UserService service;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return service;
//    }



//  }

//############################# JWT AUTHENTICATION ####################################################################


    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//csrf filtresi kapatılır.
                //.authorizeHttpRequests().requestMatchers("/**").authenticated() //Tüm yollar için kimlik doğrulaması istenir.
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll() //root'a herkes erişebilir
                .requestMatchers("/register").permitAll()

                .requestMatchers("/user").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name()) //user sayfasına ADMIN ve USER yetkisi olanlar erişebilir. User credential bilgileri databaseden çekildiğinde hasAnyAuthority kullanılır.
                .requestMatchers("/admin").hasAnyAuthority(Role.ADMIN.name()) //admin sayfasına ADMIN  yetkisi olanlar erişebilir.
                //.requestMatchers("/user").hasAnyRole("USER","ADMIN") //user sayfasına ADMIN ve USER yetkisi olanlar erişebilir. Credential bilgileri InMemory den çrkildiğinde hasAnyRole kullanılır
                //.requestMatchers("/admin").hasAnyRole("ADMIN") //admin sayfasına ADMIN  yetkisi olanlar erişebilir.

                .requestMatchers("/authenticate").permitAll().anyRequest().authenticated() //herhangi bir http requesti GET,POST kimlik doğrulamalı olur.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //kullanıcı oturum açtıktan sonra bir cookie kullanılmadan yani stateless authentication enable edilir.
                .and()
                .authenticationProvider(authenticationProvider) //ApplicationConfig'de ayarlanmı Dao AuthenticationProvider çağrılır.
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class); //kimlik doğrulama için nasıl bir filtre kullanılacağı bilgisi verilir. Tokenı parse edebilmek için jwtAuthenticationfilterı kullan.
        return http.build();
    }
}





