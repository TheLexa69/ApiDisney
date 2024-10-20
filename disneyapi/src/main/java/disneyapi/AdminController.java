package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import java.util.Optional;

public class AdminController {

    @FXML
    private Button btnAdminSalir;

    @FXML
    private Button btnBajaUsuario;

    @FXML
    private Button btnInfoApi;

    @FXML
    private Button btnListaUsuarios;

    @FXML
    private Button btnNuevoUsuario;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    @FXML
    protected Label lblAdminName;

    @FXML
    private Label lblContra;

    @FXML
    private Label lblNombre;

    @FXML
    private AnchorPane pnlScrollPaneAdmin;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtContraseña;

    @FXML
    void onBtnBajaUsuAdminScene(ActionEvent event) {
        setFieldsVisible(false);
        // Lógica para eliminar un usuario
    }

    @FXML
    void onBtnInfoApiAdminScene(ActionEvent event) {
        // Lógica para mostrar información de la API
    }

    @FXML
    void onBtnListaUsuAdminScene(ActionEvent event) {
        setFieldsVisible(false);
        // Lógica para listar usuarios
    }

    @FXML
    void onBtnNuevoUsuAdminScene(ActionEvent event) {
        clearFields();
        setFieldsVisible(true);
    }

    @FXML
    void onBtnSalirAdmin(ActionEvent event) {
        // Lógica para salir de la aplicación o cerrar la ventana
    }

    @FXML
    public void initialize() {
        lblContra.setVisible(false);
        lblNombre.setVisible(false);
    }

    @FXML
    void onBtnConfirmar(ActionEvent event) {
        String usuario = txtNombre.getText();
        String contra = txtContraseña.getText();
        String rol = "administrador";

        try {
            if (usuario != null && contra != null && !usuario.isEmpty() && !contra.isEmpty()) {
                boolean confirmacion = showConfirmation("Confirmar", "¿Desea agregar el usuario?");

                if (confirmacion) {
                    // Si el usuario confirma
                    AdminModel adminModel = new AdminModel();
                    adminModel.addUser(usuario, contra, rol);
                    showMessage("Éxito", "Usuario agregado.");
                    clearFields();
                } else {
                    // Si el usuario cancela
                    showMessage("Cancelado", "Operación cancelada.");
                }

            } else {
                // Muestra alerta si los campos están vacíos
                showAlert("Error", "Por favor, rellene todos los campos.");
                clearFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Hubo un problema al agregar el usuario.");
        }
    }

    @FXML
    void onBtnCancelar(ActionEvent event) {
        clearFields();
        setFieldsVisible(false);
    }

    // Método para ocultar o mostrar los campos de entrada
    private void setFieldsVisible(boolean visible) {
        txtNombre.setVisible(visible);
        txtContraseña.setVisible(visible);
        btnConfirmar.setVisible(visible);
        btnCancelar.setVisible(visible);
        lblContra.setVisible(visible);
        lblNombre.setVisible(visible);
    }

    // Método para limpiar los campos de texto
    private void clearFields() {
        txtNombre.clear();
        txtContraseña.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Obtener la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();

        // Verificar si el usuario seleccionó ACEPTAR
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}