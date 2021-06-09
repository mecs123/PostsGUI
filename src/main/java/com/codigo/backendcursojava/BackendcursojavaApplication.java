package com.codigo.backendcursojava;

import com.codigo.backendcursojava.models.response.UserRest;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing//Para la fecha automatica
public class BackendcursojavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendcursojavaApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringAplicationContext springAplicationContext(){
		return  new SpringAplicationContext();
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();
		return mapper;
	}
}
