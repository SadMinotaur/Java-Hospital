package sadminotaur.hospital.service;

import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.mappersdto.AdministratorDTOMapper;
import sadminotaur.hospital.mappersdto.DoctorDTOMapper;
import sadminotaur.hospital.mappersdto.PatientDTOMapper;
import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.model.User;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.dao.AdministratorDao;
import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.dao.UserDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Component
public class SessionService {

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private AdministratorDao administratorDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionDao sessionDao;

    private final String COOKIE = "Cookie";
    private final String JAVASESESSIONID = "JAVASESSIONID";

    private final PatientDTOMapper patientMapper = Mappers.getMapper(PatientDTOMapper.class);
    private final AdministratorDTOMapper adminMapper = Mappers.getMapper(AdministratorDTOMapper.class);

    public ResponseEntity<Object> post(LoginRequest loginRequest) throws ServiceException {
        User user = userDao.getByLogin(loginRequest);
        if (user != null) {
            String cookie = UUID.randomUUID().toString();
            sessionDao.add(new Session(cookie, user));
            HttpHeaders headers = new HttpHeaders();
            headers.add(COOKIE, JAVASESESSIONID + "=" + cookie);
            switch (user.getUserType()) {
                case ADMINISTRATOR:
                    return new ResponseEntity<>(
                            adminMapper.adminToAdminResponse(administratorDao.getById(user.getId())), headers, HttpStatus.OK);
                case DOCTOR:
                    return new ResponseEntity<>(
                            DoctorDTOMapper.responseWithoutSch(doctorDao.getById(user.getId())), headers, HttpStatus.OK);
                case PATIENT:
                    return new ResponseEntity<>(
                            patientMapper.patientToPatientResponse(patientDao.getById(user.getId())), headers, HttpStatus.OK);
            }
        }
        throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
    }

    public ResponseEntity<Void> logout(Cookie JAVASESSIONID) throws ServiceException {
        sessionDao.deleteByCookie(JAVASESSIONID.getValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> getUserInfo(Cookie cookie) throws ServiceException {
        User user = sessionDao.getByCookie(cookie.getValue()).getUser();
        if (user != null) {
            switch (user.getUserType()) {
                case ADMINISTRATOR:
                    return new ResponseEntity<>(
                            administratorDao.getById(user.getId()), HttpStatus.OK);
                case DOCTOR:
                    return new ResponseEntity<>(
                            doctorDao.getById(user.getId()), HttpStatus.OK);
                case PATIENT:
                    return new ResponseEntity<>(
                            patientDao.getById(user.getId()), HttpStatus.OK);
            }
        }
        throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
    }
}
