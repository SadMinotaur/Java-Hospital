package sadminotaur.hospital.model;

import java.util.Objects;

public class MedicalSpeciality {
    private int id;
    private String name;

    public MedicalSpeciality(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public MedicalSpeciality(String name) {
        this(0, name);
    }


    public MedicalSpeciality() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalSpeciality)) return false;
        MedicalSpeciality that = (MedicalSpeciality) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "MedicalSpecialty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
