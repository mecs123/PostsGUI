package com.codigo.backendcursojava.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRest {
    private Long id;
    private String postId;
    private String content;
    private String title;
    private Date expirationAt;
    private Date creationAt;
    private boolean expired= false;
    private UserRest users;
    private ExposureRest exposure;

}
