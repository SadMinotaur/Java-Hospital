package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.daoimpl.DoctorDaoImpl;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.daoimpl.ScheduleDaoImpl;
import sadminotaur.hospital.daoimpl.TimeSlotDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DoctorTest extends BaseMyBatis {

    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Test
    public void insert() throws ServiceException {
        Assert.assertNotEquals(0, doctorDao.add(doctorFirst).getId());
    }

    @Test
    public void ins() throws ServiceException {
        assertThrows(ServiceException.class, () -> {
            doctorDao.add(doctorFirst);
            doctorDao.add(doctorFirst);
        });
    }

    @Test
    public void update() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorFirst.setPassword("123");
        doctorDao.update(doctorFirst);
        Assertions.assertEquals(doctorFirst, doctorDao.getById(doctorFirst.getId()));
    }

    @Test
    public void scheduleGet() throws ServiceException {
        new PatientDaoImpl().add(patient);
        doctorDao.add(doctorFirst);
        new ScheduleDaoImpl().add(schedule, doctorFirst.getId());
        new TimeSlotDaoImpl().add(timeSlotFirst, schedule.getId());
        Assertions.assertEquals(1, doctorDao.getById(doctorFirst.getId()).getSchedule().size());
    }

    @Test
    public void scheduleGetList1() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorDao.add(doctorSecond);
        assertEquals(2, doctorDao.getDoctors().size());
    }

    @Test
    public void scheduleGetList2() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorDao.add(doctorSecond);
        doctorDao.add(doctorThird);
        assertEquals(2, doctorDao.getDoctors(medicalSpeciality1.getId()).size());
    }

    @Test
    public void scheduleGetList3() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorDao.add(doctorSecond);
        assertEquals(2, doctorDao.getDoctors(new HashSet<>(Arrays.asList(doctorFirst.getId(), doctorSecond.getId()))).size());
    }

    @Test
    public void delete() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorDao.deleteById(doctorFirst.getId());
        assertNull(doctorDao.getById(doctorFirst.getId()));
    }
}
