package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.CommissionRequest;
import sadminotaur.hospital.response.CommissionResponse;
import sadminotaur.hospital.service.CommissionService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/commissions")
public class CommissionEndpoint {

    @Autowired
    private CommissionService commissionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommissionResponse> register(
            @Valid @RequestBody CommissionRequest commissionRequest,
            @CookieValue(required = true) Cookie JAVASESSIONID
    ) throws ServiceException {
        return commissionService.register(commissionRequest, JAVASESSIONID);
    }

    @DeleteMapping(value = "/{ticket}")
    public ResponseEntity<Void> delete(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("ticket") String ticket
    ) throws ServiceException {
        return commissionService.delete(JAVASESSIONID, ticket);
    }
}
