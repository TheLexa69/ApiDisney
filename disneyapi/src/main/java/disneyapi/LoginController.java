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
        // Cargamos la imagen de inicio de sesión
        Image image = new Image(getClass().getResourceAsStream("/images/disney.png"));
        imgLoginScene.setImage(image);
        imgLoginScene.setPreserveRatio(true);
    }

    /**
     * Maneja el evento de click en el botón de Login en la escena de Login.
     * 
     * Verifica si el usuario y contraseña ingresados son correctos mediante la
     * función getUserRole() de la clase CredentialsManagement. Si el login es
     * exitoso, crea una instancia de la escena correspondiente al rol del usuario
     * (administrador o usuario) y la muestra en una ventana nueva. Si el login
     * falla, muestra una alerta con un mensaje de error.
     * 
     * @param event el evento de click en el botón de Login
     */
    @FXML
    void onBtnClickLoginScene(ActionEvent event) {
        String usuario = txtUsuarioScene.getText();
        String contra = txtContraScene.getText();

        System.out.println("Usuario: " + usuario + ", Contraseña: " + contra);

        String rol = credentialsManagement.getUserRole(usuario, contra);

        if (rol != null) { // Si el login es exitoso
            String fxmlFile = rol.equals("administrador") ? "adminscene.fxml" : "usuarioscene.fxml";
            String title = rol.equals("administrador") ? "Panel Administrador" : "Panel Usuario";

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Obtener el controlador de la nueva escena
                if (rol.equals("administrador")) {
                    AdminController adminController = loader.getController();
                    adminController.lblAdminName.setText(usuario); // Pasar el nombre del usuario
                }

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
            // Mostrar alerta si las credenciales son incorrectas
            showAlert("Login Fallido", "Usuario o contraseña incorrectos. Inténtelo de nuevo.");
        }
    }

    /**
     * Muestra una alerta con título y mensaje de error.
     * 
     * @param title   título de la alerta
     * @param message mensaje de error que se muestra en la alerta
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
