package sadminotaur.hospital.controller;

import sadminotaur.hospital.response.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping(path = "api/settings")
public class SettingsEndpoint {

    @Value("${max_name_length}")
    private int maxLength;

    @Value("${min_password_length}")
    private int minLength;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Settings> getSettings(@CookieValue(required = false) Cookie JAVASESSIONID) {
        return new ResponseEntity<>(new Settings(
                maxLength,
                minLength
        ), HttpStatus.OK);
    }
}
