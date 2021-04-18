package sadminotaur.hospital.service;

import sadminotaur.hospital.dao.*;
import sadminotaur.hospital.model.*;
import sadminotaur.hospital.requests.TicketRequest;
import sadminotaur.hospital.response.AllTicketsResponse;
import sadminotaur.hospital.response.CommissionArray;
import sadminotaur.hospital.response.CommissionDoctor;
import sadminotaur.hospital.response.TicketResponse;
import sadminotaur.hospital.model.enums.TimeSlotState;
import sadminotaur.hospital.model.enums.UserType;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TicketService {

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private TimeSlotDao timeSlotDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private MedicalSpecialityDao medicalSpecialityDao;
    @Autowired
    private CommissionDao commissionDao;
    @Autowired
    private ScheduleDao scheduleDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);
    protected final static String datePattern = "yyyy-MM-dd";
    protected final static String timePattern = "HH:mm:ss";

    public ResponseEntity<TicketResponse> registerTicket(Cookie cookie, TicketRequest ticketRequest) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.PATIENT) {
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        LocalDate dateFromReq = LocalDate.parse(ticketRequest.getDate());
        if (dateFromReq.isAfter(LocalDate.now().plusMonths(2))) {
            throw new ServiceException(ServiceErrorCode.TOO_EARLY);
        }
        LocalTime timeFromReq = LocalTime.parse(ticketRequest.getTime());
        Doctor doctorFromBase;
        if (ticketRequest.getDoctorId() != 0) {
            doctorFromBase = doctorDao.getById(ticketRequest.getDoctorId());
            if (doctorFromBase == null) {
                throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
            }
            if (doctorFromBase.getUserType() != UserType.DOCTOR) {
                throw new ServiceException(ServiceErrorCode.WRONG_USER);
            }
        } else {
            MedicalSpeciality medicalSpeciality = medicalSpecialityDao.getByName(ticketRequest.getSpeciality());
            if (medicalSpeciality == null) {
                throw new ServiceException(ServiceErrorCode.WRONG_SPECIALITY);
            }
            List<Doctor> doctors = doctorDao.getDoctors(medicalSpeciality.getId());
            Map<Doctor, List<TimeSlot>> matchedDoctors = new HashMap<>();
            for (Doctor doctor : doctors) {
                List<Schedule> schedules = doctor.getSchedule().stream().filter(schedule ->
                        schedule.getDate().isEqual(dateFromReq)
                ).collect(Collectors.toList());
                if (!schedules.isEmpty()) {
                    List<TimeSlot> timeSlots = new ArrayList<>();
                    for (Schedule schedule : schedules) {
                        timeSlots.addAll(schedule.getTimeSlots().stream().filter(timeSlot ->
                                timeSlot.getTimeStart().withNano(0).equals(timeFromReq.withNano(0)) &&
                                        timeSlot.getTimeSlotState() == TimeSlotState.EMPTY
                        ).collect(Collectors.toList()));
                    }
                    if (!timeSlots.isEmpty()) {
                        matchedDoctors.put(doctor, timeSlots);
                    }
                }
            }
            if (matchedDoctors.size() == 0) {
                throw new ServiceException(ServiceErrorCode.DOCTORS_NOT_EXIST);
            }
            doctorFromBase = new ArrayList<>(matchedDoctors.keySet()).get(new Random().nextInt(matchedDoctors.size()));
        }
        TimeSlot timeS = null;
        for (Schedule schedule : doctorFromBase.getSchedule()) {
            if (schedule.getDate().isEqual(dateFromReq)) {
                if (schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                        timeSlot.getPatient() != null &&
                                timeSlot.getPatient().getId() == session.getUser().getId()
                )) {
                    throw new ServiceException(ServiceErrorCode.DAY_BUSY);
                }
                for (TimeSlot timeSlot : schedule.getTimeSlots()) {
                    if (timeSlot.getTimeStart().withNano(0).compareTo(timeFromReq.withNano(0)) == 0) {
                        timeSlot.setPatient(patientDao.getById(session.getUser().getId()));
                        timeSlot.setTimeSlotState(TimeSlotState.BUSY);
                        timeS = timeSlot;
                        timeSlotDao.update(timeSlot);
                        break;
                    }
                }
                break;
            }
        }
        if (timeS == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_DATE);
        }
        LOGGER.info("Sending ticket " + timeS.getTicket() + " info on email and phone");
        return new ResponseEntity<>(
                new TicketResponse(
                        timeS.getTicket(),
                        doctorFromBase.getId(),
                        doctorFromBase.getFirstname(),
                        doctorFromBase.getLastname(),
                        doctorFromBase.getPatronymic(),
                        doctorFromBase.getMedicalSpeciality().getName(),
                        doctorFromBase.getRoom().getName(),
                        ticketRequest.getDate(),
                        ticketRequest.getTime()
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Void> cancelTimeslot(Cookie cookie, String timeslot) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() == UserType.DOCTOR &&
                doctorDao.getById(session.getUser().getId()).getSchedule().stream().anyMatch(schedule ->
                        schedule.getTimeSlots().stream().anyMatch(timeSlot ->
                                timeSlot.getTicket().equals(timeslot)))
        ) {
            throw new ServiceException(ServiceErrorCode.TIMESLOT_NOT_EXIST);
        }
        TimeSlot timeS = timeSlotDao.getByTicket(timeslot);
        if (session.getUser().getUserType() == UserType.PATIENT && timeS.getPatient().getId() != session.getUser().getId()) {
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        timeS.setPatient(null);
        timeS.setTimeSlotState(TimeSlotState.EMPTY);
        timeSlotDao.update(timeS);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    public ResponseEntity<AllTicketsResponse> getTickets(Cookie cookie) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.PATIENT) {
            throw new ServiceException(ServiceErrorCode.USER_NOT_EXIST);
        }
        int patientId = session.getUser().getId();
        List<Doctor> doctors = doctorDao.getDoctorsByPatientId(patientId);
        Map<TimeSlot, Doctor> matchedSlots = new HashMap<>();
        Map<TimeSlot, Schedule> matchedSch = new HashMap<>();
        for (Doctor doctor : doctors) {
            for (Schedule schedule : doctor.getSchedule()) {
                for (TimeSlot timeSlot : schedule.getTimeSlots()) {
                    if (timeSlot.getPatient() != null && timeSlot.getPatient().getId() == session.getUser().getId()) {
                        matchedSlots.put(timeSlot, doctor);
                        matchedSch.put(timeSlot, schedule);
                    }
                }
            }
        }
        List<Commission> commissions = commissionDao.getByPatient(patientId);
        List<TicketResponse> ticketResponses = new ArrayList<>();
        CommissionArray[] commissionArrays = new CommissionArray[commissions.size()];
        for (TimeSlot timeSlot : matchedSlots.keySet()) {
            Doctor doctor = matchedSlots.get(timeSlot);
            ticketResponses.add(new TicketResponse(
                    timeSlot.getTicket(),
                    doctor.getId(),
                    doctor.getFirstname(),
                    doctor.getLastname(),
                    doctor.getPatronymic(),
                    doctor.getMedicalSpeciality().getName(),
                    doctor.getRoom().getName(),
                    matchedSch.get(timeSlot).getDate().format(DateTimeFormatter.ofPattern(datePattern)),
                    timeSlot.getTimeStart().format(DateTimeFormatter.ofPattern(timePattern))
            ));
        }
        for (int i = 0; i < commissionArrays.length; i++) {
            Commission commission = commissions.get(i);
            List<CommissionDoctor> commissionDoctors = new ArrayList<>();
            for (Doctor doctor : commission.getDoctors()) {
                commissionDoctors.add(new CommissionDoctor(
                        doctor.getId(),
                        doctor.getFirstname(),
                        doctor.getLastname(),
                        doctor.getPatronymic(),
                        doctor.getMedicalSpeciality().getName()
                ));
            }
            commissionArrays[i] = new CommissionArray(
                    commission.getTicket(),
                    commission.getRoom().getName(),
                    commission.getDate().format(DateTimeFormatter.ofPattern(datePattern)),
                    commission.getTimeStart().format(DateTimeFormatter.ofPattern(timePattern)),
                    commissionDoctors.toArray(new CommissionDoctor[0]),
                    commission.getPatient().getFirstname(),
                    commission.getPatient().getFirstname(),
                    commission.getPatient().getFirstname()
            );
        }
        return new ResponseEntity<>(
                new AllTicketsResponse(
                        ticketResponses.toArray(new TicketResponse[0]),
                        commissionArrays
                ),
                HttpStatus.OK
        );
    }
}
