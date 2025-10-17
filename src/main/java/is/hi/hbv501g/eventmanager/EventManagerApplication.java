package is.hi.hbv501g.eventmanager;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.OrganizerRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbc;

    @Bean
    public CommandLineRunner clean(
            AttendeeRepository attendeeRepository,
            OrganizerRepository organizerRepository,
            EventRepository eventRepository,
            TicketRepository ticketRepository) {
        return (_-> {
            jdbc.execute("TRUNCATE TABLE attendees, events, organizer, venues, ticket RESTART IDENTITY CASCADE");
            ticketRepository.deleteAll();
            attendeeRepository.deleteAll();
            organizerRepository.deleteAll();
            eventRepository.deleteAll();
        });
    }

}
