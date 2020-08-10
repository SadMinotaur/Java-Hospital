package sadminotaur.hospital.response;

import java.util.Arrays;
import java.util.Objects;

public class DoctorResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    private String room;
    private RSchedule[] RSchedule;

    public DoctorResponse() {
    }

    public DoctorResponse(int id, String firstName, String lastName, String patronymic, String speciality, String room, RSchedule[] RSchedule) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
        this.RSchedule = RSchedule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RSchedule[] getRSchedule() {
        return RSchedule;
    }

    public void setRSchedule(RSchedule[] RSchedule) {
        this.RSchedule = RSchedule;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorResponse)) return false;
        DoctorResponse that = (DoctorResponse) o;
        return getId() == that.getId() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getSpeciality(), that.getSpeciality()) &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Arrays.equals(getRSchedule(), that.getRSchedule());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getFirstName(), getLastName(), getPatronymic(), getSpeciality(), getRoom());
        result = 31 * result + Arrays.hashCode(getRSchedule());
        return result;
    }

    @Override
    public String toString() {
        return "DoctorResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", speciality='" + speciality + '\'' +
                ", room='" + room + '\'' +
                ", schedule=" + Arrays.toString(RSchedule) +
                '}';
    }
}
