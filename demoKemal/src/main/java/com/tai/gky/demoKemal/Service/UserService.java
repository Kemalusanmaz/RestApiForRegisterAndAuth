package com.tai.gky.demoKemal.Service;



import com.tai.gky.demoKemal.Dto.AuthenticateRequestDto;
import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import com.tai.gky.demoKemal.Entity.User;
import com.tai.gky.demoKemal.Repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService, IUserService {

    private final IUserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    //Apiden gelen istek servis katmanına gönderilir. Gelen json data zaten controller katmanında RegisterRequestDto tipine çevirlmişti.
    //RegisterRequestDto nesnesinde artık apiden gelen bilgiler mevcuttur.
    @Override
    public void registerUser(RegisterRequestDto requestDto) {

        User newUser = new User();

        //Yeni kullanıcı oluşturulur.
        //Api'den gelen değer Entity katmanındaki eşdeğer değişken ile aynı değere set edilir.
        newUser.setUsername(requestDto.getUsername());
        newUser.setFirstname(requestDto.getFirstname());
        newUser.setLastname(requestDto.getLastname());
        newUser.setEmail(requestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        newUser.setId(requestDto.getId());
        newUser.setRoles(requestDto.getRoles());

        userRepository.save(newUser); //Crud işlemi ile kullanıcı kayır işleminin veri tabanına kaydedilmesi için repository katmanına geçilir.
    }

//####################################################################################################################//

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(User::new)
                .orElseThrow(()->new UsernameNotFoundException("user not found" + username));
    }

//####################################################################################################################//

}
