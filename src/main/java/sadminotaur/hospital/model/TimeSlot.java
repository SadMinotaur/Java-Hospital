package sadminotaur.hospital.model;

import sadminotaur.hospital.model.enums.TimeSlotState;

import java.time.LocalTime;
import java.util.Objects;

public class TimeSlot {

    private String ticket;
    private Patient patient;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private TimeSlotState timeSlotState = TimeSlotState.EMPTY;

    //    for batch insert. Bad solution
    private int scheduleId;

    public TimeSlot(String ticket, Patient patient, LocalTime timeStart, LocalTime timeEnd) {
        this.ticket = ticket;
        this.patient = patient;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public TimeSlot(String ticket, LocalTime timeStart, LocalTime timeEnd) {
        this.ticket = ticket;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public TimeSlot() {
    }

    public TimeSlotState getTimeSlotState() {
        return timeSlotState;
    }

    public void setTimeSlotState(TimeSlotState timeSlotState) {
        this.timeSlotState = timeSlotState;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return getScheduleId() == timeSlot.getScheduleId() &&
                Objects.equals(getTicket(), timeSlot.getTicket()) &&
                Objects.equals(getPatient(), timeSlot.getPatient()) &&
                Objects.equals(getTimeStart().withNano(0), timeSlot.getTimeStart().withNano(0)) &&
                Objects.equals(getTimeEnd().withNano(0), timeSlot.getTimeEnd().withNano(0)) &&
                getTimeSlotState() == timeSlot.getTimeSlotState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicket(), getPatient(), getTimeStart(), getTimeEnd(), getTimeSlotState(), getScheduleId());
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "ticket='" + ticket + '\'' +
                ", patient=" + patient +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", timeSlotState=" + timeSlotState +
                ", scheduleId=" + scheduleId +
                '}';
    }
}
