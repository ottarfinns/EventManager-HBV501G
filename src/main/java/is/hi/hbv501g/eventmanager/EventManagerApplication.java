package is.hi.hbv501g.eventmanager;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Ticket;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.*;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateTicketRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.EventService;
import is.hi.hbv501g.eventmanager.Services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@SpringBootApplication
public class EventManagerApplication {
    private static final Logger log = LoggerFactory.getLogger(EventManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbc;


    // CommandLineRunner automatically runs this on start
    @Bean
    public CommandLineRunner demo(AccountService accountService, EventService service, VenueRepository venueRepo, OrganizerRepository organizerRepo, TicketService ticketService) {
        return (_ -> {

            CreateUpdateUserRequest badRequest = new CreateUpdateUserRequest(
                    "user1","name",
                    null,null,null,
                    false
            );
            CreateUpdateUserRequest goodRequest = new CreateUpdateUserRequest(
                    "user1","name",
                    "hi@hi.is",null,"123456",
                    false
            );
            var badUserId = accountService.createAccount(badRequest);
            var goodUserId = accountService.createAccount(goodRequest);
            log.info("user id generated: {} (should be 0)", badUserId.toString());
            log.info("user id generated: {} (should be non-zero)", goodUserId.toString());

            //create and save valid venue
            Venue venue = new Venue(null,null,"VR-II");
            venue = venueRepo.save(venue);

            //create and save valid organizer
            Organizer organizer = new Organizer("username", "name", "email", "phoneNumber", "password");
            organizer = organizerRepo.save(organizer);

            //bad request
            CreateUpdateEventRequest badEventReq = new CreateUpdateEventRequest(
                    null,
                    eventType.concert,
                    "no name, no price, no venue, no organizer",
                    LocalDateTime.now().plusDays(10),
                    100,
                    null,
                    null,
                    null
            );
            //good request
            CreateUpdateEventRequest goodEventReq = new CreateUpdateEventRequest(
                    "HBV501G Team 18 fundur",
                    eventType.workshop,
                    "Working on Assignment 2: Elaboration",
                    LocalDateTime.now().plusDays(10),
                    4,
                    0.0,
                    venue,
                    organizer
            );
            Event badEvent = service.createEvent(badEventReq);
            Event goodEvent = service.createEvent(goodEventReq);

            log.info("bad event: {} (should be null)", badEvent == null ? "null" : badEvent.getId());
            log.info("good event id: {} (should be non-null)", (goodEvent != null && goodEvent.getId() != null) ? goodEvent.getId() : "null");


            var all = service.getEvents(null, true);
            log.info("getEvents() -> {} events", all.size());
            all.forEach(ev -> log.info("event: id={} name='{}' type={} dateTime={}",
                    ev.getId(), ev.getName(), ev.getType(), ev.getDateTime()));

            //test ticket creation
            CreateUpdateTicketRequest badTicketReq = new CreateUpdateTicketRequest(
                    goodUserId,0, goodEvent.getPrice()
            );
            CreateUpdateTicketRequest goodTicketReq = new CreateUpdateTicketRequest(
                    goodUserId,
                    goodEvent.getId(),
                    goodEvent.getPrice()
            );
            Ticket badTicket = ticketService.createTicket(badTicketReq);
            Ticket goodTicket = ticketService.createTicket(goodTicketReq);
            Ticket duplicateTicket = ticketService.createTicket(goodTicketReq);

            log.info("bad ticket: {} (should be null)", badTicket == null ? "null" : badTicket.getId());
            log.info("good ticket: {} (should be non-null)", goodTicket == null ? "null" : goodTicket.getId());
            log.info("repeat ticket: {} (should be null)", duplicateTicket == null ? "null" : duplicateTicket.getId());
            
            var filter = new EventFilterRequest("HBV501G Team 18 fundur",null,null,null,null,null,null,null,null);
            var filtered = service.getEvents(filter, true);
            log.info("getEvents() -> {} events", filtered.size());
            filtered.forEach(ev -> log.info("event: id={} name='{}' type={} dateTime={}",
                    ev.getId(), ev.getName(), ev.getType(), ev.getDateTime()));

            log.info("cleanup: start");
            jdbc.execute("TRUNCATE TABLE attendees, events, organizer, venues, ticket RESTART IDENTITY CASCADE");
            log.info("cleanup: done");
        });

    }
}
