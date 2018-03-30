package interpreter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.GaussianNoise;
import Catalano.Imaging.Filters.Resize;
import com.sun.jna.Platform;
import gui.TesseractOptions;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Interpreter {

    private static final String LINUX_DATAPATH = "/usr/share/tesseract/", WINDOWS_DATAPATH = "C:/Program Files (x86)/Tesseract-OCR/";

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

    public void setTesseractOptions(TesseractOptions options) {
        tess.setLanguage(options.getLanguage().getTesseractString());
        tess.setOcrEngineMode(options.getEngineMode().ordinal());
        tess.setPageSegMode(options.getPageSegmentation().ordinal());
    }

    public File preprocessImage(File given) {
        try {
            String fileName = given.getAbsolutePath();
            FastBitmap image = new FastBitmap(fileName);
            Resize resize = new Resize((int) (image.getWidth() * 1.5), (int) (image.getHeight() * 1.5), Resize.Algorithm.BILINEAR);
            resize.applyInPlace(image);
            image.toGrayscale();

            BradleyLocalThreshold bradleyLocalThreshold = new BradleyLocalThreshold();
            bradleyLocalThreshold.applyInPlace(image);

            image.saveAsPNG(fileName);

            return new File(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return given;
    }

    /**
     * Processes the given image and returns the text as a String.
     *
     * @param image - the path to the image file.
     * @return imageText
     */
    public String performOCR(final File image) {
        String extractedText = "";
        try {
            extractedText = tess.doOCR(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extractedText;
    }
}
