package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

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
    private Button btnGuardarLista;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> cmbRolAdminScene;

    @FXML
    protected Label lblAdminName;

    @FXML
    private Label lblContra;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblRol;

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
    void onBtnGuardarLista(ActionEvent event) {
        try {

            List<User> users = AdminModel.listUsers(); // Obtener la lista de usuarios

            // Si la lista está vacía, mostramos una alerta
            if (users == null || users.isEmpty()) {
                showAlert("Información", "No hay usuarios disponibles para guardar.");
                return;
            }

            // Usar FileChooser para seleccionar el tipo de archivo y la ubicación
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar lista de usuarios");

            // Agregar extensiones de archivos permitidos
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivo de texto (*.txt)", "*.txt"),
                    new FileChooser.ExtensionFilter("Archivo binario (*.bin)", "*.bin"),
                    new FileChooser.ExtensionFilter("Archivo JSON (*.json)", "*.json"),
                    new FileChooser.ExtensionFilter("Archivo XML (*.xml)", "*.xml"));

            // Mostrar el diálogo para guardar archivo
            File file = fileChooser.showSaveDialog(btnGuardarLista.getScene().getWindow());
            if (file != null) {
                // Obtener la extensión seleccionada
                String filePath = file.getAbsolutePath();
                if (filePath.endsWith(".txt")) {
                    guardarComoTxt(users, file);
                } else if (filePath.endsWith(".bin")) {
                    guardarComoBinario(users, file);
                } else if (filePath.endsWith(".json")) {
                    guardarComoJson(users, file); // Usa Jackson para JSON
                } else if (filePath.endsWith(".xml")) {
                    guardarComoXml(users, file); // Usa Jackson para XML
                }
            }
        } catch (Exception e) {
            System.out.println("(AdminController -> onBtnGuardarLista()): " + e.getMessage());
        }
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
                System.out.println(users.toString());
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
        ObservableList<String> rols = FXCollections.observableArrayList("administrador", "usuario");
        cmbRolAdminScene.setItems(rols);
        cmbRolAdminScene.setValue("administrador");
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
        txtNombre.setVisible(false);
        txtContraseña.setVisible(false);
        btnConfirmar.setVisible(false);
        btnGuardarLista.setVisible(false);
        btnCancelar.setVisible(false);
        lblRol.setVisible(false);
        cmbRolAdminScene.setVisible(false);
        btnClicBorrarUsuPnl.setVisible(false);
    }

    @FXML
    void onBtnConfirmar(ActionEvent event) {
        String usuario = txtNombre.getText();
        String contra = txtContraseña.getText();
        String rol = cmbRolAdminScene.getValue();

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

    @FXML
    void onSelect(ActionEvent event) {

    }

    // Método para ocultar o mostrar los campos de entrada
    private void setFieldsVisibleNuevoUsu(boolean visible) {
        txtNombre.setVisible(visible);
        txtContraseña.setVisible(visible);
        btnConfirmar.setVisible(visible);
        btnCancelar.setVisible(visible);
        lblContra.setVisible(visible);
        lblNombre.setVisible(visible);
        lblRol.setVisible(visible);
        cmbRolAdminScene.setVisible(visible);
    }

    private void setFieldsVisibleListUsu(boolean visible) {
        lstViewAdmin1.setVisible(visible);
        btnGuardarLista.setVisible(visible);
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

    //PARA JACKSSON (ObjectMapper) DEBEMOS TENER SI O SI GETTERS EN LA CLASE USER
    private void guardarComoJson(List<User> users, File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, users); // Convierte la lista de usuarios a JSON y la guarda en el archivo
            System.out.println("hola");
            showMessage("Éxito", "Lista de usuarios guardada en formato JSON.");
        } catch (IOException e) {
            showAlert("Error", "Hubo un error al guardar el archivo JSON.");
            e.printStackTrace();
        }
    }

    private void guardarComoXml(List<User> users, File file) {
        /*XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(file, users);
            showMessage("Éxito", "Lista de usuarios guardada en formato XML.");
        } catch (IOException e) {
            showAlert("Error", "Hubo un error al guardar el archivo XML.");
            e.printStackTrace();
        }*/
    }

    private void guardarComoTxt(List<User> users, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (User user : users) {
                writer.write(user.toString() + System.lineSeparator()); // Escribe cada usuario en una línea
            }
            showMessage("Éxito", "Lista de usuarios guardada en formato TXT.");
        } catch (IOException e) {
            showAlert("Error", "Hubo un error al guardar el archivo TXT.");
            e.printStackTrace();
        }
    }

    private void guardarComoBinario(List<User> users, File file) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(users); // Serializa la lista de usuarios en formato binario
            showMessage("Éxito", "Lista de usuarios guardada en formato binario.");
        } catch (IOException e) {
            showAlert("Error", "Hubo un error al guardar el archivo binario.");
            e.printStackTrace();
        }
    }

}