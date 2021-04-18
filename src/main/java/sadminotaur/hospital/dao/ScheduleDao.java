package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.model.Schedule;
import sadminotaur.hospital.serviceexception.ServiceException;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDao {
    Schedule add(Schedule schedule, int doctor) throws ServiceException;

    void update(Schedule schedule) throws ServiceException;

    Schedule getById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    void insertSchedule(List<Schedule> schedules, Doctor doctor) throws ServiceException;

    List<Schedule> getByDoctorId(int id) throws ServiceException;

    void deleteAfterDate(LocalDate date, int id) throws ServiceException;

    void deleteBetweenDates(LocalDate start, LocalDate end, int doctor) throws ServiceException;
}
