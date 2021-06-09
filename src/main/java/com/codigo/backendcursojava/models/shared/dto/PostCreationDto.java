package com.codigo.backendcursojava.models.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCreationDto implements Serializable {
    private static final long serialVersionUID = -4850790916426940338L;

    private String title;
    private String content;
    private long exposureId;
    private  int expirationTime;
    private String userEmail;
}
