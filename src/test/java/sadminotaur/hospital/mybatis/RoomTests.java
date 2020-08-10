package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.dao.RoomDao;
import sadminotaur.hospital.daoimpl.DoctorDaoImpl;
import sadminotaur.hospital.daoimpl.RoomDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Test;

public class RoomTests extends BaseMyBatis {

    private final RoomDao roomDao = new RoomDaoImpl();
    private final DoctorDao doctorDao = new DoctorDaoImpl();

    @Test
    public void selectById() throws ServiceException {
        Assert.assertEquals(room1, roomDao.getById(room1.getId()));
    }
}
