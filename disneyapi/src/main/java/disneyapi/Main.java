package disneyapi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "pass123";

    public static void main(String[] args) {
        launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Form");

        // Crear etiquetas para los campos de nombre de usuario y contraseña.
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        // Crear campos de texto para el nombre de usuario y la contraseña.
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Crear una etiqueta para mostrar el resultado del inicio de sesión.
        Label resultLabel = new Label();

        // Crear un botón "Login".
        Button loginButton = new Button("Login");

        // Establecer una acción para el botón "Login" para validar las credenciales.
        loginButton.setOnAction(event -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = passwordField.getText();

            if (enteredUsername.equals(CORRECT_USERNAME) && enteredPassword.equals(CORRECT_PASSWORD)) {
                resultLabel.setText("Login successful!");
            } else {
                resultLabel.setText("Login failed. Please check your credentials.");
            }
        });

        // Crear un layout (VBox) para organizar los elementos.
        VBox root = new VBox(10);
        root.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, resultLabel);

        // Crear la escena y establecerla en el escenario.
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);

        // Mostrar la ventana.
        primaryStage.show();
    }
}