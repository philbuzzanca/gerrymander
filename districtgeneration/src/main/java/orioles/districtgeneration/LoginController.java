package orioles.districtgeneration;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public User login(HttpSession session){
        User user = new User("sampleuser");
        session.setAttribute("user", user);
        return user;
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "OK";
    }
}
