package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.PatientRegisterDTO;
import sadminotaur.hospital.requests.PatientUpdateDTO;
import sadminotaur.hospital.response.PatientResponse;
import sadminotaur.hospital.service.PatientService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping("api/patients")
public class PatientEndpoint {

    @Autowired
    private PatientService patientService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<PatientResponse> register(@Valid @RequestBody PatientRegisterDTO patient) throws ServiceException {
        return patientService.register(patient);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientResponse> responseEntity(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("id") int id
    ) throws ServiceException {
        return patientService.getInfo(id, JAVASESSIONID);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<PatientResponse> update(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @Valid @RequestBody PatientUpdateDTO patient) throws ServiceException {
        return patientService.update(JAVASESSIONID, patient);
    }
}
