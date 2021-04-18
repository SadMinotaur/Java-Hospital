package sadminotaur.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Schedule {

    private int id;
    private LocalDate date;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
    private List<TimeSlot> timeSlots;

    //    for batch insert. Bad solution
    private int doctorId;

    public Schedule(int id, Doctor doctor, LocalDate date, LocalTime workingHoursStart, LocalTime workingHoursEnd) {
        this.id = id;
        this.date = date;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        timeSlots = new ArrayList<>();
    }

    public Schedule(Doctor doctor, LocalDate date, LocalTime workingHoursStart, LocalTime workingHoursEnd) {
        this(
                0,
                doctor,
                date,
                workingHoursStart,
                workingHoursEnd
        );
    }

    public Schedule() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getDate(), schedule.getDate()) &&
                Objects.equals(getWorkingHoursStart().withNano(0), schedule.getWorkingHoursStart().withNano(0)) &&
                Objects.equals(getWorkingHoursEnd().withNano(0), schedule.getWorkingHoursEnd().withNano(0)) &&
                Objects.equals(getTimeSlots(), schedule.getTimeSlots());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getWorkingHoursStart(), getWorkingHoursEnd(), getTimeSlots());
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", workingHoursStart=" + workingHoursStart +
                ", workingHoursEnd=" + workingHoursEnd +
                ", timeSlotSet=" + timeSlots +
                '}';
    }
}
