package sadminotaur.hospital.requests;

import sadminotaur.hospital.model.enums.UserType;
import sadminotaur.hospital.validation.EmailValidation;
import sadminotaur.hospital.validation.PhoneValidation;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PatientRegisterDTO extends UserRegisterDTO {

    private final UserType userType = UserType.PATIENT;
    @NotNull
    private String address;
    @PhoneValidation
    private String phone;
    @EmailValidation
    private String email;

    public PatientRegisterDTO() {
    }

    public PatientRegisterDTO(String firstname, String lastname, String patronymic, String login, String password, String address, String phone, String email) {
        super(firstname, lastname, patronymic, login, password);
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientRegisterDTO)) return false;
        if (!super.equals(o)) return false;
        PatientRegisterDTO that = (PatientRegisterDTO) o;
        return userType == that.userType &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userType, getAddress(), getPhone(), getEmail());
    }

    @Override
    public String toString() {
        return "PatientRegisterDTO{" +
                "userType=" + userType +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}
