package sadminotaur.hospital.serviceexception;

public enum ServiceErrorCode {
    PASSWORD_MISMATCH("Incorrect old password"),
    WRONG_COOKIE("Cookie does not match requirement"),
    WRONG_SPECIALITY("Error in speciality name or does not exist"),
    WRONG_REQUEST_PARAMETERS("Error in parameters format"),
    WRONG_DATE("Doctor not working at this date"),
    USER_NOT_EXIST("User does not exist in database"),
    DAY_BUSY("User cant visit doctor twice at the same date"),
    TOO_EARLY("User cant get ticket on this date"),
    WRONG_USER("This user cant do that"),
    PATIENT_ARE_NOT_EXIST("Patient does not exist in database"),
    DOCTORS_NOT_EXIST("Doctors are not exist in database"),
    ROOM_NOT_EXIST("Room not exist"),
    ROOM_ARE_BUSY("Room is busy"),
    TIMESLOT_BUSY("Timeslot is busy"),
    TIMESLOT_NOT_EXIST("Timeslot does not exist"),
    NOT_EMPTY("Some timeslots are busy"),
    DUPLICATE_ENTITY("Cant insert duplicate entity"),
    DATABASE_INSERT_ERROR("Cant insert entity in database"),
    DATABASE_UPDATE_ERROR("Cant update entity in database"),
    DATABASE_GET_ERROR("Cant get entity from database"),
    DATABASE_DELETE_ERROR("Cant delete entity in database");

    String errorString;

    ServiceErrorCode(String s) {
        errorString = s;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
