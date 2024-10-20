package disneyapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private Button btnUsuarioBuscar;

    @FXML
    private Button btnUsuarioSalir;

    @FXML
    private ComboBox<String> cmboxUsuario;

    @FXML
    private ImageView imgPersonajeL;

    @FXML
    private Label lblIntroduzcaPersonaje;

    @FXML
    private Label lblLogo;

    @FXML
    private Label lblPelicula;

    @FXML
    private Label lblPeliculasOutput;

    @FXML
    private Label lblSeries;

    @FXML
    private Label lblSeriesOutput;

    @FXML
    private Label lblUsuarioPanel;

    @FXML
    private Label lblVideojuegos;

    @FXML
    private Label lblVideojuegosOutput;

    @FXML
    private TextField txtIntroducirPersonaje;

    @FXML
    private AnchorPane usuarioPanel;

    @FXML
    void onBtnClickBuscarUsuario(ActionEvent event) {
        seleccion();

    }

    @FXML
    void onBtnClickSalirUsuario(ActionEvent event) {
        // Obtener el escenario (Stage) actual
        Stage stage = (Stage) btnUsuarioSalir.getScene().getWindow();

        // Cerrar el escenario
        stage.close();

    }

    @FXML
    void initialize() {
        // Añadir elementos al ComboBox en el método de inicialización
        cmboxUsuario.getItems().addAll("Personaje", "Pelicula");
        cmboxUsuario.setValue("Personaje");

    }

    // Método para mostrar una alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void seleccion() {
        // Obtener el valor seleccionado del ComboBox
        String seleccion = cmboxUsuario.getValue();
        if (seleccion.equals("Personaje")) {
            // Lógica específica para "Personaje"
            personaje();
            lblPeliculasOutput.setText(Usuario.peliculas);
            lblSeriesOutput.setText(Usuario.series);
            lblVideojuegosOutput.setText(Usuario.juegos);
        } else if (seleccion.equals("Pelicula")) {
            // Lógica específica para "Pelicula"
            System.out.println("Has seleccionado una Película.");
        } else {
            // Mostrar una alerta si las credenciales son incorrectas
            showAlert("Vacio", "Usuario o contraseña incorrectos. Inténtelo de nuevo.");
        }
    }

    public void personaje() {

        String personajeIntroducido = txtIntroducirPersonaje.getText();

        // Comprobar si el campo no está vacío
        if (!personajeIntroducido.isEmpty()) {
            // Lógica para usar el texto introducido
            System.out.println("Personaje introducido: " + personajeIntroducido);
        } else {
            // Mostrar una alerta si las credenciales son incorrectas
            showAlert("ERROR", "Lo que has introducido no esta en la base de datos");
        }
        Usuario.comprobarName(personajeIntroducido);
    }

}
