package interpreter;

import com.sun.jna.Platform;
import gui.TesseractOptions;
import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Interpreter {

    private static final String LINUX_DATAPATH ="/usr/share/tesseract/", WINDOWS_DATAPATH = "C:/Program Files (x86)/Tesseract-OCR/";

    private Tesseract tess;

    /**
     * Initializes the Tesseract based on the system you're using.
     */
    public Interpreter() {
        tess = new Tesseract();
        if (Platform.isLinux())
            tess.setDatapath(LINUX_DATAPATH);
        else
            tess.setDatapath(WINDOWS_DATAPATH);
    }
    /**
     * Processes the give image and returns the text as a String.
     *
     * @param pathToImageFile - the path to the image file.
     * @return imageText
     */
    public String processImage(final String pathToImageFile) throws TesseractException {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(pathToImageFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String imageText = tess.doOCR(img);
        return imageText;
    }

    public void setTesseractOptions(TesseractOptions options) {
        tess.setLanguage(options.getLanguage().getTesseractString());
        tess.setOcrEngineMode(options.getEngineMode().ordinal());
        tess.setPageSegMode(options.getPageSegmentation().ordinal());
    }

    public String processImage(final File image) throws TesseractException {
        return tess.doOCR(image);
    }
}
