package sadminotaur.hospital.controller;

import sadminotaur.hospital.response.statistic.DoctorTimeslotsCount;
import sadminotaur.hospital.response.statistic.TotalUserCount;
import sadminotaur.hospital.service.StatisticService;
import sadminotaur.hospital.serviceexception.ServiceException;
import sadminotaur.hospital.response.statistic.PatientCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping(path = "api/statistic")
public class StatisticEndpoint {

    @Autowired
    private StatisticService statisticService;

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TotalUserCount> allUsers(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @RequestParam(value = "doctor", required = false) boolean doctor,
            @RequestParam(value = "patient", required = false) boolean patient
    ) throws ServiceException {
        return statisticService.usersCount(JAVASESSIONID, doctor, patient);
    }

    @GetMapping(value = "/doctors")
    public ResponseEntity<DoctorTimeslotsCount> timeSlotsCount(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @RequestParam(value = "id", required = false) Integer id
    ) throws ServiceException {
        return statisticService.doctorsTimeslotCount(JAVASESSIONID, id);
    }

    @GetMapping(value = "/patients/{id}")
    public ResponseEntity<PatientCount> patientTimeSlotsCount(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("id") Integer id
    ) throws ServiceException {
        return statisticService.patientTimeslotCount(JAVASESSIONID, id);
    }
}
