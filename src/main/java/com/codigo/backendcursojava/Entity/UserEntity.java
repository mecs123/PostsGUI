package com.codigo.backendcursojava.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
//Agregar Indices
@Table(indexes = {@Index(columnList = "userId",name = "index_userid",unique = true),@Index(columnList = "email",name = "index_email")})
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String encrypPassword;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "users")//Cuando se elimiene un User se eliman los post too, user se crea  como objeto
    private List<PostEntity> post = new ArrayList<>();

}
