package sadminotaur.hospital.response.statistic;

import java.util.Objects;

public class PatientCount {

    private int patientCount;

    public PatientCount() {
    }

    public PatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientCount)) return false;
        PatientCount that = (PatientCount) o;
        return getPatientCount() == that.getPatientCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPatientCount());
    }

    @Override
    public String toString() {
        return "PatientCount{" +
                "patientCount=" + patientCount +
                '}';
    }
}
