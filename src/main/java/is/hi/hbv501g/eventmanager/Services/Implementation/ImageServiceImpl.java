package is.hi.hbv501g.eventmanager.Services.Implementation;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Image;
import is.hi.hbv501g.eventmanager.Persistence.Repositories.ImageRepository;
import is.hi.hbv501g.eventmanager.Services.ImageService;
import is.hi.hbv501g.eventmanager.Utilities.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public ResponseEntity<byte[]> findById(int id) {
        final Optional<Image> image = imageRepository.findById(id);

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.get().getType()))
                .body(ImageUtility.decompressImage(image.get().getPicByte()));
    }

    public ResponseEntity<String> uploadImage(MultipartFile file, int id) throws IOException {
        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(ImageUtility.compressImage(file.getBytes())).build());
        return ResponseEntity.ok("Image uploaded successfully: " + file.getOriginalFilename());
    }
}
