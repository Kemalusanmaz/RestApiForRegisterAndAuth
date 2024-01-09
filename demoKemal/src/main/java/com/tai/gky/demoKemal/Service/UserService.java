package com.tai.gky.demoKemal.Service;



import com.tai.gky.demoKemal.Dto.AuthenticateRequestDto;
import com.tai.gky.demoKemal.Dto.AuthenticateResponseDto;
import com.tai.gky.demoKemal.Dto.RegisterRequestDto;
import com.tai.gky.demoKemal.Dto.RegisterResponseDto;
import com.tai.gky.demoKemal.Entity.User;
import com.tai.gky.demoKemal.Enums.Role;
import com.tai.gky.demoKemal.Repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserService {

    private final IUserRepository userRepository;

  @Autowired
  private final PasswordEncoder passwordEncoder;

    //Apiden gelen istek servis katmanına gönderilir. Gelen json data zaten controller katmanında RegisterRequestDto tipine çevirlmişti.
    //RegisterRequestDto nesnesinde artık apiden gelen bilgiler mevcuttur.
//    @Override
//    public void registerUser(RegisterRequestDto requestDto) {
//
//        User newUser = new User();
//
//        //Yeni kullanıcı oluşturulur.
//        //Api'den gelen değer Entity katmanındaki eşdeğer değişken ile aynı değere set edilir.
//        newUser.setUsername(requestDto.getUsername());
//        newUser.setFirstname(requestDto.getFirstname());
//        newUser.setLastname(requestDto.getLastname());
//        newUser.setEmail(requestDto.getEmail());
//        newUser.setPassword(passwordEncoder.encode(requestDto.getPassword())); //apiden gelen password değeri BCrypt edilerek set edilir.
//        newUser.setId(requestDto.getId());
//        //newUser.setRoles(requestDto.getRoles());
//
//        userRepository.save(newUser); //Crud işlemi ile kullanıcı kayıt işleminin veri tabanına kaydedilmesi için repository katmanına geçilir.
//    }

//####################################################################################################################//

//    @Override
//    //DB'de kayıtlı bulunan verilere ulaşmak için UserDetailService implmente edilir.
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //loadUserByUsername metodu, dışarıdan gelen kullanıcı adına (username) göre bir UserDetails nesnesi döndürür.
//        // Eğer kullanıcı adına sahip bir kullanıcı bulunamazsa UsernameNotFoundException fırlatılır.
//        Optional<User> user = userRepository.findByUsername(username); //veritabanında username göre bir kullanıcı arar.
//
//        //eğer user içinde bir Optional türünde bir kullanıcı varsa, bu kullanıcıyı User sınıfının bir örneği olan UserDetails nesnesine dönüştürür.
//        // Eğer kullanıcı bulunamazsa, orElseThrow kısmı devreye girer ve bir UsernameNotFoundException fırlatılır.
//                return user.map(User::new)
//                .orElseThrow(()->new UsernameNotFoundException("user not found" + username));
//    }

//####################################JWT Register Method ####################################################//


    private final JwtService jwtService;

    public RegisterResponseDto registerUser(RegisterRequestDto registerRequestDto) { //post ile gelen kullanıcı kayıt bilgileri ile kayıt işlemi yapılır.
//        User user = User.builder().username(registerRequestDto.getUsername())
//                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
//                .role(Role.USER)
//                .firstname(registerRequestDto.getFirstname())
//                .lastname(registerRequestDto.getLastname())
//                .email(registerRequestDto.getEmail())
//                .build();

//        userRepository.save(user); //kullanıcı veri tabanına kaydedilir.
//
//        var token = jwtService.generateToken(user); // kullnıcı kaydedildikten sonra generateToken kullanıcı bilgileriyle oluşturulur.

        User admin = User.builder()
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(Role.ADMIN)
                .firstname(registerRequestDto.getFirstname())
                .lastname(registerRequestDto.getLastname())
                .email(registerRequestDto.getEmail())
                .build();

        userRepository.save(admin);

        User admin2 = User.builder()
                .username(registerRequestDto.getUsername())
                .password(registerRequestDto.getPassword())
                .role(Role.USER)
                .build();

        var token = jwtService.generateToken(admin2);


        return RegisterResponseDto.builder().token(token).build(); //register işlemi yapıldıktan sonra token üretilir.
    }

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponseDto authenticateUser(AuthenticateRequestDto authenticateRequestDto) {
        //post işlemi ile gelen username ve password authenticationrequestDto içinde saklanır.
        //authenticate metodu içinde post işlemi ile gelen username ve password argüman olarak verilir.
        //AuthenticateManager nesnesinin authenticate metodu kullanıcı adı ve şifresi girerek kontrol işlemi yapar.
        // userdetailse bakar ve password doğruluğunu kontrol eder,
        // userdetail içinde koyulan username ile db içinde var olan username eşleşme kontrolü işlemler yapılır.
        //bu koşullarda herhangi bir yanlışlıkta spring security devreye girer ve hata fırlatır.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequestDto.getUsername(),authenticateRequestDto.getPassword()));

        User user = userRepository.findByUsername(authenticateRequestDto.getUsername()).orElseThrow(); //login işleminden sonra username verisetinden çekilir ve user değişkeninde saklanır.
        String token = jwtService.generateToken(user); //bu kullanıcı adına göre jwtService nesnesi içinde bulunan generateToken metodu ile token üretilir.

        return AuthenticateResponseDto.builder().token(token).build(); // üretilen token ile birlikte response build edilir ve bu metod ile döndürülür.
    }
}
