package sadminotaur.hospital.service;

import sadminotaur.hospital.requests.DeleteDoctorDTO;
import sadminotaur.hospital.requests.DoctorRegisterDTO;
import sadminotaur.hospital.requests.ScheduleUpdateDTO;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.model.enums.TimeSlotState;
import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.model.Schedule;
import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.dao.MedicalSpecialityDao;
import sadminotaur.hospital.dao.RoomDao;
import sadminotaur.hospital.dao.TimeSlotDao;
import sadminotaur.hospital.dao.PatientDao;
import sadminotaur.hospital.mappersdto.DoctorDTOMapper;
import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.dao.ScheduleDao;
import sadminotaur.hospital.dao.DoctorDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorService {

    private static final String COOKIE = "Cookie";
    private static final String JAVASESESSIONID = "JAVASESSIONID";

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private MedicalSpecialityDao medicalSpecialityDao;
    @Autowired
    private TimeSlotDao timeSlotDao;


    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorService.class);

    public ResponseEntity<DoctorResponse> register(DoctorRegisterDTO doctorRegisterDTO, Cookie cookie) throws ServiceException {
        if (sessionDao.getByCookie(cookie.getValue()) == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        Room room = roomDao.getByName(doctorRegisterDTO.getRoom());
        if (room == null) {
            throw new ServiceException(ServiceErrorCode.ROOM_NOT_EXIST);
        }
        MedicalSpeciality medicalSpeciality = medicalSpecialityDao.getByName(doctorRegisterDTO.getSpeciality());
        if (medicalSpeciality == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_SPECIALITY);
        }
        Doctor doctor = DoctorDTOMapper.doctorDTOtoDoctor(doctorRegisterDTO, medicalSpeciality, room);
        doctorDao.add(doctor);
        return new ResponseEntity<>(
                DoctorDTOMapper.doctorRegisterResponse(doctor),
                HttpStatus.OK
        );
    }

    public ResponseEntity<DoctorResponse> getDoctor(Cookie cookie, String schedule, String startDate, String endDate, int id) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        Doctor doctor = doctorDao.getById(id);
        boolean sch = schedule.equals("yes");
        try {
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
            return new ResponseEntity<>(
                    DoctorDTOMapper.doctorResponse(
                            doctor,
                            sch,
                            start,
                            end,
                            session.getUser().getUserType() == UserType.PATIENT ? patientDao.getById(session.getUser().getId()) : null
                    ),
                    HttpStatus.OK
            );
        } catch (DateTimeParseException e) {
            throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
        }
    }

    public ResponseEntity<List<DoctorResponse>> getDoctors(Cookie cookie, String schedule, String startDate, String endDate, String speciality) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        MedicalSpeciality medicalSpeciality = medicalSpecialityDao.getByName(speciality);
        List<Doctor> doctors;
        if (medicalSpeciality != null) {
            doctors = doctorDao.getDoctors(medicalSpeciality.getId());
        } else {
            doctors = doctorDao.getDoctors();
        }
        boolean sch = schedule.equals("yes");
        try {
            LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
            LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;
            List<DoctorResponse> responseEntities = new ArrayList<>();
            for (Doctor doctor : doctors) {
                responseEntities.add(DoctorDTOMapper.doctorResponse(
                        doctor,
                        sch,
                        start,
                        end,
                        session.getUser().getUserType() == UserType.PATIENT ? patientDao.getById(session.getUser().getId()) : null
                ));
            }
            return new ResponseEntity<>(
                    responseEntities,
                    HttpStatus.OK
            );
        } catch (DateTimeParseException e) {
            throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
        }
    }

    public ResponseEntity<Void> deleteDoctor(Cookie cookie, int id, DeleteDoctorDTO deleteDoctorDTO) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null || session.getUser().getUserType() != UserType.ADMINISTRATOR) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        Doctor doctor = doctorDao.getById(id);
        try {
            LocalDate dateReq = LocalDate.parse(deleteDoctorDTO.getDate());
            if (doctor == null || doctor.getSchedule().stream().anyMatch(schedule ->
                    schedule.getDate().isAfter(dateReq) &&
                            schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                                    timeSlot.getTimeSlotState() == TimeSlotState.BUSY)
            )) {
                throw new ServiceException(ServiceErrorCode.NOT_EMPTY);
            }
            doctorDao.deleteById(id);
            scheduleDao.deleteAfterDate(dateReq, session.getUser().getId());
            for (Schedule schedule : doctor.getSchedule()) {
                schedule.getTimeSlots().forEach(timeSlot -> {
                    if (timeSlot.getTimeSlotState() == TimeSlotState.BUSY) {
                        LOGGER.info("Notifying " + timeSlot.getPatient().getFirstname() + " " +
                                timeSlot.getPatient().getLastname() + " about doctor firing");
                    }
                });
            }
        } catch (DateTimeParseException e) {
            throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
        }
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    public ResponseEntity<DoctorResponse> updateSchedule(ScheduleUpdateDTO scheduleUpdateDTO, Cookie cookie, int id) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null || session.getUser().getUserType() != UserType.ADMINISTRATOR) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        Doctor doctorFromBase = doctorDao.getById(id);
        if (doctorFromBase.getSchedule().stream().anyMatch(schedule ->
                schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                        timeSlot.getTimeSlotState() == TimeSlotState.BUSY))) {
            throw new ServiceException(ServiceErrorCode.TIMESLOT_BUSY);
        }
        LocalDate start = LocalDate.parse(scheduleUpdateDTO.getDateStart());
        LocalDate end = LocalDate.parse(scheduleUpdateDTO.getDateEnd());
        doctorFromBase.getSchedule().removeIf(schedule ->
                isInDateRange(start, end, schedule.getDate())
        );
        Doctor doctorUpdated = DoctorDTOMapper.convertSchedule(
                DoctorDTOMapper.convertScheduleUpdate(scheduleUpdateDTO),
                doctorFromBase
        );
        List<Schedule> schedules = doctorUpdated.getSchedule().stream().filter(schedule ->
                isInDateRange(start, end, schedule.getDate())
        ).collect(Collectors.toList());
        scheduleDao.deleteBetweenDates(start, end, doctorUpdated.getId());
        scheduleDao.insertSchedule(schedules, doctorUpdated);
        return new ResponseEntity<>(
                DoctorDTOMapper.doctorRegisterResponse(doctorUpdated),
                HttpStatus.OK
        );
    }

    private boolean isInDateRange(LocalDate start, LocalDate end, LocalDate compare) {
        return compare.compareTo(start) >= 0 && compare.compareTo(end) <= 0;
    }
}