package com.tai.gky.demoKemal.Service;



import com.tai.gky.demoKemal.Dto.AuthenticateRequestDto;
import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

//Kullanıcı kayıt işlemini gerçekleştirecek servis sınıfı
public interface IUserService {

    void registerUser(RegisterRequestDto requestDto);

//    void authenticate(AuthenticateRequestDto authenticateRequestDto);
}
