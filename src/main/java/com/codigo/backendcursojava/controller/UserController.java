package com.codigo.backendcursojava.controller;

import com.codigo.backendcursojava.models.request.UserDetailRequestModel;
import com.codigo.backendcursojava.models.response.PostRest;
import com.codigo.backendcursojava.models.response.UserRest;
import com.codigo.backendcursojava.models.shared.dto.PostDto;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import com.codigo.backendcursojava.service.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users") // htpp://localhost:8080/users
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper modelMapper;
    @GetMapping
    public UserRest getUser() {
        //Obtener el usuario que esta Autenticado(( Scurity trae el contexto de la Aplicacion
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto userDto =  userService.getUser(email);
        //BeanUtils.copyProperties(userDto,userToReturn);
        ModelMapper mapper = new ModelMapper();
        UserRest userToReturn = mapper.map(userDto,UserRest.class);
        return userToReturn;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailRequestModel model) {
        UserRest userReturn = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(model, userDto);
        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, userReturn);
        return userReturn;
    }

    //Listar los Post del Usuario en session por el emaiil.
    @GetMapping(path = "/posts")//localhost:8080/users/posts
    public List<PostRest> getPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        List<PostDto> postDtos = userService.getUserDto(email);
        List<PostRest> postRestList = new ArrayList<>();
        //Convertir lista de POSTDTO a PostRest
        for (PostDto posts:postDtos) {
            PostRest postRest = modelMapper.map(posts,PostRest.class);
            if (postRest.getExpirationAt().compareTo(new Date(System.currentTimeMillis())) < 0){
                postRest.setExpired(true);
            }
            postRestList.add(postRest);
        }
        return postRestList;
    }
}
