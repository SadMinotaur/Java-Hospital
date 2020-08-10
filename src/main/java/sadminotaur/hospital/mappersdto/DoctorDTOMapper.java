package sadminotaur.hospital.mappersdto;

import sadminotaur.hospital.requests.DaySchedule;
import sadminotaur.hospital.requests.DoctorRegisterDTO;
import sadminotaur.hospital.requests.ScheduleUpdateDTO;
import sadminotaur.hospital.requests.WeekSchedule;
import sadminotaur.hospital.response.DoctorResponse;
import sadminotaur.hospital.response.RDaySchedule;
import sadminotaur.hospital.response.RSchedule;
import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.model.Schedule;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.model.Patient;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DoctorDTOMapper {

    private final static PatientDTOMapper patientMapper = Mappers.getMapper(PatientDTOMapper.class);
    private final static String datePattern = "yyyy-MM-dd";
    protected final static String timePattern = "HH:mm:ss";
    private final static String[] workingDays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};

    public static Doctor doctorDTOtoDoctor(DoctorRegisterDTO doctorRegisterDTO, MedicalSpeciality medicalSpeciality, Room room) {
        return convertSchedule(doctorRegisterDTO, dtoToDoctor(doctorRegisterDTO, medicalSpeciality, room));
    }

    public static DoctorResponse doctorRegisterResponse(Doctor doctor) {
        List<Schedule> doctorSchedule = doctor.getSchedule();
        RSchedule[] schedule = new RSchedule[doctorSchedule.size()];
        for (int i = 0; i < doctorSchedule.size(); i++) {
            schedule[i] = new RSchedule(
                    doctorSchedule.get(i).getDate().format(DateTimeFormatter.ofPattern(datePattern))
            );
        }
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getPatronymic(),
                doctor.getMedicalSpeciality().getName(),
                doctor.getRoom().getName(),
                schedule
        );
    }

    public static DoctorResponse doctorResponse(Doctor doctor, boolean sch, LocalDate startDate, LocalDate endDate, Patient patient) {
        if (!sch) {
            return new DoctorResponse(
                    doctor.getId(), doctor.getFirstname(), doctor.getLastname(),
                    doctor.getPatronymic(), doctor.getMedicalSpeciality().getName(),
                    doctor.getRoom().getName(), null
            );
        }
        List<Schedule> doctorSchedule = doctor.getSchedule();
        if (startDate == null && endDate != null) {
            doctorSchedule.removeIf(schedule -> schedule.getDate().isAfter(endDate));
        }
        if (startDate != null && endDate != null) {
            doctorSchedule.removeIf(schedule -> schedule.getDate().isAfter(endDate));
            doctorSchedule.removeIf(schedule -> schedule.getDate().isBefore(startDate));
        }
        if (startDate == null && endDate == null) {
            doctorSchedule.removeIf(schedule -> schedule.getDate().isAfter(LocalDate.now().plusMonths(2)));
        }
        RSchedule[] schedule = new RSchedule[doctorSchedule.size()];
        for (int i = 0; i < doctorSchedule.size(); i++) {
            List<TimeSlot> timeSlots = doctorSchedule.get(i).getTimeSlots();
            if (timeSlots != null) {
                RDaySchedule[] daySchedules = new RDaySchedule[timeSlots.size()];
                for (int j = 0; j < timeSlots.size(); j++) {
                    daySchedules[j] = new RDaySchedule(
                            timeSlots.get(j).getTimeStart().format(DateTimeFormatter.ofPattern(timePattern)),
                            patient != null && patient.getId() == timeSlots.get(j).getPatient().getId() ? patientMapper.patientToPatientResponse(patient) : null
                    );
                }
                schedule[i] = new RSchedule(
                        doctorSchedule.get(i).getDate().format(DateTimeFormatter.ofPattern(datePattern)),
                        daySchedules
                );
            }
        }
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getPatronymic(),
                doctor.getMedicalSpeciality().getName(),
                doctor.getRoom().getName(),
                schedule
        );
    }

    public static DoctorResponse responseWithoutSch(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getPatronymic(),
                doctor.getMedicalSpeciality().getName(),
                doctor.getRoom().getName(),
                null
        );
    }

    private static Doctor dtoToDoctor(DoctorRegisterDTO doctorRegisterDTO, MedicalSpeciality medicalSpeciality, Room room) {
        return new Doctor(
                doctorRegisterDTO.getFirstname(),
                doctorRegisterDTO.getLastname(),
                doctorRegisterDTO.getPatronymic(),
                doctorRegisterDTO.getLogin(),
                doctorRegisterDTO.getPassword(),
                room,
                doctorRegisterDTO.getDuration(),
                medicalSpeciality,
                doctorRegisterDTO.getUserType()
        );
    }

    public static Doctor convertSchedule(DoctorRegisterDTO doctorRegisterDTO, Doctor doctor) {
        List<LocalDate> datesBetween = IntStream.iterate(0, i -> i + 1)
                .limit(ChronoUnit.DAYS.between(
                        LocalDate.parse(doctorRegisterDTO.getDateStart()),
                        LocalDate.parse(doctorRegisterDTO.getDateEnd())))
                .mapToObj(i -> LocalDate.parse(doctorRegisterDTO.getDateStart()).plusDays(i))
                .collect(Collectors.toList());
        List<Schedule> schedule = doctor.getSchedule();
        DaySchedule[] weekDays = doctorRegisterDTO.getWeekDaysSchedule();
        if (weekDays != null && weekDays.length > 0) {
            Map<String, DaySchedule> dayScheduleMap = new HashMap<>();
            for (DaySchedule daySchedule : weekDays) {
                dayScheduleMap.put(daySchedule.getWeekDay(), daySchedule);
            }
            for (LocalDate d : datesBetween) {
                String day = d.getDayOfWeek().name();
                if (dayScheduleMap.containsKey(day)) {
                    schedule.add(new Schedule(
                            doctor,
                            d,
                            LocalTime.parse(dayScheduleMap.get(day).getTimeStart()),
                            LocalTime.parse(dayScheduleMap.get(day).getTimeEnd())
                    ));
                }
            }
        } else {
            WeekSchedule weekSchedule = doctorRegisterDTO.getWeekSchedule();
            if (weekSchedule.getWeekDays() != null && weekSchedule.getWeekDays().length > 0) {
                for (LocalDate d : datesBetween) {
                    if (Arrays.asList(weekSchedule.getWeekDays()).contains(d.getDayOfWeek().name())) {
                        schedule.add(new Schedule(
                                doctor,
                                d,
                                LocalTime.parse(weekSchedule.getTimeStart()),
                                LocalTime.parse(weekSchedule.getTimeEnd())
                        ));
                    }
                }
            } else {
                for (LocalDate d : datesBetween) {
                    if (Arrays.asList(workingDays).contains(d.getDayOfWeek().name())) {
                        schedule.add(new Schedule(
                                doctor,
                                d,
                                LocalTime.parse(weekSchedule.getTimeStart()),
                                LocalTime.parse(weekSchedule.getTimeEnd())
                        ));
                    }
                }
            }
        }
        return doctor;
    }

    public static DoctorRegisterDTO convertScheduleUpdate(ScheduleUpdateDTO scheduleUpdateDTO) {
        return new DoctorRegisterDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                scheduleUpdateDTO.getDateStart(),
                scheduleUpdateDTO.getDateEnd(),
                scheduleUpdateDTO.getWeekDaysSchedule(),
                scheduleUpdateDTO.getWeekSchedule()
        );
    }
}
