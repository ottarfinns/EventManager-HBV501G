package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import is.hi.hbv501g.eventmanager.Persistence.Entities.Image;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.EventRepository;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.ImageRepository;
import is.hi.hbv501g.eventmanager.Services.ImageService;
import is.hi.hbv501g.eventmanager.Utilities.ImageUtility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final EventRepository eventRepository;

    public ImageServiceImpl(ImageRepository imageRepository, EventRepository eventRepository) {
        this.imageRepository = imageRepository;
        this.eventRepository = eventRepository;
    }


    public ResponseEntity<byte[]> findById(int id) {
        final Optional<Image> image = imageRepository.findByEventId(id);

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.orElseThrow().getType()))
                .body(ImageUtility.decompressImage(image.get().getPicByte()));
    }

    @Transactional
    public ResponseEntity<String> uploadImage(MultipartFile file, int id, int userId) throws IOException {
        Event event = eventRepository.findById(id).orElseThrow();
        if (event.getOrganizer().getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to perform this operation");
        }

        boolean replaced = false;
        if (event.getImage() != null) {
            imageRepository.delete(event.getImage());
            event.setImage(null);
            imageRepository.flush();
            replaced = true;
        }
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(ImageUtility.compressImage(file.getBytes()))
                .event(event)
                .build());

        if (replaced) {
            return ResponseEntity.ok("Image uploaded successfully and old image replaced: " + file.getOriginalFilename());
        }
        return ResponseEntity.ok("Image uploaded successfully: " + file.getOriginalFilename());
    }
}
