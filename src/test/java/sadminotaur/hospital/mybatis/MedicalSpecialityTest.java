package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.daoimpl.MedicalSpecialityDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MedicalSpecialityTest extends BaseMyBatis {

    private final MedicalSpecialityDaoImpl dao = new MedicalSpecialityDaoImpl();

    @Test
    public void selectById() throws ServiceException {
        assertEquals(medicalSpeciality1, dao.getById(medicalSpeciality1.getId()));
    }
}
