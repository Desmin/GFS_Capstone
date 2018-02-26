import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Interpreter {
    public Interpreter() {

    }

//    public static void main(String[] args) {
//        Interpreter adpt = new Interpreter();
//        String thing = null;
//        try {
//            thing = adpt.processImage("/home/guthriew/Downloads/understandable.jpg");
//        } catch (TesseractException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//        System.out.println(thing);
//    }

    /**
     *
     * @param pathToImageFile
     * @return
     */
    public String processImage(final String pathToImageFile) throws TesseractException {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(pathToImageFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Tesseract tesseract = new Tesseract();
        String imageText = tesseract.doOCR(img);
        return imageText;
    }
}
