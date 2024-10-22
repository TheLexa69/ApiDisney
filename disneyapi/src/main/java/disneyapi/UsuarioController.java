package disneyapi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private Button btnUsuarioBuscar;

    @FXML
    private Button btnUsuarioSalir;

    @FXML
    private Button btnUsuarioCache;

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
    Label lblUsuarioPanel;

    @FXML
    private Label lblVideojuegos;

    @FXML
    private Label lblVideojuegosOutput;

    @FXML
    private TextField txtIntroducirPersonaje;

    @FXML
    private AnchorPane usuarioPanel;

    @FXML
    void onBtnClickGuardarBusquedaUsuario(ActionEvent event) {
        createJSONFile();// CREADO CON JACKSON
    }

    @FXML
    void onBtnClickBuscarUsuario(ActionEvent event) {
        seleccion();
        createJSONFile();
    }

    @FXML
    void onBtnClickSalirUsuario(ActionEvent event) {
        // Obtener el escenario (Stage) actual
        Stage stage = (Stage) btnUsuarioSalir.getScene().getWindow();

        // Cerrar el escenario
        stage.close();

    }

    private String usuario;

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        lblUsuarioPanel.setText(usuario);
    }

    @FXML
    void onBtnClickCacheUsuario(ActionEvent event) {
        comprobarUsuario();
    }

    @FXML
    void initialize() {

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
        String seleccion = txtIntroducirPersonaje.getText();
        if (seleccion != null) {
            // Lógica específica para "Personaje"
            personaje();
            lblPeliculasOutput.setText(Usuario.peliculas);
            lblSeriesOutput.setText(Usuario.series);
            lblVideojuegosOutput.setText(Usuario.juegos);
            System.out.println(Usuario.imagen);
            try {
                Image image = new Image(Usuario.imagen);
                imgPersonajeL.setImage(image);
                imgPersonajeL.setPreserveRatio(true);

            } catch (Exception e) {
                System.out.println(e);
            }
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

    public void createJSONFile() {
        // Crear un mapa con los datos especificados
        String pel = lblPeliculasOutput.getText();
        String ser = lblSeriesOutput.getText();
        String jue = lblVideojuegosOutput.getText();
        String usu = lblUsuarioPanel.getText();
        String per = txtIntroducirPersonaje.getText();
        Map<String, String> data = new HashMap<>();

        // Asignar las claves y valores del JSON
        data.put("Usuario", usu);
        data.put("Peliculas", pel);
        data.put("Series", ser);
        data.put("Videojuegos", jue);
        data.put("Personaje", per);
        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // Escribir el mapa en un archivo JSON
        try {
            // Especificamos el nombre del archivo JSON
            File jsonFile = new File("disneyapi\\src\\main\\resources\\disneyapi\\" + lblUsuarioPanel.getText() + ".json");

            // Escribir los datos en el archivo JSON
            objectMapper.writeValue(jsonFile, data);

            System.out.println("JSON file created successfully using Jackson!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void comprobarUsuario() {
        String usuarioPanel = lblUsuarioPanel.getText();
        File jsonFile = new File("disneyapi\\src\\main\\resources\\disneyapi\\" + usuarioPanel + ".json");

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        if (jsonFile.exists()) {
            try {
                // Leer el archivo JSON y convertirlo a un mapa
                Map<String, String> data = objectMapper.readValue(jsonFile, Map.class);
                showAlert("Ultima busqueda", data.get("Personaje") );
                System.out.println("pspsps");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
