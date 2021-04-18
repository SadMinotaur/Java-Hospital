package sadminotaur.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Commission {

    private int id;
    private String ticket;
    private Patient patient;
    private Room room;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<Doctor> doctors;

    public Commission(int id, String ticket, Patient patient, Room room, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this.id = id;
        this.ticket = ticket;
        this.patient = patient;
        this.room = room;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        doctors = new ArrayList<>();
    }

    public Commission(String ticket, Patient patient, Room room, LocalDate date, LocalTime timeStart, LocalTime timeEnd) {
        this(
                0,
                ticket,
                patient,
                room,
                date,
                timeStart,
                timeEnd
        );
    }

    public Commission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commission)) return false;
        Commission that = (Commission) o;
        return getId() == that.getId() &&
                Objects.equals(getTicket(), that.getTicket()) &&
                Objects.equals(getPatient(), that.getPatient()) &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTimeStart().withNano(0), that.getTimeStart().withNano(0)) &&
                Objects.equals(getTimeEnd().withNano(0), that.getTimeEnd().withNano(0)) &&
                Objects.equals(getDoctors(), that.getDoctors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getPatient(), getRoom(), getDate(), getTimeStart(), getTimeEnd(), getDoctors());
    }

    @Override
    public String toString() {
        return "Commission{" +
                "id=" + id +
                ", ticket='" + ticket + '\'' +
                ", patient=" + patient +
                ", room=" + room +
                ", date=" + date +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", doctors=" + doctors +
                '}';
    }
}
