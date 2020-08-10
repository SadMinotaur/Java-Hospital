package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DebugTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserDao userDao;

    @Test
    public void testClear() throws Exception {
        restTemplate.postForLocation("http://localhost:8081/api/debug/clear", null);
        assertNull(userDao.getById(2));
        assertNotNull(userDao.getById(1));
    }
}
