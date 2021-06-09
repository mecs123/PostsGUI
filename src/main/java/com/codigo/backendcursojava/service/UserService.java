package com.codigo.backendcursojava.service;

import com.codigo.backendcursojava.Entity.PostEntity;
import com.codigo.backendcursojava.Entity.UserEntity;
import com.codigo.backendcursojava.models.shared.dto.PostDto;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import com.codigo.backendcursojava.repository.PostRepository;
import com.codigo.backendcursojava.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service //userService
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) throw new RuntimeException("El Usuario ya existe ");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());
        userEntity.setEncrypPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity storeUserEntity = userRepository.save(userEntity);
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storeUserEntity, userToReturn);
        return userToReturn;
    }

    @Override
    public UserDto getUser(String email) {
        //Trae el user con el email para traer el idUser.
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        UserDto userToreturn = new UserDto();
         BeanUtils.copyProperties(userEntity, userToreturn);
         return userToreturn;
    }

    @Override
    public List<PostDto> getUserDto(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> postEntities = postRepository.getByUsersIdOrderByCreationAtDesc(userEntity.getId());

        //Convertir de list a list
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post:postEntities) {
            PostDto postDto = modelMapper.map(post,PostDto.class);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    @Override //Cuando extiende de la clase UserDetail--> sirve para la parte de session
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity userEntity=  userRepository.findByEmail(email);
       if (userEntity == null) throw new UsernameNotFoundException(email);
        //Aca retorna una entidad cuando se inicia session
        return new User(userEntity.getEmail(),userEntity.getEncrypPassword(),new ArrayList<>());
    }

}
