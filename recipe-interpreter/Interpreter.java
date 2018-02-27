import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Interpreter {
    /**
     * Processes the give image and returns the text as a String.
     * @param pathToImageFile - the path to the image file.
     * @return imageText
     */
    public static String processImage(final String pathToImageFile) throws TesseractException {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(pathToImageFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract/");
        String imageText = tesseract.doOCR(img);
        return imageText;
    }
}
