import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@RestController				// render the string directly back to the caller
@EnableAutoConfiguration	// based on the jar dep, predict Spring config
public class RevGerrymandering {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RevGerrymandering.class, args);
	}

	@RequestMapping("/")	//  “routing” info- any HTTP req with / path map to home method
	String home() {
		return "Hello World!";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	String home2(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}
}
