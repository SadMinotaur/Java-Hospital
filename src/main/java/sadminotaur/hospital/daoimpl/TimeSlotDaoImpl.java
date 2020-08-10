package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.dao.TimeSlotDao;
import sadminotaur.hospital.model.enums.TimeSlotState;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeSlotDaoImpl extends DaoImplBase implements TimeSlotDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeSlotDaoImpl.class);

    public TimeSlotDaoImpl() {
    }

    @Override
    public TimeSlot add(TimeSlot timeSlot, int scheduleId) throws ServiceException {
        LOGGER.debug("DAO insert timeSlot {}", timeSlot);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTimeSlotsMapper(sqlSession).addWithoutPatient(timeSlot, scheduleId);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert timeSlot {} {}", timeSlot, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return timeSlot;
    }

    @Override
    public void batchInsert(List<TimeSlot> timeSlots, int scheduleId) throws ServiceException {
        LOGGER.debug("DAO batch insert timeSlots {}", timeSlots);
        try (SqlSession sqlSession = getSessionBatch()) {
            try {
                timeSlots.forEach(timeSlot -> timeSlot.setScheduleId(scheduleId));
                sqlSession.insert("net.thumbtack.school.hospital.mappers.HospitalMapper.insertTimeSlots", timeSlots);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert timeSlots {} {}", timeSlots, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void update(TimeSlot timeSlot) throws ServiceException {
        LOGGER.debug("DAO update timeSlots {}", timeSlot);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTimeSlotsMapper(sqlSession).update(timeSlot);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update timeSlots {} {}", timeSlot, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void updateBusySlots(List<TimeSlot> timeSlots, TimeSlotState timeSlotState) throws ServiceException {
        LOGGER.debug("DAO update timeslots {}", timeSlots);
        try (SqlSession sqlSession = getSession()) {
            try {
                int result;
                if (timeSlotState == TimeSlotState.BUSY) {
                    result = sqlSession.update("net.thumbtack.school.hospital.mappers.HospitalMapper.setBusySlots", timeSlots);
                } else {
                    result = sqlSession.update("net.thumbtack.school.hospital.mappers.HospitalMapper.setEmptySlots", timeSlots);
                }
                if (timeSlots.size() != result) {
                    throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update timeslots {} {}", timeSlots, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public TimeSlot getByTicket(String ticket) throws ServiceException {
        LOGGER.debug("DAO get timeSlot by ticket {}", ticket);
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).getByTicket(ticket);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get timeSlot by ticket {} {}", ticket, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<TimeSlot> getByPatient(int idPatient) throws ServiceException {
        LOGGER.debug("DAO get timeSlots by patient {}", idPatient);
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).getByPatientId(idPatient);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get timeSlots by patient {} {}", idPatient, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<TimeSlot> getAll(int schedule) throws ServiceException {
        LOGGER.debug("DAO get timeSlots {}", schedule);
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).getByScheduleId(schedule);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get timeSlots {} {}", schedule, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public int getDoctorTimeslotsCount(int doctorId) throws ServiceException {
        LOGGER.debug("DAO get timeSlots count {}", doctorId);
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).doctorBusyCount(doctorId);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get timeSlots count {} {}", doctorId, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public int getBusyCount() throws ServiceException {
        LOGGER.debug("DAO get timeslots busy count");
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).busyCount();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get busy timeslots count ", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public int getPatientBusyCount(int id) throws ServiceException {
        LOGGER.debug("DAO get timeslots busy count {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getTimeSlotsMapper(sqlSession).patientBusyCount(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get busy timeslots count {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteByTicket(String ticket) throws ServiceException {
        LOGGER.debug("DAO delete timeSlots by ticket {}", ticket);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTimeSlotsMapper(sqlSession).deleteByTicket(ticket);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete timeSlots by ticket {} {}", ticket, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
