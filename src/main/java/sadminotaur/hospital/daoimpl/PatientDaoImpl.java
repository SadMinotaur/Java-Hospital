package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientDaoImpl extends DaoImplBase implements PatientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    public PatientDaoImpl() {
    }

    @Override
    public Patient add(Patient patient) throws ServiceException {
        LOGGER.debug("DAO insert patient {}", patient);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).add(patient);
                getPatientMapper(sqlSession).add(patient);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert patient {} {}", patient, ex);
                sqlSession.rollback();
                if (ex.getClass() == PersistenceException.class) {
                    throw new ServiceException(ServiceErrorCode.DUPLICATE_ENTITY);
                }
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return patient;
    }

    @Override
    public void update(Patient patient) throws ServiceException {
        LOGGER.debug("DAO update patient {}", patient);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(patient);
                getPatientMapper(sqlSession).update(patient);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update patient {} {}", patient, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public Patient getById(int id) throws ServiceException {
        LOGGER.debug("DAO get patient by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get patient by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public int getCount() throws ServiceException {
        LOGGER.debug("DAO get patients count");
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).patientCount();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get patients count", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOGGER.debug("DAO delete patient by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete patient by id {} {}", id, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
