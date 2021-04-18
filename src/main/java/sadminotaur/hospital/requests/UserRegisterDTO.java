package sadminotaur.hospital.requests;

import sadminotaur.hospital.validation.*;

import java.util.Objects;

public class UserRegisterDTO {

    @FirstnameValidation
    private String firstname;
    @LastnameValidation
    private String lastname;
    @PatronymicValidation
    private String patronymic;
    @LoginValidation
    private String login;
    @PasswordValidation
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String firstname, String lastname, String patronymic, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegisterDTO)) return false;
        UserRegisterDTO that = (UserRegisterDTO) o;
        return Objects.equals(getFirstname(), that.getFirstname()) &&
                Objects.equals(getLastname(), that.getLastname()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstname(), getLastname(), getPatronymic(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}