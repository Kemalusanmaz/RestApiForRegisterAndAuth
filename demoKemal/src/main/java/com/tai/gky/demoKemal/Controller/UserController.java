package com.tai.gky.demoKemal.Controller;

import com.tai.gky.demoKemal.Configuration.SecurityConfig;


import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import com.tai.gky.demoKemal.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/v1")
public class UserController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;

//    @GetMapping("/index")
//    public ResponseEntity<String> sayHello(){
//        return ResponseEntity.ok("Hello from API");
//    }


//    }
//    @GetMapping("/login")
//    public String login(
//            @Validated
//            @RequestBody AuthenticateRequestDto authenticateRequestDto
//           ){
//        userService.authenticate(authenticateRequestDto);
//                return "User login succesfully";
//   }


    @PostMapping("/register")
    public String register(
            @Validated
            //Register post iişleminde kullanıcı bilgilerinin RegisterRequestDto'ya kaydedilirken validasyon anotasyonlarının (NotEmpty,Email) çalışması için eklenir.
            @RequestBody RegisterRequestDto registerRequestDto) throws Exception//apiden gelen http isteği requestBody anotasyonu ile RegisterRequestDto tipine çevrilir.

    {
        try {
            userService.registerUser(registerRequestDto); //Servis katmanında kullanıcı kayıt işlemi için oluşturulan registerUser metodu çağrılır.
            return "User registered succesfully";
        } catch (Exception exception) {

            return exception + "User registered unsuccesfully";
        }
    }

        @GetMapping("/")
        public String homePage () {
        return "Welcome to the Home Page";
    }
        @GetMapping("/user")
        public String userPage () {
        return "Welcome to the User Page";
    }

        @GetMapping("/admin")
        public String adminPage () {
        return "Welcome to the Admin Page";
    }

}
