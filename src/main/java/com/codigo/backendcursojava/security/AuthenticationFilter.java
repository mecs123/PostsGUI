package com.codigo.backendcursojava.security;

import com.codigo.backendcursojava.SpringAplicationContext;
import com.codigo.backendcursojava.models.request.UserLoginRequestModel;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import com.codigo.backendcursojava.service.UserServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //AUTENTICACION INICIO DE SESSION
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    //Esto llama cuando intentamos logearnos y despues va a verificar la autenticacion
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(),
                    userModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //Si la autenticacion es valida.
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws  IOException, ServerException{
        String usernameEmail= ((User)authentication.getPrincipal()).getUsername();
        //Se crea el token:
        String token= Jwts.builder().setSubject(usernameEmail).setExpiration(new Date(System.currentTimeMillis()+ SecurityConstans.EXPIRATION_DATE))
                .signWith(SignatureAlgorithm.HS512,SecurityConstans.TOKEN_SECRET).compact();
        //Añadir el header con el Id del Ususario
        UserServiceInterface userService = (UserServiceInterface) SpringAplicationContext.getBean("userService");
        UserDto userDto= userService.getUser(usernameEmail);
        // Se añade un nuevo header
        response.addHeader("userId",userDto.getUserId() );
        //Se añade al header de Authorization
        response.addHeader(SecurityConstans.HEADER_STRING,SecurityConstans.TOKEN_PREFIX+token);
    }




}
