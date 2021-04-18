package sadminotaur.hospital.response;

import java.util.Arrays;
import java.util.Objects;

public class CommissionArray {

    private String ticket;
    private String room;
    private String date;
    private String time;
    private CommissionDoctor[] commissionDoctors;
    private String firstName;
    private String lastName;
    private String patronymic;

    public CommissionArray() {
    }

    public CommissionArray(String ticket, String room, String date, String time, CommissionDoctor[] commissionDoctors, String firstName, String lastName, String patronymic) {
        this.ticket = ticket;
        this.room = room;
        this.date = date;
        this.time = time;
        this.commissionDoctors = commissionDoctors;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public CommissionDoctor[] getCommissionDoctors() {
        return commissionDoctors;
    }

    public void setCommissionDoctors(CommissionDoctor[] commissionDoctors) {
        this.commissionDoctors = commissionDoctors;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommissionArray)) return false;
        CommissionArray that = (CommissionArray) o;
        return Objects.equals(getTicket(), that.getTicket()) &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTime(), that.getTime()) &&
                Arrays.equals(getCommissionDoctors(), that.getCommissionDoctors()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getTicket(), getRoom(), getDate(), getTime(), getFirstName(), getLastName(), getPatronymic());
        result = 31 * result + Arrays.hashCode(getCommissionDoctors());
        return result;
    }

    @Override
    public String toString() {
        return "CommissionArray{" +
                "ticket='" + ticket + '\'' +
                ", room='" + room + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", commissionDoctors=" + Arrays.toString(commissionDoctors) +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }
}
