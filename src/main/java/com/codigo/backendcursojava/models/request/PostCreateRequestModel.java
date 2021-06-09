package com.codigo.backendcursojava.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostCreateRequestModel {

    private String title;
    private String content;
    private long exposureId;
    private int expirationTime;

}
