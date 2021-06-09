package com.codigo.backendcursojava.repository;

import com.codigo.backendcursojava.Entity.PostEntity;
import com.codigo.backendcursojava.models.shared.dto.PostDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

    List<PostEntity> getByUsersIdOrderByCreationAtDesc(long userId);

    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id=:exposure AND expiration_at > :now  ORDER BY creation_at DESC LIMIT 20",nativeQuery = true)
    List<PostEntity> getlastPublicPost(@Param("exposure") long exposure,@Param("now") Date now);

    PostEntity findByPostId(String postId);

}
