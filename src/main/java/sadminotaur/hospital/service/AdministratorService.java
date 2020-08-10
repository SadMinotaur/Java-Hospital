package sadminotaur.hospital.service;

import sadminotaur.hospital.requests.AdministratorRegisterDTO;
import sadminotaur.hospital.requests.AdministratorUpdateDTO;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.model.Administrator;
import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.dao.AdministratorDao;
import sadminotaur.hospital.mappersdto.AdministratorDTOMapper;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;

@Component
public class AdministratorService {

    private static final String COOKIE = "Cookie";
    private static final String JAVASESESSIONID = "JAVASESSIONID";

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private AdministratorDao administratorDao;

    private final AdministratorDTOMapper administratorDTOMapper = Mappers.getMapper(AdministratorDTOMapper.class);

    public ResponseEntity<AdministratorResponse> register(AdministratorRegisterDTO administratorRegisterDTO, Cookie cookie) throws ServiceException {
        getAdministratorSession(cookie);
        Administrator administrator = administratorDTOMapper.adminDTOToAdmin(administratorRegisterDTO);
        administratorDao.add(administrator);
        return new ResponseEntity<>(
                administratorDTOMapper.adminToAdminResponse(administrator),
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<AdministratorResponse> update(AdministratorUpdateDTO administratorUpdateDTO, Cookie cookie) throws ServiceException {
        Session session = getAdministratorSession(cookie);
        Administrator adminFromBase = administratorDao.getById(session.getUser().getId());
        Administrator adminUpdated = adminUpdate(administratorUpdateDTO, adminFromBase);
        administratorDao.update(adminUpdated);
        return new ResponseEntity<>(
                administratorDTOMapper.adminToAdminResponse(adminUpdated),
                HttpStatus.OK
        );
    }

    private Administrator adminUpdate(AdministratorUpdateDTO administratorUpdateDTO, Administrator administrator) throws ServiceException {
        administrator.setFirstname(administratorUpdateDTO.getFirstname());
        administrator.setLastname(administratorUpdateDTO.getLastname());
        administrator.setPatronymic(administratorUpdateDTO.getPatronymic());
        administrator.setPosition(administratorUpdateDTO.getPosition());
        if (administratorUpdateDTO.getOldPassword().equals(administrator.getPassword())) {
            administrator.setPassword(administratorUpdateDTO.getNewPassword());
        } else {
            throw new ServiceException(ServiceErrorCode.PASSWORD_MISMATCH);
        }
        return administrator;
    }

    private Session getAdministratorSession(Cookie cookie) throws ResponseStatusException, ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.ADMINISTRATOR) {
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        return session;
    }
}
