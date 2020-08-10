package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.AdministratorDao;
import sadminotaur.hospital.mappersdto.AdministratorDTOMapper;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdministratorEndpointTest extends ControllerBase {

    @Autowired
    private AdministratorDao administratorDao;

    private final AdministratorDTOMapper adminMapper = Mappers.getMapper(AdministratorDTOMapper.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void correctPostTest() {
        ResponseEntity<AdministratorResponse> loginResponse = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/admins",
                HttpMethod.POST,
                new HttpEntity<>(
                        administratorDTO,
                        loginResponse.getHeaders()
                ),
                AdministratorResponse.class
        );
        assertEquals(
                adminMapper.adminDTOToAdminResponse(administratorDTO),
                response.getBody()
        );
    }


    @Test
    public void errorPostTest1() {
        assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.exchange(
                    "http://localhost:8081/api/admins",
                    HttpMethod.POST,
                    new HttpEntity<>(administratorDTO),
                    AdministratorResponse.class
            );
        });
    }

    @Test
    public void updateTest() {
        ResponseEntity<AdministratorResponse> loginResponse = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/admins",
                HttpMethod.POST,
                new HttpEntity<>(
                        administratorDTO,
                        loginResponse.getHeaders()
                ),
                AdministratorResponse.class
        );
        ResponseEntity<AdministratorResponse> loginAdmin = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        administratorDTO.getLogin(),
                        administratorDTO.getPassword()
                )),
                AdministratorResponse.class
        );
        ResponseEntity<AdministratorResponse> update = restTemplate.exchange(
                "http://localhost:8081/api/admins",
                HttpMethod.PUT,
                new HttpEntity<>(
                        administratorUpdateDTO,
                        loginAdmin.getHeaders()
                ),
                AdministratorResponse.class
        );
        assertEquals("изменено", update.getBody().getFirstname());
        assertEquals("изменено", update.getBody().getLastname());
        assertEquals("изменено", update.getBody().getPatronymic());
    }

    @Test
    public void notValidPostTest() {
        assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<AdministratorResponse> loginResponse = restTemplate.exchange(
                    "http://localhost:8081/api/sessions",
                    HttpMethod.POST,
                    new HttpEntity<>(new LoginRequest(
                            "admin",
                            "admin"
                    )),
                    AdministratorResponse.class
            );
            administratorDTO.setPosition(null);
            ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/admins",
                    HttpMethod.POST,
                    new HttpEntity<>(
                            administratorDTO,
                            loginResponse.getHeaders()
                    ),
                    AdministratorResponse.class
            );
        });
    }


}
