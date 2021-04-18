package sadminotaur.hospital.requests;

import sadminotaur.hospital.validation.FirstnameValidation;
import sadminotaur.hospital.validation.LastnameValidation;
import sadminotaur.hospital.validation.PasswordUpdateValidation;
import sadminotaur.hospital.validation.PatronymicValidation;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@PasswordUpdateValidation
public class AdministratorUpdateDTO {
    
    @FirstnameValidation
    private String firstname;
    @LastnameValidation
    private String lastname;
    @PatronymicValidation
    private String patronymic;
    @NotNull
    private String position;
    private String oldPassword;
    private String newPassword;

    public AdministratorUpdateDTO() {
    }

    public AdministratorUpdateDTO(String firstname, String lastName, String patronymic, String position, String oldPassword, String newPassword) {
        this.firstname = firstname;
        this.lastname = lastName;
        this.patronymic = patronymic;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (!(o instanceof AdministratorUpdateDTO)) return false;
        AdministratorUpdateDTO that = (AdministratorUpdateDTO) o;
        return Objects.equals(getFirstname(), that.getFirstname()) &&
                Objects.equals(getLastname(), that.getLastname()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getPosition(), that.getPosition()) &&
                Objects.equals(getOldPassword(), that.getOldPassword()) &&
                Objects.equals(getNewPassword(), that.getNewPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getPatronymic(), getPosition(), getOldPassword(), getNewPassword());
    }

    @Override
    public String toString() {
        return "AdministratorUpdateDTO{" +
                "firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
