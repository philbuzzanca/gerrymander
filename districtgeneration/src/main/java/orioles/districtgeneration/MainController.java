package orioles.districtgeneration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class MainController {
    final String helpText = "<p>To begin using this tool, select a state either by clicking on the state on the map or by selecting it from the dropdown menu.</p>\n<p>Once a state has been selected, specify any constraints that the district generation should adhere to by toggling the checkboxes on the sidebar. After selecting the constraints, you can generate congressional districts by clicking on the <strong>Build</strong> button.</p>\n";
    final String aboutText = "<p>\"A Gerrymander is a voting district that is designed to serve some political purpose. The name refers to both a salamander and Eldridge Gerry, whose newly created voting district about 200 years ago was said to resemble a salamander. Within the past 10 years, databases for voter characterization as well as tools for precise map drawing have made it possible to create congressional districts that favor the party responsible for the creation of the districts. Redistricting is done in states where census data requires a change in the number of delegates in the state, and the 2010 census triggered redistricting in a number of states. Many of these redistricting efforts resulted in a shift in the political representation in the states. As the realization of the impact of these changes has grown, various technical approaches to the issue have been proposed, some as quantitative measures of the presence of Gerrymandering, others as legal challenges to redistricting, and yet others as draft bills in Congress to minimize the effect of future redistricting. The system to be developed in this project will allow for the generation of congressional district boundaries without any political influence.\"</p>";
   
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("helpText", helpText);
        model.addAttribute("aboutText", aboutText);
        return "index";
    }
    
}
