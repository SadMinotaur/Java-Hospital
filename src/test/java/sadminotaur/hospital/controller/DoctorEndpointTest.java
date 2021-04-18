package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.dao.ScheduleDao;
import sadminotaur.hospital.dao.TimeSlotDao;
import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.requests.DeleteDoctorDTO;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DoctorEndpointTest extends ControllerBase {

    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private TimeSlotDao timeSlotDao;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void correctPost1DoctorTest() throws ServiceException {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        assertNotEquals(0, scheduleDao.getByDoctorId(response.getBody().getId()).size());
    }

    @Test
    public void correctPost2DoctorTest() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO2, login.getHeaders()),
                DoctorResponse.class
        );
        assertEquals(2, response.getBody().getRSchedule().length);
    }

    @Test
    public void correctPost3DoctorTest() throws ServiceException {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO3, login.getHeaders()),
                DoctorResponse.class
        );
        Doctor doctor = doctorDao.getById(response.getBody().getId());
        assertEquals(21, doctor.getSchedule().get(1).getTimeSlots().size());
    }

    @Test
    public void errorPost1DoctorTest() {
        assertThrows(HttpClientErrorException.class, () -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "JAVASESSIONID=" + UUID.randomUUID().toString());
            ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/doctors",
                    HttpMethod.POST,
                    new HttpEntity<>(doctorRegisterDTO1, headers),
                    DoctorResponse.class
            );
        });
    }

    @Test
    public void errorPost2DoctorTest() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        assertThrows(HttpClientErrorException.class, () -> {
            doctorRegisterDTO3.setWeekDaysSchedule(null);
            ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                    "http://localhost:8081/api/doctors",
                    HttpMethod.POST,
                    new HttpEntity<>(doctorRegisterDTO3, login.getHeaders()),
                    DoctorResponse.class
            );
        });
    }

    @Test
    public void getDoctor1Test() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<DoctorResponse> getResponse = restTemplate.exchange(
                "http://localhost:8081/api/doctors/" + response.getBody().getId() + "/?schedule=yes",
                HttpMethod.GET,
                new HttpEntity<>(login.getHeaders()),
                DoctorResponse.class
        );
        assertNotNull(getResponse.getBody().getRSchedule());
    }

    @Test
    public void getDoctor2Test() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<DoctorResponse> getResponse = restTemplate.exchange(
                "http://localhost:8081/api/doctors/" + response.getBody().getId() + "/?schedule=no",
                HttpMethod.GET,
                new HttpEntity<>(login.getHeaders()),
                DoctorResponse.class
        );
        assertNull(getResponse.getBody().getRSchedule());
    }

    @Test
    public void getDoctor3Test() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<DoctorResponse> getResponse = restTemplate.exchange(
                "http://localhost:8081/api/doctors/" + response.getBody().getId() + "/?schedule=yes&startDate=" +
                        LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(datePattern)),
                HttpMethod.GET,
                new HttpEntity<>(login.getHeaders()),
                DoctorResponse.class
        );
        assertNotNull(getResponse.getBody().getRSchedule());
    }

    @Test
    public void deleteDoctor() throws ServiceException {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        restTemplate.exchange(
                "http://localhost:8081/api/doctors/" + response.getBody().getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new DeleteDoctorDTO(LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern))), login.getHeaders()),
                Void.class
        );
        assertNull(doctorDao.getById(response.getBody().getId()));
    }

    @Test
    public void updateDoctorSchedule() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> response = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO1, login.getHeaders()),
                DoctorResponse.class
        );
        assertNotNull(restTemplate.exchange(
                "http://localhost:8081/api/doctors/" + response.getBody().getId(),
                HttpMethod.PUT,
                new HttpEntity<>(scheduleUpdateDTO, login.getHeaders()),
                DoctorResponse.class
        ).getBody().getRSchedule());
    }
}
