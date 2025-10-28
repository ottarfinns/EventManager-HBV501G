package is.hi.hbv501g.eventmanager.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    /**
     * Takes in an image, authenticates user and saves the image for the specified event.
     *
     * @param file    The image file to be uploaded.
     * @param eventId The Id of the event the image is associated with.
     * @param userId  The Id of the organizer attempting the upload.
     * @return A ResponseEntity containing a success message if the image was
     *         succesfully uploaded or an appropriate error message if the
     *         upload fails.
     * @throws IOException if an I/O error occurs while reading the image bytes.
     */
    ResponseEntity<String> uploadImage(MultipartFile file, int eventId, int userId) throws IOException;

    /**
     * Gets the image related to the event from the given event Id.
     *
     * @param eventId The Id of the event the image is associated with.
     * @return A ResponseEntity containing a success message if the image was
     *         found or an appropriate error message if it was not found.
     * @throws IOException if an I/O error occurs while reading the image bytes.
     */
    ResponseEntity<byte[]> findByEventId(int eventId) throws IOException;
}
