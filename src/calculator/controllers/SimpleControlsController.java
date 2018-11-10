package calculator.controllers;

import calculator.Calculation;
import calculator.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * This is the main controller for the app and this is where all button events occur.
 * This class was kept and plain as possible and all calculation are done in the "Calculation" class.
 */
public class SimpleControlsController {
    /**
     * Represents the base application
     */
    protected static Calculator app;

    /**
     * This is the calculation instance.
     * That is responsible for doing all the calculations and updating the display.
     */
    protected static Calculation calculation;

    /**
     * This is called similar to the constructor to initialize the needed properties.
     */
    @FXML
    public void initialize(Label display) {
        calculation = new Calculation(display);
    }

    /**
     * Sets the base application
     *
     * @param calc Base application instance
     */
    public static void setApp(Calculator calc) {
        app = calc;
    }

    /**
     * This is called when the clear ("C") button is pressed.
     *
     * @param event This is the event passed so we can access needed data in this case whether "all clear" or "clear" was pressed.
     */
    @FXML
    public void clearButtonClick(ActionEvent event) {
        // This gets the data from the event
        // clear all is true is the "AC" was pressed and false is "C" was pressed
        boolean clear_all = Boolean.parseBoolean(getDataFromEvent(event));

        calculation.clear(clear_all);
    }

    /**
     * This is called when the equals button is pressed.
     */
    @FXML
    public void equalsButtonClick() {
        calculation.equals(false);
    }

    /**
     * This is called when any operational button is pressed (+/-/x/÷).
     *
     * @param event This is the event passed so we can access needed data in this case which operator was pressed.
     */
    @FXML
    public void operationButtonClick(ActionEvent event) {
        calculation.appendOperation(getDataFromEvent(event));
    }

    /**
     * This is called when the plus or minis/ negate button is pressed.
     * I.e. ("+/-") button.
     */
    @FXML
    public void negateClick() {
        calculation.negate();
    }

    /**
     * This is called when the decimal point button is pressed.
     */
    @FXML
    public void decimalPointButtonClick() {
        calculation.decimalPoint();
    }

    /**
     * This is called when any number button (0-9) is pressed.
     *
     * @param event This is the event passed so we can access needed data in this case which number was pressed.
     */
    @FXML
    public void numberButtonClick(ActionEvent event) {
        calculation.appendNumber(Integer.valueOf(getDataFromEvent(event)));
    }

    /**
     * This takes an event and returns its user data.
     *
     * @param event Event to extract data from
     * @return Returns the user data as a string
     */
    String getDataFromEvent(ActionEvent event, String ...args) {
        return ((Node) event.getSource()).getUserData().toString();
    }
}
