package sadminotaur.hospital.controller;

import sadminotaur.hospital.response.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SettingsEndpointTest {

    @Value("${max_name_length}")
    private int maxLength;

    @Value("${min_password_length}")
    private int minLength;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getTest() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JAVASESSIONID=1");
        ResponseEntity<Settings> response = restTemplate.exchange(
                "http://localhost:8081/api/settings",
                HttpMethod.GET,
                new HttpEntity<>(null, requestHeaders),
                Settings.class
        );
        assertEquals(
                new Settings(maxLength, minLength),
                response.getBody()
        );
    }
}
