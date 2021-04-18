package sadminotaur.hospital.model;

import sadminotaur.hospital.model.enums.UserType;

import java.util.*;

public class Doctor extends User {

    private Room room;
    private int duration;
    private MedicalSpeciality medicalSpeciality;
    private List<Schedule> schedule;

    public Doctor(int id, String firstname, String lastname, String patronymic, String login, String password, Room room, int duration, MedicalSpeciality medicalSpeciality, UserType userType) {
        super(id, firstname, lastname, patronymic, login, password, userType);
        this.room = room;
        this.medicalSpeciality = medicalSpeciality;
        this.duration = duration;
        schedule = new ArrayList<>();
    }

    public Doctor(String firstname, String lastname, String patronymic, String login, String password, Room room, int duration, MedicalSpeciality medicalSpeciality, UserType userType) {
        this(
                0,
                firstname,
                lastname,
                patronymic,
                login,
                password,
                room,
                duration,
                medicalSpeciality,
                userType
        );
    }

    public Doctor() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public MedicalSpeciality getMedicalSpeciality() {
        return medicalSpeciality;
    }

    public void setMedicalSpeciality(MedicalSpeciality medicalSpeciality) {
        this.medicalSpeciality = medicalSpeciality;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(getRoom(), doctor.getRoom()) &&
                Objects.equals(getMedicalSpeciality(), doctor.getMedicalSpeciality());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRoom(), getMedicalSpeciality());
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "room=" + room +
                ", duration=" + duration +
                ", medicalSpeciality=" + medicalSpeciality +
                ", schedule=" + schedule +
                "} " + super.toString();
    }
}
