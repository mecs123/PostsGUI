package com.codigo.backendcursojava.models.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExposureDto implements Serializable {
    private static final long serialVersionUID = 1652729334807754927L;
    private long id;
    private String type;
    private List<PostDto> post;


}
