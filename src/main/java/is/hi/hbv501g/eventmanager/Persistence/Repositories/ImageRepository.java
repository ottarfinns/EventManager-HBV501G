package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Optional<Image> findByName(String imageName);
}
