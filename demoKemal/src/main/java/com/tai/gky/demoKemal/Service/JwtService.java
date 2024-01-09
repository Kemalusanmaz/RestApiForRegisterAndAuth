package com.tai.gky.demoKemal.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {


    //tokenın içindeki verileri parse edebilmek için (db sorgulaması için username'in elde edilmesi gerekiyor )
    // şifresini kırabilmek için veya token generate etmek için kullanılan servis katmanı



    public String findByUsername(String token) {
        return exportToken(token, Claims::getSubject); //gelen token içerisinden exportToken metodu sayesinde subject bölümünden user name'i çekmeyi sağlar
    }

    private<T> T  exportToken(String token, Function<Claims,T> claimsTFunction) {
    //jwtden gelecek tüm talepleri karşılayacak bir metot.//Gelen token her tipte olabileceği için tür bağımsız ayarlandı
    //Gelen her jwt nesne içinde claimsi çözbeilmek için bir fonksiyonda kullanılması gerekir.

        //Jwts token parse etme kütüphanesi
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey()) //decode edilmiş secret -key çağrılır.
                .build().parseClaimsJws(token).getBody(); //token objesi parse edilir ve body kısmı çağrılır.

        return claimsTFunction.apply(claims); //claimsTfunctiona claims nesnesi uygulanarak username çekilir.

    }


    @Value("${security.jwt.secret.key}") //config dosyasındaki değişkeni çağırmak için Value anotasyonu kullanılır.
    public String SECRET_KEY; //secret key application.properties config dosyasında tutulan 64 karakter uzunluğunda benzersiz bir kod olmalıdır.

    @Value("${security.jwt.expiration}")
    private long EXPRIRATON_TIME;

    private Key getKey() { //secret-key oluşturmak için metot
        byte[] key = Decoders.BASE64.decode(SECRET_KEY); //secret key base64 ile decode edilir.
        return Keys.hmacShaKeyFor(key); //oluşturulan byte geri gönderilir.
    }

    //jwt geçerli mi username secret tarafından geçerli mi
    public boolean tokenControl(String token, UserDetails userDetails) {
        final String username = findByUsername(token); //findByUserName metodu argüman olarak aldığı token içindeki subject bölümü yani username bölümünü parse eder.
        return (username.equals(userDetails.getUsername()) && !exportToken(token,Claims::getExpiration).before(new Date()));
            //parse edilen username, database'den çekilen username ile eşit mi
            //token, Claims nesnesinden çekilden expration date metodu ile xamanı dolmuş ise true değerini gönderir
            //bu sayede token username ve verilecek süre zarfında geçöerli olup olmadığı koontrol edilmiş olur.

    }



    public String generateToken(UserDetails userDetails) { //kullanıcı register olurken token üretilmesi için oluşturulur.
        return Jwts.builder()
                .setClaims(new HashMap<>()) //bir Hashmap üretilir.
                .setSubject(userDetails.getUsername()) //tokenın oluşturulduğu Claims nesnesinin subject kısmı username'i ifade eder. db'ye kayıt olan kullanıcın username'i çağrılır ve subject set edilir.
                .setIssuedAt(new Date(System.currentTimeMillis())) // claim nesnesinin IssuedAt kısmı tokenın oluşturulduğu zaman olarak set edilir
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //claim nesnesinin Expriation kısmı ttokenın ne kaadar geçerli olacağı bilgisini verir. Oluşturulduğu günden bir gün sonra olarak ayarlandı.
                .signWith(getKey(), SignatureAlgorithm.HS256) // oluşturulan token secret key ile şifrelenir.HS256 tipinde şifrelenir.
                .compact(); //token generate etme işlemi tamamlanır.
    }
}
