package sadminotaur.hospital.controller;

import sadminotaur.hospital.dao.CommissionDao;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.response.CommissionResponse;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.response.PatientResponse;
import sadminotaur.hospital.serviceexception.ServiceException;
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
public class CommissionEndpointTest extends ControllerBase {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private CommissionDao commissionDao;

    @Test
    public void register() {
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
        ResponseEntity<PatientResponse> doctorLogin = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "test",
                        "12345678"
                )),
                PatientResponse.class
        );
        ResponseEntity<PatientResponse> registerPatient = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        commissionRequest.setPatientId(registerPatient.getBody().getId());
        ResponseEntity<CommissionResponse> commissionResp = restTemplate.exchange(
                "http://localhost:8081/api/commissions",
                HttpMethod.POST,
                new HttpEntity<>(commissionRequest, doctorLogin.getHeaders()),
                CommissionResponse.class
        );
        assertEquals(1, commissionResp.getBody().getDoctorIds().length);
    }

    @Test
    public void cancel() throws ServiceException {
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
        ResponseEntity<PatientResponse> doctorLogin = restTemplate.exchange(
                "http://localhost:8081/api/sessions",
                HttpMethod.POST,
                new HttpEntity<>(new LoginRequest(
                        "test",
                        "12345678"
                )),
                PatientResponse.class
        );
        ResponseEntity<PatientResponse> registerPatient = restTemplate.exchange(
                "http://localhost:8081/api/patients",
                HttpMethod.POST,
                new HttpEntity<>(patient),
                PatientResponse.class
        );
        commissionRequest.setPatientId(registerPatient.getBody().getId());
        ResponseEntity<CommissionResponse> commissionResp = restTemplate.exchange(
                "http://localhost:8081/api/commissions",
                HttpMethod.POST,
                new HttpEntity<>(commissionRequest, doctorLogin.getHeaders()),
                CommissionResponse.class
        );
        restTemplate.exchange(
                "http://localhost:8081/api/commissions/" + commissionResp.getBody().getTicket(),
                HttpMethod.DELETE,
                new HttpEntity<>(null, registerPatient.getHeaders()),
                Void.class
        );
        assertNull(commissionDao.getByTicket(commissionResp.getBody().getTicket()));
    }
}
