package calculator;

import javafx.scene.control.Label;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;

import static calculator.controllers.MenuBarController.isFeMode;
import static calculator.controllers.MenuBarController.isRadianMode;

/**
 * This class is responsible for doing all the calculations and updating the display.
 */
public class Calculation {
    /**
     * This is the display in the GUI.
     */
    private Label display;

    /**
     * This is used to know whether any value as been inputted.
     * This is needed to stop any errors occurring if the user tries to click an operator before and value was inserted.
     */
    private boolean calcHasValue = false;

    /**
     * This is used to know whether an operator was the last value to be added to the calculation
     */
    private boolean opWasLastAdded = false;

    /**
     * This is used to know whether the root button was the last button pressed
     */
    private boolean rootLastClicked = false;

    /**
     * This represents the entire calculation.
     */
    private String calculation = "";

    /**
     * This represents what is displayed in the GUI.
     * This is needed as what is displayed is different to the whole calculation.
     */
    private String displayValue = "";

    /**
     * Used when FE mode is on, this format the number in FE mode.
     */
    private DecimalFormat sf = new DecimalFormat("0.######E0");

    /**
     * Calculation constructor.
     *
     * @param display This is the display passed from teh controller so we can update it
     */
    public Calculation(Label display) {
        this.display = display;

        updateDisplay();
    }

    /**
     * This clears the display and possibly the calculation is "all clear (AC)" was pressed.
     *
     * @param all - If true "all clear (AC)" was pressed otherwise "clear (C)" was pressed.
     */
    public void clear(boolean all) {
        if(all) {
            // AC was pressed to clear the calculation and therefore no value is inputted so "calcHasValue" needs to be false
            calculation = "";
            calcHasValue = false;
            opWasLastAdded = false;
        }
        displayValue = "";

        updateDisplay();
    }

    /**
     * This takes a number (0-9) which was pressed by the user and adds it to the calculation.
     *
     * @param value Value to append.
     */
    public void appendNumber(int value) {
        // Now a value is inputted "calcHasValue" can be true
        if(!calcHasValue) calcHasValue = true;

        if (rootLastClicked) {
            displayValue = convertToIntIfPossible(Math.pow(Math.E, Math.log(Double.valueOf(displayValue)/value)));
            rootLastClicked = false;
        }

        displayValue += String.valueOf(value);

        opWasLastAdded = false;

        updateDisplay();
    }

    /**
     * This appends an operator to the current calculation (+/-/x/÷).
     *
     * @param op - This is the operator to add (either +/-/x/÷).
     */
    public void appendOperation(String op) {
        // Can't add operator if the calculation has no value or the last value is an operator
        if(!calcHasValue || opWasLastAdded) return;

        calculation += displayValue + op;

        // Call equals to get the current value
        equals(true);

        // Set the display back to empty but don't update the GUI yet until a new number is pressed
        displayValue = "";

        opWasLastAdded = true;
    }

    /**
     * This is calculates the current value of the calculation
     *
     * @param afterOp - If true equals was pressed straight after an operator
     */
    public void equals(boolean afterOp) {
        // If this was not called straight after an operator was added then append the display value to the calculation
        if (!afterOp) calculation += displayValue;

        try {
            System.out.println(!afterOp ? calculation : calculation.substring(0, calculation.length() - 1));

            // A ternary operator was used here to check is an operator was the last added value to the calculation
            // If so remove it to stop an error occurring
            Expression expr = new ExpressionBuilder(!afterOp ? calculation : calculation.substring(0, calculation.length() - 1)).build();

            // Evaluate the expression, and it returns the answer as a double
            double answer = expr.evaluate();

            // As the answer is always returns as a double this check for it can be safely converted to an integer
            displayValue = convertToIntIfPossible(answer);

            // If true this means the equals button was clicked
            // Therefore clear the calculation to stop unwanted numbers/calculations
            if (!afterOp) calculation = "";

            updateDisplay();
        } catch (Exception e) {
            System.out.println(calculation);
            System.out.println("Error: " + e.getMessage());

            displayValue = "Error";
            updateDisplay();
        }
    }

    /**
     * This negates the current display value
     */
    public void negate() {
        // Can't negate if the calculation has no value
        if(!calcHasValue) return;

        displayValue = String.valueOf(Integer.parseInt(displayValue) * -1);
        updateDisplay();
    }

    /**
     * This adds a decimal point to the current display value
     */
    public void decimalPoint() {
        // Stop multiple decimal points being added
        if (displayValue.contains(".")) return;

        displayValue += ".";

        opWasLastAdded = false;

        updateDisplay();
    }

    /**
     * This calculator a value to the power of another
     *
     * @param powerOf This the value to raise to value to the power of
     */
    public void power(String powerOf) {
        switch (powerOf) {
            case "y":
                appendOperation("^");
                return;
            case "10":
                displayValue = convertToIntIfPossible(Math.pow(10, Double.parseDouble(displayValue)));
                break;
            default:
                displayValue = convertToIntIfPossible(Math.pow(Double.parseDouble(displayValue), Integer.parseInt(powerOf)));
                break;
        }
        updateDisplay();
    }

    /**
     * This calculator a value to the root of another
     *
     * @param rootOf This the value to root the vale by
     */
    public void root(String rootOf) {
        switch (rootOf) {
            case "2":
                displayValue = convertToIntIfPossible(Math.sqrt(Double.valueOf(displayValue)));
                break;
            case "3":
                displayValue = convertToIntIfPossible(Math.cbrt(Double.valueOf(displayValue)));
                break;
            case "y":
                rootLastClicked = true;
                break;
        }
        updateDisplay();
    }

    /**
     * This add the E constant to the value
     *
     * @param toPower If true the E constant is raised to the power of the current display value
     */
    public void e(boolean toPower) {
        if(toPower) {
            displayValue = convertToIntIfPossible(Math.pow(Math.E, Double.valueOf(displayValue)));
        } else {
            displayValue = String.valueOf(Math.E);
        }

        updateDisplay();
    }

    /**
     * This divides the current display value by one
     */
    public void oneDividedBy() {
        displayValue = convertToIntIfPossible(1 / Double.valueOf(displayValue));
        equals(false);
    }

    /**
     * This add pi to the current display value
     */
    public void pi() {
        displayValue = String.valueOf(Math.PI);
        updateDisplay();
    }

    /**
     * This calculates the factorial of the current display value
     */
    public void factorial() {
        double value = Double.valueOf(displayValue);

        double fact = 1;

        for (int i = 1; i <= value; i++) {
            fact *= i;
        }

        displayValue = convertToIntIfPossible(fact);
        updateDisplay();
    }

    /**
     * This calculates value applied by a given trig functions
     *
     * @param type This is a string value of the trig function to apply (sin/cos/tan)
     */
    public void trigFunction(String type) {
        double value = isRadianMode() ? Double.valueOf(displayValue) : Math.toRadians(Double.valueOf(displayValue));
        double result;

        switch (type) {
            case "sin":
                result = Math.sin(value);
                break;
            case "cos":
                result = Math.cos(value);
                break;
            case "tan":
                result = Math.tan(value);
                break;
            default:
                return;
        }

        displayValue = convertToIntIfPossible(result);

        updateDisplay();
    }

    /**
     * This calculates value applied by a given hypotenuse trig functions
     *
     * @param type This is a string value of the hypotenuse trig function to apply (sin/cos/tan)
     */
    public void trighFunction(String type) {
        double value = isRadianMode() ? Double.valueOf(displayValue) : Math.toRadians(Double.valueOf(displayValue));
        double result;

        switch (type) {
            case "sin":
                result = Math.sinh(value);
                break;
            case "cos":
                result = Math.cosh(value);
                break;
            case "tan":
                result = Math.tanh(value);
                break;
            default:
                return;
        }

        displayValue = convertToIntIfPossible(result);

        updateDisplay();
    }

    /**
     * This method updates the display with the latest value.
     */
    private void updateDisplay() {
        if (isFeMode()) {
            display.setText(displayValue.equals("") ? "0E0" : sf.format(Double.valueOf(displayValue)));
        } else {
            display.setText(displayValue.equals("") ? "0" : String.valueOf(displayValue));
        }
    }

    /**
     * This takes a double and sees if it can be converted into a integer, finally it converts that value into a string.
     *
     * @param value Number to convert if possible
     * @return Returns the string representation of the value
     */
    private String convertToIntIfPossible(double value) {
        return (int) value == value ? String.valueOf((int) value) : String.valueOf(value);
    }
}
