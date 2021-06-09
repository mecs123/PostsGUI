package com.codigo.backendcursojava.facade;

import com.codigo.backendcursojava.Entity.PostEntity;
import com.codigo.backendcursojava.models.response.PostRest;

import java.util.Date;

public class PostFacade {

    public  Boolean validatePostExpired(PostRest postEntity, Boolean expirate){
        expirate = false;
        if (postEntity.getExpirationAt().compareTo(new Date(System.currentTimeMillis())) < 0){
            expirate = true;
        }

        return expirate;
    }
}
