package disneyapi;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
                System.out.println("Se ha generado un JSON");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Hace una solicitud a la API de Disney y guarda los datos en un archivo JSON.
     * Si el archivo no existe, se crean las carpetas necesarias.
     * 
     * @throws Exception si hay un error al hacer la solicitud o al escribir en el
     *                   archivo.
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

    // HACER DEBAJO LA FUNCION DE ENSEÑAR EN UNA CAJA DIALOG CON LOS DATOS FILTRADOS DE LA API

    public static void main(String[] args) {
        launch(args);
    }
}