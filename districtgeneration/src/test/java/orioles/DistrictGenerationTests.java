package orioles;

import com.orioles.DistrictGeneration;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DistrictGeneration.class)
public class DistrictGenerationTests {
	@Test
	public void contextLoads() {}
}
