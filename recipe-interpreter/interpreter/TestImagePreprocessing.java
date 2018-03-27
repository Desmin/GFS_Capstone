package interpreter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.FastGraphics;
import Catalano.Imaging.Filters.*;

import java.awt.image.BufferedImage;
import java.io.File;

public class TestImagePreprocessing {

    public static void main(String[] args) {
        File file = new File("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four.PNG");
        Interpreter interpreter = new Interpreter();

        interpreter.preprocessImage(file);
        /*FastBitmap image = new FastBitmap("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four.PNG");
        Resize resize = new Resize((int) (image.getWidth() * 1.5), (int) (image.getHeight() * 1.5), Resize.Algorithm.BILINEAR);
        resize.applyInPlace(image);
        image.toGrayscale();


        BradleyLocalThreshold bradleyLocalThreshold = new BradleyLocalThreshold();
        FastBitmap bradImage = new FastBitmap(image);
        bradleyLocalThreshold.applyInPlace(bradImage);
        bradImage.saveAsPNG("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four-BRAD.PNG");

        /*NiblackThreshold niblackThreshold = new NiblackThreshold();
        FastBitmap niblockImage = new FastBitmap(image);
        niblackThreshold.applyInPlace(niblockImage);
        contrastCorrection.applyInPlace(niblockImage);
        niblockImage.saveAsPNG("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four-NIB.PNG");

        SauvolaThreshold sauvolaThreshold = new SauvolaThreshold();
        FastBitmap sauvolaImage = new FastBitmap(image);
        sauvolaThreshold.applyInPlace(sauvolaImage);
        contrastCorrection.applyInPlace(sauvolaImage);
        sauvolaImage.saveAsPNG("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four-SAU.PNG");

        WolfJolionThreshold wolfJolionThreshold = new WolfJolionThreshold();
        FastBitmap wolfImage = new FastBitmap(image);
        wolfJolionThreshold.applyInPlace(wolfImage);
        contrastCorrection.applyInPlace(wolfImage);
        wolfImage.saveAsPNG("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four-WOLF.PNG");*/
    }
}
