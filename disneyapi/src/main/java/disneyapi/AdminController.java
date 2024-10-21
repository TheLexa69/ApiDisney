package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;
import java.util.List;

public class AdminController {

    @FXML
    private Button btnAdminSalir;

    @FXML
    private Button btnClicBorrarUsuPnl;

    @FXML
    private Button btnInfoApi;

    @FXML
    private Button btnListaUsuarios;

    @FXML
    private Button btnNuevoUsuario;

    @FXML
    protected ImageView imgAdministrator;

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
    private AnchorPane infoAPI;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtContraseña;

    @FXML
    private ListView<String> lstViewAdmin1;

    private ObservableList<String> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    void onBtnInfoApiAdminScene(ActionEvent event) {
        // Lógica para mostrar información de la API
        infoAPI.visibleProperty().setValue(true);
    }

    @FXML
    void onBtnClickCerrarInfo(ActionEvent event) {
        infoAPI.visibleProperty().setValue(false);
    }

    @FXML
    void onBtnListaUsuAdminScene(ActionEvent event) {
        clearFields();
        setFieldsVisibleNuevoUsu(false);
        setFieldsVisibleListUsu(true);

        listaUsuarios.clear();

        try {
            List<User> users = AdminModel.listUsers();
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
                    listaUsuarios.add(user.toString()); // Agrega cada usuario a la lista observable
                }
                lstViewAdmin1.setItems(listaUsuarios); // Establece la lista en el ListView

                // Si hay usuarios en la lista, muestra el botón de eliminar
                btnClicBorrarUsuPnl.setVisible(true);

            } else {
                // Si no hay usuarios, puedes ocultar el botón o mostrar un mensaje
                btnClicBorrarUsuPnl.setVisible(false);
                showAlert("Información", "No hay usuarios disponibles.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Hubo un problema al cargar los usuarios.");
        }
    }

    @FXML
    void onBtnClickBorrarUsuario(ActionEvent event) {
        String selectedUser = lstViewAdmin1.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Extraer el ID del usuario
            String userId = selectedUser.replaceAll(".*id='(\\d+)'.*", "$1"); // Regex para extraer el ID

            // Confirmar la acción
            boolean confirmed = showConfirmation("Confirmar",
                    "¿Está seguro de que desea eliminar al usuario con ID " + userId + "?");
            if (confirmed) {
                try {
                    AdminModel.removeUserById(userId); // Llama al método para eliminar el usuario por ID
                    listaUsuarios.remove(selectedUser); // Elimina de la lista observable
                    lstViewAdmin1.setItems(listaUsuarios); // Actualiza el ListView
                    showMessage("Éxito", "Usuario eliminado.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Hubo un problema al eliminar el usuario.");
                }
            }
        } else {
            showAlert("Error", "Por favor, seleccione un usuario para eliminar.");
        }
    }

    @FXML
    void onBtnNuevoUsuAdminScene(ActionEvent event) {
        clearFields();
        setFieldsVisibleNuevoUsu(true);
        setFieldsVisibleListUsu(false);
        btnClicBorrarUsuPnl.setVisible(false);
    }

    @FXML
    void onBtnSalirAdmin(ActionEvent event) {
        // Lógica para salir de la aplicación o cerrar la ventana
        Stage stage = (Stage) btnAdminSalir.getScene().getWindow();
        stage.close();
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
        setFieldsVisibleNuevoUsu(false);
    }

    // Método para ocultar o mostrar los campos de entrada
    private void setFieldsVisibleNuevoUsu(boolean visible) {
        txtNombre.setVisible(visible);
        txtContraseña.setVisible(visible);
        btnConfirmar.setVisible(visible);
        btnCancelar.setVisible(visible);
        lblContra.setVisible(visible);
        lblNombre.setVisible(visible);
    }

    private void setFieldsVisibleListUsu(boolean visible) {
        lstViewAdmin1.setVisible(visible);
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