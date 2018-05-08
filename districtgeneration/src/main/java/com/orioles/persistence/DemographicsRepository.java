package com.orioles.persistence;

import com.orioles.helper_model.Demographics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface DemographicsRepository extends CrudRepository<Demographics, Long>{
    Demographics findByCdIDIgnoreCase(String cdID);
}
