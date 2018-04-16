package orioles.districtgeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
  @Autowired
  private UserRepository userRepository;
  
  @PostMapping("/register")
  public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password){
    System.out.println("received username " + username);
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    user.setPassword(encoder.encode(password));
    user.setParty(Party.OTHER);
    userRepository.save(user);
    return "OK";
  }
}
