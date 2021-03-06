package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.application.domain.WebsiteContentText;
import nl.halewijn.persoonlijkheidstest.application.services.local.LocalWebsiteContentTextService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalWebsiteContentTextServiceTest {

    @Autowired
	private LocalWebsiteContentTextService localWebsiteContentTextServiceService;
	
	@Test
	public void getByContentIdTest() {
        String contentText = "This is a piece of content.";
        WebsiteContentText content = new WebsiteContentText();
        content.setContentText(contentText);
        content = localWebsiteContentTextServiceService.save(content);
        content = localWebsiteContentTextServiceService.getByContentId(content.getContentId());
        assertEquals(contentText, content.getContentText());
	}  
}