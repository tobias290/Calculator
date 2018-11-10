package calculator;

import calculator.controllers.MenuBarController;
import calculator.controllers.SimpleControlsController;
import calculator.exceptions.FXMLNotFound;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * This is the entry class and it just sets up all important information such as window size and includes icons and calculator.stylesheets.
 */
public class Calculator extends Application {
    /**
     * Represents the stage which is the visible window.
     */
    private Stage stage;

    /**
     * This represents the current scene which is what sets the window dimensions and the interface.
     */
    private Scene currentScene;

    /**
     * These represents the different themes available within the application.
     */
    public enum Theme {
        ORANGE_GREY,
        DARK,
    }

    /**
     * This is what keeps track of the current theme of the application.
     */
    private Theme currentTheme = Theme.ORANGE_GREY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        try {
            // Prepare a loader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/calculator-simple.fxml"));
            Parent root = loader.load();

            MenuBarController.setApp(this);
            SimpleControlsController.setApp(this);

            primaryStage.setTitle("Calculator");
            primaryStage.getIcons().add(new Image(this.getClass().getResource("images/calc-icon.png").toExternalForm()));

            // Create a scene which is where all the GUI elements go
            currentScene = new Scene(root, 420, 630);
            switchTheme(currentTheme);

            // Set the scene and make sure it can't be resized
            primaryStage.setScene(currentScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            throw new FXMLNotFound("'fxml/calculator-simple.fxml' was not found in this project");
        }
    }

    /**
     * This method switches between simple and scientific calculator modes.
     *
     * @param toScientific Tells the method whether to load the scientific or normal calculator.
     * @throws Exception Thrown if the method cannot find an existing FXML view.
     */
    public void switchCalculatorMode(boolean toScientific) throws FXMLNotFound {
        // Prepare a loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource(toScientific ? "fxml/calculator-advanced.fxml" : "fxml/calculator-simple.fxml"));

        try {
            Parent root = loader.load();

            // Create a scene which is where all the GUI elements go
            currentScene = toScientific ? new Scene(root, 740, 630) : new Scene(root, 420, 630);
            switchTheme(currentTheme);

            // Set the scene and make sure it can't be resized
            this.stage.setScene(currentScene);
            this.stage.setResizable(false);
            this.stage.show();
        } catch (Exception e) {
            throw new FXMLNotFound((toScientific ? "'fxml/calculator-advanced.fxml'" : "'fxml/calculator-simple.fxml'") + " was not found in this project");
        }
    }

    /**
     * This switched the current colour theme of the applications.
     *
     * @param theme This is the theme to switch to.
     */
    public void switchTheme(Theme theme) {
        switch (theme) {
            case ORANGE_GREY:
                currentTheme = Theme.ORANGE_GREY;
                currentScene.getStylesheets().clear();
                currentScene.getStylesheets().add(getClass().getResource("stylesheets/orange_grey/menu.css").toExternalForm());
                currentScene.getStylesheets().add(getClass().getResource("stylesheets/orange_grey/style.css").toExternalForm());
                break;
            case DARK:
                currentTheme = Theme.DARK;
                currentScene.getStylesheets().clear();
                currentScene.getStylesheets().add(getClass().getResource("stylesheets/dark/menu.css").toExternalForm());
                currentScene.getStylesheets().add(getClass().getResource("stylesheets/dark/style.css").toExternalForm());
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
