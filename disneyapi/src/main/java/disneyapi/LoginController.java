package disneyapi;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btnLoginScene;

    @FXML
    private ImageView imgLoginScene;

    @FXML
    private TextField txtContraScene;

    @FXML
    private TextField txtUsuarioScene;

    // Instanciar la clase de manejo de credenciales
    private CredentialsManagement credentialsManagement = new CredentialsManagement();

    @FXML
    public void initialize() {
        // Cargar la imagen de inicio de sesión
        Image image = new Image(getClass().getResourceAsStream("/images/disney.png"));
        imgLoginScene.setImage(image);
        imgLoginScene.setPreserveRatio(true);
    }

    /**
     * Handles the button click event for the login scene.
     * Retrieves the username and password from input fields, verifies the
     * credentials using readCredentials function,
     * and based on the login success, loads the corresponding scene for the user
     * role.
     * If login is successful, loads the admin or user scene with the specified
     * title.
     * If login fails, displays an alert for incorrect credentials.
     *
     * @param event the ActionEvent representing the button click
     */
    @FXML
    void onBtnClickLoginScene(ActionEvent event) {
        String usuario = txtUsuarioScene.getText();
        String contra = txtContraScene.getText();

        System.out.println("Usuario: " + usuario + ", Contraseña: " + contra);

        // Verificar credenciales con la función readCredentials
        boolean isLoginSuccessful = credentialsManagement.readCredentials(usuario, contra);

        if (isLoginSuccessful) {
            // Si el login es exitoso, determinar el rol y cargar la escena correspondiente
            String rol = usuario.equals("profe") ? "administrador" : "usuario"; // Basado en el nombre de usuario
            String fxmlFile = rol.equals("administrador") ? "adminscene.fxml" : "usuarioscene.fxml";
            String title = rol.equals("administrador") ? "Panel Administrador" : "Panel Usuario";

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setResizable(false);
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            } catch (IOException e) {
                System.err.println("Error loading FXML: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Mostrar una alerta si las credenciales son incorrectas
            showAlert("Login Fallido", "Usuario o contraseña incorrectos. Inténtelo de nuevo.");
        }
    }

    // Método para mostrar una alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
