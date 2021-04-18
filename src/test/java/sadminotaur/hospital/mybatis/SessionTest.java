package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.daoimpl.SessionDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest extends BaseMyBatis {

    private final SessionDao sessionDao = new SessionDaoImpl();
    private final PatientDao patientDao = new PatientDaoImpl();

    @Test
    public void insert() throws ServiceException {
        patientDao.add(patient);
        sessionDao.add(sessionPat);
        assertDoesNotThrow(() -> {
            sessionDao.getByCookie(sessionPat.getCookie()).getUser();
        });
    }


    @Test
    public void delete() throws ServiceException {
        patientDao.add(patient);
        sessionDao.add(sessionPat);
        sessionDao.deleteByCookie(sessionPat.getCookie());
        assertThrows(NullPointerException.class, () -> {
            sessionDao.getByCookie(sessionPat.getCookie()).getUser().getId();
        });
    }
}
