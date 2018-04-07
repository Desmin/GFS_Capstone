package gui.controllers;

import gui.TesseractOptions;
import interpreter.Interpreter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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
    private MenuItem performOCRMenuItem;

    @FXML
    private TabPane tabPane;

    private Interpreter interpreter = new Interpreter();

    private String selectedImagePath, fileName;

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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Downloads"));
        fileChooser.setTitle("Select An Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(imageView.getScene().getWindow()));

        if (file.isPresent()) {
            Image image = new Image(file.get().toURI().toString());
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
            imageView.setImage(image);
            selectedImagePath = interpreter.preprocessImage(file.get().getPath());
            fileName = file.get().getName().substring(0, file.get().getName().length()-4);
            performOCRButton.setDisable(false);
            performOCRMenuItem.setDisable(false);
        }
    }

    /**
     * Using the text received from the OCR server, a recipe is formatted into its separable parts
     * and placed within a text area.
     */
    @FXML
    private void interpretRecipe() {
        String recipeText = recipeTextArea.getText();
        interpreter.getIngredients(recipeText);
    }

    /**
     * Prepares and opens the Tesseract options stage.
     */
    @FXML
    private void showOptionsStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/options_stage_2.0.fxml"));
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
            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("../fxml/material-fx-v0_3.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveAsTextFile() {
        try {
            FileUtils.writeStringToFile(new File("./" + fileName + ".txt"), recipeTextArea.getText(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showUsageDialog() {
        Alert usage = new Alert(Alert.AlertType.NONE);
        usage.setHeaderText("Usage");
        usage.setContentText("First, select an image. Our preprocessing will prepare that image for use with the" +
                " Tesseract's, a program that will extract text from your image. When you're ready, our program will" +
                " interpret your recipe from that text, making this process as easy as possible for you.");
        usage.getButtonTypes().add(ButtonType.CLOSE);
        usage.show();
    }

    @FXML
    private void showAboutDialog() {
        Alert usage = new Alert(Alert.AlertType.NONE);
        usage.setHeaderText("About");
        usage.setContentText("This application was created by Desmin Little and Wesley Guthrie for Gordan Food" +
                " Service. Created Winter semester of 2018.");
        usage.getButtonTypes().add(ButtonType.CLOSE);
        usage.show();
    }

    @FXML
    private void performOCR() {
        recipeTextArea.setText(interpreter.performOCR(new File(selectedImagePath)));
        tabPane.getSelectionModel().select(1);
    }

    @FXML
    private void exitApplication() {
        ((Stage) imageView.getScene().getWindow()).close();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
