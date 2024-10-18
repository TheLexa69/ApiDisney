package disneyapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ---- VERIFICAMOS SI EXISTE LA CARPETA DE CREDENCIALES ----
        CredentialsManagement credentialsManagement = new CredentialsManagement();
        String urlCredentials = "disneyapi/src/main/resources/data/credentials.xml";
        File credentialsFile = new File(urlCredentials);
        if (!credentialsFile.exists()) {
            try {
                credentialsManagement.generateCredentials();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ---- VERIFICAMOS SI EXISTE EL ARCHIVO JSON DE LA API Y SI NO LO GENERA ----
        String urlDisneyApiFile = "disneyapi/src/main/resources/disneyapi/disneyapi.json";
        File disneyApiFile = new File(urlDisneyApiFile);
        if (disneyApiFile.exists()) {
            try {
                // ENSEÑAMOS EL MAINSCENE CON 3 DATOS DE LA API
                showMainScene(primaryStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                generateJSONfromAPI(); // GENERAMOS LA API JSON
                showMainScene(primaryStage); // ENSEÑAMOS EL MAINSCENE CON 3 DATOS DE LA API
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows the main scene with 3 data from the API
     * 
     * @param primaryStage the stage to show the main scene
     * @throws IOException if there is an error loading the scene
     */
    public void showMainScene(Stage primaryStage) {
        // ENSEÑAMOS EL MAINSCENE CON 3 DATOS DE LA API
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Disney API");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();

            MainController mainController = new MainController();
            //mainController.set
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hace una solicitud a la API de Disney y guarda los datos en un archivo
     * JSON. Si el archivo no existe, se crean las carpetas necesarias.
     *
     * @throws Exception si hay un error al hacer la solicitud o al escribir en
     *                   el archivo.
     */
    public void generateJSONfromAPI() throws Exception {
        String apiUrl = "https://api.disneyapi.dev/character/"; // (FUTURAMENTE SE CAMBIARA Y SE PASARA POR PARAMETRO)
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        System.out.println(content.toString());

        // Especificamos la ruta del archivo JSON.
        String filePath = "disneyapi/src/main/resources/disneyapi/disneyapi.json";

        // Creamos el directorio si no existe.
        File file = new File(filePath);
        //
        // -- -- -- -- System.out.println( "\n\nadasdasd " + file.getAbsolutePath() +
        // "\n");
        file.getParentFile().mkdirs(); // Esto crea las carpetas disneyapi si no existen

        // Guardamos el contenido en un archivo JSON.
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content.toString());
            System.out.println("\n\nDatos guardados exitosamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\n\nError al guardar los datos en el archivo JSON.");
        }
    }

    public JSONArray getCharactersFromAPI(File file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            // Convert the JSON to a JSONObject and extract the array "data"
            JSONObject json = new JSONObject(jsonContent.toString());
            return json.getJSONArray("data");

        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Handle the exception as needed
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
