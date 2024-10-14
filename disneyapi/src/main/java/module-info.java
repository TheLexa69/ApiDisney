module disneyapi {
    requires javafx.controls;
    requires javafx.fxml;

    opens disneyapi to javafx.fxml;
    exports disneyapi;
}
