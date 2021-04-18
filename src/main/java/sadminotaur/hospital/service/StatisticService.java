package sadminotaur.hospital.service;

import sadminotaur.hospital.dao.*;
import sadminotaur.hospital.response.statistic.DoctorTimeslotsCount;
import sadminotaur.hospital.response.statistic.TotalUserCount;
import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.response.statistic.PatientCount;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class StatisticService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private TimeSlotDao timeSlotsDao;

    public ResponseEntity<TotalUserCount> usersCount(Cookie cookie, boolean doctor, boolean patient) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() == null) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
        }
        TotalUserCount totalUserCount = new TotalUserCount(userDao.getUserCount());
        if (doctor) {
            totalUserCount.setDoctorsCount(doctorDao.getCount());
        }
        if (patient) {
            totalUserCount.setPatientCount(patientDao.getCount());
        }
        return new ResponseEntity<>(
                totalUserCount,
                HttpStatus.OK
        );
    }

    public ResponseEntity<DoctorTimeslotsCount> doctorsTimeslotCount(Cookie cookie, Integer doctor) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() == null) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
        }
        DoctorTimeslotsCount doctorTimeslotsCount = new DoctorTimeslotsCount(timeSlotsDao.getBusyCount());
        if (doctor != null) {
            doctorTimeslotsCount.setSelectedDocBusyTCount(timeSlotsDao.getDoctorTimeslotsCount(doctor));
        }
        return new ResponseEntity<>(
                doctorTimeslotsCount,
                HttpStatus.OK
        );
    }


    public ResponseEntity<PatientCount> patientTimeslotCount(Cookie cookie, Integer patient) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() == null) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
        }
        if (patient == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
        }
        return new ResponseEntity<>(
                new PatientCount(
                        timeSlotsDao.getPatientBusyCount(patient)
                ),
                HttpStatus.OK
        );
    }
}
