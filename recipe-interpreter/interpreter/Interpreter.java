package interpreter;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.BradleyLocalThreshold;
import Catalano.Imaging.Filters.Grayscale;
import Catalano.Imaging.Filters.Resize;
import com.sun.jna.Platform;
import gui.TesseractOptions;
import gui.controllers.MainStageController;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;

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

    public String preprocessImage(String pathToImage) {
        System.out.println(pathToImage);
        FastBitmap image = new FastBitmap(pathToImage);

        Grayscale grayscale = new Grayscale();
        Resize resize = new Resize((int) (image.getWidth() * 1.5), (int) (image.getHeight() * 1.5), Resize.Algorithm.BILINEAR);
        BradleyLocalThreshold bradleyLocalThreshold = new BradleyLocalThreshold();

        grayscale.applyInPlace(image);
        resize.applyInPlace(image);
        bradleyLocalThreshold.applyInPlace(image);

        String pathToFile = System.getProperty("java.io.tmpdir") + "tmp.png";
        System.out.println(pathToFile);

        image.saveAsPNG(pathToFile);

        return pathToFile;
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

    /**
     * Processes the recipe text and returns the recipe ingredients.
     *
     * @param recipeText - the text from the recipe OCR.
     * @return ingredients
     */
    public String getIngredients(final String recipeText) {
        String ingredients = "";
        System.out.println("\n\nInvoked recipe interpreter.");
        String[] recipeLines = recipeText.split("\n\n");
        boolean ingredientText = false;
        for (String recipeLine : recipeLines) {
            if (recipeLine.toLowerCase().contains("ingredients")) {
                ingredientText = true;
                continue;

            } else if (recipeLine.toLowerCase().contains("instructions")) {
                ingredientText = false;
                continue;
            }

            if (ingredientText) {
                String value = "";
                String metric = "";
                String ingredient = "";
                String[] recipeWords = recipeLine.split(" ");
//                boolean valueFound = false;
                for (int i = 0; i < recipeWords.length; i++) {
                    if (MainStageController.isNumeric(recipeWords[i])) {
                        value = recipeWords[i];
                        i++;
                        metric = recipeWords[i];
                        i++;
                        while (i < recipeWords.length) {
                            ingredient += recipeWords[i] + " ";
                            i++;
                        }
                        break;
                    }
                }
                System.out.println("NEW INGREDIENT");
                System.out.println(value);
                System.out.println(metric);
                System.out.println(ingredient);
                ingredients += value + " " + metric + " " + ingredient + "\n";
            }
        }
        return ingredients;
    }
}
