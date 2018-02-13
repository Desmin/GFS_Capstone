package src.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * The main stage controller contains methods for all action events supported by the main stage, most of which are
 * click events.
 *
 * @author Desmin Little
 */
public class MainStageController {

    /**
     * Menu items pertaining to OCR processing and interpretation.
     */
    @FXML
    private MenuItem uploadImageMenuItem, interpretImageMenuItem;

    /**
     * A menu item that allows a user to specify OCR processing options.
     */
    @FXML
    private MenuItem changeOCROptionsMenuItem;

    /**
     * Non-essential menu items provided for convenience.
     */
    @FXML
    private MenuItem usageMenuItem, aboutMenuItem, quitMenuItem;

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

    /**
     * Buttons for invoking the upload and interpretation processes.
     */
    @FXML
    private Button uploadImageButton, interpretTextButton;

    /**
     * Prompts the user to select an image for upload. Verifies that the selected file is an image before uploading.
     * Invoked by the upload image menu item and button.
     */
    @FXML
    private void promptWithImageSelector() {
        System.out.println("Invoked image selector.");
    }

    /**
     * Using the text received from the OCR server, a recipe is formatted into its separable parts
     * and placed within a text area.
     */
    @FXML
    private void interpretRecipe() {
        System.out.println("Invoked recipe interpreter.");
    }
}
