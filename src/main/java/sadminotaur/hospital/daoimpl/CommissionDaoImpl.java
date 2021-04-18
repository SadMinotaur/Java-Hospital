package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Commission;
import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.dao.CommissionDao;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommissionDaoImpl extends DaoImplBase implements CommissionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionDaoImpl.class);

    public CommissionDaoImpl() {
    }

    @Override
    public Commission add(Commission commission, List<Doctor> doctors) throws ServiceException {
        LOGGER.debug("DAO insert commission {}", commission);
        try (SqlSession sqlSession = getSession()) {
            try {
                getCommissionMapper(sqlSession).add(commission);
                getCommissionMapper(sqlSession).addDoctors(commission, doctors);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert commission {} {}", commission, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return commission;
    }

    @Override
    public Commission getById(int id) throws ServiceException {
        LOGGER.debug("DAO get commission by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getCommissionMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get commission by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public Commission getByTicket(String ticket) throws ServiceException {
        LOGGER.debug("DAO get commission by ticket {}", ticket);
        try (SqlSession sqlSession = getSession()) {
            return getCommissionMapper(sqlSession).getByTicket(ticket);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get commission by ticket {} {}", ticket, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Commission> getByPatient(int patient) throws ServiceException {
        LOGGER.debug("DAO get commission by patient {}", patient);
        try (SqlSession sqlSession = getSession()) {
            return getCommissionMapper(sqlSession).getByPatient(patient);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get commission by patient {} {}", patient, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteByTicket(String ticket, List<TimeSlot> timeSlots) throws ServiceException {
        LOGGER.debug("DAO delete commission by ticket {}", ticket);
        try (SqlSession sqlSession = getSession()) {
            try {
                if(timeSlots.size() > 0) {
                    if (timeSlots.size() != sqlSession.update("sadminotaur.hospital.mappers.HospitalMapper.setEmptySlots", timeSlots)) {
                        throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
                    }
                }
                if (getCommissionMapper(sqlSession).deleteByTicket(ticket) == 0) {
                    throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete commission {} {}", ticket, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
