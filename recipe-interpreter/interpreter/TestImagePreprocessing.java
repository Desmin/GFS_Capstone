package interpreter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.Grayscale;
import Catalano.Imaging.Filters.Resize;

public class TestImagePreprocessing {

    public static void main(String[] args) {
        //"C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four.PNG"
        FastBitmap image = new FastBitmap("C:/Users/desli/Downloads/CMS_Creative_164657191_Kingfisher.jpg");

        Grayscale grayscale = new Grayscale();
        Resize resize = new Resize((int) (image.getWidth() * 1.5), (int) (image.getHeight() * 1.5), Resize.Algorithm.BILINEAR);
        BradleyLocalThreshold bradleyLocalThreshold = new BradleyLocalThreshold();

        grayscale.applyInPlace(image);
        resize.applyInPlace(image);
        bradleyLocalThreshold.applyInPlace(image);

        String pathToFile = System.getProperty("java.io.tmpdir") + "tmp.png";
        System.out.println(pathToFile);

        image.saveAsPNG(pathToFile);

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
