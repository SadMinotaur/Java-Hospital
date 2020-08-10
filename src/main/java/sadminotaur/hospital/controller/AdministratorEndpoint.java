package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.AdministratorRegisterDTO;
import sadminotaur.hospital.requests.AdministratorUpdateDTO;
import sadminotaur.hospital.response.AdministratorResponse;
import sadminotaur.hospital.service.AdministratorService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/admins")
public class AdministratorEndpoint {

    @Autowired
    private AdministratorService administratorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<AdministratorResponse> register(
            @Valid @RequestBody AdministratorRegisterDTO administratorRegisterDTO,
            @CookieValue(required = true) Cookie JAVASESSIONID
    ) throws ServiceException {
        return administratorService.register(administratorRegisterDTO, JAVASESSIONID);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<AdministratorResponse> update(
            @Valid @RequestBody AdministratorUpdateDTO administratorUpdateDTO,
            @CookieValue(required = true) Cookie JAVASESSIONID
    ) throws ServiceException {
        return administratorService.update(administratorUpdateDTO, JAVASESSIONID);
    }
}
