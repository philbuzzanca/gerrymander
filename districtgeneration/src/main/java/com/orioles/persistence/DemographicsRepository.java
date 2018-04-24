package com.orioles.persistence;

import com.orioles.model.Demographics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface DemographicsRepository extends CrudRepository<Demographics, Long>{
    Demographics findByCdIDIgnoreCase(String cdID);
}
