package com.codigo.backendcursojava.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

///SIRVE COMO MIDLEWARE SI ES UN TOKEN VALIDO PARA QUE ACCEDA AL ENDPOINT
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
       //Atrapar el Header
        String headers = request.getHeader(SecurityConstans.HEADER_STRING);
        if (headers ==null  || !headers.startsWith(SecurityConstans.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationtoken = getAuthentication(request);
        //Nos muestra el contexto y a setiarle el token extraido de getAuthentication
        SecurityContextHolder.getContext().setAuthentication(authenticationtoken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstans.HEADER_STRING);
        if (token != null){
            token = token.replace(SecurityConstans.TOKEN_PREFIX,"");
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstans.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user != null){
                return  new UsernamePasswordAuthenticationToken(user,null, new ArrayList<>());
            }
            return  null;
        }
        return  null;
        
    }
}
