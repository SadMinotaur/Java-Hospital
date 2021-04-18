package sadminotaur.hospital.response;

import java.util.Arrays;
import java.util.Objects;

public class RSchedule {

    private String date;
    private RDaySchedule[] RDaySchedule;

    public RSchedule() {
    }

    public RSchedule(String date, RDaySchedule[] RDaySchedule) {
        this.date = date;
        this.RDaySchedule = RDaySchedule;
    }

    public RSchedule(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RDaySchedule[] getRDaySchedule() {
        return RDaySchedule;
    }

    public void setRDaySchedule(RDaySchedule[] RDaySchedule) {
        this.RDaySchedule = RDaySchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RSchedule)) return false;
        RSchedule schedule = (RSchedule) o;
        return Objects.equals(getDate(), schedule.getDate()) &&
                Arrays.equals(getRDaySchedule(), schedule.getRDaySchedule());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getDate());
        result = 31 * result + Arrays.hashCode(getRDaySchedule());
        return result;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "date='" + date + '\'' +
                ", daySchedule=" + Arrays.toString(RDaySchedule) +
                '}';
    }
}
