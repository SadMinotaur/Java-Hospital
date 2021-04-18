package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.daoimpl.MedicalSpecialityDaoImpl;
import sadminotaur.hospital.daoimpl.RoomDaoImpl;
import sadminotaur.hospital.daoimpl.CommissionDaoImpl;
import sadminotaur.hospital.daoimpl.DoctorDaoImpl;
import sadminotaur.hospital.daoimpl.PatientDaoImpl;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommissionTest extends BaseMyBatis {

    private final CommissionDaoImpl commissionDao = new CommissionDaoImpl();
    private final MedicalSpecialityDaoImpl medicalSpecialityDao = new MedicalSpecialityDaoImpl();
    private final RoomDaoImpl roomDao = new RoomDaoImpl();
    private final DoctorDaoImpl doctorDao = new DoctorDaoImpl();
    private final PatientDaoImpl patientDao = new PatientDaoImpl();

    @Before
    public void init() throws ServiceException {
        doctorDao.add(doctorFirst);
        doctorDao.add(doctorSecond);
        patientDao.add(patient);
    }

    @Test
    public void insertAndSelect() throws ServiceException {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctorFirst);
        doctorList.add(doctorSecond);
        commissionDao.add(commission, doctorList);
        Assert.assertEquals(2, commissionDao.getById(commission.getId()).getDoctors().size());
    }

    @Test
    public void delete() throws ServiceException {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctorFirst);
        doctorList.add(doctorSecond);
        commissionDao.add(commission, doctorList);
        commissionDao.deleteByTicket(commission.getTicket(), new ArrayList<>());
        assertNull(commissionDao.getById(commission.getId()));
    }
}
