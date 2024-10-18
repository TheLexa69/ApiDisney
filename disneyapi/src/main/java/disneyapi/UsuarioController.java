package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UsuarioController {

    @FXML
    private Button btnUsuarioBuscar;

    @FXML
    private Button btnUsuarioSalir;

    @FXML
    private ComboBox<String> cmboxUsuario;

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
    private Label lblUsuarioPanel;

    @FXML
    private Label lblVideojuegos;

    @FXML
    private Label lblVideojuegosOutput;

    @FXML
    private TextField txtIntroducirPersonaje;

    @FXML
    private AnchorPane usuarioPanel;


    @FXML
    void onBtnClickBuscarUsuario(ActionEvent event) {
        // Obtener el valor seleccionado del ComboBox
        String seleccion = cmboxUsuario.getValue();
        if (seleccion.equals("Personaje")) {
            // Lógica específica para "Personaje"
            System.out.println("Has seleccionado un Personaje.");
        } else if (seleccion.equals("Pelicula")) {
            // Lógica específica para "Pelicula"
            System.out.println("Has seleccionado una Película.");
        }else{
            System.out.println("Vacio");
        }
    }

    @FXML
    void onBtnClickSalirUsuario(ActionEvent event) {

    }

    @FXML
    void initialize() {
        // Añadir elementos al ComboBox en el método de inicialización
        cmboxUsuario.getItems().addAll("Personaje", "Pelicula");
        cmboxUsuario.setValue("Personaje");

    }

}
