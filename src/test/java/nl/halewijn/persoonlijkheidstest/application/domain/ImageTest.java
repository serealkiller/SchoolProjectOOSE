package nl.halewijn.persoonlijkheidstest.application.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.application.domain.Image;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class ImageTest {
	
	@Test
	public void gettersAndSettersTest() {
		Image image = new Image();
		image.setImageAlt("imageAlt");
		image.setImageDescription("imageDescription");
		image.setImagePath("imagePath");
		assertEquals("imageAlt", image.getImageAlt());
		assertEquals("imageDescription", image.getImageDescription());
		assertEquals(0, image.getImageId(), 0);
		assertEquals("imagePath", image.getImagePath());
	}
}