package sadminotaur.hospital.requests;

import sadminotaur.hospital.validation.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@PasswordUpdateValidation
public class PatientUpdateDTO {

    @FirstnameValidation
    private String firstname;
    @LastnameValidation
    private String lastname;
    @PatronymicValidation
    private String patronymic;
    @EmailValidation
    private String email;
    @NotNull
    private String address;
    @PhoneValidation
    private String phone;
    private String oldPassword;
    private String newPassword;

    public PatientUpdateDTO() {
    }

    public PatientUpdateDTO(String firstname, String lastname, String patronymic, String email, String address, String phone, String oldPassword, String newPassword) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientUpdateDTO)) return false;
        PatientUpdateDTO that = (PatientUpdateDTO) o;
        return Objects.equals(getFirstname(), that.getFirstname()) &&
                Objects.equals(getLastname(), that.getLastname()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getOldPassword(), that.getOldPassword()) &&
                Objects.equals(getNewPassword(), that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getPatronymic(), getEmail(), getAddress(), getPhone(), getOldPassword(), getNewPassword());
    }

    @Override
    public String toString() {
        return "PatientUpdateDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
