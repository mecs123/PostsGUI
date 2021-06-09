package com.codigo.backendcursojava.security;


import com.codigo.backendcursojava.service.UserServiceInterface;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Data
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private  final UserServiceInterface userServiceInterface;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserServiceInterface userServiceInterface,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userServiceInterface = userServiceInterface;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.GET,"/posts/last").permitAll()
                .antMatchers(HttpMethod.GET,"/posts/{id}").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()))//Se adiciona autorizacion para los endpoints
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//Se especifica la Url filter como Autenticacion
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userServiceInterface).passwordEncoder(bCryptPasswordEncoder);
    }

    //Se Configura la url de inicio de session
    public  AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}
