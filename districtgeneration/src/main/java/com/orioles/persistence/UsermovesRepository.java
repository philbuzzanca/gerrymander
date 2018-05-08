package com.orioles.persistence;

import com.orioles.model.Usermoves;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

@Component
public interface UsermovesRepository extends CrudRepository<Usermoves, Long> {
	List<Usermoves> findByUsername(String username);
}