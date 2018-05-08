package com.orioles.persistence;

import org.springframework.stereotype.Component;
import com.orioles.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Component
public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findByUsername(String username);
}