package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.AdministratorDao;
import sadminotaur.hospital.daoimpl.AdministratorDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdministratorTest extends BaseMyBatis {

    private final AdministratorDao dao = new AdministratorDaoImpl();

    @Test
    public void insert() throws ServiceException {
        assertNotEquals(0, dao.add(administrator).getId());
    }

    @Test
    public void update() throws ServiceException {
        dao.add(administrator);
        administrator.setPosition("changed");
        dao.update(administrator);
        Assert.assertEquals(administrator, dao.getById(administrator.getId()));
    }

    @Test
    public void delete() throws ServiceException {
        dao.add(administrator);
        dao.deleteById(administrator.getId());
        assertNull(dao.getById(administrator.getId()));
    }
}
