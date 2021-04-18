package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.DeleteDoctorDTO;
import sadminotaur.hospital.requests.DoctorRegisterDTO;
import sadminotaur.hospital.requests.ScheduleUpdateDTO;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.service.DoctorService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/doctors")
public class DoctorEndpoint {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<DoctorResponse> register(
            @Valid @RequestBody DoctorRegisterDTO doctorRegisterDTO,
            @CookieValue(required = true) Cookie JAVASESSIONID
    ) throws ServiceException {
        return doctorService.register(doctorRegisterDTO, JAVASESSIONID);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<DoctorResponse> getInfo(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @RequestParam(value = "schedule") String schedule,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @PathVariable("id") int id
    ) throws ServiceException {
        return doctorService.getDoctor(JAVASESSIONID, schedule, startDate, endDate, id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<DoctorResponse>> getInfo(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @RequestParam(value = "schedule") String schedule,
            @RequestParam(value = "speciality", required = false) String speciality,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate
    ) throws ServiceException {
        return doctorService.getDoctors(JAVASESSIONID, schedule, startDate, endDate, speciality);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> deleteDoctor(
            @Valid @RequestBody DeleteDoctorDTO deleteDoctorDTO,
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("id") int id
    ) throws ServiceException {
        return doctorService.deleteDoctor(JAVASESSIONID, id, deleteDoctorDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<DoctorResponse> updateSchedule(
            @Valid @RequestBody ScheduleUpdateDTO scheduleUpdateDTO,
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("id") int id
    ) throws ServiceException {
        return doctorService.updateSchedule(scheduleUpdateDTO, JAVASESSIONID, id);
    }
}
