package com.codigo.backendcursojava.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginRequestModel {

    private String email;
    private String password;

}
