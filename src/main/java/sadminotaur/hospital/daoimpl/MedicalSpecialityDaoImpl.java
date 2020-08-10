package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.dao.MedicalSpecialityDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MedicalSpecialityDaoImpl extends DaoImplBase implements MedicalSpecialityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalSpecialityDaoImpl.class);

    public MedicalSpecialityDaoImpl() {
    }

    @Override
    public MedicalSpeciality getById(int id) throws ServiceException {
        LOGGER.debug("DAO get room by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getMedicalSpecialityMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get medicalSpeciality by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public MedicalSpeciality getByName(String speciality) throws ServiceException {
        LOGGER.debug("DAO get room by name {}", speciality);
        try (SqlSession sqlSession = getSession()) {
            return getMedicalSpecialityMapper(sqlSession).getByName(speciality);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get medicalSpeciality by name {} {}", speciality, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }
}
