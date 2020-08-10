package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.dao.TimeSlotDao;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.*;

import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TicketEndpointTest extends ControllerBase {

    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private TimeSlotDao timeSlotDao;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void ticketRegister1() throws ServiceException {
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
        Assert.assertEquals(1, doctorDao.getById(doctor.getBody().getId()).getSchedule().stream().filter(schedule ->
                schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                        timeSlot.getPatient() != null)
        ).count());
    }

    @Test
    public void ticketRegister2() throws ServiceException {
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
        ticketRequest.setSpeciality("test");
        ResponseEntity<TicketResponse> ticketResp = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        Assert.assertEquals(1, doctorDao.getById(doctor.getBody().getId()).getSchedule().stream().filter(schedule ->
                schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                        timeSlot.getPatient() != null
                )).count());
    }

    @Test
    public void ticketCancel() throws ServiceException {
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
        ResponseEntity<TicketResponse> ticketResponseResponseEntity = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        ResponseEntity<Void> ticketCancel = restTemplate.exchange(
                "http://localhost:8081/api/tickets/" + ticketResponseResponseEntity.getBody().getTicket(),
                HttpMethod.DELETE,
                new HttpEntity<>(null, registerPatient.getHeaders()),
                Void.class
        );
        assertNull(timeSlotDao.getByTicket(ticketResponseResponseEntity.getBody().getTicket()).getPatient());
    }

    @Test
    public void getAllTickets() {
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
        ticketRequest.setSpeciality("test");
        ResponseEntity<TicketResponse> ticketResp = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.POST,
                new HttpEntity<>(ticketRequest, registerPatient.getHeaders()),
                TicketResponse.class
        );
        ResponseEntity<PatientResponse> doctorLogin = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "test",
                        "12345678"
                )),
                PatientResponse.class
        );
        commissionRequest.setPatientId(registerPatient.getBody().getId());
        ResponseEntity<CommissionResponse> commissionResp = restTemplate.exchange(
                "http://localhost:8081/api/commissions",
                HttpMethod.POST,
                new HttpEntity<>(commissionRequest, doctorLogin.getHeaders()),
                CommissionResponse.class
        );
        ResponseEntity<AllTicketsResponse> tickets = restTemplate.exchange(
                "http://localhost:8081/api/tickets",
                HttpMethod.GET,
                new HttpEntity<>(registerPatient.getHeaders()),
                AllTicketsResponse.class
        );
        assertEquals(1, tickets.getBody().getTicketResponses().length);
        assertEquals(1, tickets.getBody().getCommissionArrays().length);
    }
}
