package disneyapi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usuario {
    public static String peliculas;
    public static String series;
    public static String juegos;
    public static String imagen;
    public static void comprobarName(String nombreBuscado) {
        try {
            // Lee el contenido del archivo JSON
            String jsonString = new String(
                    Files.readAllBytes(Paths.get("disneyapi\\src\\main\\resources\\disneyapi\\disneyapi.json")));

            // Procesa el JSON
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            boolean encontrado = false; // Variable para indicar si se encontró el nombre

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject character = dataArray.getJSONObject(i);
                String name = character.getString("name");
                // Comparar el nombre con el nombre buscado
                if (name.equalsIgnoreCase(nombreBuscado)) {
                    JSONArray filmsArray = character.getJSONArray("films"); // Obtiene el array de films
                    JSONArray seriesArray = character.getJSONArray("tvShows"); // Obtiene el array de series
                    JSONArray juegosArray = character.getJSONArray("videoGames"); // Obtiene el array de juegos
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
                            filmsString.append(", "); // Agrega una coma si hay más films
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
                            seriesString.append(", "); // Agrega una coma si hay más series
                            seriesString.append("\n");
                        }
                    }
                    // Imprime cada juego en el array
                    StringBuilder juegosString = new StringBuilder();
                    for (int j = 0; j <juegosArray.length(); j++) {
                        juegosString.append(juegosArray.getString(j));
                        juegos = " ";
                        juegos = juegosString.toString();
                        if (j < juegosArray.length() - 1) {
                            juegosString.append(", ");  // Agrega una coma si hay más juegos
                            juegosString.append("\n");
                        }
                    }
                    // Imprimir los films
                    System.out.println("Pelis:" + peliculas);
                    // Imprimir los series
                    System.out.println("series:" + series);
                    // Imprimir los juegos
                    System.out.println("juegos:" + juegos);
                    System.err.println();
                    encontrado = true;
                    break; // Sale del bucle si encuentra el nombre
                }
            }

            if (!encontrado) {
                System.out.println("Nombre no encontrado: " + nombreBuscado);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}
