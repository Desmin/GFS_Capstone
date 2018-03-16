package interpreter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.GaussianNoise;

public class TestImagePreprocessing {

    public static void main(String[] args) {
        FastBitmap image = new FastBitmap("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four.PNG");
        image.toGrayscale();
        GaussianNoise noiseRemoval = new GaussianNoise();
        //noiseRemoval.applyInPlace(image);
        image.saveAsPNG("C:/Users/desli/Documents/git/GFS_Capstone/recipe-interpreter/interpreter/test-recipe-four-GS.PNG");
    }
}
