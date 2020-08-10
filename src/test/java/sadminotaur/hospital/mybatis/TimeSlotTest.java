package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.daoimpl.DoctorDaoImpl;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.daoimpl.ScheduleDaoImpl;
import sadminotaur.hospital.daoimpl.TimeSlotDaoImpl;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.model.enums.TimeSlotState;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TimeSlotTest extends BaseMyBatis {

    private final TimeSlotDaoImpl timeSlotDao = new TimeSlotDaoImpl();

    @Before
    public void init() throws ServiceException {
        new DoctorDaoImpl().add(doctorFirst);
        new ScheduleDaoImpl().add(schedule, doctorFirst.getId());
        new PatientDaoImpl().add(patient);
    }

    @Test
    public void getByIdAndUpdate() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotFirst.setTimeEnd(LocalTime.now().plusMinutes(4));
        timeSlotDao.update(timeSlotFirst);
        Assert.assertEquals(timeSlotFirst, timeSlotDao.getByTicket(timeSlotFirst.getTicket()));
    }

    @Test
    public void setBusy() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotDao.add(timeSlotSecond, schedule.getId());
        timeSlotDao.updateBusySlots(Arrays.asList(timeSlotFirst, timeSlotSecond), TimeSlotState.BUSY);
        Assert.assertEquals(TimeSlotState.BUSY, timeSlotDao.getByTicket(timeSlotFirst.getTicket()).getTimeSlotState());
        Assert.assertEquals(TimeSlotState.BUSY, timeSlotDao.getByTicket(timeSlotSecond.getTicket()).getTimeSlotState());
    }

    @Test
    public void batchInsert() throws ServiceException {
        List<TimeSlot> timeSlotList = new ArrayList<>();
        timeSlotList.add(timeSlotFirst);
        timeSlotList.add(timeSlotSecond);
        timeSlotDao.batchInsert(timeSlotList, schedule.getId());
        assertNotNull(timeSlotDao.getByTicket(""));
        assertNotNull(timeSlotDao.getByTicket("test"));
    }

    @Test
    public void getById() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        assertNull(timeSlotDao.getByTicket(timeSlotFirst.getTicket()).getPatient());
    }

    @Test
    public void getByPatientId() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotFirst.setPatient(patient);
        timeSlotDao.update(timeSlotFirst);
        assertEquals(1, timeSlotDao.getByPatient(timeSlotFirst.getPatient().getId()).size());
    }

    @Test
    public void getByScheduleId() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        assertEquals(3, timeSlotDao.getAll(schedule.getId()).size());
    }

    @Test
    public void delete() throws ServiceException {
        timeSlotDao.add(timeSlotFirst, schedule.getId());
        timeSlotDao.deleteByTicket(timeSlotFirst.getTicket());
        assertNull(timeSlotDao.getByTicket(timeSlotFirst.getTicket()));
    }

}
