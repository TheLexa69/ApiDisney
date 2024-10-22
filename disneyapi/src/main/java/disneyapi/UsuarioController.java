package disneyapi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private String usuario;

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
        // createJSONFile();
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

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        lblUsuarioPanel.setText(usuario);
    }

    /**
     * Maneja el clic en el botón "Cache".
     * 
     * Muestra un mensaje de alerta al usuario para confirmar si desea recuperar
     * los datos de caché. se sobrescribe los datos actuales.
     * Si el usuario acepta, se recuperan los datos de caché.
     * 
     * 
     * @param event el evento de clic
     */
    @FXML
    void onBtnClickCacheUsuario(ActionEvent event) {
        // Ruta de la carpeta de caché
        File cacheDir = new File("disneyapi/src/main/resources/disneyapi/disney_cache");

        // Verificamos si la carpeta existe, si no, se crea
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        // Ruta del archivo de caché
        File jsonFile = new File(cacheDir, "disneyapi.json");
        createJSONFile();
        // Verificar si el archivo de caché existe
        if (jsonFile.exists()) {
            // Mostrar un mensaje de alerta al usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Caché Encontrada");
            alert.setHeaderText("Tienes búsquedas anteriores, ¿quieres recuperarlas?");
            alert.setContentText("Si recuperas los datos, se sobrescribirán los actuales.");

            // Agregar botones para confirmar o cancelar
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                // Crear un objeto ObjectMapper de Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    // Leer el archivo JSON y convertirlo a un mapa
                    Map<String, String> data = objectMapper.readValue(jsonFile,
                            new TypeReference<Map<String, String>>() {
                            });

                    // Recuperar datos y llenar los cuadros
                    lblPeliculasOutput.setText(data.get("Peliculas"));
                    lblSeriesOutput.setText(data.get("Series"));
                    lblVideojuegosOutput.setText(data.get("Videojuegos"));
                    txtIntroducirPersonaje.setText(data.get("Personaje"));

                    // Cargar la imagen si está disponible
                    String imagenUrl = data.get("Imagen"); // Asegúrate de que almacenes la URL en el caché
                    if (imagenUrl != null && !imagenUrl.isEmpty()) {
                        Image image = new Image(imagenUrl);
                        imgPersonajeL.setImage(image);
                        imgPersonajeL.setPreserveRatio(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Error", "No se pudo recuperar los datos de la caché.");
                }
            }
        } else {
            // Si no hay caché, ejecutar la lógica de comprobarUsuario
            comprobarUsuario();
        }
    }

    /**
     * Inicializa la escena de usuario.
     *
     * Verifica si existe el archivo de caché y, si es así, muestra un mensaje de
     * confirmación al usuario. Si el usuario acepta, recupera los datos desde la
     * caché y los muestra en la interfaz de usuario. Si el usuario cancela, no
     * hace nada.
     */
    @FXML
    void initialize() {
        File jsonFile = new File("disneyapi/src/main/resources/data/disneyapi/disney_cache/disneyapi.json");

        // Verificar si el archivo de caché existe
        if (jsonFile.exists()) {
            // Mostrar un mensaje de confirmación al usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Caché Encontrada");
            alert.setHeaderText("Tienes búsquedas anteriores, ¿quieres recuperarlas?");
            alert.setContentText("Si recuperas los datos, se sobrescribirán los actuales.");

            // Agregar botones para confirmar o cancelar
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                // Si el usuario acepta, recuperar los datos
                recuperarDatosDesdeCache();
            }
        }
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
     * Verifica si el campo de texto de la interfaz de usuario es vacío o no.
     * Si no está vacío, llama al método comprobarName() de la clase Usuario para
     * verificar si el personaje existe en la base de datos. Si existe, muestra
     * los datos del personaje en la interfaz de usuario. Si no existe, muestra
     * una alerta con un mensaje de error.
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
     * 
     * 
     * 
     * Se utiliza un objeto ObjectMapper de Jackson para escribir el mapa en el arc
     * ivo JSON.
     * Si el directorio de caché no existe, se crea automáticamente.
     * 
     * Muestra un mensaje de éxito si los datos se guardan correctam
     * En caso de error al guardar en caché, se muestra un mensaje de error.
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
        data.put("Imagen", Usuario.imagen); // Asegúrate de que se guarde la imagen si es necesario

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        // Escribir el mapa en un archivo JSON
        try {
            File cacheDir = new File("disneyapi/src/main/resources/data/disneyapi/disney_cache");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs(); // Crea la carpeta si no existe
            }

            File jsonFile = new File(cacheDir, "disneyapi.json"); // Guardar como disneyapi.json

            // Escribir los datos en el archivo JSON
            objectMapper.writeValue(jsonFile, data);

            System.out.println("JSON file created successfully in " + jsonFile.getAbsolutePath());

            // Mostrar mensaje de confirmación
            showAlert("Éxito", "Los datos se han guardado en caché.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo guardar en caché.");
        }
    }

    /**
     * Comprueba si el archivo de caché existe y, si es así, llena los campos
     * con los datos recuperados de la caché. Si no se puede leer el archivo de
     * caché, muestra un mensaje de error.
     */
    public void comprobarUsuario() {
        File jsonFile = new File("disneyapi/src/main/resources/data/disneyapi/disney_cache/disneyapi.json");

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        if (jsonFile.exists()) {
            try {
                // Leer el archivo JSON y convertirlo a un mapa
                Map<String, String> data = objectMapper.readValue(jsonFile, new TypeReference<Map<String, String>>() {
                });

                // Rellenamos los campos con los datos recuperados
                lblPeliculasOutput.setText(data.get("Peliculas"));
                lblSeriesOutput.setText(data.get("Series"));
                lblVideojuegosOutput.setText(data.get("Videojuegos"));
                txtIntroducirPersonaje.setText(data.get("Personaje"));

                // Cargamos la imagen si está disponible
                String imagenUrl = data.get("Imagen");
                if (imagenUrl != null && !imagenUrl.isEmpty()) {
                    Image image = new Image(imagenUrl);
                    imgPersonajeL.setImage(image);
                    imgPersonajeL.setPreserveRatio(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo recuperar los datos de la caché.");
            }
        }
    }

    /**
     * Enseña una alerta de error con título y mensaje.
     *
     * @param title   el título de la alerta
     * @param message el mensaje de error
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Recupera los datos de la caché y los muestra en la interfaz de usuario.
     * 
     * Si el archivo de caché existe, se lee y se convierte en un mapa. Luego, se
     * rellenan los campos con los datos recuperados y se carga la imagen si
     * existe.
     * 
     * Si no se puede leer el archivo de caché, se muestra un mensaje de error.
     */
    private void recuperarDatosDesdeCache() {
        File jsonFile = new File("disneyapi/src/main/resources/data/disneyapi/disney_cache/disneyapi.json");

        // Creamos un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Leemos el archivo JSON y lo convertimos un mapa
            Map<String, String> data = objectMapper.readValue(jsonFile, new TypeReference<Map<String, String>>() {
            });

            // Rellenamos los campos con los datos recuperados
            lblPeliculasOutput.setText(data.get("Peliculas"));
            lblSeriesOutput.setText(data.get("Series"));
            lblVideojuegosOutput.setText(data.get("Videojuegos"));
            txtIntroducirPersonaje.setText(data.get("Personaje"));

            // Cargamos la imagen si está disponible
            String imagenUrl = data.get("Imagen");
            if (imagenUrl != null && !imagenUrl.isEmpty()) {
                Image image = new Image(imagenUrl);
                imgPersonajeL.setImage(image);
                imgPersonajeL.setPreserveRatio(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo recuperar los datos de la caché.");
        }
    }

}
