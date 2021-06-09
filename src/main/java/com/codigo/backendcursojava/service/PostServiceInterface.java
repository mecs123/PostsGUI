package com.codigo.backendcursojava.service;

import com.codigo.backendcursojava.Entity.PostEntity;
import com.codigo.backendcursojava.models.shared.dto.PostCreationDto;
import com.codigo.backendcursojava.models.shared.dto.PostDto;

import java.util.List;

public interface PostServiceInterface {
    PostDto createPost(PostCreationDto post);
    List<PostDto> getLastPost();
    PostDto getPost(String id);
    void deletePost(String postId, long user);
    PostDto updatePost(String postId, long user,PostCreationDto postCreationDto);




}
