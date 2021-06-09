package com.codigo.backendcursojava.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
