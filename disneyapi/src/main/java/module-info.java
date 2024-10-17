module disneyapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.json;

    opens disneyapi to javafx.fxml;
    exports disneyapi;
}
