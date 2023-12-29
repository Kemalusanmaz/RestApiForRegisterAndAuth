package com.tai.gky.demoKemal.Service;


import com.tai.gky.demoKemal.Dto.RegisterRequestDto;

//Kullanıcı kayıt işlemini gerçekleştirecek servis sınıfı
public interface IUserService {

    void registerUser(RegisterRequestDto requestDto);

}
