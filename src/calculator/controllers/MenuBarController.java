package calculator.controllers;

import calculator.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;


/**
 * This class handles all functionality relating to the menu bar.
 */
public class MenuBarController {
    /**
     * This is the base application class.
     */
    private static Calculator app;

    /**
     * This is the scientific calculator calculator.
     */
    private static ScientificCalculatorController scientificCalculatorController;

    /**
     * Tells the controller whether the scientific calculator has been loaded or not.
     */
    private static boolean hasLoaded = false;

    /**
     * This tells the controller whether the current mode if scientific or not.
     */
    private static boolean isCurrentModeScientific = false;

    /**
     * This tells the controller whether the current mode is in degrees of radians.
     */
    private static boolean isRadianMode = false;

    /**
     * This tells the controller whether the calculator is using hypotenuse trigonometry or not.
     */
    private static boolean isTrigHMode = false;

    /**
     * This tells the controller whether the numbers should be formatted in force exponent mode.
     */
    private static boolean isFeMode = false;

    /**
     * This is the menu button for changing between degrees and radian mode.
     */
    @FXML
    private MenuItem angleModeItem;

    /**
     * This is the menu button to switch calculator modes (between normal and scientific).
     */
    @FXML
    private MenuItem switchModeItem;

    /**
     * This is the menu button to switch trig modes.
     */
    @FXML
    private CheckMenuItem hypotenuseModeCheckItem;

    /**
     * This button is used to enable or disable 'Force Exponent' mode.
     */
    @FXML
    private CheckMenuItem feModeCheckItem;

    /**
     * This is the menu button to enable the orange colour theme.
     */
    @FXML
    private CheckMenuItem orangeThemeCheckItem;

    /**
     * This is the menu button to enable the dark colour theme.
     */
    @FXML
    private CheckMenuItem darkThemeCheckItem;

    /**
     * This is called similar to the constructor to initialize the needed properties.
     */
    @FXML
    public void initialize() {
        switchModeItem.setText((hasLoaded != isCurrentModeScientific) ? "Simple Mode" : "Scientific Mode");
        angleModeItem.setText(isRadianMode ? "Degrees Mode" : "Radian Mode");

        hasLoaded = true;
    }

    /**
     * This sets the base application property.
     *
     * @param app Calculator app instance.
     */
    public static void setApp(Calculator app) {
        MenuBarController.app = app;
    }

    /**
     * This sets the scientific calculator property.
     *
     * @param controller Scientific calculator controller instance.
     */
    static void setScientificCalculatorController(ScientificCalculatorController controller) {
        scientificCalculatorController = controller;
    }

    /**
     * @return Returns whether the calculator is current calculating angles in radians. If not then it is calculating in degrees.
     */
    public static boolean isRadianMode() {
        return isRadianMode;
    }

    /**
     * @return Returns whether the calculator is using hypotenuse trig functions or not.
     */
    static boolean isTrighMode() {
        return isTrigHMode;
    }

    /**
     * @return Returns whether the calculator is formatting numbers is force exponent mode.
     */
    public static boolean isFeMode() {
        return isFeMode;
    }

    /**
     * This is called via a menu button to switch between degrees and radian mode.
     */
    @FXML
    public void switchAngleMode() {
        isRadianMode = !isRadianMode;
        angleModeItem.setText(isRadianMode ? "Degrees Mode" : "Radian Mode");
    }

    /**
     * This is called via a menu button to switch between using hypotenuse trig functions and normal trig functions.
     */
    @FXML
    public void changeTrigMode() {
        isTrigHMode = !isTrigHMode;

        hypotenuseModeCheckItem.setSelected(isTrigHMode);

        // Only if the scientific calculator has been set can we switch trig modes
        if(scientificCalculatorController != null)
            scientificCalculatorController.switchTrigMode(isTrigHMode);
    }

    /**
     * This is called via a menu button to enable or disable force exponent mode.
     */
    @FXML
    public void changeFeMode() {
        isFeMode = !isFeMode;
    }

    /**
     * This is called via a menu button to switch between simple and scientific calculator mode.
     */
    @FXML
    public void switchCalculatorModeClick() {
        try {
            app.switchCalculatorMode(!isCurrentModeScientific);
            isCurrentModeScientific = !isCurrentModeScientific;
        } catch (Exception e) {
            System.out.println("Error switching calculator mode");
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    /**
     * This is called via a menu button to switch the colour theme of the applications.
     *
     * @param event Tells the method the theme clicked.
     */
    @FXML
    public void changeTheme(ActionEvent event) {
        String theme = ((CheckMenuItem)event.getSource()).getUserData().toString();

        if(theme.equals("orange_grey")) {
            orangeThemeCheckItem.setSelected(true);
            darkThemeCheckItem.setSelected(false);
            app.switchTheme(Calculator.Theme.ORANGE_GREY);
        } else if (theme.equals("dark")) {
            darkThemeCheckItem.setSelected(true);
            orangeThemeCheckItem.setSelected(false);
            app.switchTheme(Calculator.Theme.DARK);
        }
    }
}
