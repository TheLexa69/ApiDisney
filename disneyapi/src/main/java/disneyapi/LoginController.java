package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    void onBtnClickLoginScene(ActionEvent event) {

    }

}
