package sadminotaur.hospital.requests;

import sadminotaur.hospital.validation.TicketValidation;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@TicketValidation
public class TicketRequest {

    private int doctorId;
    private String speciality;
    @NotNull
    private String date;
    @NotNull
    private String time;

    public TicketRequest() {
    }

    public TicketRequest(int doctorId, String speciality, @NotNull String date, @NotNull String time) {
        this.doctorId = doctorId;
        this.speciality = speciality;
        this.date = date;
        this.time = time;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketRequest)) return false;
        TicketRequest that = (TicketRequest) o;
        return Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(speciality, that.speciality) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, speciality, date, time);
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "doctorId='" + doctorId + '\'' +
                ", speciality='" + speciality + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
