package sadminotaur.hospital.controller;

import sadminotaur.hospital.daoimpl.AdministratorDaoImpl;
import sadminotaur.hospital.daoimpl.SessionDaoImpl;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GetInfo {

    @Autowired
    private SessionDaoImpl sessionDao;

    @Autowired
    private AdministratorDaoImpl administratorDao;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getInfoTest() {
        ResponseEntity<AdministratorResponse> adminResponse = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<AdministratorResponse> adminInfo = restTemplate.exchange(
                "http://localhost:8081/api/account",
                HttpMethod.GET,
                new HttpEntity<>(null, adminResponse.getHeaders()),
                AdministratorResponse.class
        );
        assertEquals(adminResponse.getBody(), adminInfo.getBody());
    }

}
