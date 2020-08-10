package sadminotaur.hospital.response;

import java.util.Objects;

public class PatientResponse {

    private int id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phone;
    private String address;
    private String email;

    public PatientResponse() {
    }

    public PatientResponse(int id, String firstname, String lastname, String patronymic, String phone, String address, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientResponse)) return false;
        PatientResponse that = (PatientResponse) o;
        return Objects.equals(getFirstname(), that.getFirstname()) &&
                Objects.equals(getLastname(), that.getLastname()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getLastname(), getPatronymic(), getPhone(), getAddress(), getEmail());
    }

    @Override
    public String toString() {
        return "PatientResponse{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
