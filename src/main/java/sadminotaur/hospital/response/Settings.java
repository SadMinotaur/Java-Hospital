package sadminotaur.hospital.response;

import java.util.Objects;

public class Settings {

    private int maxNameLength;
    private int minPasswordLength;

    public Settings() {
    }

    public Settings(int maxNameLength, int minPasswordLength) {
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
    }

    public int getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Settings)) return false;
        Settings settings = (Settings) o;
        return getMaxNameLength() == settings.getMaxNameLength() &&
                getMinPasswordLength() == settings.getMinPasswordLength();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaxNameLength(), getMinPasswordLength());
    }

    @Override
    public String toString() {
        return "Settings{" +
                "maxNameLength=" + maxNameLength +
                ", minPasswordLength=" + minPasswordLength +
                '}';
    }
}
