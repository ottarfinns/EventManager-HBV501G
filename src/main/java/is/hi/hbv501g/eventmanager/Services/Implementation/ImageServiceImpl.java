package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Image;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.ImageRepository;
import is.hi.hbv501g.eventmanager.Services.ImageService;
import is.hi.hbv501g.eventmanager.Utilities.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public Image getImageById(long id) {
        return null;
    }

    public ResponseEntity<String> uploadImage(MultipartFile file, int id) throws IOException {
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(ImageUtility.compressImage(file.getBytes())).build());
        return ResponseEntity.ok("Image uploaded successfully: " + file.getOriginalFilename());
    }
}
