package com.codigo.backendcursojava.repository;

import com.codigo.backendcursojava.Entity.ExposureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {

    ExposureEntity findById(long id);
}
