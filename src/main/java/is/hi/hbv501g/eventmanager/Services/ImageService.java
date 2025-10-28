package is.hi.hbv501g.eventmanager.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ResponseEntity<String> uploadImage(MultipartFile file, int id, int userId) throws IOException;

    ResponseEntity<byte[]> findById(int id);
}
