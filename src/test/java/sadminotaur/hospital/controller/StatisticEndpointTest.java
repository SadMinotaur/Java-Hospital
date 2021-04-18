package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.response.PatientResponse;
import sadminotaur.hospital.response.TicketResponse;
import sadminotaur.hospital.response.statistic.DoctorTimeslotsCount;
import sadminotaur.hospital.response.statistic.TotalUserCount;
import sadminotaur.hospital.response.statistic.PatientCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StatisticEndpointTest extends ControllerBase {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getTotalCountTest() {
        ResponseEntity<AdministratorResponse> loginResponse = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<TotalUserCount> response = restTemplate.exchange(
                "http://localhost:8081/api/statistic/users?doctor=true",
                HttpMethod.GET,
                new HttpEntity<>(null, loginResponse.getHeaders()),
                TotalUserCount.class
        );
        assertEquals(0, response.getBody().getDoctorsCount());
        assertEquals(1, response.getBody().getTotalCount());
    }

    @Test
    public void getTimeslotCountTest1() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> doctor = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO3, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<PatientResponse> registerPatient = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        ticketRequest.setDoctorId(doctor.getBody().getId());
        ResponseEntity<TicketResponse> ticketResp = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        ResponseEntity<DoctorTimeslotsCount> response = restTemplate.exchange(
                "http://localhost:8081/api/statistic/doctors?id=" + ticketResp.getBody().getDoctorId(),
                HttpMethod.GET,
                new HttpEntity<>(null, registerPatient.getHeaders()),
                DoctorTimeslotsCount.class
        );
        assertEquals(1, response.getBody().getDoctorsBusyCount());
        assertEquals(1, response.getBody().getSelectedDocBusyTCount());
    }

    @Test
    public void getTimeslotCountTest2() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> doctor = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO3, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<PatientResponse> registerPatient = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        ticketRequest.setDoctorId(doctor.getBody().getId());
        ResponseEntity<TicketResponse> ticketResp = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        ResponseEntity<DoctorTimeslotsCount> response = restTemplate.exchange(
                "http://localhost:8081/api/statistic/doctors?" + ticketResp.getBody().getDoctorId(),
                HttpMethod.GET,
                new HttpEntity<>(null, registerPatient.getHeaders()),
                DoctorTimeslotsCount.class
        );
        assertEquals(1, response.getBody().getDoctorsBusyCount());
        assertEquals(0, response.getBody().getSelectedDocBusyTCount());
    }

    @Test
    public void getTimeslotCountTest() {
        ResponseEntity<AdministratorResponse> login = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "admin",
                        "admin"
                )),
                AdministratorResponse.class
        );
        ResponseEntity<DoctorResponse> doctor = restTemplate.exchange(
                "http://localhost:8081/api/doctors",
                HttpMethod.POST,
                new HttpEntity<>(doctorRegisterDTO3, login.getHeaders()),
                DoctorResponse.class
        );
        ResponseEntity<PatientResponse> registerPatient = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        ticketRequest.setDoctorId(doctor.getBody().getId());
        ResponseEntity<TicketResponse> ticketResp = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        ResponseEntity<PatientCount> response = restTemplate.exchange(
                "http://localhost:8081/api/statistic/patients/" + registerPatient.getBody().getId(),
                HttpMethod.GET,
                new HttpEntity<>(null, registerPatient.getHeaders()),
                PatientCount.class
        );
        assertEquals(1, response.getBody().getPatientCount());
    }
}
