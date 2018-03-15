package gui.controllers;

import gui.TesseractOptions;
import interpreter.Interpreter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * The main stage controller contains methods for all action events supported by the main stage, most of which are
 * click events.
 *
 * @author Desmin Little
 */
public class MainStageController {
    /**
     * An image view for presenting the recipe in image form.
     */
    @FXML
    private ImageView imageView;

    /**
     * The text area were a recipe will be placed into after the OCR data is received.
     */
    @FXML
    private TextArea recipeTextArea;

    @FXML
    private Button performOCRButton;

    @FXML
    private TabPane tabPane;

    private Interpreter interpreter = new Interpreter();

    private File selectedImage;

    /**
     * An object used to represent Tesseract's options that can easily be passed between controllers.
     */
    private TesseractOptions tesseractOptions = new TesseractOptions();

    public TesseractOptions getTesseractOptions() {
        return tesseractOptions;
    }

    public void setTesseractOptions(TesseractOptions tesseractOptions) {
        this.tesseractOptions = tesseractOptions;
        interpreter.setTesseractOptions(tesseractOptions);
    }

    /**
     * Prompts the user to select an image for upload. Verifies that the selected file is an image before uploading.
     * Invoked by the upload image menu item and button.
     */
    @FXML
    private void promptWithImageSelector() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select An Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Optional<File> image = Optional.ofNullable(fileChooser.showOpenDialog(imageView.getScene().getWindow()));

        if (image.isPresent()) try {
            selectedImage = interpreter.preprocessImage(image.get());
            imageView.setImage(new Image(new FileInputStream(selectedImage)));
            performOCRButton.setDisable(false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Using the text received from the OCR server, a recipe is formatted into its separable parts
     * and placed within a text area.
     */
    @FXML
    private void interpretRecipe() {
        System.out.println("Invoked recipe interpreter.");
    }

    /**
     * Prepares and opens the Tesseract options stage.
     */
    @FXML
    private void showOptionsStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/options_stage.fxml"));
            Parent root = fxmlLoader.load();
            /*
             * We give an instance of this controller to the option stage controller, so this controller
             * can receive the selected options.
             */
            ((OptionsStageController) fxmlLoader.getController()).setMainStageController(this);
            Stage stage = new Stage();
            /*
             * These modality and ownership settings force the user to exit the options stage before they can
             * return to the main stage.
             */
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(imageView.getScene().getWindow());
            stage.setTitle("Options");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void performOCR() {
        try {
            recipeTextArea.setText(interpreter.processImage(selectedImage));
            tabPane.getSelectionModel().select(1);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitApplication() {
        ((Stage) imageView.getScene().getWindow()).close();
    }
}
