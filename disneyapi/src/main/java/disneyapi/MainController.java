package disneyapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

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

    @FXML
    void initialize() {
        TitledPane2.setText("Hola");
    }

}
