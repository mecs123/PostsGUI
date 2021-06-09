package com.codigo.backendcursojava.service;

import com.codigo.backendcursojava.Entity.ExposureEntity;
import com.codigo.backendcursojava.Entity.PostEntity;
import com.codigo.backendcursojava.Entity.UserEntity;
import com.codigo.backendcursojava.models.shared.dto.PostCreationDto;
import com.codigo.backendcursojava.models.shared.dto.PostDto;
import com.codigo.backendcursojava.repository.ExposureRepository;
import com.codigo.backendcursojava.repository.PostRepository;
import com.codigo.backendcursojava.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService implements PostServiceInterface{
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExposureRepository exposureRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostCreationDto post) {
        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        ExposureEntity exposureEntity = exposureRepository.findById(post.getExposureId());
        PostEntity postEntity = new PostEntity();
        postEntity.setUsers(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpirationAt(new Date(System.currentTimeMillis()+(post.getExpirationTime()*60000)));
        PostEntity createPost= postRepository.save(postEntity);
        PostDto postToReturn = modelMapper.map(createPost,PostDto.class);
        return postToReturn;
    }

    @Override
    public List<PostDto> getLastPost() {
        long exposurePublicId =2;
        List<PostEntity> postEntities = postRepository.getlastPublicPost(exposurePublicId,new Date(System.currentTimeMillis()));
        List<PostDto> postDtosList = new ArrayList<>();
        for (PostEntity post:postEntities) {
            PostDto postDto = modelMapper.map(post,PostDto.class);
            postDtosList.add(postDto);

        }
        return postDtosList;
    }

    @Override
    public PostDto getPost(String postId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        PostDto postDto = modelMapper.map(postEntity,PostDto.class);
        return  postDto;
    }

    @Override
    public void deletePost(String postId, long user) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUsers().getId() !=user)throw new RuntimeException("No se puede relizar esta accion");
        postRepository.delete(postEntity);
    }

    @Override
    public PostDto updatePost(String postId, long user, PostCreationDto postCreationDto) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if (postEntity.getUsers().getId() !=user)throw new RuntimeException("No se puede relizar esta accion");

        ExposureEntity exposureEntity = exposureRepository.findById(postCreationDto.getExposureId());
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postCreationDto.getTitle());
        postEntity.setContent(postCreationDto.getContent());
        postEntity.setExpirationAt(new Date(System.currentTimeMillis()+(postCreationDto.getExpirationTime()*60000)));
        PostEntity updatePost= postRepository.save(postEntity);
        PostDto postToReturn = modelMapper.map(updatePost,PostDto.class);
        return  postToReturn;
    }
}
