package com.tai.gky.demoKemal.Configuration;

import com.tai.gky.demoKemal.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { //bu nesne sayesinde http request ve response isteklerine cevap verilir.

    private final JwtService jwtService; //token'ın parselandığı servis nesnesi.

    private final UserDetailsService userDetailsService; //username spring security yaşam döngüsü içindeki User Details'a verilmesi gerekir.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");//buraya bir http isteği gelir header
        final String jwtToken; //gelen istekle birlikte bir token gelecek.
        final String username; //bu token parse edilerek username çekilecek. Gelen username db'de mevcuttsa geçişe izin verilir.

        //bu koşul gelen istek jwt security ile mi geliyor sorgulamasıdır.
        if(header == null || !header.startsWith("Bearer ")){ //header boş ise header Bearer ile başlarsa (jwt Authorization ya da Bearer ifadesi ile gelir.)
            filterChain.doFilter(request,response); //filterchaine request ve responseları vererek işlemkesilir. Yani istemci tarafından gelen değer geri döndürülür.
            return;
        }

        jwtToken = header.substring(7); //gelen header eğer 7. satırdan başlarsa token'a ulaşılabilir. 6. satırdan sonraki kısım şifrelenmiş  token kısmı ilk 6'sı (Bearer )
        username = jwtService.findByUsername(jwtToken); //bu metot ile token içinden username çelilir.

        //username boş değilse ve mevcut bir oturum yoksa
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //tokendan parse edilen username userDetails servisine verilir.
            if (jwtService.tokenControl(jwtToken,userDetails)){
                //bu nesne user details parametresi alarak verilen kullanıcı bilgilerini oluşturur. get Authorized ile kullanıcının yetklileri bu nesneye verilir.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                //authentication nesnesine güncel bilgileri ekler. User details içindeki var olan bilgiler güncellendiyse,
                // spring scecurity içindeki en güncel halini authenticationToken içine verilir.
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }




}
