package com.orioles.persistence;

import com.orioles.helper_model.PDemo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PDemoRepository extends CrudRepository<PDemo, Long> {
	PDemo findByPid(int pid);
}
