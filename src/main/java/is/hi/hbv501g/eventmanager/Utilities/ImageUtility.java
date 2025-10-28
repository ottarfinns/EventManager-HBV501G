package is.hi.hbv501g.eventmanager.Utilities;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtility {
    /**
     * Compresses a raw image using a Deflater.
     *
     * @param data The uncompressed image data as a byte array.
     * @return A compressed byte array of the image data.
     */
    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(buffer);
            outputStream.write(buffer, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception _) {
        }
        return outputStream.toByteArray();
    }

    /**
     * Decompresses a previously compressed image using an Inflater.
     *
     * @param data The compressed image data as a byte array.
     * @return The decompressed image data as a byte array.
     */
    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (Exception _) {}
        return outputStream.toByteArray();
    }
}
