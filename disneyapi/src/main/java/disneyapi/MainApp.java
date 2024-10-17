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
        // Ruta del archivo disneyapi.xml
        String urlDisneyApiFile = "disneyapi/src/main/resources/disneyapi/disneyapi.json";
        File disneyApiFile = new File(urlDisneyApiFile);
        //-- -- -- -- System.out.println("Ruta del archivo: " + disneyApiFile.getAbsolutePath());
        if (disneyApiFile.exists()) {
            // Si el archivo existe, mostramos los datos en una ventana
            try {
                // Enseñamos una ventana con los datos de la api filtrados
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Si no existe, hacemos la solicitud a la API y guardamos en un JSON
            try {
                generateJSONfromAPI();
                Stage primaryStage1 = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("usuarioscene.fxml"));
                Parent root = loader.load();
                primaryStage1.setTitle("Disney API");
                primaryStage1.setResizable(false);
                primaryStage1.setScene(new Scene(root, 600, 400));
                primaryStage1.show();
                System.out.println("Se ha generado un JSON");
                System.out.println(getCharactersFromAPI(disneyApiFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hace una solicitud a la API de Disney y guarda los datos en un archivo
     * JSON. Si el archivo no existe, se crean las carpetas necesarias.
     *
     * @throws Exception si hay un error al hacer la solicitud o al escribir en
     * el archivo.
     */
    public void generateJSONfromAPI() throws Exception {
        String apiUrl = "https://api.disneyapi.dev/character/";  //(FUTURAMENTE SE CAMBIARA Y SE PASARA POR PARAMETRO)
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

        //Especificamos la ruta del archivo JSON.
        String filePath = "disneyapi/src/main/resources/disneyapi/disneyapi.json";

        // Creamos el directorio si no existe.
        File file = new File(filePath);
        //-- -- -- -- System.out.println("\n\nadasdasd " + file.getAbsolutePath() + "\n");
        file.getParentFile().mkdirs(); //Esto crea las carpetas disneyapi si no existen

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

    // HACER DEBAJO LA FUNCION DE ENSEÑAR EN UNA CAJA DIALOG CON LOS DATOS FILTRADOS DE LA API
    public static void main(String[] args) {
        launch(args);
    }
    /*public void leerCosas(){
        
    }*/
}
