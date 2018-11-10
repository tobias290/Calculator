package calculator.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This is the main controller for the app and this is where all button events occur.
 * This class was kept and plain as possible and all calculation are done in the "Calculation" class.
 */
public class SimpleCalculatorController extends SimpleControlsController {
    /**
     * This is the display label at the top of the screen
     */
    @FXML
    private Label display;

    @FXML
    public void initialize() {
        super.initialize(display);
    }
}
