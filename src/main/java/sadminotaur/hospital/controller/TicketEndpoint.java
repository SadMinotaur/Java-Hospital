package sadminotaur.hospital.controller;

import sadminotaur.hospital.requests.TicketRequest;
import sadminotaur.hospital.response.AllTicketsResponse;
import sadminotaur.hospital.response.TicketResponse;
import sadminotaur.hospital.service.TicketService;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/tickets")
public class TicketEndpoint {

    @Autowired
    private TicketService ticketService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketResponse> registerTicket(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @Valid @RequestBody TicketRequest ticketRequest
    ) throws ServiceException {
        return ticketService.registerTicket(JAVASESSIONID, ticketRequest);
    }

    @DeleteMapping(value = "/{timeslot}")
    public ResponseEntity<Void> getAllTickets(
            @CookieValue(required = true) Cookie JAVASESSIONID,
            @PathVariable("timeslot") String timeslot
    ) throws ServiceException {
        return ticketService.cancelTimeslot(JAVASESSIONID, timeslot);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllTicketsResponse> getAllTickets(
            @CookieValue(required = true) Cookie JAVASESSIONID
    ) throws ServiceException {
        return ticketService.getTickets(JAVASESSIONID);
    }
}
