package sadminotaur.hospital.serviceexception;

public class ServiceException extends Exception {

    private ServiceErrorCode serviceErrorCode;

    public ServiceException(ServiceErrorCode serviceErrorCode) {
        this.serviceErrorCode = serviceErrorCode;
    }

    public ServiceErrorCode getServiceErrorCode() {
        return serviceErrorCode;
    }

    public void setServiceErrorCode(ServiceErrorCode serviceErrorCode) {
        this.serviceErrorCode = serviceErrorCode;
    }
}
