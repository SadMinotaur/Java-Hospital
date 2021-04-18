package sadminotaur.hospital.response.statistic;

import java.util.Objects;

public class TotalUserCount {

    private int totalCount;
    private int doctorsCount;
    private int patientCount;

    public TotalUserCount() {
    }

    public TotalUserCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getDoctorsCount() {
        return doctorsCount;
    }

    public void setDoctorsCount(int doctorsCount) {
        this.doctorsCount = doctorsCount;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TotalUserCount)) return false;
        TotalUserCount that = (TotalUserCount) o;
        return getTotalCount() == that.getTotalCount() &&
                getDoctorsCount() == that.getDoctorsCount() &&
                getPatientCount() == that.getPatientCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotalCount(), getDoctorsCount(), getPatientCount());
    }

    @Override
    public String toString() {
        return "TotalCount{" +
                "totalCount=" + totalCount +
                ", doctorsCount=" + doctorsCount +
                ", patientCount=" + patientCount +
                '}';
    }
}
