package com.codigo.backendcursojava.controller;

import com.codigo.backendcursojava.facade.PostFacade;
import com.codigo.backendcursojava.models.request.PostCreateRequestModel;
import com.codigo.backendcursojava.models.response.OperationStatusModel;
import com.codigo.backendcursojava.models.response.PostRest;
import com.codigo.backendcursojava.models.shared.dto.PostCreationDto;
import com.codigo.backendcursojava.models.shared.dto.PostDto;
import com.codigo.backendcursojava.models.shared.dto.UserDto;
import com.codigo.backendcursojava.service.PostServiceInterface;
import com.codigo.backendcursojava.service.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostServiceInterface postService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserServiceInterface userServiceInterface;

    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel){

        //Capturar el Usuario que esta Utenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        ModelMapper mapper = new ModelMapper();
        PostCreationDto dto = mapper.map(createRequestModel,PostCreationDto.class);
        dto.setUserEmail(email);
        PostDto postDto = postService.createPost(dto);
        PostRest postToReturn = mapper.map(postDto,PostRest.class);
        PostFacade postFacade = new PostFacade();
        postFacade.validatePostExpired(postToReturn,false);
        postToReturn.setExpired(true);
        return  postToReturn;
    }

    @GetMapping(path = "/last")//localhost:8080/posts/last
    public List<PostRest> lastPost(){
        List<PostDto> postDtos  = postService.getLastPost();

        List<PostRest> postRestList = new ArrayList<>();
        //Convertir lista de POST a PostRest
        for (PostDto posts:postDtos) {
            PostRest postRest = modelMapper.map(posts,PostRest.class);
            postRestList.add(postRest);
        }
        return postRestList;
    }

    @GetMapping(path = "/{id}") //http:localhost:/posts/uuid
    public PostRest getPost(@PathVariable String id){
        PostDto postDto = postService.getPost(id);
        PostRest postRestExpired = modelMapper.map(postDto,PostRest.class);

        if (postRestExpired.getExpirationAt().compareTo(new Date(System.currentTimeMillis())) >0 ){
            postRestExpired.setExpired(true);

        }
        //Valida si el post es privado o ya expiro
        if(postRestExpired.getExposure().getId() ==1 || postRestExpired.isExpired() ){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getPrincipal().toString();
            UserDto user = userServiceInterface.getUser(email);
            if (user.getId() != postDto.getUsers().getId()){
                throw  new RuntimeException("No tienes permisos para realizar esta Opcion");

            }

        }
        return  postRestExpired;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deletePost(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        UserDto user = userServiceInterface.getUser(email);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE:"+user.getEmail());
        postService.deletePost(id,user.getId());
        operationStatusModel.setResult("SUCCES");
        return operationStatusModel;
    }
    @PutMapping(path = "/{id}")
    public PostRest updatePost(@PathVariable String id, @RequestBody PostCreateRequestModel postCreateRequestModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());
        PostCreationDto postUpdateDto = modelMapper.map(postCreateRequestModel,PostCreationDto.class);
        PostDto postDto=  postService.updatePost(id,user.getId(),postUpdateDto);
        PostRest updatePost= modelMapper.map(postDto,PostRest.class);
        return updatePost;
    }




}
