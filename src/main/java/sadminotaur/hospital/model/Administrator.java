package sadminotaur.hospital.model;

import sadminotaur.hospital.model.enums.UserType;

import java.util.Objects;

public class Administrator extends User {

    private String position;

    public Administrator(int id, String firstname, String lastname, String patronymic, String login, String password, UserType userType, String position) {
        super(id, firstname, lastname, patronymic, login, password, userType);
        this.position = position;
    }

    public Administrator(String firstname, String lastname, String patronymic, String login, String password, String position, UserType userType) {
        this(
                0,
                firstname,
                lastname,
                patronymic,
                login,
                password,
                userType,
                position
        );
    }

    public Administrator() {
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrator)) return false;
        if (!super.equals(o)) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(getPosition(), that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPosition());
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "position='" + position + '\'' +
                "} " + super.toString();
    }
}
