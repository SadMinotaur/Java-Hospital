package sadminotaur.hospital.controller;

import sadminotaur.hospital.daoimpl.CommonDaoImpl;
import sadminotaur.hospital.requests.*;
import sadminotaur.hospital.model.Administrator;
import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.junit.jupiter.api.BeforeEach;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ControllerBase {

    private final CommonDaoImpl commonDao = new CommonDaoImpl();
    protected final static String datePattern = "yyyy-MM-dd";
    protected final static String timePattern = "HH:mm:ss";
    protected Room room1 = new Room(1, "first");
    protected MedicalSpeciality medicalSpeciality = new MedicalSpeciality(1, "test");

    @BeforeEach()
    public void clear() throws ServiceException {
        commonDao.clear();
    }

    protected DoctorRegisterDTO doctorRegisterDTO1 = new DoctorRegisterDTO(
            "проверка",
            "проверка",
            "проверка",
            "testDoctor",
            "12345678",
            room1.getName(),
            10,
            medicalSpeciality.getName(),
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern)),
            LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(datePattern)),
            null,
            new WeekSchedule(
                    LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                    LocalTime.NOON.plusMinutes(10).format(DateTimeFormatter.ofPattern(timePattern)),
                    null
            )
    );
    protected DoctorRegisterDTO doctorRegisterDTO2 = new DoctorRegisterDTO(
            "проверка",
            "проверка",
            "проверка",
            "test",
            "12345678",
            room1.getName(),
            10,
            medicalSpeciality.getName(),
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern)),
            LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern(datePattern)),
            null,
            new WeekSchedule(
                    LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                    LocalTime.NOON.plusMinutes(400).format(DateTimeFormatter.ofPattern(timePattern)),
                    new String[]{"MONDAY", "FRIDAY"}
            )
    );
    protected DoctorRegisterDTO doctorRegisterDTO3 = new DoctorRegisterDTO(
            "проверка",
            "проверка",
            null,
            "test",
            "12345678",
            room1.getName(),
            10,
            medicalSpeciality.getName(),
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern)),
            LocalDate.now().plusDays(8).format(DateTimeFormatter.ofPattern(datePattern)),
            new DaySchedule[]{
                    new DaySchedule(
                            "MONDAY",
                            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                            LocalTime.NOON.plusMinutes(200).format(DateTimeFormatter.ofPattern(timePattern))
                    ),
                    new DaySchedule(
                            "FRIDAY",
                            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                            LocalTime.NOON.plusMinutes(200).format(DateTimeFormatter.ofPattern(timePattern))
                    ),
            },
            null
    );
    protected ScheduleUpdateDTO scheduleUpdateDTO = new ScheduleUpdateDTO(
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern)),
            LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern(datePattern)),
            new DaySchedule[]{
                    new DaySchedule(
                            "MONDAY",
                            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                            LocalTime.now().plusMinutes(220).format(DateTimeFormatter.ofPattern(timePattern))
                    ),
                    new DaySchedule(
                            "FRIDAY",
                            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
                            LocalTime.NOON.plusMinutes(220).format(DateTimeFormatter.ofPattern(timePattern))
                    ),
            },
            null
    );
    protected PatientRegisterDTO patient = new PatientRegisterDTO(
            "проверка",
            "проверка",
            null,
            "testPatient",
            "12345678",
            "test",
            "89999999999",
            "test"
    );
    protected PatientUpdateDTO patientUpdateDTO = new PatientUpdateDTO(
            "проверка",
            "проверка",
            "проверка",
            "test",
            "test1",
            "89999999999",
            "12345678",
            "87654321"
    );
    protected Administrator administrator = new Administrator(
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            UserType.ADMINISTRATOR
    );
    protected AdministratorRegisterDTO administratorDTO = new AdministratorRegisterDTO(
            "проверка",
            "проверка",
            "проверка",
            "testAdmin",
            "12345678",
            "test administrator"
    );
    protected AdministratorUpdateDTO administratorUpdateDTO = new AdministratorUpdateDTO(
            "изменено",
            "изменено",
            "изменено",
            "изменено",
            "12345678",
            "87654321"
    );
    protected TicketRequest ticketRequest = new TicketRequest(
            0,
            null,
            LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).format(DateTimeFormatter.ofPattern(datePattern)),
            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern))
    );
    protected CommissionRequest commissionRequest = new CommissionRequest(
            0,
            new Integer[]{},
            room1.getName(),
            LocalDate.now().format(DateTimeFormatter.ofPattern(datePattern)),
            LocalTime.NOON.format(DateTimeFormatter.ofPattern(timePattern)),
            5
    );
}
