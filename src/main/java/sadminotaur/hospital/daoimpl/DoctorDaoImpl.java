package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.model.Schedule;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Component
public class DoctorDaoImpl extends DaoImplBase implements DoctorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    public DoctorDaoImpl() {
    }

    @Override
    public Doctor add(Doctor doctor) throws ServiceException {
        LOGGER.debug("DAO insert doctor {}", doctor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).add(doctor);
                getDoctorMapper(sqlSession).add(doctor);
                List<Schedule> schedules = doctor.getSchedule();
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
                LOGGER.info("Can't insert doctor {} {}", doctor, ex);
                sqlSession.rollback();
                if (ex.getClass() == PersistenceException.class) {
                    throw new ServiceException(ServiceErrorCode.DUPLICATE_ENTITY);
                }
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return doctor;
    }

    @Override
    public void update(Doctor doctor) throws ServiceException {
        LOGGER.debug("DAO update doctor {}", doctor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(doctor);
                getDoctorMapper(sqlSession).update(doctor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update doctor {} {}", doctor, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }


    @Override
    public Doctor getById(int id) throws ServiceException {
        LOGGER.debug("DAO get doctor by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctor by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Doctor> getDoctors() throws ServiceException {
        LOGGER.debug("DAO get doctors");
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getDoctors();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctors", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Doctor> getDoctors(int speciality) throws ServiceException {
        LOGGER.debug("DAO get doctors");
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getDoctorsBySpeciality(speciality);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctors", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Doctor> getDoctors(Set<Integer> doctors) throws ServiceException {
        LOGGER.debug("DAO get doctors");
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getDoctorsInRange(doctors);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctors", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public int getCount() throws ServiceException {
        LOGGER.debug("DAO get doctors count");
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).doctorCount();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctors count", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public List<Doctor> getDoctorsByPatientId(int patient) throws ServiceException {
        LOGGER.debug("DAO get doctors");
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getDoctorsByPatient(patient);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get doctors", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOGGER.debug("DAO delete doctor by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete doctor by id {} {}", id, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
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