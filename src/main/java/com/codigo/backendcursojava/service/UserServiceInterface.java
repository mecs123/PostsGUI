package com.codigo.backendcursojava.service;

import com.codigo.backendcursojava.models.shared.dto.PostDto;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    List<PostDto> getUserDto(String email);

}
