package com.tai.gky.demoKemal.Entity;

import com.tai.gky.demoKemal.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Builder
@Data //Getter - Setter
@AllArgsConstructor //Başka bir nesne üzerinden değişkenler ile çağırabilmek için
@NoArgsConstructor //Başka bir nesne üzerinden değişkensiz çağırabilmek için
@Document("_user") //MongoDb collection adı
public class User implements  IUser, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    @Enumerated(EnumType.STRING) //Enum nesnesinin string değerini alabilmek için anotasyon kullanılır.
    Role role; //user ve admin enum classında tutulmuştur.

//    private List<GrantedAuthority> authorities;
//
//    public User(User user) {
//        this.password = user.getPassword();
//        this.username = user.getUsername();
//
//        authorities = Arrays.stream(role.name().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//   }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //user nesnesinin kullanıcılarını döndürür. Kullanıcılar user admin rolünde olabilir.
        return List.of(new SimpleGrantedAuthority(role.name())); //Liste içinde bu roller döndürülür.
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() { //user nesnesinin kullanıcılarını döndürür. Kullanıcılar user admin rolünde olabilir.
//        return authorities;
//    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
