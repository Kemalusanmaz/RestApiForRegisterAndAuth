package com.tai.gky.demoKemal.Repository;

import com.tai.gky.demoKemal.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends IBaseRepository<User, String> {

    Optional<User> findByUsername(String username); //kullanıcı adına göre sorgulama yapılır. Eğer user name sistemin içinde varsa db'den çeker.

}
