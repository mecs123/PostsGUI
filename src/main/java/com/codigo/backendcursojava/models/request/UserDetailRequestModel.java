package com.codigo.backendcursojava.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetailRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
