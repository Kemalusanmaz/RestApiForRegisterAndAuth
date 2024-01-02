package com.tai.gky.demoKemal.Service;


import com.tai.gky.demoKemal.Dto.AuthenticateRequestDto;
import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import com.tai.gky.demoKemal.Entity.User;
import com.tai.gky.demoKemal.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class UserService implements IUserService{

    private final IUserRepository userRepository;

    public final User newUser = new User();

    //Apiden gelen istek servis katmanına gönderilir. Gelen json data zaten controller katmanında RegisterRequestDto tipine çevirlmişti.
    //RegisterRequestDto nesnesinde artık apiden gelen bilgiler mevcuttur.
    @Override
    public void registerUser(RegisterRequestDto requestDto) {

        //Yeni kullanıcı oluşturulur.
        //Api'den gelen değer Entity katmanındaki eşdeğer değişken ile aynı değere set edilir.
        newUser.setUsername(requestDto.getUsername());
        newUser.setFirstname(requestDto.getFirstname());
        newUser.setLastname(requestDto.getLastname());
        newUser.setEmail(requestDto.getEmail());
        newUser.setPassword(requestDto.getPassword());
        newUser.setId(requestDto.getId());

        userRepository.save(newUser); //Crud işlemi ile kullanıcı kayır işleminin veri tabanına kaydedilmesi için repository katmanına geçilir.
    }

    @Override
    public void authenticate(AuthenticateRequestDto authenticateRequestDto) {
        var user = userRepository.findByUsername(authenticateRequestDto.getUsername()).
                orElseThrow(()->new UsernameNotFoundException("User not found with username: "+ authenticateRequestDto.getUsername()) );

    }


}
