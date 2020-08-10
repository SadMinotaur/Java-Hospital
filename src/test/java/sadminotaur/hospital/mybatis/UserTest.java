package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.UserDao;
import sadminotaur.hospital.daoimpl.UserDaoImpl;

import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest extends BaseMyBatis {

    private final UserDao userDao = new UserDaoImpl();

    @Test
    public void insert() throws ServiceException {
        assertNotEquals(0, userDao.add(user).getId());
    }

    @Test
    public void getCount() throws ServiceException {
        assertEquals(1, userDao.getUserCount());
    }

    @Test
    public void getByIdAndUpdate() throws ServiceException {
        userDao.add(user);
        user.setFirstname("1");
        userDao.update(user);
        Assert.assertEquals(user, userDao.getById(user.getId()));
    }

    @Test
    public void delete() throws ServiceException {
        userDao.add(user);
        userDao.deleteById(user.getId());
        assertNull(userDao.getById(user.getId()));
    }
}
