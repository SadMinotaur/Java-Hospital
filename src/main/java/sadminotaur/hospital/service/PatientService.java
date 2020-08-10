package sadminotaur.hospital.service;

import sadminotaur.hospital.requests.PatientRegisterDTO;
import sadminotaur.hospital.requests.PatientUpdateDTO;
import sadminotaur.hospital.response.PatientResponse;
import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.mappersdto.PatientDTOMapper;
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
public class PatientService {

    private static final String COOKIE = "Cookie";
    private static final String JAVASESESSIONID = "JAVASESSIONID";

    @Autowired
    private PatientDao patientDao;
    @Autowired
    private SessionDao sessionDao;

    private final PatientDTOMapper patientMapper = Mappers.getMapper(PatientDTOMapper.class);

    public ResponseEntity<PatientResponse> register(PatientRegisterDTO registerRegisterDTO) throws ServiceException {
        Patient patient = patientMapper.patientDTOtoPatient(registerRegisterDTO);
        HttpHeaders headers = new HttpHeaders();
        patient.setPhone(patient.getPhone().replace("-", ""));
        String cookie = UUID.randomUUID().toString();
        patientDao.add(patient);
        sessionDao.add(new Session(
                cookie,
                patient
        ));
        headers.add(COOKIE, JAVASESESSIONID + "=" + cookie);
        return new ResponseEntity<>(
                patientMapper.patientToPatientResponse(patient),
                headers,
                HttpStatus.OK
        );
    }

    public ResponseEntity<PatientResponse> getInfo(int id, Cookie cookie) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session != null && session.getUser().getUserType() != UserType.PATIENT) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(COOKIE, cookie.getValue());
            Patient patient = patientDao.getById(id);
            if (patient != null) {
                if (patient.getUserType() != UserType.PATIENT) {
                    throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
                }
                return new ResponseEntity<>(
                        patientMapper.patientToPatientResponse(patient),
                        headers,
                        HttpStatus.OK
                );
            }
        }
        throw new ServiceException(ServiceErrorCode.PATIENT_ARE_NOT_EXIST);
    }

    public ResponseEntity<PatientResponse> update(Cookie cookie, PatientUpdateDTO patientDTO) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.PATIENT){
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        Patient patientFromBase = patientDao.getById(session.getUser().getId());
        Patient patientUpdated = patientUpdate(patientFromBase, patientDTO);
        patientDao.update(patientUpdated);
        return new ResponseEntity<>(
                patientMapper.patientToPatientResponse(patientUpdated),
                HttpStatus.OK
        );
    }


    private Patient patientUpdate(Patient patient, PatientUpdateDTO patientUpdateDTO) throws ServiceException {
        patient.setFirstname(patientUpdateDTO.getFirstname());
        patient.setLastname(patientUpdateDTO.getLastname());
        patient.setPatronymic(patientUpdateDTO.getPatronymic());
        patient.setEmail(patientUpdateDTO.getEmail());
        patient.setAddress(patientUpdateDTO.getAddress());
        patient.setAddress(patientUpdateDTO.getAddress());
        if (patientUpdateDTO.getOldPassword().equals(patient.getPassword())) {
            patient.setPassword(patientUpdateDTO.getNewPassword());
        } else {
            throw new ServiceException(ServiceErrorCode.PASSWORD_MISMATCH);
        }
        return patient;
    }
}
