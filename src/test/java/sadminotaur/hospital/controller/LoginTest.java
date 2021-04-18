package sadminotaur.hospital.controller;

import sadminotaur.hospital.daoimpl.AdministratorDaoImpl;
import sadminotaur.hospital.daoimpl.SessionDaoImpl;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.mappersdto.AdministratorDTOMapper;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginTest extends ControllerBase {

    @Autowired
    private SessionDaoImpl sessionDao;
    @Autowired
    private AdministratorDaoImpl administratorDao;

    private final AdministratorDTOMapper adminMapper = Mappers.getMapper(AdministratorDTOMapper.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void correctPostTest() throws ServiceException {
        administratorDao.add(administrator);
        ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        administrator.getLogin(),
                        administrator.getPassword()
                )),
                AdministratorResponse.class
        );
        assertEquals(adminMapper.adminToAdminResponse(administrator), response.getBody());
    }

    @Test
    public void errorPostTest1() {
        assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<Object> response = restTemplate.exchange(
                    "http://localhost:8081/api/sessions",
                    HttpMethod.POST,
                    new HttpEntity<>(new LoginRequest(
                            "test",
                            "test"
                    )),
                    Object.class
            );
        });
    }

    @Test
    public void notValidPostTest() {
        assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<Object> response = restTemplate.exchange(
                    "http://localhost:8081/api/sessions",
                    HttpMethod.POST,
                    new HttpEntity<>(new LoginRequest(
                            null,
                            null
                    )),
                    Object.class
            );
        });
    }

    @Test
    public void logoutTest() throws ServiceException {
        administratorDao.add(administrator);
        ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "test",
                        "test"
                )),
                AdministratorResponse.class
        );
        restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.DELETE,
                new HttpEntity<>(null, response.getHeaders()),
                Object.class
        );
        assertNull(sessionDao.getByCookie(response.getHeaders().getFirst("Cookie")));
    }
}
