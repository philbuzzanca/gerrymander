package com.orioles.persistence;

import com.orioles.model.Precinct;
import com.orioles.model.PrecinctId;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PrecinctRepository extends CrudRepository<Precinct, PrecinctId> {
    List<Precinct> findByIdState(String stateName);
//    List<Precinct> findById(int id);
}
