package com.tai.gky.demoKemal.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data //Getter - Setter
@AllArgsConstructor //Başka bir nesne üzerinden değişkenler ile çağırabilmek için
@NoArgsConstructor //Başka bir nesne üzerinden değişkensiz çağırabilmek için
@Document("_user") //MongoDb collection adı
public class User implements  IUser { //Security oluşturmak için UserDetail implement edilir

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

}
