package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.service.SessionService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping
public class SessionsEndpoint {

    @Autowired
    private SessionService sessionService;

    @PostMapping(path = "api/sessions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) throws ServiceException {
        return sessionService.post(loginRequest);
    }

    @DeleteMapping(path = "api/sessions")
    public ResponseEntity<Void> logout(@CookieValue(required = true) Cookie JAVASESSIONID) throws ServiceException {
        return sessionService.logout(JAVASESSIONID);
    }

    @GetMapping(path = "api/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserInfo(@CookieValue(required = true) Cookie JAVASESSIONID) throws ServiceException {
        return sessionService.getUserInfo(JAVASESSIONID);
    }
}
