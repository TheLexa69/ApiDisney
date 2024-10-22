package disneyapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Usuario {
    public static String peliculas;
    public static String series;
    public static String juegos;
    public static String imagen;

    /**
     * Comprueba si el nombre de personaje pasado como par metro existe en el
     * archivo JSON
     * y, si es as , muestra los films, series y juegos asociados a ese personaje.
     * Si no se encuentra el personaje, muestra un mensaje de error.
     *
     * @param nombreBuscado nombre del personaje a buscar
     */
    public static void comprobarName(String nombreBuscado) {
        try {

            peliculas = "";
            series = "";
            juegos = "";
            imagen = "";
            String jsonString = new String(
                    Files.readAllBytes(Paths.get("disneyapi\\src\\main\\resources\\disneyapi\\disneyapi.json")));

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            boolean encontrado = false; // Variable para indicar si se encontró el nombre

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject character = dataArray.getJSONObject(i);
                String name = character.getString("name");
                // Comparamos el nombre con el nombre buscado
                if (name.equalsIgnoreCase(nombreBuscado)) {
                    JSONArray filmsArray = character.getJSONArray("films"); 
                    JSONArray seriesArray = character.getJSONArray("tvShows"); 
                    JSONArray juegosArray = character.getJSONArray("videoGames"); 
                    String imgString = character.getString("imageUrl");
                    imagen = imgString;
                    System.out.println("Nombre encontrado: " + name);

                    // Imprime cada film en el array
                    StringBuilder filmsString = new StringBuilder();
                    for (int j = 0; j < filmsArray.length(); j++) {
                        filmsString.append(filmsArray.getString(j));
                        peliculas = " ";
                        peliculas = filmsString.toString();
                        if (j < filmsArray.length() - 1) {
                            filmsString.append(", "); // Agregamos una coma si hay más films
                            filmsString.append("\n");
                        }
                    }
                    // Imprime cada serie en el array
                    StringBuilder seriesString = new StringBuilder();
                    for (int j = 0; j < seriesArray.length(); j++) {
                        seriesString.append(seriesArray.getString(j));
                        series = " ";
                        series = seriesString.toString();
                        if (j < seriesArray.length() - 1) {
                            seriesString.append(", "); // Agregamos una coma si hay más series
                            seriesString.append("\n");
                        }
                    }
                    // Imprime cada juego en el array
                    StringBuilder juegosString = new StringBuilder();
                    for (int j = 0; j < juegosArray.length(); j++) {
                        juegosString.append(juegosArray.getString(j));
                        juegos = " ";
                        juegos = juegosString.toString();
                        if (j < juegosArray.length() - 1) {
                            juegosString.append(", "); // Agregamos una coma si hay más juegos
                            juegosString.append("\n");
                        }
                    }
                    // Imprimimos los films
                    System.out.println("Pelis:" + peliculas);
                    // Imprimimos los series
                    System.out.println("series:" + series);
                    // Imprimimos los juegos
                    System.out.println("juegos:" + juegos);
                    System.err.println();
                    encontrado = true;
                    break; // Sale del bucle si encuentra el nombre
                }
            }

            if (!encontrado) {
                showAlert("ERROR", "Lo que has introducido no esta en la base de datos");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
 * Displays an error alert with the specified title and message.
 *
 * @param title   the title of the alert
 * @param message the error message displayed in the alert
 */
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
