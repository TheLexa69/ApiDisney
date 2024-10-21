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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper; // For JSON support
//import com.fasterxml.jackson.dataformat.xml.XmlMapper; // For Jackson XML support (disabled here)
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

    /**
     * Maneja el evento de clic en el botón de Información de la API en la escena
     * del
     * administrador.
     * 
     * @param event el evento de clic en el botón
     */
    @FXML
    void onBtnInfoApiAdminScene(ActionEvent event) {
        // Lógica para mostrar información de la API
        infoAPI.visibleProperty().setValue(true);
    }

    /**
     * Maneja el evento de clic en el botón para cerrar la ventana de información de
     * la API
     *
     * @param event el evento de clic en el botón
     */
    @FXML
    void onBtnClickCerrarInfo(ActionEvent event) {
        infoAPI.visibleProperty().setValue(false);
    }

    /**
     * Maneja el evento de click en el botón para guardar la lista de usuarios.
     *
     * Obtiene la lista de usuarios y permite al usuario guardar esta lista en un
     * archivo
     * con formato seleccionado (.txt, .bin, .json, .xml) usando un diálogo de
     * selección
     * de archivo. Si no hay usuarios disponibles, muestra una alerta informativa.
     *
     * @param event el evento de click en el botón
     */
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

    /**
     * Maneja el evento de click en el botón "Lista de usuarios" en la escena de
     * Administrador.
     * 
     * Limpia los campos de entrada, oculta los campos de nuevo usuario y muestra
     * los campos
     * de lista de usuarios. Después, intenta obtener la lista de usuarios desde el
     * archivo XML
     * y la muestra en un ListView. Si no hay usuarios, muestra un mensaje de
     * información.
     * 
     * @param event el evento de click en el botón
     */
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

    /**
     * Maneja el evento de click en el botón de eliminar usuario en la escena de
     * administrador.
     * 
     * Extrae el ID del usuario seleccionado en la lista de usuarios y lo pasa a
     * la función removeUserById() de la clase AdminModel para eliminar el usuario
     * por ID. Si el usuario se elimina correctamente, muestra un mensaje de éxito y
     * actualiza la lista de usuarios.
     * 
     * @param event el evento de click en el botón de eliminar usuario
     */
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

    /**
     * Maneja el evento de click en el botón "Nuevo usuario" en la escena de
     * administrador.
     * 
     * Limpia los campos de texto, muestra los campos y botones para crear un nuevo
     * usuario
     * y oculta los campos y botones para mostrar la lista de usuarios. Establece
     * "administrador" como el valor predeterminado en el ComboBox de roles.
     * 
     * @param event el evento de click en el botón de nuevo usuario
     */
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

    /**
     * Cierra la ventana de administrador.
     * 
     * @param event el evento de click en el botón de salir
     */
    @FXML
    void onBtnSalirAdmin(ActionEvent event) {
        // Lógica para salir de la aplicación o cerrar la ventana
        Stage stage = (Stage) btnAdminSalir.getScene().getWindow();
        stage.close();
    }

    /**
     * Inicializa la escena de administrador. Oculta los campos y botones que
     * no se van a utilizar en la escena principal.
     */
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

    /**
     * Maneja el evento de click en el botón de Confirmar en la escena de Agregar
     * Usuario.
     * 
     * Verifica si los campos de usuario y contraseña están vacíos. Si no lo
     * están, muestra una confirmación para agregar el usuario. Si el usuario
     * confirma, utiliza el modelo AdminModel para agregar el usuario al archivo
     * XML de credenciales. Si el usuario cancela, muestra un mensaje de
     * cancelación. Si los campos están vacíos, muestra una alerta con un mensaje
     * de error.
     * 
     * @param event el evento de click en el botón de Confirmar
     */
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

    /**
     * Cancela la operación y limpia los campos de la interfaz.
     * 
     * @param event el evento de acción
     */
    @FXML
    void onBtnCancelar(ActionEvent event) {
        clearFields();
        setFieldsVisibleNuevoUsu(false);
    }

    @FXML
    void onSelect(ActionEvent event) {

    }

    /**
     * Establece visibilidad a los campos para agregar un usuario
     * en la escena administrador.
     *
     * @param visible true o false
     */
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

    /**
     * Oculta o muestra los campos de la lista de usuarios.
     * 
     * @param visible si es true, se muestran los campos, de lo contrario se ocultan
     */
    private void setFieldsVisibleListUsu(boolean visible) {
        lstViewAdmin1.setVisible(visible);
        btnGuardarLista.setVisible(visible);
    }

    /**
     * Limpia los campos de entrada.
     */
    private void clearFields() {
        txtNombre.clear();
        txtContraseña.clear();
    }

    /**
     * Muestra una alerta de error con título y mensaje.
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

    /**
     * Muestra una alerta de confirmación con título y mensaje.
     * 
     * @param title   título de la alerta
     * @param message mensaje de confirmación que se muestra en la alerta
     */
    private void showMessage(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación con título y mensaje.
     * 
     * Muestra una alerta de confirmación con el título y mensaje dados. Si el
     * usuario
     * selecciona ACEPTAR, devuelve true; en caso contrario, devuelve false.
     * 
     * @param title   título de la alerta
     * @param message mensaje de la alerta
     * @return true si el usuario seleccionó ACEPTAR, false en caso contrario
     */
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

    /**
     * Guarda la lista de usuarios en un archivo JSON.
     * 
     * @param users la lista de usuarios a guardar
     * @param file  el archivo donde se guardará la lista de usuarios en formato
     *              JSON
     * @throws IOException si hay un error al escribir en el archivo JSON
     */
    private void guardarComoJson(List<User> users, File file) {
        // PARA JACKSSON (ObjectMapper) DEBEMOS TENER SI O SI GETTERS EN LA CLASE USER
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

    /**
     * Guarda la lista de usuarios en un archivo XML.
     * 
     * @param users la lista de usuarios a guardar
     * @param file  el archivo donde se guardará la lista de usuarios
     * @throws Exception si hay un error durante el proceso de guardado
     */
    private void guardarComoXml(List<User> users, File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("users");
            doc.appendChild(rootElement);

            for (User user : users) {
                Element userElement = doc.createElement("user");
                rootElement.appendChild(userElement);

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(user.getId()));
                userElement.appendChild(idElement);

                Element usernameElement = doc.createElement("username");
                usernameElement.appendChild(doc.createTextNode(user.getUsername()));
                userElement.appendChild(usernameElement);

                Element rolElement = doc.createElement("rol");
                rolElement.appendChild(doc.createTextNode(user.getRole()));
                userElement.appendChild(rolElement);
            }

            // Transformamos el documento en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Para formatear el XML con sangrías

            // Escribimos el contenido XML en el archivo
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            showMessage("Éxito", "Lista de usuarios guardada en formato XML.");
        } catch (Exception e) {
            showAlert("Error", "Hubo un error al guardar el archivo XML.");
            e.printStackTrace();
        }
    }

    /**
     * Guarda una lista de usuarios en un archivo de texto plano.
     * 
     * Cada usuario se escribe en una línea del archivo.
     * 
     * @param users la lista de usuarios a guardar
     * @param file  el archivo donde se guardará la lista de usuarios
     * @throws IOException si hay un error al escribir en el archivo
     */
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

    /**
     * Guarda una lista de usuarios en un archivo de formato binario.
     * 
     * Serializa la lista de usuarios en formato binario y la escribe en el archivo.
     * 
     * @param users la lista de usuarios a guardar
     * @param file  el archivo donde se guardará la lista de usuarios
     * @throws IOException si hay un error al escribir en el archivo
     */
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