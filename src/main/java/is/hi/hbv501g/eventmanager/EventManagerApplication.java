package is.hi.hbv501g.eventmanager;

import is.hi.hbv501g.eventmanager.Persistence.Repositories.AttendeeRepository;
import is.hi.hbv501g.eventmanager.Requests.CreateUpdateUserRequest;
import is.hi.hbv501g.eventmanager.Services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventManagerApplication {
    private static final Logger log = LoggerFactory.getLogger(EventManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
    }

    // ComanndLineRunner automatically runs this on start
    @Bean
    public CommandLineRunner demo(AccountService service, AttendeeRepository repo){
        return (_ -> {
            CreateUpdateUserRequest badRequest = new CreateUpdateUserRequest(
                    "user1","name",
                    null,null,null,
                    false
            );CreateUpdateUserRequest goodRequest = new CreateUpdateUserRequest(
                    "user1","name",
                    "hi@hi.is",null,"123456",
                    false
            );
            var badUserId = service.createAccount(badRequest);
            var goodUserId = service.createAccount(goodRequest);
            log.info("user id generated: {} (should be 0)", badUserId.toString());
            log.info("user id generated: {} (should be non-zero)", goodUserId.toString());

            repo.deleteAll(); // clean out when done
        });
    }

}
