package sadminotaur.hospital.service;

import sadminotaur.hospital.dao.*;
import sadminotaur.hospital.model.*;
import sadminotaur.hospital.requests.CommissionRequest;
import sadminotaur.hospital.response.CommissionResponse;
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
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class CommissionService {

    @Autowired
    private CommissionDao commissionDao;
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private TimeSlotDao timeSlotDao;
    @Autowired
    private PatientDao patientDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommissionService.class);
    private final static String datePattern = "yyyy-MM-dd";
    private final static String timePattern = "HH:mm:ss";

    public ResponseEntity<CommissionResponse> register(CommissionRequest commissionRequest, Cookie cookie) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.DOCTOR) {
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        Patient patient = patientDao.getById(commissionRequest.getPatientId());
        if (patient == null) {
            throw new ServiceException(ServiceErrorCode.PATIENT_ARE_NOT_EXIST);
        }
        Set<Integer> doctorsIds = new HashSet<>(Arrays.asList(commissionRequest.getDoctorIds()));
        doctorsIds.add(session.getUser().getId());
        List<Doctor> doctors = doctorDao.getDoctors(doctorsIds);
        Room room = null;
        for (Doctor doctor : doctors) {
            if (doctor.getRoom().getName().equals(commissionRequest.getRoom())) {
                room = doctor.getRoom();
                break;
            }
        }
        if (room == null) {
            throw new ServiceException(ServiceErrorCode.ROOM_NOT_EXIST);
        }
        LocalTime timeStartReq;
        LocalTime timeEndReq;
        LocalDate dateReq;
        try {
            timeStartReq = LocalTime.parse(commissionRequest.getTime());
            timeEndReq = timeStartReq.plusMinutes(commissionRequest.getDuration());
            dateReq = LocalDate.parse(commissionRequest.getDate());
        } catch (DateTimeParseException e) {
            throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
        }
        List<TimeSlot> timeSlotsToUpdate = new ArrayList<>();
        for (Doctor doctor : doctors) {
            int scheduleOrder = dateReq.compareTo(doctor.getSchedule().get(0).getDate());
            if (scheduleOrder >= 0) {
                Schedule schedule = doctor.getSchedule().get(scheduleOrder);
                for (TimeSlot timeSlot : schedule.getTimeSlots()) {
                    if (isBetweenTime(timeEndReq, timeSlot.getTimeStart(), timeSlot.getTimeEnd()) ||
                            isBetweenTime(timeStartReq, timeSlot.getTimeStart(), timeSlot.getTimeEnd())
                    ) {
                        timeSlotsToUpdate.add(timeSlot);
                    }
                }
            } else {
                throw new ServiceException(ServiceErrorCode.WRONG_REQUEST_PARAMETERS);
            }
        }
        timeSlotDao.updateBusySlots(timeSlotsToUpdate, TimeSlotState.BUSY);
        Commission commission = new Commission(
                generateTicket(doctors, timeStartReq, dateReq),
                patient,
                room,
                dateReq,
                timeStartReq,
                timeEndReq
        );
        commission.setDoctors(doctors);
        commissionDao.add(commission, commission.getDoctors());
        LOGGER.info("Sending ticket " + commission.getTicket() + " info on email and phone");
        return new ResponseEntity<>(
                new CommissionResponse(
                        commission.getTicket(),
                        commission.getPatient().getId(),
                        doctorsIds.toArray(new Integer[0]),
                        commission.getRoom().getName(),
                        commission.getDate().format(DateTimeFormatter.ofPattern(datePattern)),
                        commission.getTimeStart().format(DateTimeFormatter.ofPattern(timePattern)),
                        commissionRequest.getDuration()
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Void> delete(Cookie cookie, String ticket) throws ServiceException {
        Session session = sessionDao.getByCookie(cookie.getValue());
        if (session == null) {
            throw new ServiceException(ServiceErrorCode.WRONG_COOKIE);
        }
        if (session.getUser().getUserType() != UserType.PATIENT) {
            throw new ServiceException(ServiceErrorCode.WRONG_USER);
        }
        Commission commission = commissionDao.getByTicket(ticket);
        List<TimeSlot> timeSlotsToUpdate = new ArrayList<>();
        for (Doctor doctor : commission.getDoctors()) {
            int scheduleOrder = commission.getDate().compareTo(doctor.getSchedule().get(0).getDate());
            if (scheduleOrder >= 0) {
                Schedule neededSchedule = doctor.getSchedule().get(scheduleOrder);
                for (TimeSlot timeSlot : neededSchedule.getTimeSlots()) {
                    if (isBetweenTime(commission.getTimeEnd(), timeSlot.getTimeStart(), timeSlot.getTimeEnd()) ||
                            isBetweenTime(commission.getTimeStart(), timeSlot.getTimeStart(), timeSlot.getTimeEnd())
                    ) {
                        timeSlotsToUpdate.add(timeSlot);
                    }
                }
            }
        }
        commissionDao.deleteByTicket(ticket, timeSlotsToUpdate);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    private boolean isBetweenTime(LocalTime time, LocalTime start, LocalTime end) {
        return time.isAfter(start) && time.isBefore(end);
    }

    private String generateTicket(List<Doctor> doctors, LocalTime localTime, LocalDate date) {
        StringBuilder stringBuilder = new StringBuilder("D");
        for (Doctor doctor : doctors) {
            stringBuilder.append("D").append(doctor.getId());
        }
        stringBuilder.append(date.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        stringBuilder.append(localTime.format(DateTimeFormatter.ofPattern("HHmm")));
        return stringBuilder.toString();
    }
}
