package sadminotaur.hospital.mybatis;

import sadminotaur.hospital.daoimpl.CommonDaoImpl;
import sadminotaur.hospital.model.*;
import sadminotaur.hospital.model.enums.UserType;

import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.Before;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BaseMyBatis {

    private final CommonDaoImpl commonDao = new CommonDaoImpl();

    @Before()
    public void clear() throws ServiceException {
        commonDao.clear();
    }

    protected Room room1 = new Room(1,"first");
    protected Room room2 = new Room(2,"second");
    protected Room room3 = new Room(3,"third");
    protected Administrator administrator = new Administrator(
            "test1",
            "test2",
            "test3",
            "test4",
            "test5",
            "test6",
            UserType.ADMINISTRATOR
    );
    protected MedicalSpeciality medicalSpeciality1 = new MedicalSpeciality(
            1,
            "test"
    );
    protected MedicalSpeciality medicalSpeciality2 = new MedicalSpeciality(
            2,
            "test1"
    );
    protected Patient patient = new Patient(
            "test1",
            "test2",
            "test3",
            "test4",
            "test5",
            "test6",
            "test7",
            "test8",
            UserType.PATIENT

    );
    protected Doctor doctorFirst = new Doctor(
            "test01",
            "test02",
            "test03",
            "test04",
            "test05",
            room1,
            10,
            medicalSpeciality1,
            UserType.DOCTOR
    );
    protected Doctor doctorSecond = new Doctor(
            "test01",
            "test02",
            "test03",
            "test0004",
            "test05",
            room2,
            10,
            medicalSpeciality1,
            UserType.DOCTOR
    );
    protected Doctor doctorThird = new Doctor(
            "test01",
            "test02",
            "test03",
            "test0000004",
            "test05",
            room3,
            10,
            medicalSpeciality2,
            UserType.DOCTOR
    );
    protected User user = new User(
            "test2",
            "test3",
            "test4",
            "test5",
            "test6",
            UserType.ADMINISTRATOR
    );
    protected Session sessionPat = new Session("test", patient);
    protected Session sessionDoc = new Session("test", doctorFirst);
    protected Session sessionAdm = new Session("test", administrator);
    protected Schedule schedule = new Schedule(
            doctorFirst,
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now().plusMinutes(100)
    );
    protected TimeSlot timeSlotFirst = new TimeSlot(
            "",
            patient,
            LocalTime.now(),
            LocalTime.now().plusMinutes(10)
    );

    protected TimeSlot timeSlotSecond = new TimeSlot(
            "test",
            patient,
            LocalTime.now().plusMinutes(10),
            LocalTime.now().plusMinutes(20)
    );
    protected Commission commission = new Commission(
            "test",
            patient,
            room1,
            LocalDate.now(),
            LocalTime.now(),
            LocalTime.now()
    );
    protected List<Schedule> scheduleList = new ArrayList<>();
}
