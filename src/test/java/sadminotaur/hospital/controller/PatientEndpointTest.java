package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.requests.PatientRegisterDTO;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.response.PatientResponse;
import sadminotaur.hospital.mappersdto.PatientDTOMapper;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PatientEndpointTest extends ControllerBase {

    @Autowired
    private PatientDao patientDao;

    private final PatientDTOMapper patientMapper = Mappers.getMapper(PatientDTOMapper.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void correctPostTest() {
        ResponseEntity<PatientResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        assertEquals(
                patientMapper.patientDTOToPatientResponse(patient),
                response.getBody()
        );
        assertNotNull(response.getHeaders().getFirst("Cookie"));
    }

    @Test
    public void errorPostTest1() {
        assertThrows(HttpClientErrorException.class, () -> {
            patientDao.add(patientMapper.patientDTOtoPatient(patient));
            ResponseEntity<PatientResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/patients",
                    HttpMethod.POST,
                    new HttpEntity<>(patient),
                    PatientResponse.class
            );
        });
    }

    @Test
    public void notValidPostTest() {
        patient.setPhone("test");
        assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<PatientResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/patients",
                    HttpMethod.POST,
                    new HttpEntity<>(patient),
                    PatientResponse.class
            );
        });
    }

    @Test
    public void notValidPostTest2() {
        patient.setEmail(null);
        assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<PatientResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/patients",
                    HttpMethod.POST,
                    new HttpEntity<>(patient),
                    PatientResponse.class
            );
        });
    }

    @Test
    public void getTest1() throws ServiceException {
        Patient patientInserted = patientDao.add(patientMapper.patientDTOtoPatient(patient));
        ResponseEntity<AdministratorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<PatientResponse> patientResponse = restTemplate.exchange(
                "http://localhost:8081/api/patients/" + patientInserted.getId(),
                HttpMethod.GET,
                new HttpEntity<>(null, response.getHeaders()),
                PatientResponse.class
        );
        assertEquals(
                patientMapper.patientDTOToPatientResponse(patient),
                patientResponse.getBody()
        );
        assertNotNull(patientResponse.getHeaders().getFirst("Cookie"));
    }

    @Test
    public void patientUpdate() {
        ResponseEntity<PatientResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<PatientRegisterDTO>(patient),
                PatientResponse.class
        );
        patientUpdateDTO.setAddress("1234");
        ResponseEntity<PatientResponse> patientResponse = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.PUT,
                new HttpEntity<>(patientUpdateDTO, response.getHeaders()),
                PatientResponse.class
        );
        Assert.assertEquals(patientUpdateDTO.getAddress(), patientResponse.getBody().getAddress());
    }
}
