package is.hi.hbv501g.eventmanager;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Event.eventType;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Organizer;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Venue;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.VenueRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateEventRequest;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Requests.EventFilterRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import is.hi.hbv501g.eventmanager.Services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class EventManagerApplication {
    private static final Logger log = LoggerFactory.getLogger(EventManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoEvent(EventService service, VenueRepository venueRepo, EventRepository eventRepo, OrganizerRepository organizerRepo) {
        return (_ -> {
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
                    LocalDateTime.of(2025, 9, 30, 15, 0),
                    4,
                    0.0,
                    venue,
                    organizer
            );
            Event bad = service.createEvent(badEventReq);
            Event good = service.createEvent(goodEventReq);

            log.info("bad event: {} (should be null)", bad == null ? "null" : bad.getId());
            log.info("good event id: {} (should be non-null)", (good != null && good.getId() != null) ? good.getId() : "null");


            var all = service.getEvents(null, true);
            log.info("getEvents() -> {} events", all.size());
            all.forEach(ev -> log.info("event: id={} name='{}' type={} dateTime={}",
                    ev.getId(), ev.getName(), ev.getType(), ev.getDateTime()));

            var filter = new EventFilterRequest("HBV501G Team 18 fundur",null,null,null,null,null,null,null,null);
            var filtered = service.getEvents(filter, true);
            log.info("getEvents() -> {} events", filtered.size());
            filtered.forEach(ev -> log.info("event: id={} name='{}' type={} dateTime={}",
                    ev.getId(), ev.getName(), ev.getType(), ev.getDateTime()));

            //clean up
            eventRepo.deleteAll();
            organizerRepo.deleteAll();
            venueRepo.deleteAll();
        });

    }
}
