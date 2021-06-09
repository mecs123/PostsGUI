package com.codigo.backendcursojava.repository;

import com.codigo.backendcursojava.Entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    // UserEntity findUserByFirstName(String nombre);


}
