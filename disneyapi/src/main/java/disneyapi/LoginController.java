package disneyapi;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btnLoginScene;

    @FXML
    private ImageView imgLoginScene;

    @FXML
    private TextField txtContraScene;

    @FXML
    private TextField txtUsuarioScene;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/images/disney.png"));
        imgLoginScene.setImage(image);
        imgLoginScene.setPreserveRatio(true);

    }

    @FXML
    void onBtnClickLoginScene(ActionEvent event) throws Exception {
        //LOS DATOS SE SACAN DE LAS CAJAS DE TEXTO PERO FALTA HACER LAS SENTENCIAS DE COMPROBACIÓN
        String usuario = txtUsuarioScene.getText();
        String contra = txtContraScene.getText();

        System.out.println("Usuario: " + usuario + ", Contraseña: " + contra);
        String rol = "administrador";

        String fxmlFile = rol.equals("administrador") ? "adminscene.fxml" : "usuarioscene.fxml";
        String title = rol.equals("administrador") ? "Panel Administrador" : "Panel Usuario";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            System.out.println("Cargando FXML desde: " + getClass().getResource("./resources/disneyapi/usuarioscene.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
