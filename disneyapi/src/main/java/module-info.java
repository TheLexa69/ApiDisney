module disneyapi {
    requires javafx.controls;
    requires javafx.graphics;
    requires transitive javafx.fxml;
    requires json.simple;
    requires org.json;
    requires org.jdom2;
    

    opens disneyapi to javafx.fxml;
    exports disneyapi;
}
