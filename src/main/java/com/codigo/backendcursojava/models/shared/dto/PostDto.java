package com.codigo.backendcursojava.models.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto implements Serializable {
    private static final long serialVersionUID = 1685027670579551254L;

    private Long id;
    private String postId;
    private String content;
    private String title;
    private Date expirationAt;
    private Date creationAt;
    private UserDto users;
    private ExposureDto exposure;
}
