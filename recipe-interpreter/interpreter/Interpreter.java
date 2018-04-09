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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("test-recipes/text/" + "apple-pie-stuffed-apples.txt");
        System.out.println(file.getAbsolutePath());
        Scanner scn = new Scanner(file);
        String text = "";
        while (scn.hasNext()) {
            text += scn.nextLine() + "\n";
        }
        Interpreter interpreter = new Interpreter();
        Recipe recipe = interpreter.getIngredients(text);
        System.out.println(recipe.toString());
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
    public Recipe getIngredients(final String recipeText) {
        String directions = "";
        ArrayList<Ingredient> listOfIngredients = new ArrayList<>();
        System.out.println("\n\nInvoked recipe interpreter.");
        String[] recipeLines = recipeText.split("\n\n");
        //new BufferedReader(new StringReader(recipeText)).lines().forEach(recipeLine -> {
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
                boolean valueFound = false;
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
                        valueFound = true;
                        break;
                    }
                }
                if (!valueFound) {
                    value = "null";
                    metric = "null";
                    ingredient = recipeLine;
                }
                listOfIngredients.add(new Ingredient(value, metric, ingredient));
            } else {
                directions += recipeLine;
            }
        }
        return new Recipe(listOfIngredients, directions);
    }
}
