package is.hi.hbv501g.eventmanager.Services;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image getImageById(long id);

    ResponseEntity<String> uploadImage(MultipartFile file, int id) throws IOException;
}
