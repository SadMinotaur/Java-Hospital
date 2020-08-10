package sadminotaur.hospital.serviceexception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> stringResponseEntity(ServiceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put(ex.getServiceErrorCode().name(), ex.getServiceErrorCode().getErrorString());
        return new ResponseEntity<>(
                body,
                HttpStatus.BAD_REQUEST
        );
    }
}
