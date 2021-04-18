package sadminotaur.hospital.model;

import sadminotaur.hospital.model.enums.UserType;

import java.util.Objects;

public class Patient extends User {

    private String phone;
    private String address;
    private String email;

    public Patient(int id, String firstname, String lastname, String patronymic, String login, String password, String phone, String address, String email, UserType userType) {
        super(id, firstname, lastname, patronymic, login, password, userType);
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Patient(String firstname, String lastname, String patronymic, String login, String password, String phone, String address, String email, UserType userType) {
        this(
                0,
                firstname,
                lastname,
                patronymic,
                login,
                password,
                phone,
                address,
                email,
                userType
        );
    }

    public Patient() {
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
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getPhone(), patient.getPhone()) &&
                Objects.equals(getAddress(), patient.getAddress()) &&
                Objects.equals(getEmail(), patient.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPhone(), getAddress(), getEmail());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}
