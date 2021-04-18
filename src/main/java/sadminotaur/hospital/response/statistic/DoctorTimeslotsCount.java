package sadminotaur.hospital.response.statistic;

import java.util.Objects;

public class DoctorTimeslotsCount {

    private int selectedDocBusyTCount;
    private int doctorsBusyCount;

    public DoctorTimeslotsCount() {
    }

    public DoctorTimeslotsCount(int doctorsBusyCount) {
        this.doctorsBusyCount = doctorsBusyCount;
    }

    public int getSelectedDocBusyTCount() {
        return selectedDocBusyTCount;
    }

    public void setSelectedDocBusyTCount(int selectedDocBusyTCount) {
        this.selectedDocBusyTCount = selectedDocBusyTCount;
    }

    public int getDoctorsBusyCount() {
        return doctorsBusyCount;
    }

    public void setDoctorsBusyCount(int doctorsBusyCount) {
        this.doctorsBusyCount = doctorsBusyCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorTimeslotsCount)) return false;
        DoctorTimeslotsCount that = (DoctorTimeslotsCount) o;
        return getSelectedDocBusyTCount() == that.getSelectedDocBusyTCount() &&
                getDoctorsBusyCount() == that.getDoctorsBusyCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSelectedDocBusyTCount(), getDoctorsBusyCount());
    }

    @Override
    public String toString() {
        return "TimeslotsCount{" +
                "selectedDocBusyTCount=" + selectedDocBusyTCount +
                ", doctorsBusyCount=" + doctorsBusyCount +
                '}';
    }
}
