package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientTest extends BaseMyBatis {

    private final PatientDao dao = new PatientDaoImpl();

    @Test
    public void insert() throws ServiceException {
        assertNotEquals(0, dao.add(patient).getId());
    }

    @Test
    public void update() throws ServiceException {
        dao.add(patient);
        patient.setEmail("changed");
        dao.update(patient);
        Assert.assertEquals(patient, dao.getById(patient.getId()));
    }

    @Test
    public void delete() throws ServiceException {
        dao.add(patient);
        dao.deleteById(patient.getId());
        assertNull(dao.getById(patient.getId()));
    }
}
