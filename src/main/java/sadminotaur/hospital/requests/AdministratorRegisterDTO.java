package sadminotaur.hospital.requests;

import sadminotaur.hospital.model.enums.UserType;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AdministratorRegisterDTO extends UserRegisterDTO {

    private final UserType userType = UserType.ADMINISTRATOR;
    @NotNull
    private String position;

    public AdministratorRegisterDTO() {
    }

    public AdministratorRegisterDTO(String firstname, String lastname, String patronymic, String login, String password, String position) {
        super(firstname, lastname, patronymic, login, password);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdministratorRegisterDTO)) return false;
        if (!super.equals(o)) return false;
        AdministratorRegisterDTO that = (AdministratorRegisterDTO) o;
        return userType == that.userType &&
                Objects.equals(getPosition(), that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userType, getPosition());
    }

    @Override
    public String toString() {
        return "AdministratorRegisterDTO{" +
                "userType=" + userType +
                ", position='" + position + '\'' +
                "} " + super.toString();
    }
}
