package disneyapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Random;

public class MainController {

    @FXML
    private TitledPane TitledPane1;

    @FXML
    private TitledPane TitledPane2;

    @FXML
    private TitledPane TitledPane3;

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgViewNL1;

    @FXML
    private ImageView imgViewNL2;

    @FXML
    private ImageView imgViewNL3;

    @FXML
    private TextArea txtAreaNL1;

    @FXML
    private TextArea txtAreaNL2;

    @FXML
    private TextArea txtAreaNL3;

    /**
     * Maneja el evento de click en el botón de inicio de sesión.
     * 
     * Este método se llama cuando se hace clic en el botón de inicio de sesión.
     * 
     * @param event la acción de clic del botón
     * @throws Exception si ocurre un error
     */
    @FXML
    void onBtnLoginClick(ActionEvent event) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginscene.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa la escena principal.
     * 
     * Este método se llama automáticamente cuando se carga la escena principal.
     */
    @FXML
    void initialize() {
        // Bloquear la edición en los TextAreas
        txtAreaNL1.setEditable(false);
        txtAreaNL2.setEditable(false);
        txtAreaNL3.setEditable(false);

        // Evitar que el TextArea pueda ser enfocado (opcional, si no quieres que se
        // pueda seleccionar el texto)
        txtAreaNL1.setFocusTraversable(false);
        txtAreaNL2.setFocusTraversable(false);
        txtAreaNL3.setFocusTraversable(false);

        // Aquí puedes cargar los personajes aleatorios como lo definimos anteriormente
        try {
            loadRandomCharacters();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga tres personajes aleatorios de la API de Disney.
     * Lanza una excepción si hay un error al leer el archivo JSON.
     */
    private void loadRandomCharacters() throws Exception {
        // Leer el archivo JSON (asumiendo que está en resources/disneyapi.json)
        // Gson gson = new Gson();
        Reader reader = new FileReader("disneyapi/src/main/resources/disneyapi/disneyapi.json"); // Ruta del archivo
                                                                                                 // JSON
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        // Generamos tres números aleatorios
        Random random = new Random();
        int randomIndex1 = random.nextInt(dataArray.size());
        int randomIndex2 = random.nextInt(dataArray.size());
        int randomIndex3 = random.nextInt(dataArray.size());

        // Obtenemos los tres personajes aleatorios
        JsonObject character1 = dataArray.get(randomIndex1).getAsJsonObject();
        JsonObject character2 = dataArray.get(randomIndex2).getAsJsonObject();
        JsonObject character3 = dataArray.get(randomIndex3).getAsJsonObject();

        // Asignamos los datos del primer personaje
        setCharacterData(character1, TitledPane1, imgViewNL1, txtAreaNL1);

        // Asignamos los datos del segundo personaje
        setCharacterData(character2, TitledPane2, imgViewNL2, txtAreaNL2);

        // Asignamos los datos del tercer personaje
        setCharacterData(character3, TitledPane3, imgViewNL3, txtAreaNL3);
    }

    /**
     * Asigna los datos de un personaje a un TitledPane, ImageView y TextArea.
     * 
     * @param character  el objeto JSON que contiene la informaci n del personaje
     * @param titledPane el TitledPane donde se mostrar el nombre del personaje
     * @param imageView  el ImageView donde se mostrar la imagen del personaje
     * @param textArea   el TextArea donde se mostrar n las peliculas y series del
     *                   personaje
     */
    private void setCharacterData(JsonObject character, TitledPane titledPane, ImageView imageView, TextArea textArea) {
        // Asignamos el nombre al TitledPane
        String name = character.get("name").getAsString();
        titledPane.setText(name);

        // Asignamos la imagen al ImageView
        String imageUrl = character.get("imageUrl").getAsString();
        System.out.println(imageUrl);
        Image image = new Image(imageUrl);
        System.out.println(image);
        imageView.setImage(image);

        // Asignamos las peliculas y series al TextArea
        JsonArray filmsArray = character.getAsJsonArray("films");
        JsonArray tvShowsArray = character.getAsJsonArray("tvShows");

        StringBuilder filmsAndShows = new StringBuilder("Peliculas:\n");

        // Añadimos las peliculas
        for (int i = 0; i < filmsArray.size(); i++) {
            filmsAndShows.append(filmsArray.get(i).getAsString()).append("\n");
        }

        filmsAndShows.append("Series:\n");

        // Añadimos las series
        for (int i = 0; i < tvShowsArray.size(); i++) {
            filmsAndShows.append(tvShowsArray.get(i).getAsString()).append("\n");
        }

        textArea.setText(filmsAndShows.toString());
    }
}