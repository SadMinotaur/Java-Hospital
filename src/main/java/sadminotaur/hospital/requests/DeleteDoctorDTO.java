package sadminotaur.hospital.requests;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DeleteDoctorDTO {

    @NotNull
    private String date;

    public DeleteDoctorDTO() {
    }

    public DeleteDoctorDTO(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleteDoctorDTO)) return false;
        DeleteDoctorDTO that = (DeleteDoctorDTO) o;
        return Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate());
    }

    @Override
    public String toString() {
        return "DeleteDoctor{" +
                "date=" + date +
                '}';
    }
}
