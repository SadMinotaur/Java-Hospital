package sadminotaur.hospital.response;

import java.util.Objects;

public class CommissionDoctor {

    private int doctorId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;

    public CommissionDoctor() {
    }

    public CommissionDoctor(int doctorId, String firstName, String lastName, String patronymic, String speciality) {
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommissionDoctor)) return false;
        CommissionDoctor that = (CommissionDoctor) o;
        return Objects.equals(getDoctorId(), that.getDoctorId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getSpeciality(), that.getSpeciality());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDoctorId(), getFirstName(), getLastName(), getPatronymic(), getSpeciality());
    }

    @Override
    public String toString() {
        return "CommissionDoctor{" +
                "doctorId='" + doctorId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", speciality='" + speciality + '\'' +
                '}';
    }
}
