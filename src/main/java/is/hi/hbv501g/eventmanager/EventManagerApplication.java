package is.hi.hbv501g.eventmanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerApplication.class, args);
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventManager");
        //EntityManager em = emf.createEntityManager();
    }

}
