package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.model.Schedule;
import sadminotaur.hospital.dao.ScheduleDao;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ScheduleDaoImpl extends DaoImplBase implements ScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleDaoImpl.class);

    public ScheduleDaoImpl() {
    }

    @Override
    public Schedule add(Schedule schedule, int doctor) throws ServiceException {
        LOGGER.debug("DAO insert schedule {}", schedule);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).add(schedule, doctor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert schedule {} {}", schedule, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return schedule;
    }

    @Override
    public void update(Schedule schedule) throws ServiceException {
        LOGGER.debug("DAO update schedule {}", schedule);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).update(schedule);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update schedule {} {}", schedule, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public Schedule getById(int id) throws ServiceException {
        LOGGER.debug("DAO get schedule by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get schedule by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Schedule> getByDoctorId(int id) throws ServiceException {
        LOGGER.debug("DAO get schedule by doctorId {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getByDoctorId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get schedule by doctorId {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOGGER.debug("DAO delete schedule by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).deleteById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete schedule {} {}", id, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAfterDate(LocalDate date, int id) throws ServiceException {
        LOGGER.debug("DAO delete schedule after date {} {}", id, date);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).deleteAfterDate(date, id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete schedule after date {} {} {}", id, date, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteBetweenDates(LocalDate start, LocalDate end, int doctor) throws ServiceException {
        LOGGER.debug("DAO delete schedules {} {} {}", start, end, doctor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).deleteBetweenDates(start, end, doctor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete schedule {} {} {} {}", start, end, doctor, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void insertSchedule(List<Schedule> schedules, Doctor doctor) throws ServiceException {
        LOGGER.debug("DAO batch insert schedule {}", schedules);
        try (SqlSession sqlSession = getSession()) {
            try {
                if (!schedules.isEmpty()) {
                    schedules.forEach(schedule -> schedule.setDoctorId(doctor.getId()));
                    sqlSession.insert("sadminotaur.hospital.mappers.HospitalMapper.insertSchedules", schedules);
                    for (Schedule schedule : schedules) {
                        for (LocalTime tempTime = schedule.getWorkingHoursStart();
                             tempTime.compareTo(schedule.getWorkingHoursEnd()) <= 0;
                             tempTime = tempTime.plusMinutes(doctor.getDuration())
                        ) {
                            schedule.getTimeSlots().add(new TimeSlot(
                                    generateTicket(doctor, tempTime, schedule.getDate()),
                                    tempTime,
                                    tempTime.plusMinutes(doctor.getDuration())
                            ));
                        }
                        List<TimeSlot> timeSlots = schedule.getTimeSlots();
                        if (!timeSlots.isEmpty()) {
                            timeSlots.forEach(timeSlot -> timeSlot.setScheduleId(schedule.getId()));
                            sqlSession.insert("sadminotaur.hospital.mappers.HospitalMapper.insertTimeSlots", timeSlots);
                        }
                    }
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert schedules {} {}", schedules, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
    }

    private String generateTicket(Doctor doctor, LocalTime localTime, LocalDate date) {
        return "D" + doctor.getId() +
                date.format(DateTimeFormatter.ofPattern("ddMMyyyy")) +
                localTime.format(DateTimeFormatter.ofPattern("HHmm"));
    }
}
