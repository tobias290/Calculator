package calculator.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static calculator.controllers.MenuBarController.isTrighMode;

/**
 * This is the controller for the scientific calculator view.
 */
public class ScientificCalculatorController extends SimpleControlsController {
    /**
     * This is the display label at the top of the screen.
     */
    @FXML
    private Label display;

    /**
     * These are the buttons for sin, cosine and tan.
     */
    @FXML
    private Button sin, cos, tan;

    /**
     * This is called similar to the constructor to initialize the needed properties.
     */
    @FXML
    public void initialize() {
        super.initialize(display);
        MenuBarController.setScientificCalculatorController(this);
        switchTrigMode(isTrighMode());
    }

    /**
     * Called when any of the power buttons are clicked.
     *
     * @param event Event with user data to know what the value was raised to (2/3/x).
     */
    @FXML
    public void powerClick(ActionEvent event) {
        SimpleControlsController.calculation.power(getDataFromEvent(event));
    }

    /**
     * Called when any of the root buttons are clicked.
     *
     * @param event Event with user data to know what the value was rooted by (2/3/x).
     */
    @FXML
    public void root(ActionEvent event) {
        SimpleControlsController.calculation.root(getDataFromEvent(event));
    }

    /**
     * Called when either of the E constant buttons are clicked.
     *
     * @param event Event with user data to know whether the E constant was raised to a value.
     */
    @FXML
    public void eConstantClick(ActionEvent event) {
        SimpleControlsController.calculation.e(getDataFromEvent(event).equals("y"));
    }

    /**
     * Called when the one divided by x (1/x) button is clicked.
     */
    @FXML
    public void oneDividedByClick() {
        SimpleControlsController.calculation.oneDividedBy();
    }

    /**
     * Called when the factorial button is clicked.
     */
    @FXML
    public void factorial() {
        SimpleControlsController.calculation.factorial();
    }

    /**
     * Called when the pi button is clicked.
     */
    @FXML
    public void pi() {
        SimpleControlsController.calculation.pi();
    }

    /**
     * Called when any of the trig function buttons are clicked.
     *
     * @param event Event with user data to know which trig function was clicked (sin/cos/tan).
     */
    @FXML
    public void trigFunctionClick(ActionEvent event) {
        if(isTrighMode()) {
            SimpleControlsController.calculation.trighFunction(getDataFromEvent(event));
        } else {
            SimpleControlsController.calculation.trigFunction(getDataFromEvent(event));
        }
    }

    /**
     * This switched the text of the trig buttons between sin/cos/tan and sinh/cosh/tanh.
     *
     * @param toHypotenuseMode This is used to know whether we are switching to hypotenuse mode or back to normal.
     */
    void switchTrigMode(boolean toHypotenuseMode) {
        if (toHypotenuseMode) {
            sin.setText("sinh");
            cos.setText("cosh");
            tan.setText("tanh");
        } else {
            sin.setText("sin");
            cos.setText("cos");
            tan.setText("tan");
        }
    }
}
