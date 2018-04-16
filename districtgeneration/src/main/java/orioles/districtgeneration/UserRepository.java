package orioles.districtgeneration;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{
  List<User> findByEmail(String email);
}
