package gui.controllers;

import gui.TesseractOptions;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The options stage controller provides all functionality for options UI.
 *
 * @author Desmin Little
 */
public class OptionsStageController {

    @FXML
    private ComboBox<TesseractOptions.Language> languageComboBox;
    @FXML
    private ComboBox<TesseractOptions.PageSegmentation> pageSegmentationComboBox;
    @FXML
    private ComboBox<TesseractOptions.EngineMode> engineModeComboBox;

    @FXML
    private Label optionsDescriptionLabel;

    private MainStageController mainStageController;

    private TesseractOptions tesseractOptions;

    /**
     * An instance of the main stage controller, so that we can communicate the selected options to the
     * main stage.
     */
    public void setMainStageController(MainStageController mainStageController) {
        this.mainStageController = mainStageController;
        if (Objects.nonNull(mainStageController.getTesseractOptions())) {
            this.tesseractOptions = new TesseractOptions(mainStageController.getTesseractOptions());
            setSelectedItems(tesseractOptions);
        }
    }

    /**
     * We initialize the combo boxes with enum values.
     */
    @FXML
    public void initialize() {
        languageComboBox.setItems(FXCollections.observableArrayList(TesseractOptions.Language.values()));
        pageSegmentationComboBox.setItems(FXCollections.observableArrayList(TesseractOptions.PageSegmentation.values()));
        engineModeComboBox.setItems(FXCollections.observableArrayList(TesseractOptions.EngineMode.values()));

    }

    /**
     * Sets the combo boxes selected items to the given options. This method is only used to
     * initialize the options stage.
     */
    private void setSelectedItems(TesseractOptions tesseractOptions) {
        languageComboBox.getSelectionModel().select(tesseractOptions.getLanguage());
        pageSegmentationComboBox.getSelectionModel().select(tesseractOptions.getPageSegmentation());
        engineModeComboBox.getSelectionModel().select(tesseractOptions.getEngineMode());
        updateOptions();
    }

    /**
     * Updates the options based on what has been selected. This method is called each time a new option is
     * selected by any of the combo boxes.
     */
    @FXML
    private void updateOptions() {
        TesseractOptions.Language language = languageComboBox.getSelectionModel().getSelectedItem();
        TesseractOptions.PageSegmentation pageSegmentation = pageSegmentationComboBox.getSelectionModel().getSelectedItem();
        TesseractOptions.EngineMode engineMode = engineModeComboBox.getSelectionModel().getSelectedItem();

        String string = "The Tesseract will interpret the image into "
                + language.toString()
                + ". You have set page segmentation to: "
                + pageSegmentation.getDescription()
                + " and the engine mode to: "
                + engineMode.getDescription();
        optionsDescriptionLabel.setText(string);

        tesseractOptions.updateOptions(language, pageSegmentation, engineMode);
    }

    /**
     * Saves the currently selected options and sends them over to the main stage controller
     * for later use.
     */
    @FXML
    private void saveOptions() {
        if (Objects.nonNull(mainStageController))
            mainStageController.setTesseractOptions(tesseractOptions);
        closeStage();
    }

    /**
     * Closes the options stage.
     */
    @FXML
    private void closeStage() {
        ((Stage) languageComboBox.getScene().getWindow()).close();
    }

}
