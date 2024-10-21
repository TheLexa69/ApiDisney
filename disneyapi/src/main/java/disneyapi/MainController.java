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

    // Método que se ejecuta al cargar la interfaz
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

    // Método para cargar 3 personajes aleatorios del archivo JSON
    private void loadRandomCharacters() throws Exception {
        // Leer el archivo JSON (asumiendo que está en resources/disneyapi.json)
        Gson gson = new Gson();
        Reader reader = new FileReader("disneyapi/src/main/resources/disneyapi/disneyapi.json"); // Ruta del archivo
                                                                                                 // JSON
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        // Generar tres números aleatorios
        Random random = new Random();
        int randomIndex1 = random.nextInt(dataArray.size());
        int randomIndex2 = random.nextInt(dataArray.size());
        int randomIndex3 = random.nextInt(dataArray.size());

        // Obtener los tres personajes aleatorios
        JsonObject character1 = dataArray.get(randomIndex1).getAsJsonObject();
        JsonObject character2 = dataArray.get(randomIndex2).getAsJsonObject();
        JsonObject character3 = dataArray.get(randomIndex3).getAsJsonObject();

        // Asignar los datos del primer personaje
        setCharacterData(character1, TitledPane1, imgViewNL1, txtAreaNL1);

        // Asignar los datos del segundo personaje
        setCharacterData(character2, TitledPane2, imgViewNL2, txtAreaNL2);

        // Asignar los datos del tercer personaje
        setCharacterData(character3, TitledPane3, imgViewNL3, txtAreaNL3);
    }

    // Método para asignar los datos del personaje a los elementos de la interfaz
    private void setCharacterData(JsonObject character, TitledPane titledPane, ImageView imageView, TextArea textArea) {
        // Asignar el nombre al TitledPane
        String name = character.get("name").getAsString();
        titledPane.setText(name);

        // Asignar la imagen al ImageView
        String imageUrl = character.get("imageUrl").getAsString();
        System.out.println(imageUrl);
        Image image = new Image(imageUrl);
        System.out.println(image);
        imageView.setImage(image);

        // Asignar los films y series al TextArea
        JsonArray filmsArray = character.getAsJsonArray("films");
        JsonArray tvShowsArray = character.getAsJsonArray("tvShows");

        StringBuilder filmsAndShows = new StringBuilder("Peliculas:\n");

        // Añadir los films
        for (int i = 0; i < filmsArray.size(); i++) {
            filmsAndShows.append(filmsArray.get(i).getAsString()).append("\n");
        }

        filmsAndShows.append("Series:\n");

        // Añadir las series
        for (int i = 0; i < tvShowsArray.size(); i++) {
            filmsAndShows.append(tvShowsArray.get(i).getAsString()).append("\n");
        }

        textArea.setText(filmsAndShows.toString());
    }
}