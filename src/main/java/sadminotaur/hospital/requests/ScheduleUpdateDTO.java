package sadminotaur.hospital.requests;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

public class ScheduleUpdateDTO {

    @NotNull
    private String dateStart;
    @NotNull
    private String dateEnd;
    private DaySchedule[] weekDaysSchedule;
    private WeekSchedule weekSchedule;

    public ScheduleUpdateDTO() {
    }

    public ScheduleUpdateDTO(String dateStart, String dateEnd, DaySchedule[] weekDaysSchedule, WeekSchedule weekSchedule) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedule = weekDaysSchedule;
        this.weekSchedule = weekSchedule;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public DaySchedule[] getWeekDaysSchedule() {
        return weekDaysSchedule;
    }

    public void setWeekDaysSchedule(DaySchedule[] weekDaysSchedule) {
        this.weekDaysSchedule = weekDaysSchedule;
    }

    public WeekSchedule getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekSchedule weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleUpdateDTO)) return false;
        ScheduleUpdateDTO that = (ScheduleUpdateDTO) o;
        return Objects.equals(getDateStart(), that.getDateStart()) &&
                Objects.equals(getDateEnd(), that.getDateEnd()) &&
                Arrays.equals(getWeekDaysSchedule(), that.getWeekDaysSchedule()) &&
                Objects.equals(getWeekSchedule(), that.getWeekSchedule());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getDateStart(), getDateEnd(), getWeekSchedule());
        result = 31 * result + Arrays.hashCode(getWeekDaysSchedule());
        return result;
    }

    @Override
    public String toString() {
        return "ScheduleUpdateDTO{" +
                "dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", weekDaysSchedule=" + Arrays.toString(weekDaysSchedule) +
                ", weekSchedule=" + weekSchedule +
                '}';
    }
}
