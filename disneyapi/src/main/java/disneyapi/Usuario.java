package disneyapi;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usuario {
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
                    System.out.println("Nombre encontrado: " + name);
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
