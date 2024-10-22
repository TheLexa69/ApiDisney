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

    /**
     * Guarda la busqueda actual en un archivo JSON
     * 
     * @param event el evento de click en el bot n de Guardar
     */
    @FXML
    void onBtnClickGuardarBusquedaUsuario(ActionEvent event) {
        createJSONFile();// CREADO CON JACKSON
    }

    /**
     * Maneja el evento de click en el bot n de Buscar en la escena de Usuario.
     * 
     * Llama al m todo de selecci n de contenido y crea un archivo JSON con la
     * informaci n actual.
     * 
     * @param event el evento de click en el bot n
     */
    @FXML
    void onBtnClickBuscarUsuario(ActionEvent event) {
        seleccion();
        createJSONFile();
    }

    /**
     * Cierra la ventana actual y regresa al Login.
     * 
     * @param event el evento de click en el bot n de Salir
     */
    @FXML
    void onBtnClickSalirUsuario(ActionEvent event) {
        // Obtener el escenario (Stage) actual
        Stage stage = (Stage) btnUsuarioSalir.getScene().getWindow();

        // Cerrar el escenario
        stage.close();

    }

    private String usuario;

    /**
     * Establece el nombre del usuario actual en el panel de usuario y en la
     * etiqueta correspondiente.
     * 
     * @param usuario el nombre del usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
        lblUsuarioPanel.setText(usuario);
    }

    /**
     * Comprueba si el usuario actual tiene una versi n guardada en la cache
     * y la muestra en una ventana emergente. Si no hay versi n guardada,
     * muestra un mensaje de error.
     * 
     * @param event el evento de click en el bot n
     */
    @FXML
    void onBtnClickCacheUsuario(ActionEvent event) {
        comprobarUsuario();
    }

    /**
     * Inicializa la escena de usuario.
     * 
     * Se llama autom aticamente al cargar la escena.
     * 
     * No hace nada actualmente, pero se puede utilizar para establecer valores
     * predeterminados o para realizar otras acciones de inicializaci n.
     */
    @FXML
    void initialize() {}

    // Método para mostrar una alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Maneja el evento de click en el botón "Buscar" en la escena de usuario.
     * 
     * Llama a personaje() para obtener la información del personaje seleccionado.
     * Asigna los valores de las películas, series y videojuegos del personaje a los
     * campos de texto. Asigna la imagen del personaje a un ImageView.
     * 
     * Si el campo de texto para introducir el personaje está vacío, muestra una
     * alerta con un mensaje de error.
     */
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
        }
    }


    /**
     * Muestra en consola el personaje que se ha introducido y verifica si se encuentra
     * en la base de datos. Si no se encuentra, muestra una alerta con un mensaje de
     * error.
     */
    public void personaje() {

        String personajeIntroducido = txtIntroducirPersonaje.getText();

        // Comprobar si el campo no está vacío
        if (!personajeIntroducido.isEmpty()) {
            // Lógica para usar el texto introducido
            System.out.println("Personaje introducido: " + personajeIntroducido);
        } 
        Usuario.comprobarName(personajeIntroducido);
    }

    /**
     * Crea un archivo JSON con los datos del usuario y personaje
     * introducidos en la interfaz gráfica.
     * 
     * Utiliza la biblioteca Jackson para serializar el mapa de datos
     * en un archivo JSON.
     * 
     * @throws IOException si hay un error al escribir el archivo JSON
     */
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

    /**
     * Comprueba si el usuario ha buscado previamente un personaje. Si es
     * así, muestra un mensaje con el personaje buscado.
     */
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
