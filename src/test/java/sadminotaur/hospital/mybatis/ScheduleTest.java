package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.ScheduleDao;
import sadminotaur.hospital.daoimpl.DoctorDaoImpl;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.daoimpl.ScheduleDaoImpl;
import sadminotaur.hospital.daoimpl.TimeSlotDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class ScheduleTest extends BaseMyBatis {

    private final ScheduleDao scheduleDao = new ScheduleDaoImpl();
    private final TimeSlotDaoImpl timeSlotDao = new TimeSlotDaoImpl();

    @Before
    public void init() throws ServiceException {
        new DoctorDaoImpl().add(doctorFirst);
        new PatientDaoImpl().add(patient);
    }

    @Test
    public void insert() throws ServiceException {
        assertNotEquals(0, scheduleDao.add(schedule, doctorFirst.getId()).getId());
    }

    @Test
    public void getByIdAndUpdate() throws ServiceException {
        scheduleDao.add(schedule, doctorFirst.getId());
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        schedule.setDate(LocalDate.now().plusDays(1));
        scheduleDao.update(schedule);
        Assert.assertNotEquals(schedule, scheduleDao.getById(schedule.getId()));
    }

    @Test
    public void delete() throws ServiceException {
        scheduleDao.add(schedule, doctorFirst.getId());
        scheduleDao.deleteById(schedule.getId());
        assertNull(scheduleDao.getById(schedule.getId()));
    }

    @Test
    public void insertList() throws ServiceException {
        scheduleList.add(schedule);
        schedule.setDate(LocalDate.now().plusDays(10));
        scheduleList.add(schedule);
        scheduleDao.insertSchedule(scheduleList, doctorFirst);
        Assert.assertNotEquals(0, scheduleList.get(0).getId());
        Assert.assertNotEquals(0, scheduleList.get(1).getId());
    }

    @Test
    public void selectList() throws ServiceException {
        scheduleList.add(schedule);
        schedule.setDate(LocalDate.now().plusDays(10));
        scheduleList.add(schedule);
        scheduleDao.insertSchedule(scheduleList, doctorFirst);
        assertNotEquals(0, scheduleDao.getByDoctorId(doctorFirst.getId()).size());
    }
}
