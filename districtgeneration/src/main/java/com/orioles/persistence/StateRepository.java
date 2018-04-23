package com.orioles.persistence;

import com.orioles.model.State;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {
    List<State> findByName(String name);
}
