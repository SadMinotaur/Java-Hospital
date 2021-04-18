package sadminotaur.hospital.response;

import java.util.Objects;

public class RDaySchedule {

    private String time;
    private PatientResponse patientResponse;

    public RDaySchedule() {
    }

    public RDaySchedule(String time, PatientResponse patientResponse) {
        this.time = time;
        this.patientResponse = patientResponse;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PatientResponse getPatientResponse() {
        return patientResponse;
    }

    public void setPatientResponse(PatientResponse patientResponse) {
        this.patientResponse = patientResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RDaySchedule)) return false;
        RDaySchedule that = (RDaySchedule) o;
        return Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getPatientResponse(), that.getPatientResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getPatientResponse());
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "time='" + time + '\'' +
                ", patientResponse=" + patientResponse +
                '}';
    }
}
