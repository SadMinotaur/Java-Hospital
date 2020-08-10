package sadminotaur.hospital.requests;

import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.validation.DoctorValidation;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

@DoctorValidation
public class DoctorRegisterDTO extends UserRegisterDTO {

    private final UserType userType = UserType.DOCTOR;
    @NotNull
    private String room;
    private int duration;
    @NotNull
    private String speciality;
    @NotNull
    private String dateStart;
    @NotNull
    private String dateEnd;
    private DaySchedule[] weekDaysSchedule;
    private WeekSchedule weekSchedule;

    public DoctorRegisterDTO() {
    }

    public DoctorRegisterDTO(String firstname, String lastname, String patronymic, String login, String password, String room, int duration, String speciality, String dateStart, String dateEnd, DaySchedule[] weekDaysSchedule, WeekSchedule weekSchedule) {
        super(firstname, lastname, patronymic, login, password);
        this.room = room;
        this.duration = duration;
        this.speciality = speciality;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedule = weekDaysSchedule;
        this.weekSchedule = weekSchedule;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
        if (!(o instanceof DoctorRegisterDTO)) return false;
        if (!super.equals(o)) return false;
        DoctorRegisterDTO that = (DoctorRegisterDTO) o;
        return getDuration() == that.getDuration() &&
                userType == that.userType &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Objects.equals(getSpeciality(), that.getSpeciality()) &&
                Objects.equals(getDateStart(), that.getDateStart()) &&
                Objects.equals(getDateEnd(), that.getDateEnd()) &&
                Arrays.equals(getWeekDaysSchedule(), that.getWeekDaysSchedule()) &&
                Objects.equals(getWeekSchedule(), that.getWeekSchedule());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), userType, getRoom(), getDuration(), getSpeciality(), getDateStart(), getDateEnd(), getWeekSchedule());
        result = 31 * result + Arrays.hashCode(getWeekDaysSchedule());
        return result;
    }

    @Override
    public String toString() {
        return "DoctorRegisterDTO{" +
                "room='" + room + '\'' +
                ", duration=" + duration +
                ", speciality='" + speciality + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", weekDaysSchedule=" + Arrays.toString(weekDaysSchedule) +
                ", weekSchedule=" + weekSchedule +
                "} " + super.toString();
    }
}
