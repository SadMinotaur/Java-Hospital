package sadminotaur.hospital.requests;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

public class WeekSchedule {

    @NotNull
    private String timeStart;
    @NotNull
    private String timeEnd;
    @NotNull
    private String[] weekDays;

    public WeekSchedule() {
    }

    public WeekSchedule(String timeStart, String timeEnd, String[] weekDays) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.weekDays = weekDays;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String[] getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String[] weekDays) {
        this.weekDays = weekDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekSchedule)) return false;
        WeekSchedule that = (WeekSchedule) o;
        return Objects.equals(getTimeStart(), that.getTimeStart()) &&
                Objects.equals(getTimeEnd(), that.getTimeEnd()) &&
                Arrays.equals(getWeekDays(), that.getWeekDays());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getTimeStart(), getTimeEnd());
        result = 31 * result + Arrays.hashCode(getWeekDays());
        return result;
    }

    @Override
    public String toString() {
        return "WeekSchedule{" +
                "timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", weekDays=" + Arrays.toString(weekDays) +
                '}';
    }
}
